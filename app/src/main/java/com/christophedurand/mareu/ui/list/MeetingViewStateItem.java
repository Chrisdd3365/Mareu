package com.christophedurand.mareu.ui.list;


import java.time.LocalDate;


public class MeetingViewStateItem {

    private final String id;
    private final String title;
    private final Integer avatar;
    private final String participants;
    private final String place;
    private final LocalDate date;


    public MeetingViewStateItem(String id, String title, Integer avatar, String participants, String place, LocalDate date) {
        this.id = id;
        this.title = title;
        this.avatar = avatar;
        this.participants = participants;
        this.place = place;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public String getParticipants() {
        return participants;
    }

    public String getPlace() {
        return place;
    }

    public LocalDate getDate() {
        return date;
    }
}
