package com.christophedurand.mareu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class DummyMeetingGenerator {
    //-- PROPERTIES
    static List<String> participants = new ArrayList<>();

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("efage","feafaeg", "efagaeg", participants)
    );

    //-- METHODS
    static List<Meeting> generateMeetings() { return new ArrayList<>(DUMMY_MEETINGS); }

}
