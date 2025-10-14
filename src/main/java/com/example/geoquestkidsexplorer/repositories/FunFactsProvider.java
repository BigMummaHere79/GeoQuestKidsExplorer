package com.example.geoquestkidsexplorer.repositories;

import com.example.geoquestkidsexplorer.models.Country;

import java.util.List;

public interface FunFactsProvider {
    List<Country> getCountries();
}