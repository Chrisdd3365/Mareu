package com.christophedurand.mareu.ui.list;


import java.util.List;


public class MeetingViewState {

    private final List<MeetingViewStateItem> mMeetingViewStateItemsList;


    public MeetingViewState(List<MeetingViewStateItem> meetingViewStateItemsList) {
        this.mMeetingViewStateItemsList = meetingViewStateItemsList;
    }

    public List<MeetingViewStateItem> getMeetingViewStateItemsList() {
        return mMeetingViewStateItemsList;
    }

}