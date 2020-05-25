package com.christophedurand.mareu;

import android.os.Bundle;

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

    //-- METHODS
    public void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_meeting, fragment).commit();
    }

}
