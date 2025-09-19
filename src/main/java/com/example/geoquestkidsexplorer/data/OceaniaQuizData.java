package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.QuizQuestions;
import java.util.Arrays;
import java.util.List;

public class OceaniaQuizData {

    public static List<QuizQuestions> getPracticeQuestions() {
        return Arrays.asList(
                new QuizQuestions("AU", "What is the capital city of Australia?",
                        Arrays.asList("Sydney", "Melbourne", "Canberra", "Brisbane"), "Canberra",
                        "Canberra is a planned city, not just a large metropolis that grew over time."),
                new QuizQuestions("AU", "Which country is home to the Great Barrier Reef?",
                        Arrays.asList("New Zealand", "Fiji", "Australia", "Papua New Guinea"), "Australia",
                        "The Great Barrier Reef is so large it can be seen from outer space."),
                new QuizQuestions("NZ", "What is the name of the flightless bird native to New Zealand?",
                        Arrays.asList("Emu", "Kakapo", "Kiwi", "Moa"), "Kiwi",
                        "The kiwi bird has nostrils at the tip of its long beak, giving it a great sense of smell."),
                new QuizQuestions("PG", "What is the largest island in Oceania?",
                        Arrays.asList("Tasmania", "New Guinea", "South Island", "Borneo"), "New Guinea",
                        "New Guinea is the world's second-largest island, after Greenland."),
                new QuizQuestions("FJ", "What popular traditional drink from Fiji is made from the root of a pepper plant?",
                        Arrays.asList("Coconut Water", "Kava", "Tamarind Juice", "Sago"), "Kava",
                        "Kava has been used for centuries in Pacific Island ceremonies for its relaxing effects."),
                new QuizQuestions("AU", "Which of these is considered the smallest continent on Earth?",
                        Arrays.asList("Australia", "Antarctica", "Europe", "South America"), "Australia",
                        "Australia is both the world's largest island and its smallest continent."),
                new QuizQuestions("AU", "The famous Sydney Opera House is located in which Australian city?",
                        Arrays.asList("Melbourne", "Perth", "Adelaide", "Sydney"), "Sydney",
                        "The roof of the Sydney Opera House is made of over one million tiles."),
                new QuizQuestions("NZ", "New Zealand is primarily made up of two large islands. What are they called?",
                        Arrays.asList("East and West Islands", "North and South Islands", "Maori and Pakeha Islands", "Tasman and Cook Islands"), "North and South Islands",
                        "The South Island is home to the Southern Alps, a stunning mountain range."),
                new QuizQuestions("PF", "Which of these is a famous Polynesian island group in Oceania?",
                        Arrays.asList("Galapagos Islands", "Canary Islands", "Tahiti", "Madagascar"), "Tahiti",
                        "Tahiti is the largest island in French Polynesia, an overseas collectivity of France."),
                new QuizQuestions("SB", "Which island nation is made up of over 1,000 islands and atolls?",
                        Arrays.asList("Samoa", "Solomon Islands", "Vanuatu", "Tonga"), "Solomon Islands",
                        "The Solomon Islands were the site of major battles during World War II.")
        );
    }
}

