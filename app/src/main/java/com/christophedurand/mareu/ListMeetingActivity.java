package com.christophedurand.mareu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ListMeetingActivity extends AppCompatActivity {
    //-- PROPERTIES
    MeetingApiService mApiService;
    MeetingFragment meetingFragment;
    List<Meeting> listMeetings = new ArrayList<>();

    //-- VIEW LIFE CYCLE
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

    @OnClick(R.id.filter_image_button)
    void showFilterRadioButtonDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.radiobutton_dialog);
        dialog.setTitle("Filtrer les réunions");
        dialog.setCancelable(true);
// there are a lot of settings, for dialog, check them all out!
// set up radiobutton
        RadioButton rdA = (RadioButton) dialog.findViewById(R.id.rd_a);
        RadioButton rdB = (RadioButton) dialog.findViewById(R.id.rd_b);

// now that the dialog is set up, it's time to show it
        dialog.show();
    }

    //-- METHODS
    public void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_meeting, fragment).commit();
    }

}
