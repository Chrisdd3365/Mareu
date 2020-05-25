package com.christophedurand.mareu;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * Model object representing a Reunion
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
    private List<String> participants;

    /**
     * Constructor
     * @param date
     * @param place
     * @param topic
     * @param participants
     */
    public Meeting(Date date, String place, String topic,
                     List<String> participants) {
        this.date = date;
        this.place = place;
        this.topic = topic;
        this.participants = participants;
    }

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

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

}
