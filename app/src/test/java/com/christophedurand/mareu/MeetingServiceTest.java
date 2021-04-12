package com.christophedurand.mareu;

import android.graphics.Color;

import com.christophedurand.mareu.model.Meeting;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;


/**
 * Unit test on Meeting service
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {
/*
    private MeetingApiService service;
    private List<Meeting> meetings = new ArrayList<>();
    private Calendar myCalendar;


    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
        meetings = service.getMeetings();
        myCalendar = Calendar.getInstance();
    }

    private String setDate(int day, int month, int year, int hour, int minute) {
        myCalendar.set(Calendar.DAY_OF_MONTH, day);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.HOUR_OF_DAY, hour);
        myCalendar.set(Calendar.MINUTE, minute);

        String date = myCalendar.getTime();

        return date;
    }


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
        Meeting meeting = new Meeting(setDate(29, 05, 2020, 12,01),
                "Salle Bravo", "Réunion projet X",
                "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", Color.RED);

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
        Meeting meeting = new Meeting(setDate(29,05,2020,13,01),
                "Salle Bravo", "Réunion projet X",
                "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", Color.BLUE);

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
    public void meetingsIsFilteredByDateWithSuccess() {
        // INIT 4 INSTANCES OF MEETING INTO MEETINGS LIST
        meetings = Arrays.asList(
                new Meeting(setDate(03,06,2020,13,00),
                        "Salle Alpha", "Réunion projet X",
                        "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", Color.GREEN),
                new Meeting(setDate(04,06,2020,14,00),
                        "Salle Bravo", "Réunion projet X",
                "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", Color.YELLOW),
                new Meeting(setDate(03,06,2020,15,00),
                        "Salle Charlie", "Réunion projet X",
                "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", Color.MAGENTA),
                new Meeting(setDate(05,7,2020,12,00),
                        "Salle Delta", "Réunion projet X",
                "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", Color.BLACK)
        );

        // INIT FILTERED MEETINGS ARRAY LIST
        List<Meeting> filteredMeetings = new ArrayList<>();

        for (int i = 0; i<meetings.size(); i++) {
            Meeting meetingFiltered = meetings.get(i);
            Date meetingFilteredDate = meetingFiltered.getDate();
            String myCalendarFilteredDate = setDate(03, 06, 2020, 00, 00);

            String myFormat = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

            String meetingFilteredDateString = sdf.format(meetingFilteredDate);
            String myCalendarFilteredDateString = sdf.format(myCalendarFilteredDate);

            // ADD INTO FILTERED MEETINGS IF IT'S MATCHING
            if (meetingFilteredDateString.equals(myCalendarFilteredDateString) ) {
                filteredMeetings.add(meetingFiltered);
            }
        }

        // INIT MEETINGS WITH FILTERED MEETINGS
        meetings = filteredMeetings;

        // EXPECTED MEETINGS IS SORTED BY HOUR
        Collections.sort(filteredMeetings, Comparator.comparing(Meeting::getDate));

        assertEquals(2, filteredMeetings.size());
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(filteredMeetings.toArray() ) );
    }

    // UNIT TESTING MEETINGS ARRAY LIST IS SORTED BY PLACE
    @Test
    public void meetingsIsFilteredByPlaceWithSuccess() {
        // INIT 4 INSTANCES OF MEETING INTO MEETINGS LIST
        meetings = Arrays.asList(
                new Meeting(setDate(29,05,2020,13,01),
                        "Salle Delta", "Réunion projet X",
                        "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", Color.GREEN),
                new Meeting(setDate(27,05,2020,14,01),
                        "Salle Beta", "Réunion projet X",
                        "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", Color.YELLOW),
                new Meeting(setDate(29,04,2020,15,01),
                        "Salle Alpha", "Réunion projet X",
                        "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", Color.MAGENTA),
                new Meeting(setDate(29,05,2020,12,01),
                        "Salle Alpha", "Réunion projet X",
                        "abc@lamzone.com, def@lamzone.com, ghi@lamzone.com", Color.BLACK)
        );

        // INIT FILTERED MEETINGS ARRAY LIST
        List<Meeting> filteredMeetings = new ArrayList<>();

        // INIT FILTER BY PLACE STRING
        String titlePlace = "Salle Alpha";

        for (int i = 0; i<meetings.size(); i++) {
            Meeting meetingFiltered = meetings.get(i);
            String meetingPlace = meetingFiltered.getPlace();

            // ADD INTO FILTERED MEETINGS IF IT'S MATCHING
            if (meetingPlace.equals(titlePlace) ) {
                filteredMeetings.add(meetingFiltered);
            }
        }

        // INIT MEETINGS WITH FILTERED MEETINGS
        meetings = filteredMeetings;

        // EXPECTED MEETINGS IS SORTED BY HOUR
        Collections.sort(meetings, Comparator.comparing(Meeting::getDate));

        assertEquals(2, filteredMeetings.size());
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(filteredMeetings.toArray() ) );
    }*/

}