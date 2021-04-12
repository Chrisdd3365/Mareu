package com.christophedurand.mareu.ui.list;


import java.util.List;
import java.util.Objects;


public class MeetingViewState {

    private final List<MeetingViewStateItem> mMeetingViewStateItemsList;


    public MeetingViewState(List<MeetingViewStateItem> meetingViewStateItemsList) {
        this.mMeetingViewStateItemsList = meetingViewStateItemsList;
    }

    public List<MeetingViewStateItem> getMeetingViewStateItemsList() {
        return mMeetingViewStateItemsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingViewState that = (MeetingViewState) o;
        return Objects.equals(mMeetingViewStateItemsList, that.mMeetingViewStateItemsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mMeetingViewStateItemsList);
    }

    @Override
    public String toString() {
        return "MeetingViewState{" +
            "mMeetingViewStateItemsList=" + mMeetingViewStateItemsList +
            '}';
    }
}