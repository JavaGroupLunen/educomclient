package com.educom.restclient.model;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;



public abstract class Person {

    protected  String firstName;
    protected  String lastName ;
    protected  String email;
    protected String phoneNumber;
    protected Gender gender;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    protected LocalDate geburstDatum;
    protected String adresse;
    protected String stadt;
    protected String land;
    protected String plz;




    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getGeburstDatum() {
        return geburstDatum;
    }

    public void setGeburstDatum(LocalDate geburstDatum) {
        this.geburstDatum = geburstDatum;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adres) {
        this.adresse = adres;
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

}
