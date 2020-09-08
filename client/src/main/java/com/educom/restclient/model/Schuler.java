package com.educom.restclient.model;


import java.util.HashSet;
import java.util.Set;



public class Schuler extends Person{

    private long id;
    private String vater;
    private String mutter;
    private String klasse;

    private Schule schule;

    private Set<Kurs> kurses = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return
                ", vorname='" + firstName + '\'' +
                ", nachname='" + lastName + '\'' +
                ", vater='" + vater + '\'' +
                ", mutter='" + mutter + '\'' +
                ", schule=" + schule +
                ", kurses=" + kurses +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                ", geburstDatum=" + geburstDatum +
                ", adres='" + adresse + '\'' +
                ", stadt='" + stadt + '\'' +
                ", land='" + land + '\'' +
                ", plz='" + plz + '\''
                ;
    }
}
