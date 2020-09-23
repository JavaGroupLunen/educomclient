package com.educom.restclient.model;


import java.util.Set;


public class StundenPlanDetails {

    private long id;
    private StundenPlan stundenPlan;
    private Schuler schuler;
    private Boolean speateAnfang;
    private Double speateTeilnameMin;

    private Set<Bemerkung> bemerkungen;
    private Boolean anwesend;


    public StundenPlanDetails(StundenPlan stundenPlan, Schuler schuler, Boolean speateAnfang, Double speateTeilnameMin, Boolean anwesend) {
        this.stundenPlan = stundenPlan;
        this.schuler = schuler;
        this.speateAnfang = speateAnfang;
        this.speateTeilnameMin = speateTeilnameMin;

        this.anwesend = anwesend;
    }

    public StundenPlanDetails() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StundenPlan getStundenPlan() {
        return stundenPlan;
    }

    public void setStundenPlan(StundenPlan stundenPlan) {
        this.stundenPlan = stundenPlan;
    }

    public Schuler getSchuler() {
        return schuler;
    }

    public void setSchuler(Schuler schuler) {
        this.schuler = schuler;
    }

    public Boolean getSpeateAnfang() {
        return speateAnfang;
    }

    public void setSpeateAnfang(Boolean speateAnfang) {
        this.speateAnfang = speateAnfang;
    }

    public Double getSpeateTeilnameMin() {
        return speateTeilnameMin;
    }

    public void setSpeateTeilnameMin(Double speateTeilnameMin) {
        this.speateTeilnameMin = speateTeilnameMin;
    }

    public Set<Bemerkung> getBemerkungen() {
        return bemerkungen;
    }

    public void setBemerkungen(Set<Bemerkung> bemerkungen) {
        this.bemerkungen = bemerkungen;
    }

    public Boolean getAnwesend() {
        return anwesend;
    }

    public void setAnwesend(Boolean anwesend) {
        this.anwesend = anwesend;
    }
}
