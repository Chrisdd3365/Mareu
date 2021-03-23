package com.christophedurand.mareu.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.christophedurand.mareu.R;
import com.christophedurand.mareu.model.Meeting;
import com.christophedurand.mareu.viewmodel.ListMeetingViewModel;
import com.christophedurand.mareu.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ListMeetingActivity extends AppCompatActivity implements ListMeetingsInterface {
    //-- PROPERTIES
    //private MeetingApiService mApiService;
    //private List<Meeting> mMeetings;
    // TODO : on peut le laisser en variable locale dans onCreate();
    private ListMeetingViewModel mListMeetingViewModel;

    // UI
    @BindView(R.id.recycler_view_meetings)
    RecyclerView mRecyclerView;
    // DATE
    private DatePickerDialog.OnDateSetListener dateSelectedListener ;

    //-- VIEW LIFE CYCLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // INIT INSTANCE OF API SERVICE
//        mApiService = DI.getMeetingApiService();
//        // INIT LIST MEETINGS
//        mMeetings = mApiService.getMeetings();

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
        MyMeetingRecyclerViewAdapter myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(myMeetingRecyclerViewAdapter);

        ViewModelFactory viewModelFactory = ViewModelFactory.getInstance();
        mListMeetingViewModel = new ViewModelProvider(this, viewModelFactory).get(ListMeetingViewModel.class);
        mListMeetingViewModel.getMeetingsListLiveData().observe(this, meetingsList ->
            myMeetingRecyclerViewAdapter.setNewData(meetingsList)
        );

        dateSelectedListener = (view, year, monthOfYear, dayOfMonth) -> {
            Calendar.getInstance().set(Calendar.YEAR, year);
            Calendar.getInstance().set(Calendar.MONTH, monthOfYear);
            Calendar.getInstance().set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // TODO : dispatcher l'info au VM qui va trier pour nous
            // List<Meeting> filteredMeetings = new ArrayList<>();
            // mApiService.filterMeetingsByDate(mMeetings, filteredMeetings, Calendar.getInstance());
            // Collections.sort(mMeetings, (lhs, rhs) -> lhs.getDate().compareTo(rhs.getDate()));
        };
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
        // TODO : dispatcher l'info au VM qui va parler au repo
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

            // TODO : dispatcher l'info au VM qui va trier (ou non !) pour nous
        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, dateSelectedListener,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
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

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButtonRoom = dialog.findViewById(checkedId);

//            int tag = Integer.parseInt((String) (radioButtonRoom.getTag() ) );
//
//            String titlePlace = getResources().getStringArray(R.array.places_titles)[tag];
//
//            List<Meeting> filteredMeetings = new ArrayList<>();
//            mApiService.filterMeetingsByPlace(mMeetings, filteredMeetings, titlePlace);
//            // TODO : dispatcher l'info au VM qui va trier pour nous
//            Collections.sort(mMeetings, (lhs, rhs) -> lhs.getDate().compareTo(rhs.getDate()));
        });

        buttonOk.setOnClickListener(v -> dialog.dismiss());
    }

}
