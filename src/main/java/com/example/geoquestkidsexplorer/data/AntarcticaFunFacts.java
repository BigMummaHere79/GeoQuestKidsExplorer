package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.Country;
import com.example.geoquestkidsexplorer.repositories.FunFactsProvider;

import java.util.Collections;
import java.util.List;

public class AntarcticaFunFacts implements FunFactsProvider {
    @Override
    public List<Country> getCountries() {
        return Collections.singletonList(
                new Country("Antarctica", "Antarctica", "Antarctica.png", new String[][] {
                        {"Antarctica is the coldest continent on Earth, with temperatures dropping below -80°C.", "❄️"},
                        {"It is covered almost entirely by ice, containing about 70% of the world’s fresh water.", "🧊"},
                        {"There are no permanent residents, but scientists live in research stations year-round.", "🔬"},
                        {"Antarctica is home to penguins, seals, and other unique wildlife adapted to the cold.", "🐧"},
                        {"The continent is governed by the Antarctic Treaty, dedicated to peace and scientific research.", "🕊️"}
                })
        );
    }
}