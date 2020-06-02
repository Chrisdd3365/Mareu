package com.christophedurand.mareu;

import com.christophedurand.mareu.DI.DI;
import com.christophedurand.mareu.Model.Meeting;
import com.christophedurand.mareu.Service.DummyMeetingGenerator;
import com.christophedurand.mareu.Service.MeetingApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Unit test on Meeting service
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {
    //-- PROPERTIES
    private MeetingApiService service;
    private List<Meeting> meetings = new ArrayList<>();

    //-- SETUP
    // INIT MEETING API SERVICE INSTANCE
    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
        meetings = service.getMeetings();
    }

    private Date setDate(int day, int month, int year, int hour, int minute) {
        // GET INSTANCE OF CALENDAR
        final Calendar myCalendar = Calendar.getInstance();

        // SET A DATE WITH CALENDAR INSTANCE
        myCalendar.set(Calendar.DAY_OF_MONTH, day);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.HOUR_OF_DAY, hour);
        myCalendar.set(Calendar.MINUTE, minute);

        // INIT DATE
        Date date = myCalendar.getTime();

        return date;
    }

    //-- UNIT TESTS METHODS
    // UNIT TESTING INIT LIST OF MEETINGS
    @Test
    public void getMeetingsWithSuccess() {
        // INIT EXPECTED MEETINGS ARRAY LIST
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;

        // COMPARE IF MEETINGS IS EQUAL TO EXPECTED MEETINGS
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray() ) );
    }

    // UNIT TESTING ADDING MEETING INTO LIST OF MEETINGS
    @Test
    public void addMeetingWithSuccess() {
        // INIT MEETING
        Meeting meeting = new Meeting(setDate(29, 05, 2020, 12,01), "Salle Bravo", "Réunion projet X",
                "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", R.drawable.circle_red_64dp);

        // ADD MEETING INTO MEETINGS
        meetings.add(meeting);

        // INIT MEETING TO ADD INTO MEETINGS
        Meeting meetingToAdd = service.getMeetings().get(0);

        // CHECK STATEMENT THAT "MEETINGS CONTAINS MEETING" IS TRUE
        assertTrue(service.getMeetings().contains(meetingToAdd) );
    }

    // UNIT TESTING DELETING MEETING FROM LIST OF MEETINGS
    @Test
    public void deleteMeetingWithSuccess() {
        // INIT MEETING
        Meeting meeting = new Meeting(setDate(29,05,2020,13,01), "Salle Bravo", "Réunion projet X",
                "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", R.drawable.circle_red_64dp);

        // ADD MEETING INTO MEETINGS
        meetings.add(meeting);

        // INIT MEETING TO DELETE FROM MEETINGS
        Meeting meetingToDelete = service.getMeetings().get(0);

        // DELETE MEETING FROM MEETINGS
        service.deleteMeeting(meetingToDelete);

        // CHECK STATEMENT THAT "MEETINGS CONTAINS MEETING TO DELETE" IS FALSE
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    // UNIT TESTING MEETINGS ARRAY LIST IS SORTED BY DATE
    @Test
    public void meetingsIsSortedByDateWithSuccess() {
        // INIT 4 INSTANCES OF MEETING INTO MEETINGS LIST
        meetings = Arrays.asList(
                new Meeting(setDate(29,05,2020,13,01), "Salle Alpha", "Réunion projet X",
                        "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", R.drawable.circle_orange_64dp),
                new Meeting(setDate(27,05,2020,14,01), "Salle Bravo", "Réunion projet X",
                "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", R.drawable.circle_green_64dp),
                new Meeting(setDate(29,04,2020,15,01), "Salle Charlie", "Réunion projet X",
                "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", R.drawable.circle_red_64dp),
                new Meeting(setDate(29,05,2020,12,01), "Salle Delta", "Réunion projet X",
                "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", R.drawable.circle_blue_64dp)
        );

        // INIT EXPECTED MEETINGS ARRAY LIST
        List<Meeting> expectedMeetings = new ArrayList<>(meetings);

        // EXPECTED MEETINGS IS SORTED BY DATE
        Collections.sort(expectedMeetings, Comparator.comparing(Meeting::getDate));

        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    // UNIT TESTING MEETINGS ARRAY LIST IS SORTED BY PLACE
    @Test
    public void meetingsIsSortedByPlaceWithSuccess() {
        // INIT 4 INSTANCES OF MEETING INTO MEETINGS LIST
        meetings = Arrays.asList(
                new Meeting(setDate(29,05,2020,13,01), "Salle Delta", "Réunion projet X",
                        "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", R.drawable.circle_orange_64dp),
                new Meeting(setDate(27,05,2020,14,01), "Salle Charlie", "Réunion projet X",
                        "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", R.drawable.circle_green_64dp),
                new Meeting(setDate(29,04,2020,15,01), "Salle Bravo", "Réunion projet X",
                        "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", R.drawable.circle_red_64dp),
                new Meeting(setDate(29,05,2020,12,01), "Salle Alpha", "Réunion projet X",
                        "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", R.drawable.circle_blue_64dp)
        );

        // INIT EXPECTED MEETINGS ARRAY LIST
        List<Meeting> expectedMeetings = new ArrayList<>(meetings);

        // EXPECTED MEETINGS IS SORTED BY PLACE
        Collections.sort(expectedMeetings, Comparator.comparing(Meeting::getPlace));

        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

}