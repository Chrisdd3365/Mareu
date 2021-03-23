package com.christophedurand.mareu.repository;

import com.christophedurand.mareu.model.Meeting;
import com.christophedurand.mareu.service.MeetingApiService;

import java.util.List;


public class ListMeetingRepository {

    private final MeetingApiService mMeetingApiService;


    public ListMeetingRepository(MeetingApiService mMeetingApiService) {
        this.mMeetingApiService = mMeetingApiService;
    }

    public List<Meeting> getMeetingsList() {
        return mMeetingApiService.getMeetings();
    }

}
