package com.educom.restclient.model;



import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;



public class Schuler extends Person{

    private long id;
    private String vater;
    private String mutter;
    private String klasse;

    private  Schule schule;

    private Set<Kurs> kurses = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getKlasse() {
        return klasse;
    }

    public void setKlasse(String klasse) {
        this.klasse = klasse;
    }

    public String getVater() {
  return vater;
 }

 public void setVater(String vater) {
  this.vater = vater;
 }

 public String getMutter() {
  return mutter;
 }

 public void setMutter(String mutter) {
  this.mutter = mutter;
 }

 public Schule getSchule() {
  return schule;
 }

 public void setSchule(Schule schule) {
  this.schule = schule;
 }

 public Set<Kurs> getKurses() {
  return kurses;
 }

 public void setKurses(Set<Kurs> kurses) {
  this.kurses = kurses;
 }

    public Schuler() {
    }



    @Override
    public String toString() {
        return "Schuler{" +
                "vater='" + vater + '\'' +
                ", mutter='" + mutter + '\'' +
                ", schule=" + schule +
                ", kurses=" + kurses +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                ", geburstDatum=" + geburstDatum +
                ", adres='" + adresse + '\'' +
                ", stadt='" + stadt + '\'' +
                ", land='" + land + '\'' +
                ", plz='" + plz + '\'' +
                '}';
    }
}
