package com.christophedurand.mareu.ui.list;


import java.time.LocalDate;
import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingViewStateItem that = (MeetingViewStateItem) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(avatar, that.avatar) &&
            Objects.equals(participants, that.participants) &&
            Objects.equals(place, that.place) &&
            Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, avatar, participants, place, date);
    }

    @Override
    public String toString() {
        return "MeetingViewStateItem{" +
            "id='" + id + '\'' +
            ", title='" + title + '\'' +
            ", avatar=" + avatar +
            ", participants='" + participants + '\'' +
            ", place='" + place + '\'' +
            ", date=" + date +
            '}';
    }
}
