package com.avpc.model.dto;

import com.avpc.model.Member;

import java.util.List;

/**
 * Created by thiago on 03/07/17.
 */

public class MessageDTO {

    private Long id;
    private String message;
    private List<Member> destinationMembers;
    private Member sendMember;
    private Long date;

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

    public List<Member> getDestinationMembers() {
        return destinationMembers;
    }

    public void setDestinationMembers(List<Member> destinationMembers) {
        this.destinationMembers = destinationMembers;
    }

    public Member getSendMember() {
        return sendMember;
    }

    public void setSendMember(Member sendMember) {
        this.sendMember = sendMember;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
