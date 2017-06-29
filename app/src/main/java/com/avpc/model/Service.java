package com.avpc.model;

import java.util.Date;
import java.util.List;

/**
 * Created by thiago on 09/06/17.
 */

public class Service {

    private Long id;
    private Date startDate;
    private Date finalDate;
    private String comments;
    private String typeOfService;
    private String localization;
    private String serviceDescription;
    private String material;

    private List<Member> membersInService;
}
