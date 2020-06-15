package com.christophedurand.mareu.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.christophedurand.mareu.DI.DI;
import com.christophedurand.mareu.Model.Meeting;
import com.christophedurand.mareu.R;
import com.christophedurand.mareu.Service.MeetingApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ListMeetingActivity extends AppCompatActivity implements ListMeetingsInterface {
    //-- PROPERTIES
    private MeetingApiService mApiService;
    private List<Meeting> mMeetings;
    // UI
    @BindView(R.id.recycler_view_meetings)
    RecyclerView mRecyclerView;
    private MyMeetingRecyclerViewAdapter myMeetingRecyclerViewAdapter;
    // CALENDAR
    private final Calendar myCalendar = Calendar.getInstance();
    // DATE
    private DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        mMeetings = mApiService.getMeetings();
        initList();

        List<Meeting> filteredMeetings = new ArrayList<>();
        mApiService.filterMeetingsByDate(mMeetings, filteredMeetings, myCalendar);

        mMeetings = filteredMeetings;
        initList();
        Collections.sort(mMeetings, (lhs, rhs) -> lhs.getDate().compareTo(rhs.getDate()));
    };

    //-- VIEW LIFE CYCLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // INIT INSTANCE OF API SERVICE
        mApiService = DI.getMeetingApiService();
        // INIT LIST MEETINGS
        mMeetings = mApiService.getMeetings();
        // SET CONTENT VIEW WITH LAYOUT
        setContentView(R.layout.activity_main);
        // BIND BUTTER KNIFE DEPENDENCY
        ButterKnife.bind(this);
        // SET RECYCLER VIEW LAYOUT MANAGER
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // SET DIVIDER ITEM DECORATION
        DividerItemDecoration myDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(myDivider);
        // SET ADAPTER
        myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(mMeetings, this);
        mRecyclerView.setAdapter(myMeetingRecyclerViewAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    //-- ON CLICK
    @OnClick(R.id.add_meeting)
    void addMeeting() {
        AddMeetingActivity.navigate(this);
    }

    @OnClick(R.id.filter_image_button)
    void showFilterRadioButtonDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.radiobutton_filter_dialog);
        dialog.setCancelable(true);

        RadioGroup radioGroup = dialog.findViewById(R.id.radio_group_filters);
        RadioButton radioButtonDate = dialog.findViewById(R.id.radiobutton_date);
        RadioButton radioButtonPlace = dialog.findViewById(R.id.radiobutton_place);
        RadioButton radioButtonWithoutFilter = dialog.findViewById(R.id.radiobutton_without_filter);
        Button buttonOk = dialog.findViewById(R.id.button_ok);

        dialog.show();

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            filterListButtonIsTapped(radioButtonDate, radioButtonPlace, radioButtonWithoutFilter);
            dialog.dismiss();
        });

        buttonOk.setOnClickListener(v -> dialog.dismiss());
    }

    //-- METHODS
    /**
     * Action of delete meeting button
     */
    @Override
    public void onDeleteMeeting(Meeting meeting) {
        mMeetings = mApiService.getMeetings();
        mMeetings.remove(meeting);
        initList();
    }

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
    private void filterListButtonIsTapped(RadioButton dateRadioButton, RadioButton placeRadioButton,
                                          RadioButton withoutFilterRadioButton) {
        if (dateRadioButton.isChecked() ) {
            placeRadioButton.setChecked(false);
            withoutFilterRadioButton.setChecked(false);
            showDatePickerDialog();

        } else if (placeRadioButton.isChecked() ) {
            dateRadioButton.setChecked(false);
            withoutFilterRadioButton.setChecked(false);
            setupPlaceRadioGroupDialog();

        } else if (withoutFilterRadioButton.isChecked()) {
            placeRadioButton.setChecked(false);
            dateRadioButton.setChecked(false);

            mMeetings = mApiService.getMeetings();
            initList();
        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void setupPlaceRadioGroupDialog() {
        Dialog dialog = new Dialog(this);
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
            mApiService.filterMeetingsByPlace(mMeetings, filteredMeetings, titlePlace);

            mMeetings = filteredMeetings;
            initList();
            Collections.sort(mMeetings, (lhs, rhs) -> lhs.getDate().compareTo(rhs.getDate()));
        });

        buttonOk.setOnClickListener(v -> dialog.dismiss());
    }

}
