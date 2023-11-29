package com.devbramm.soga.models;

public class UserProfile {
    private String uid;
    private String uPhoneNumber;
    private String uDisplayName;
    private String uProfilePhoto;
    private String uProfileAbout;
    private String uProfileStatus;

    public UserProfile() {
    }

    public UserProfile(String uid, String uPhoneNumber, String uDisplayName, String uProfilePhoto, String uProfileAbout, String uProfileStatus) {
        this.uid = uid;
        this.uPhoneNumber = uPhoneNumber;
        this.uDisplayName = uDisplayName;
        this.uProfilePhoto = uProfilePhoto;
        this.uProfileAbout = uProfileAbout;
        this.uProfileStatus = uProfileStatus;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getuPhoneNumber() {
        return uPhoneNumber;
    }

    public void setuPhoneNumber(String uPhoneNumber) {
        this.uPhoneNumber = uPhoneNumber;
    }

    public String getuDisplayName() {
        return uDisplayName;
    }

    public void setuDisplayName(String uDisplayName) {
        this.uDisplayName = uDisplayName;
    }

    public String getuProfilePhoto() {
        return uProfilePhoto;
    }

    public void setuProfilePhoto(String uProfilePhoto) {
        this.uProfilePhoto = uProfilePhoto;
    }

    public String getuProfileAbout() {
        return uProfileAbout;
    }

    public void setuProfileAbout(String uProfileAbout) {
        this.uProfileAbout = uProfileAbout;
    }

    public String getuProfileStatus() {
        return uProfileStatus;
    }

    public void setuProfileStatus(String uProfileStatus) {
        this.uProfileStatus = uProfileStatus;
    }
}
