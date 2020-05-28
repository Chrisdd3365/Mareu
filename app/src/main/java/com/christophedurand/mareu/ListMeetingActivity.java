package com.christophedurand.mareu;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ListMeetingActivity extends AppCompatActivity {
    //-- PROPERTIES
    MeetingApiService mApiService;
    MeetingFragment meetingFragment;
    List<Meeting> listMeetings = new ArrayList<>();

    //-- VIEW LIFE CYCLE
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApiService = DI.getMeetingApiService();

        listMeetings = mApiService.getMeetings();

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        meetingFragment = MeetingFragment.newInstance(listMeetings);

        initFragment(meetingFragment);
    }

    //-- ON CLICK
    @OnClick(R.id.add_meeting)
    void addMeeting() {
        AddMeetingActivity.navigate(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.filter_image_button)
    void showFilterRadioButtonDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.radiobutton_dialog);
        dialog.setCancelable(true);

        RadioGroup radioGroup = dialog.findViewById(R.id.radio_group);
        Button buttonOk = dialog.findViewById(R.id.button_ok);

        dialog.show();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButtonDate = dialog.findViewById(R.id.radiobutton_date);
                RadioButton radioButtonPlace = dialog.findViewById(R.id.radiobutton_place);

                meetingFragment.updateList(listMeetings, radioButtonDate, radioButtonPlace);
            }
        });

        buttonOk.setOnClickListener(v -> dialog.dismiss());
    }

    //-- METHODS
    public void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_meeting, fragment).commit();
    }

}
