package com.educom.restclient.model;

public class Stundenplanauswahl {
    private String vorname;
    private String nachname;
    private String klasse;
    private String schulename;
    private Boolean auswahl;
    private String kurs;

    public Stundenplanauswahl(String vorname, String nachname, String klasse, String schulename, Boolean auswahl, String kurs) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.klasse = klasse;
        this.schulename = schulename;
        this.auswahl = auswahl;
        this.kurs = kurs;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getKlasse() {
        return klasse;
    }

    public void setKlasse(String klasse) {
        this.klasse = klasse;
    }

    public String getSchulename() {
        return schulename;
    }

    public void setSchulename(String schulename) {
        this.schulename = schulename;
    }

    public Boolean getAuswahl() {
        return auswahl;
    }

    public void setAuswahl(Boolean auswahl) {
        this.auswahl = auswahl;
    }

    public String getKurs() {
        return kurs;
    }

    public void setKurs(String kurs) {
        this.kurs = kurs;
    }
}
