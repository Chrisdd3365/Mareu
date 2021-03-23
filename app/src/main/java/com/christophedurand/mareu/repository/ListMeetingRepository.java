package com.christophedurand.mareu.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.christophedurand.mareu.model.Meeting;
import com.christophedurand.mareu.service.MeetingApiService;

import java.util.ArrayList;
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
     * @param meeting
     */
    public void deleteMeeting(Meeting meeting) {
        // TODO !
    }

    /**
     * Create a meeting
     * @param meeting
     */
    public void createMeeting(Meeting meeting) {
        // TODO !
    }
}
