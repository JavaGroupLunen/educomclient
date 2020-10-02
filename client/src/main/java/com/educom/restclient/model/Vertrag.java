package com.educom.restclient.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


public class Vertrag implements Serializable {

    private long id;

    private Date vertragsdatum;
    private LocalDate vertragsbegin;
    private LocalDate vertragsende;
    private ZahlungsType zahlungstype;
    private Double einmaligeKosten;
    private Double anmeldegebuhr;
    private Double materialprice;
    private Double summe;
    private Double monatlischeRate;
    private Double restbetrag;
    private Double rabat;
    private Double rabatPercent;


    private Schuler schuler;


    public Vertrag(Date vertragsdatum, LocalDate vertragsbegin, LocalDate vertragsende, ZahlungsType zahlungstype, Double einmaligeKosten, Double anmeldegebuhr, Double materialprice, Double summe, Double monatlischeRate, Double restbetrag, Double rabat, Double rabatPercent, Schuler schuler) {
        this.vertragsdatum = vertragsdatum;
        this.vertragsbegin = vertragsbegin;
        this.vertragsende = vertragsende;
        this.zahlungstype = zahlungstype;
        this.einmaligeKosten = einmaligeKosten;
        this.anmeldegebuhr = anmeldegebuhr;
        this.materialprice = materialprice;
        this.summe = summe;
        this.monatlischeRate = monatlischeRate;
        this.restbetrag = restbetrag;
        this.rabat = rabat;
        this.rabatPercent = rabatPercent;
        this.schuler = schuler;
    }

    public Vertrag() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getVertragsdatum() {
        return vertragsdatum;
    }

    public void setVertragsdatum(Date vertragsdatum) {
        this.vertragsdatum = vertragsdatum;
    }

    public LocalDate getVertragsbegin() {
        return vertragsbegin;
    }

    public void setVertragsbegin(LocalDate vertragsbegin) {
        this.vertragsbegin = vertragsbegin;
    }

    public LocalDate getVertragsende() {
        return vertragsende;
    }

    public void setVertragsende(LocalDate vertragsende) {
        this.vertragsende = vertragsende;
    }

    public ZahlungsType getZahlungstype() {
        return zahlungstype;
    }

    public void setZahlungstype(ZahlungsType zahlungstype) {
        this.zahlungstype = zahlungstype;
    }

    public Double getEinmaligeKosten() {
        return einmaligeKosten;
    }

    public void setEinmaligeKosten(Double einmaligeKosten) {
        this.einmaligeKosten = einmaligeKosten;
    }

    public Double getAnmeldegebuhr() {
        return anmeldegebuhr;
    }

    public void setAnmeldegebuhr(Double anmeldegebuhr) {
        this.anmeldegebuhr = anmeldegebuhr;
    }

    public Double getMaterialprice() {
        return materialprice;
    }

    public void setMaterialprice(Double materialprice) {
        this.materialprice = materialprice;
    }

    public Double getSumme() {
        return summe;
    }

    public void setSumme(Double totalprice) {
        this.summe = totalprice;
    }

    public Double getMonatlischeRate() {
        return monatlischeRate;
    }

    public void setMonatlischeRate(Double monatlischeRate) {
        this.monatlischeRate = monatlischeRate;
    }

    public Double getRestbetrag() {
        return restbetrag;
    }

    public void setRestbetrag(Double restbetrag) {
        this.restbetrag = restbetrag;
    }

    public Double getRabat() {
        return rabat;
    }

    public void setRabat(Double rabat) {
        this.rabat = rabat;
    }

    public Double getRabatPercent() {
        return rabatPercent;
    }

    public void setRabatPercent(Double rabatPercent) {
        this.rabatPercent = rabatPercent;
    }

    public Schuler getSchuler() {
        return schuler;
    }

    public void setSchuler(Schuler schuler) {
        this.schuler = schuler;
    }
}
