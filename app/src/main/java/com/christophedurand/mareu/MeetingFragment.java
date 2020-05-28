package com.christophedurand.mareu;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MeetingFragment extends Fragment implements ListMeetingsInterface {
    //-- PROPERTIES
    private List<Meeting> mMeetings;
    private RecyclerView mRecyclerView;
    private MyMeetingRecyclerViewAdapter myMeetingRecyclerViewAdapter;
    private Context context;

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
    public void updateList(List<Meeting> meetings, RadioButton dateRadioButton, RadioButton placeRadioButton) {
        mMeetings = meetings;

        if (dateRadioButton.isChecked() ) {
            Collections.sort(mMeetings, Comparator.comparing(Meeting::getDate));
            placeRadioButton.setChecked(false);
        }
        else if (placeRadioButton.isChecked() ) {
            Collections.sort(mMeetings, Comparator.comparing(Meeting::getPlace));
            dateRadioButton.setChecked(false);
        }

        myMeetingRecyclerViewAdapter.notifyDataSetChanged();
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
