package com.devbramm.soga.models;

public class ContactItemList {

    private String ContactName;
    private String ContactPhone;
    private String ContactInSoga;
    private String ContactAbout;

    public ContactItemList() {
    }

    public ContactItemList(String contactName, String contactPhone, String contactInSoga, String contactAbout) {
        ContactName = contactName;
        ContactPhone = contactPhone;
        ContactInSoga = contactInSoga;
        ContactAbout = contactAbout;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactPhone() {
        return ContactPhone;
    }

    public void setContactPhone(String contactPhone) {
        ContactPhone = contactPhone;
    }

    public String getContactInSoga() {
        return ContactInSoga;
    }

    public void setContactInSoga(String contactInSoga) {
        ContactInSoga = contactInSoga;
    }

    public String getContactAbout() {
        return ContactAbout;
    }

    public void setContactAbout(String contactAbout) {
        ContactAbout = contactAbout;
    }
}
