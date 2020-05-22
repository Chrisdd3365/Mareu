package com.christophedurand.mareu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddMeetingActivity extends AppCompatActivity {
    //-- PROPERTIES
    // UI
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.topicLyt)
    TextInputLayout topicInput;
    @BindView(R.id.timeLyt)
    TextInputLayout timeInput;
    @BindView(R.id.placeLyt)
    TextInputLayout placeInput;
    @BindView(R.id.participantsLyt)
    TextInputLayout participantsInput;
    @BindView(R.id.create)
    MaterialButton createMeetingButton;

    private MeetingApiService mApiService;
    private List<String> mMeetingParticipants = new ArrayList<>();
    //private String mNeighbourImage;

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
        //mNeighbourImage = randomImage();
        //Glide.with(this).load(mNeighbourImage).placeholder(R.drawable.ic_account)
                //.apply(RequestOptions.circleCropTransform()).into(avatar);
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

    @OnClick(R.id.create)
    void createMeeting() {
        mMeetingParticipants.add(participantsInput.getEditText().getText().toString());

        Meeting meeting = new Meeting(
                topicInput.getEditText().getText().toString(),
                timeInput.getEditText().getText().toString(),
                placeInput.getEditText().getText().toString(),
                mMeetingParticipants
        );

        mApiService.createMeeting(meeting);

        finish();
    }

    /**
     * Generate a random image. Useful to mock image picker
     * @return String
     */
    String randomImage() {
        return "https://i.pravatar.cc/150?u="+ System.currentTimeMillis();
    }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

}
