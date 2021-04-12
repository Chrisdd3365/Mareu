package com.christophedurand.mareu.model;

import android.graphics.Color;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model object representing a Meeting
 */
public class Meeting implements Serializable {

    /** id of the meeting */
    private String id;

    /** Date of the meeting */
    private LocalDate date;

    /** Place where the meeting will take place */
    private String place;

    /** Topic of the meeting */
    private String topic;

    /** People who will attend to the meeting */
    private String participants;

    /** Avatar of the meeting */
    private Integer avatar;


    /**
     * Constructor
     * @param date
     * @param place
     * @param topic
     * @param participants
     * @param avatar
     */
    public Meeting(String id, LocalDate date, String place, String topic,
                   String participants, Integer avatar) {
        this.id = id;
        this.date = date;
        this.place = place;
        this.topic = topic;
        this.participants = participants;
        this.avatar = avatar;
    }


    public String getId() { return id; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }


    public static List<Integer> setupAvatarsArrayList() {
        List<Integer> myAvatarList = new ArrayList<>();
        myAvatarList.add(Color.BLUE);
        myAvatarList.add(Color.GREEN);
        myAvatarList.add(Color.YELLOW);
        myAvatarList.add(Color.MAGENTA);
        myAvatarList.add(Color.BLACK);
        myAvatarList.add(Color.RED);
        myAvatarList.add(Color.CYAN);

        return myAvatarList;
    }

    public static int getRandomNumber(int min, int max) {
        // RETURN A RANDOM NUMBER BETWEEN MIN AND MAX
        return (new Random()).nextInt((max - min) + 1) + min;
    }

}
