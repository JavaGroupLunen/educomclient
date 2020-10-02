package com.educom.restclient.model;

import java.util.LinkedList;
import java.util.List;


public class Schule {
    private String name;
    private List<Schuler> Schulerlist = new LinkedList<>();

    @Override
    public String toString() {
        return name;

    }
}
