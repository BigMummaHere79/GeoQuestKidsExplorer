package com.example.geoquestkidsexplorer.models;

public class Country {
    private String name;
    private String continent;
    private String flagFileName;
    private String[][] funFacts;

    public Country(String name, String continent, String flagFileName, String[][] funFacts) {
        this.name = name;
        this.continent = continent;
        this.flagFileName = flagFileName;
        this.funFacts = funFacts != null ? funFacts : new String[0][];
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContinent() { return continent; }
    public void setContinent(String continent) { this.continent = continent; }
    public String getFlagFileName() { return flagFileName; }
    public void setFlagFileName(String flagFileName) { this.flagFileName = flagFileName; }
    public String[][] getFunFacts() { return funFacts; }
    public void setFunFacts(String[][] funFacts) { this.funFacts = funFacts; }
}