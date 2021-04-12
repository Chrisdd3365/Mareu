package com.christophedurand.mareu.ui.list;

import android.annotation.SuppressLint;
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

import com.christophedurand.mareu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListMeetingsRecyclerViewAdapter extends RecyclerView.Adapter<ListMeetingsRecyclerViewAdapter.ViewHolder>  {

    private final List<MeetingViewStateItem> mMeetingViewStateItemsList = new ArrayList<>();
    private final ListMeetingsInterface mListener;


    public ListMeetingsRecyclerViewAdapter(ListMeetingsInterface listener) {
        mListener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.item_list_avatar)
        public ImageView mMeetingAvatar;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.item_list_title)
        public TextView mMeetingTitle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.item_list_participants)
        public TextView mMeetingParticipants;
        @SuppressLint("NonConstantResourceId")
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

        final MeetingViewStateItem meetingViewStateItem = mMeetingViewStateItemsList.get(position);

        ShapeDrawable circle = new ShapeDrawable( new OvalShape() );
        circle.setIntrinsicHeight(60);
        circle.setIntrinsicWidth(60);
        circle.setBounds(new Rect(30, 30, 30, 30) );
        circle.getPaint().setColor(meetingViewStateItem.getAvatar());
        holder.mMeetingAvatar.setBackground(circle);

        holder.mMeetingTitle.setText(meetingViewStateItem.getTitle());

        holder.mMeetingParticipants.setText(meetingViewStateItem.getParticipants());

        holder.mDeleteButton.setOnClickListener((View v) -> mListener.onDeleteMeeting(meetingViewStateItem.getId()));
    }


    @Override
    public int getItemCount() {
        return mMeetingViewStateItemsList.size();
    }

    public void setNewData(@NonNull List<MeetingViewStateItem> meetings) {
        mMeetingViewStateItemsList.clear();
        mMeetingViewStateItemsList.addAll(meetings);

        notifyDataSetChanged();
    }
    
}
