package com.christophedurand.mareu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model object representing a Reunion
 */
public class Meeting implements Serializable {
    //-- PROPERTIES
    /** Date of the reunion */
    private String date;

    /** Time of the reunion */
    private String time;

    /** Place where the reunion will take place */
    private String place;

    /** Topic of the reunion */
    private String topic;

    /** People who will attend to the reunion */
    private List<String> participants;

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
    public Meeting(String date, String time, String place, String topic,
                     List<String> participants, Integer avatar) {
        this.date = date;
        this.time = time;
        this.place = place;
        this.topic = topic;
        this.participants = participants;
        this.avatar = avatar;
    }

    //-- GETTER & SETTER
    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

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

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
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
        myAvatarList.add(R.drawable.circle_blue_64dp);
        myAvatarList.add(R.drawable.circle_darkblue_64dp);
        myAvatarList.add(R.drawable.circle_green_64dp);
        myAvatarList.add(R.drawable.circle_orange_64dp);
        myAvatarList.add(R.drawable.circle_pink_64dp);
        myAvatarList.add(R.drawable.circle_red_64dp);

        return myAvatarList;
    }

    public static int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }

}
