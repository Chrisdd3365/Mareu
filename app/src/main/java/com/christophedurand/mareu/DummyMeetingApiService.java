package com.christophedurand.mareu;

import java.util.List;


/**
 * Dummy mock for the Api
 */
public class DummyMeetingApiService implements MeetingApiService  {
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

}
