package com.educom.restclient.model;


import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class StundenPlan {
    private long id;
    private LocalDate datum;
    private Time beginn;
    private Time ende;
    private String raum;

    private Kurs kurs;

    private Lehre lehre;
    private Boolean vertretung;

    private List<StundenPlanDetails> detail;

    public StundenPlan() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Time getBeginn() {
        return beginn;
    }

    public void setBeginn(Time beginn) {
        this.beginn = beginn;
    }

    public Time getEnde() {
        return ende;
    }

    public void setEnde(Time ende) {
        this.ende = ende;
    }

    public String getRaum() {
        return raum;
    }

    public void setRaum(String raum) {
        this.raum = raum;
    }

    public Kurs getKurs() {
        return kurs;
    }

    public void setKurs(Kurs kurs) {
        this.kurs = kurs;
    }

    public Lehre getLehre() {
        return lehre;
    }

    public void setLehre(Lehre lehre) {
        this.lehre = lehre;
    }

    public Boolean getVertretung() {
        return vertretung;
    }

    public void setVertretung(Boolean vertretung) {
        this.vertretung = vertretung;
    }

    public List<StundenPlanDetails> getDetail() {
        return detail;
    }

    public void setDetail(List<StundenPlanDetails> detail) {
        this.detail = detail;
    }
}
