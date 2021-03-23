package com.christophedurand.mareu.ui;

import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.christophedurand.mareu.model.Meeting;
import com.christophedurand.mareu.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder>  {
    //-- PROPERTIES
    private final List<Meeting> mMeetings = new ArrayList<>();
    private final ListMeetingsInterface mListener;

    //-- INIT
    public MyMeetingRecyclerViewAdapter(ListMeetingsInterface listener) {
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
        // TODO comme on a dit, pas de Meeting "technique" ici, plutôt un MeetingViewState
        final Meeting meeting = mMeetings.get(position);
        String topic = meeting.getTopic();
        String place = meeting.getPlace();

        // TODO à mettre dans le VM!
        String myFormat = "dd/MM/yyyy kk:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        String date = sdf.format(meeting.getDate());

        // SETUP UI
        ShapeDrawable circle = new ShapeDrawable( new OvalShape() );
        circle.setIntrinsicHeight(60);
        circle.setIntrinsicWidth(60);
        circle.setBounds(new Rect(30, 30, 30, 30) );
        circle.getPaint().setColor(meeting.getAvatar() );
        holder.mMeetingAvatar.setBackground(circle);

        // TODO à mettre dans le VM!
        holder.mMeetingTitle.setText(topic + " - " + date + " - " + place);

        holder.mMeetingParticipants.setText(meeting.getParticipants());

        // ON CLICK
        holder.mDeleteButton.setOnClickListener((View v) -> {
            mListener.onDeleteMeeting(meeting);
        });
    }

    //--  METHODS
    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public void setNewData(@NonNull List<Meeting> meetings) {
        mMeetings.clear();
        mMeetings.addAll(meetings);

        notifyDataSetChanged();
    }
    
}
