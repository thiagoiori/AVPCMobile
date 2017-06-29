package com.avpc.model;

import java.util.Date;
import java.util.List;

/**
 * Created by thiago on 09/06/17.
 */

public class Message {

    private Long id;
    private String message;
    private List<Member> destinationMembers;
    private Member sendMember;
    private Date date;
}
