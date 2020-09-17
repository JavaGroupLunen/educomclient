package com.educom.restclient.model;


import java.time.LocalDate;
import java.util.List;


public class Lehre extends Person{
    private long id;
    private Double stundenLohn;

    private VerfugbarkeitFurLehre verfugbarkeitFurLehre;
    private List<Kurs> kanngeben;

    public Lehre(Double stundenLohn) {
        this.stundenLohn = stundenLohn;
    }

    public Lehre() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Lehre(String text, String text1, String text2) {
        super();
    }


    public Double getStundenLohn() {
        return stundenLohn;
    }

    public void setStundenLohn(Double stundenLohn) {
        this.stundenLohn = stundenLohn;
    }

    public VerfugbarkeitFurLehre getVerfugbarkeitFurLehre() {
        return verfugbarkeitFurLehre;
    }

    public void setVerfugbarkeitFurLehre(VerfugbarkeitFurLehre verfugbarkeitFurLehre) {
        this.verfugbarkeitFurLehre = verfugbarkeitFurLehre;
    }

    public List<Kurs> getKanngeben() {
        return kanngeben;
    }

    public void setKanngeben(List<Kurs> kanngeben) {
        this.kanngeben = kanngeben;
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        super.setPhoneNumber(phoneNumber);
    }

    @Override
    public Gender getGender() {
        return super.getGender();
    }

    @Override
    public void setGender(Gender gender) {
        super.setGender(gender);
    }

    @Override
    public LocalDate getGeburstDatum() {
        return super.getGeburstDatum();
    }

    @Override
    public void setGeburstDatum(LocalDate geburstDatum) {
        super.setGeburstDatum(geburstDatum);
    }

    @Override
    public String getAdresse() {
        return super.getAdresse();
    }

    @Override
    public void setAdresse(String adres) {
        super.setAdresse(adres);
    }

    @Override
    public String getStadt() {
        return super.getStadt();
    }

    @Override
    public void setStadt(String stadt) {
        super.setStadt(stadt);
    }

    @Override
    public String getLand() {
        return super.getLand();
    }

    @Override
    public void setLand(String land) {
        super.setLand(land);
    }

    @Override
    public String getPlz() {
        return super.getPlz();
    }

    @Override
    public void setPlz(String plz) {
        super.setPlz(plz);
    }

    @Override
    public String toString() {
        return  firstName + " " +
                lastName ;

    }
}
