package com.christophedurand.mareu.Model;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Model object representing a Meeting
 */
public class Meeting implements Serializable {
    //-- PROPERTIES
    /** Date of the reunion */
    private Date date;

    /** Place where the reunion will take place */
    private String place;

    /** Topic of the reunion */
    private String topic;

    /** People who will attend to the reunion */
    private String participants;

    /** Avatar of the reunion */
    private Integer avatar;

    //-- INIT
    /**
     * Constructor
     * @param date
     * @param place
     * @param topic
     * @param participants
     * @param avatar
     */
    public Meeting(Date date, String place, String topic,
                   String participants, Integer avatar) {
        this.date = date;
        this.place = place;
        this.topic = topic;
        this.participants = participants;
        this.avatar = avatar;
    }

    //-- GETTER & SETTER
    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

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

    //-- METHODS
    public static List<Integer> setupAvatarsArrayList() {
        // INIT EMPTY ARRAY LIST
        List<Integer> myAvatarList = new ArrayList<>();
        // ADD ALL AVATARS NEEDED
        myAvatarList.add(Color.BLUE);
        myAvatarList.add(Color.GREEN);
        myAvatarList.add(Color.YELLOW);
        myAvatarList.add(Color.MAGENTA);
        myAvatarList.add(Color.BLACK);
        myAvatarList.add(Color.RED);
        myAvatarList.add(Color.CYAN);
        // RETURN AVATAR LIST FILLED WITH DRAWABLES
        return myAvatarList;
    }

    public static int getRandomNumber(int min, int max) {
        // RETURN A RANDOM NUMBER BETWEEN MIN AND MAX
        return (new Random()).nextInt((max - min) + 1) + min;
    }

}
