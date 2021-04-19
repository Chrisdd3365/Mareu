package com.christophedurand.mareu.ui.list;

import android.annotation.SuppressLint;
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
import com.christophedurand.mareu.ui.add.AddMeetingActivity;
import com.christophedurand.mareu.viewmodel.ViewModelFactory;

import java.time.LocalDate;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ListMeetingActivity extends AppCompatActivity implements ListMeetingsInterface {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recycler_view_meetings)
    RecyclerView mRecyclerView;

    private DatePickerDialog.OnDateSetListener dateSelectedListener;
    private ListMeetingViewModel mListMeetingViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration myDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(myDivider);

        ViewModelFactory viewModelFactory = ViewModelFactory.getInstance();
        mListMeetingViewModel = new ViewModelProvider(this, viewModelFactory).get(ListMeetingViewModel.class);

        ListMeetingsRecyclerViewAdapter listMeetingsRecyclerViewAdapter =
                new ListMeetingsRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(listMeetingsRecyclerViewAdapter);


        mListMeetingViewModel.getMeetingViewStateLiveData().observe(this, meetingViewState ->
            listMeetingsRecyclerViewAdapter.setNewData(meetingViewState.getMeetingViewStateItemsList())
        );


        dateSelectedListener = (view, year, monthOfYear, dayOfMonth) -> {
            LocalDate localDate = LocalDate.of(year, monthOfYear, dayOfMonth);
            mListMeetingViewModel.onFilterMeetingsByDateButtonClicked(localDate);
        };
    }


    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.add_meeting)
    void addMeeting() {
        AddMeetingActivity.navigate(this);
    }

    @SuppressLint("NonConstantResourceId")
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


    /**
     * Action of delete meeting button
     */
    @Override
    public void onDeleteMeeting(String meetingId) {
        mListMeetingViewModel.onDeleteMeetingButtonClicked(meetingId);
    }

    /**
     * Update data in the list of meetings and recycler view adapter
     */
    private void filterListButtonIsTapped(RadioButton dateRadioButton,
                                          RadioButton placeRadioButton,
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
            mListMeetingViewModel.onFilterMeetingsByPlaceButtonClicked(radioButtonRoom.getText().toString());
        });

        buttonOk.setOnClickListener(v -> dialog.dismiss());
    }

}
