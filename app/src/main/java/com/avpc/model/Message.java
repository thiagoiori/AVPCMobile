package com.avpc.model;

import java.util.Date;
import java.util.List;

/**
 * Created by thiago on 09/06/17.
 */

public class Message {

    private Long id;
    private String message;
    private long destinationMember;
    private long sendMember;
    private long date;

    public long getDestinationMember() {
        return destinationMember;
    }

    public void setDestinationMember(long destinationMember) {
        this.destinationMember = destinationMember;
    }

    public long getSendMember() {
        return sendMember;
    }

    public void setSendMember(long sendMember) {
        this.sendMember = sendMember;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
