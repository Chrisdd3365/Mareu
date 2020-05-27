package com.christophedurand.mareu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder>  {
    //-- PROPERTIES
    private final List<Meeting> mMeetings;
    private ListMeetingsInterface mListener;

    //-- CONSTRUCTOR
    public MyMeetingRecyclerViewAdapter(List<Meeting> items, ListMeetingsInterface listener) {
        mMeetings = items;
        mListener = listener;
    }

    //-- VIEW HOLDER
    public class ViewHolder extends RecyclerView.ViewHolder {
        // UI
        @BindView(R.id.item_list_avatar)
        public ImageView mMeetingAvatar;
        @BindView(R.id.item_list_title)
        public TextView mMeetingTitle;
        @BindView(R.id.item_list_participants)
        public TextView mMeetingParticipants;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Meeting meeting = mMeetings.get(position);

        holder.mMeetingAvatar.setImageResource(meeting.getAvatar());

        holder.mMeetingTitle.setText(meeting.getTopic() + " - " + meeting.getDate() + " - " +
                meeting.getTime() + " - " + meeting.getPlace());

        holder.mMeetingParticipants.setText(meeting.getParticipants());

        holder.mDeleteButton.setOnClickListener((View v) -> {
            mListener.onDeleteMeeting(meeting);
        });
    }

    //--  METHODS
    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

}
