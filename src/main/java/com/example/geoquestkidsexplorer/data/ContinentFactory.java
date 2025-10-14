package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.Continents;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContinentFactory {
    private static final Map<String, Continents> continents = new HashMap<>();

    static {
        continents.put("Africa", new Continents("Africa", "africanflags/", "#c8e6c9", new AfricaFunFacts().getCountries()));
        continents.put("Asia", new Continents("Asia", "asiaflags/", "#ffe0b2", new AsiaFunFacts().getCountries()));
        continents.put("Europe", new Continents("Europe", "europeflags/", "#b3e5fc", new EuropeFunFacts().getCountries()));
        continents.put("North America", new Continents("North America", "northamericaflags/", "#ffcdd2", new NorthAmericaFunFacts().getCountries()));
        continents.put("South America", new Continents("South America", "southamericaflags/", "#e6ee9c", new SouthAmericaFunFacts().getCountries()));
        continents.put("Oceania", new Continents("Oceania", "oceaniaflags/", "#b2ebf2", new OceaniaFunFacts().getCountries()));
        continents.put("Antarctica", new Continents("Antarctica", "antarcticaflags/", "#ffffff", new AntarcticaFunFacts().getCountries()));
    }

    public static Continents getContinent(String name) {
        return continents.getOrDefault(name, null);
    }

    public static List<String> getAllContinentNames() {
        return Arrays.asList("Africa", "Asia", "Europe", "North America", "South America", "Oceania", "Antarctica");
    }
}