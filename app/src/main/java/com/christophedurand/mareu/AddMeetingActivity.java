package com.christophedurand.mareu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMeetingActivity extends AppCompatActivity {
    //-- PROPERTIES
    // UI
    @BindView(R.id.topicLyt)
    TextInputLayout topicInput;
    @BindView(R.id.dateLyt)
    TextInputLayout dateInput;
    @BindView(R.id.date)
    TextInputEditText dateEditText;
    @BindView(R.id.timeLyt)
    TextInputLayout timeInput;
    @BindView(R.id.time)
    TextInputEditText timeEditText;
    @BindView(R.id.placeLyt)
    TextInputLayout placeInput;
    @BindView(R.id.participantsLyt)
    TextInputLayout participantsInput;
    @BindView(R.id.create)
    MaterialButton createMeetingButton;

    private MeetingApiService mApiService;
    private List<String> mMeetingParticipants = new ArrayList<>();

    // TIME AND DATE PROPERTIES
    // CALENDAR
    final Calendar myCalendar = Calendar.getInstance();
    // DATE
    DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDateLabel();
    };
    // TIME
    TimePickerDialog.OnTimeSetListener time = (view, hour, minute) -> {
        myCalendar.set(Calendar.HOUR, hour);
        myCalendar.set(Calendar.MINUTE, minute);
        updateTimeLabel(hour, minute);
    };

    //-- VIEW LIFE CYCLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_meeting);

        ButterKnife.bind(this);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mApiService = DI.getMeetingApiService();

        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        topicInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                createMeetingButton.setEnabled(s.length() > 0);
            }
        });

    }

    //-- ON CLICK
    @OnClick(R.id.date)
    void dateEditTextIsTapped(View view) {
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.time)
    void timeEditTextIsTapped(View view) {
        new TimePickerDialog(this, time, myCalendar.get(Calendar.HOUR),
                myCalendar.get(Calendar.MINUTE), true).show();
    }

    @OnClick(R.id.create)
    void createMeeting() {
        mMeetingParticipants.add(Objects.requireNonNull(participantsInput.getEditText()).getText().toString());

        Meeting meeting = new Meeting(Objects.requireNonNull(dateInput.getEditText().getText().toString()),
                Objects.requireNonNull(topicInput.getEditText()).getText().toString(),
                Objects.requireNonNull(timeInput.getEditText()).getText().toString(),
                Objects.requireNonNull(placeInput.getEditText()).getText().toString(),
                mMeetingParticipants
        );

        mApiService.createMeeting(meeting);

        finish();
    }

    //-- METHODS
    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }


    /**
     * Used to update label into date edit text
     */
    private void updateDateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        dateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTimeLabel(int selectedHour, int selectedMinute) {
        timeEditText.setText(checkDigit(selectedHour) + ":" + checkDigit(selectedMinute));
    }

    /**
     * Check if the current number is equal or lower to 9, then add a 0 before the checked number
     * @param number
     * @return
     */
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}
