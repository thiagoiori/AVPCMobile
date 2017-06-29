package com.avpc.model;

/**
 * Created by Jordi on 29/10/2016.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Member implements Parcelable {

    public Member() {

    }

    private Long id;
    private String dni;
    private String name;
    private String surname1;
    private String surname2;
    private String mobilePhoneNumber;
    private String landPhoneNumber;
    private String email;
    private String address;
    private String city;
    private String postalCode;
    private String userGroup;
    private long registryDate;
    private long deletionDate;
    private long lastAccesDate;
    private long birthDate;
    private double longitude;
    private double latitude;
    private Boolean availability;
    private String photoURL;
    private Integer services;


    protected Member(Parcel in) {
        dni = in.readString();
        name = in.readString();
        surname1 = in.readString();
        surname2 = in.readString();
        mobilePhoneNumber = in.readString();
        landPhoneNumber = in.readString();
        email = in.readString();
        address = in.readString();
        city = in.readString();
        postalCode = in.readString();
        userGroup = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        photoURL = in.readString();
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getLandPhoneNumber() {
        return landPhoneNumber;
    }

    public void setLandPhoneNumber(String landPhoneNumber) {
        this.landPhoneNumber = landPhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public long getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(long registryDate) {
        this.registryDate = registryDate;
    }

    public long getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(long deletionDate) {
        this.deletionDate = deletionDate;
    }

    public long getLastAccesDate() {
        return lastAccesDate;
    }

    public void setLastAccesDate(long lastAccesDate) {
        this.lastAccesDate = lastAccesDate;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Integer getServices() {
        return services;
    }

    public void setServices(Integer services) {
        this.services = services;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dni);
        dest.writeString(name);
        dest.writeString(surname1);
        dest.writeString(surname2);
        dest.writeString(mobilePhoneNumber);
        dest.writeString(landPhoneNumber);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(postalCode);
        dest.writeString(userGroup);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeString(photoURL);
    }
}