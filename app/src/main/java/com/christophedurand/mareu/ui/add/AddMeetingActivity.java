package com.christophedurand.mareu.ui.add;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.christophedurand.mareu.model.Meeting;
import com.christophedurand.mareu.R;
import com.christophedurand.mareu.viewmodel.ViewModelFactory;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.christophedurand.mareu.model.Meeting.getRandomNumber;
import static com.christophedurand.mareu.model.Meeting.setupAvatarsArrayList;


public class AddMeetingActivity extends AppCompatActivity {

    @BindView(R.id.avatar)
    ImageView avatarImageView;
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
    @BindView(R.id.place)
    TextInputEditText placeEditText;
    @BindView(R.id.participantsLyt)
    TextInputLayout participantsInput;
    @BindView(R.id.create)
    MaterialButton createMeetingButton;

    private Integer mMeetingAvatar;

    private final Calendar myCalendar = Calendar.getInstance();

    private final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDateLabel();
    };

    private final TimePickerDialog.OnTimeSetListener time = (view, hour, minute) -> {
        myCalendar.set(Calendar.HOUR_OF_DAY, hour);
        myCalendar.set(Calendar.MINUTE, minute);
        DateFormat.is24HourFormat(AddMeetingActivity.this);
        updateTimeLabel();
    };

    private AddMeetingViewModel mAddMeetingViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);
        init();
        generateMeetingAvatar();

        ViewModelFactory viewModelFactory = ViewModelFactory.getInstance();
        mAddMeetingViewModel = new ViewModelProvider(this, viewModelFactory).get(AddMeetingViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        Objects.requireNonNull(topicInput.getEditText()).addTextChangedListener(new TextWatcher() {
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


    @OnClick(R.id.date)
    void dateEditTextIsTapped() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                R.style.DialogTheme,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @OnClick(R.id.time)
    void timeEditTextIsTapped() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                R.style.DialogTheme,
                time,
                myCalendar.get(Calendar.HOUR),
                myCalendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    @OnClick(R.id.place)
    void placeEditTextIsTapped() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.radiobutton_place_dialog);
        dialog.setCancelable(true);

        RadioGroup radioGroup = dialog.findViewById(R.id.radio_group_places);
        Button buttonOk = dialog.findViewById(R.id.button_ok);

        dialog.show();

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButtonRoom = dialog.findViewById(checkedId);

            int tag = Integer.parseInt((String) (radioButtonRoom.getTag() ) );

            placeEditText.setText(getResources().getStringArray(R.array.places_titles)[tag]);
        });

        buttonOk.setOnClickListener(v -> dialog.dismiss());

    }

    @OnClick(R.id.create)
    void createMeeting() {
        Meeting meeting = new Meeting(
                UUID.randomUUID().toString(),
                LocalDate.now(),
                Objects.requireNonNull(placeInput.getEditText()).getText().toString(),
                Objects.requireNonNull(topicInput.getEditText()).getText().toString(),
                Objects.requireNonNull(participantsInput.getEditText()).getText().toString(),
                mMeetingAvatar
        );
        mAddMeetingViewModel.onCreateMeetingButtonClicked(meeting);

        finish();
    }


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
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        dateEditText.setText(sdf.format(myCalendar.getTime() ) );
    }

    /**
     * Used to update label into time edit text
     */
    private void updateTimeLabel() {
        String myFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        timeEditText.setText(sdf.format(myCalendar.getTime() ) );
    }

    private void generateMeetingAvatar() {
        ShapeDrawable circle = new ShapeDrawable( new OvalShape() );
        circle.setIntrinsicHeight(60);
        circle.setIntrinsicWidth(60);
        circle.setBounds(new Rect(30, 30, 30, 30) );
        mMeetingAvatar = setupAvatarsArrayList().get(getRandomNumber(0,6) );
        circle.getPaint().setColor(mMeetingAvatar);
        avatarImageView.setBackground(circle);
    }

}
