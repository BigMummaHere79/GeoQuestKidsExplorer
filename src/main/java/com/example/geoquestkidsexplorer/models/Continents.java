package com.example.geoquestkidsexplorer.models;

import java.util.List;

public class Continents {
    private String name;
    private String flagDirectory;
    private String backgroundColor;
    private List<Country> countries;

    public Continents(String name, String flagDirectory, String backgroundColor, List<Country> countries) {
        this.name = name;
        this.flagDirectory = flagDirectory;
        this.backgroundColor = backgroundColor;
        this.countries = countries;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFlagDirectory() { return flagDirectory; }
    public void setFlagDirectory(String flagDirectory) { this.flagDirectory = flagDirectory; }
    public String getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }
    public List<Country> getCountries() { return countries; }
    public void setCountries(List<Country> countries) { this.countries = countries; }
}