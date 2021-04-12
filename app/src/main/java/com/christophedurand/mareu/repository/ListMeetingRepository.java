package com.christophedurand.mareu.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.christophedurand.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ListMeetingRepository {

    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>();


    public ListMeetingRepository() {
        meetingsLiveData.setValue(new ArrayList<>());
    }


    public LiveData<List<Meeting>> getMeetingsList() {
        return meetingsLiveData;
    }


    /**
     * Deletes a meeting
     * @param meetingId
     */
    public void deleteMeeting(String meetingId) {
        List<Meeting> meetingsList = meetingsLiveData.getValue();
        assert meetingsList != null;
        for (Iterator<Meeting> i = meetingsList.iterator(); i.hasNext();) {
            Meeting meeting = i.next();
            if (meeting.getId().equals(meetingId)) {
                i.remove();
                break;
            }
        }
        meetingsLiveData.setValue(meetingsList);
    }

    /**
     * Create a meeting
     * @param meeting
     */
    public void createMeeting(Meeting meeting) {
        List<Meeting> meetingsList = meetingsLiveData.getValue();
        assert meetingsList != null;
        meetingsList.add(meeting);
        meetingsLiveData.setValue(meetingsList);
    }

}
