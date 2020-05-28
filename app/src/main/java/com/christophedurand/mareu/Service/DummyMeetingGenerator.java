package com.christophedurand.mareu.Service;

import com.christophedurand.mareu.Model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class DummyMeetingGenerator {
    //-- PROPERTIES
    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList();

    //-- METHODS
    static List<Meeting> generateMeetings() { return new ArrayList<>(DUMMY_MEETINGS); }

}
