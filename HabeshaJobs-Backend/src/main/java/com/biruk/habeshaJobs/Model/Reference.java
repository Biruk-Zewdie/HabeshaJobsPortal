package com.biruk.habeshaJobs.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Reference {

    @Column(name="reference_name")
    private String name;
    @Column(name="reference_phone_number")
    private String phoneNumber;
    @Column(name="reference_email_address")
    private String email;
    private String relationship;

    public Reference() {

    }

    public Reference(String name, String phoneNumber, String email, String relationship) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.relationship = relationship;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", relationship='" + relationship + '\'' +
                '}';
    }
}
