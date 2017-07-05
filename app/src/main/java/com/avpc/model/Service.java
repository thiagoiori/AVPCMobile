package com.avpc.model;

import java.util.Date;
import java.util.List;

public class Service {

    private long id;
    private long startDate;
    private long finalDate;
    private String comments;
    private String typeOfService;
    private String localization;
    private String serviceDescription;
    private String material;
    private long inServiceMemberId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(long finalDate) {
        this.finalDate = finalDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public long getInServiceMemberId() {
        return inServiceMemberId;
    }

    public void setInServiceMemberId(long inServiceMemberId) {
        this.inServiceMemberId = inServiceMemberId;
    }
}
