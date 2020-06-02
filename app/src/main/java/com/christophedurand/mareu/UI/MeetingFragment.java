package com.christophedurand.mareu.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.christophedurand.mareu.DI.DI;
import com.christophedurand.mareu.Model.Meeting;
import com.christophedurand.mareu.MyMeetingRecyclerViewAdapter;
import com.christophedurand.mareu.R;
import com.christophedurand.mareu.Service.MeetingApiService;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@RequiresApi(api = Build.VERSION_CODES.N)
public class MeetingFragment extends Fragment implements ListMeetingsInterface {
    //-- PROPERTIES
    private List<Meeting> mMeetings;
    private RecyclerView mRecyclerView;
    private MyMeetingRecyclerViewAdapter myMeetingRecyclerViewAdapter;
    private Context context;
    private MeetingApiService mApiService;
    // CALENDAR
    final Calendar myCalendar = Calendar.getInstance();
    // DATE
    DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        mMeetings = mApiService.getMeetings();
        initList();

        List<Meeting> filteredMeetings = new ArrayList<>();

        for (int i = 0; i<mMeetings.size(); i++) {
            Meeting meetingFiltered = mMeetings.get(i);
            Date meetingFilteredDate = meetingFiltered.getDate();
            Date myCalendarFilteredDate = myCalendar.getTime();

            String myFormat = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

            String meetingFilteredDateString = sdf.format(meetingFilteredDate);
            String myCalendarFilteredDateString = sdf.format(myCalendarFilteredDate);

            if (meetingFilteredDateString.equals(myCalendarFilteredDateString) ) {
                mMeetings = filteredMeetings;
                //TODO: ajouter plusieurs réunions
                mMeetings.add(meetingFiltered);
                initList();
                Collections.sort(mMeetings, Comparator.comparing(Meeting::getDate));
            }
        }
    };

    /**
     * Create and return a new instance
     * @return @{@link MeetingFragment}
     */
    public static MeetingFragment newInstance(List<Meeting> meetings) {
        MeetingFragment fragment = new MeetingFragment();

        Bundle args = new Bundle();
        args.putSerializable("meetings", (Serializable) meetings);
        fragment.setArguments(args);

        return fragment;
    }

    //-- VIEW LIFE CYCLE
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);

        context = view.getContext();

        mApiService = DI.getMeetingApiService();

        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        initList();

        assert getArguments() != null;
        mMeetings = (List<Meeting>) getArguments().getSerializable("meetings");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    //-- METHODS
    /**
     * Init the list of meetings
     */
    private void initList() {
        myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(mMeetings, this);
        mRecyclerView.setAdapter(myMeetingRecyclerViewAdapter);
    }

    /**
     * Update data in the list of meetings and recycler view adapter
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filterListButtonIsTapped(RadioButton dateRadioButton, RadioButton placeRadioButton,
                                         RadioButton withoutFilterRadioButton) {
        if (dateRadioButton.isChecked() ) {
            placeRadioButton.setChecked(false);
            withoutFilterRadioButton.setChecked(false);

            new DatePickerDialog(context, R.style.DialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        } else if (placeRadioButton.isChecked() ) {
            dateRadioButton.setChecked(false);
            withoutFilterRadioButton.setChecked(false);

            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.radiobutton_place_dialog);
            dialog.setCancelable(true);

            RadioGroup radioGroup = dialog.findViewById(R.id.radio_group_places);
            Button buttonOk = dialog.findViewById(R.id.button_ok);

            dialog.show();

            mMeetings = mApiService.getMeetings();
            initList();

            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButtonRoom = dialog.findViewById(checkedId);

                int tag = Integer.parseInt((String) (radioButtonRoom.getTag() ) );

                String titlePlace = getResources().getStringArray(R.array.places_titles)[tag];

                List<Meeting> filteredMeetings = new ArrayList<>();

                for (int i = 0; i<mMeetings.size(); i++) {
                    Meeting meetingFiltered = mMeetings.get(i);
                    String meetingPlace = meetingFiltered.getPlace();

                    if (meetingPlace.equals(titlePlace) ) {
                        mMeetings = filteredMeetings;
                        mMeetings.add(meetingFiltered);
                        initList();
                        Collections.sort(mMeetings, Comparator.comparing(Meeting::getDate));
                    }
                }
            });

            buttonOk.setOnClickListener(v -> dialog.dismiss());

        } else if (withoutFilterRadioButton.isChecked()) {
            placeRadioButton.setChecked(false);
            dateRadioButton.setChecked(false);

            mMeetings = mApiService.getMeetings();
            initList();
        }
    }

    /**
     * Action of delete meeting button
     */
    @Override
    public void onDeleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);
        initList();
    }

}
