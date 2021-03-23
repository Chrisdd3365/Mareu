package com.christophedurand.mareu.service;

import com.christophedurand.mareu.model.Meeting;

import java.util.Calendar;
import java.util.List;


/**
 * Meeting API client
 */
public interface MeetingApiService {
    /**
     * Get all my Meetings
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Deletes a meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a meeting
     * @param meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * Filter meetings by date
     * @param mMeetings
     * @param filteredMeetings
     * @param myCalendar
     */
    void filterMeetingsByDate(List<Meeting> mMeetings, List<Meeting> filteredMeetings, Calendar myCalendar);

    /**
     * Filter meetings by place
     * @param  mMeetings
     * @param filteredMeetings
     * @param titlePlace
     */
    void filterMeetingsByPlace(List<Meeting> mMeetings, List<Meeting> filteredMeetings, String titlePlace);

}
