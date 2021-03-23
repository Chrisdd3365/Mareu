package com.christophedurand.mareu.service;

import com.christophedurand.mareu.model.Meeting;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Dummy mock for the Api
 */
// TODO à supprimer (tout le package "service") une fois que le repo est temriné
public class DummyMeetingApiService implements MeetingApiService {
    //-- PROPERTIES
    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

    //-- METHODS
    /**
     * {@inheritDoc}
     * @return {@link List}
     */
    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * {@inheritDoc}
     * @param meeting
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    /**
     * {@inheritDoc}
     * @param meeting
     */
    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    /**
     * {@inheritDoc}
     * @param mMeetings
     * @param filteredMeetings
     * @param myCalendar
     */
    @Override
    public void filterMeetingsByDate(List<Meeting> mMeetings, List<Meeting> filteredMeetings, Calendar myCalendar) {
        for (int i = 0; i<mMeetings.size(); i++) {
            Meeting meetingFiltered = mMeetings.get(i);
            Date meetingFilteredDate = meetingFiltered.getDate();
            Date myCalendarFilteredDate = myCalendar.getTime();

            String myFormat = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

            String meetingFilteredDateString = sdf.format(meetingFilteredDate);
            String myCalendarFilteredDateString = sdf.format(myCalendarFilteredDate);

            if (meetingFilteredDateString.contains(myCalendarFilteredDateString) ) {
                filteredMeetings.add(meetingFiltered);
            }
        }
    }

    /**
     * {@inheritDoc}
     * @param mMeetings
     * @param filteredMeetings
     * @param titlePlace
     */
    @Override
    public void filterMeetingsByPlace(List<Meeting> mMeetings, List<Meeting> filteredMeetings, String titlePlace) {
        for (int i = 0; i<mMeetings.size(); i++) {
            Meeting meetingFiltered = mMeetings.get(i);
            String meetingPlace = meetingFiltered.getPlace();

            if (meetingPlace.equals(titlePlace) ) {
                filteredMeetings.add(meetingFiltered);
            }
        }
    }

}
