package com.christophedurand.mareu;

import java.io.Serializable;
import java.util.List;


/**
 * Model object representing a Reunion
 */
public class Meeting implements Serializable {
    //-- PROPERTIES
    /** Time of the reunion */
    private String time;

    /** Place where the reunion will take place */
    private String place;

    /** Topic of the reunion */
    private String topic;

    /** People who will attend to the reunion */
    private List<String> participants;

    /**
     * Constructor
     * @param time
     * @param place
     * @param topic
     * @param participants
     */
    public Meeting(String time, String place, String topic,
                     List<String> participants) {
        this.time = time;
        this.place = place;
        this.topic = topic;
        this.participants = participants;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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

}
