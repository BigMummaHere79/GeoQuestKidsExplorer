package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.QuizQuestions;
import java.util.Arrays;
import java.util.List;

public class AsiaQuizData {

    public static List<QuizQuestions> getPracticeQuestions() {
        return Arrays.asList(
                new QuizQuestions("IN", "What is the capital of India?",
                        Arrays.asList("Mumbai", "New Delhi", "Kolkata", "Chennai"), "New Delhi",
                        "India is the world's largest democracy, with over 1.4 billion people."),
                new QuizQuestions("CN", "The Great Wall of China was built to protect against what?",
                        Arrays.asList("Flooding", "Invaders", "Wild animals", "Earthquakes"), "Invaders",
                        "The Great Wall of China is a series of fortifications that stretches for thousands of miles across the northern part of the country."),
                new QuizQuestions("JP", "Which city is the capital of Japan?",
                        Arrays.asList("Kyoto", "Osaka", "Tokyo", "Hiroshima"), "Tokyo",
                        "Tokyo is the most populous metropolitan area in the world."),
                new QuizQuestions("TH", "Which country is known as the 'Land of Smiles'?",
                        Arrays.asList("Vietnam", "Thailand", "Malaysia", "Cambodia"), "Thailand",
                        "Thailand is the only Southeast Asian country that has never been colonized by a European power."),
                new QuizQuestions("RU", "What is the name of the vast, frozen region in Russia that makes up most of Northern Asia?",
                        Arrays.asList("Siberia", "Ural", "Kamchatka", "Caucasus"), "Siberia",
                        "Siberia is so large that it accounts for 77% of Russia's total land area."),
                new QuizQuestions("VN", "Which country is famous for its delicious 'Pho' noodle soup?",
                        Arrays.asList("Laos", "Cambodia", "Vietnam", "Thailand"), "Vietnam",
                        "The Son Doong Cave in Vietnam is so big that it has its own ecosystem, rivers, and weather system."),
                new QuizQuestions("SG", "What is the only island city-state in Asia?",
                        Arrays.asList("Maldives", "Taiwan", "Singapore", "Sri Lanka"), "Singapore",
                        "Singapore has a ban on chewing gum in the country, with some exceptions for medical use."),
                new QuizQuestions("SA", "What is the capital of Saudi Arabia?",
                        Arrays.asList("Riyadh", "Jeddah", "Mecca", "Medina"), "Riyadh",
                        "Saudi Arabia has the world's largest oil reserves."),
                new QuizQuestions("ID", "Which country is the world's largest archipelago with over 17,000 islands?",
                        Arrays.asList("Philippines", "Indonesia", "Japan", "Maldives"), "Indonesia",
                        "The Komodo dragon, the world's largest lizard, is native to the islands of Indonesia."),
                new QuizQuestions("PH", "What is the name of the famous volcano in the Philippines known for its perfect cone shape?",
                        Arrays.asList("Mount Pinatubo", "Mount Apo", "Taal Volcano", "Mayon Volcano"), "Mayon Volcano",
                        "Mayon Volcano is one of the most active volcanoes in the Philippines, with a near-perfect conical shape.")
        );
    }
}

