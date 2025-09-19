package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.QuizQuestions;
import java.util.Arrays;
import java.util.List;

public class AntarcticaQuizData {

    public static List<QuizQuestions> getPracticeQuestions() {
        return Arrays.asList(
                new QuizQuestions("AQ", "What is the largest land animal in Antarctica?",
                        Arrays.asList("Weddell Seal", "Emperor Penguin", "Antarctic Midge", "Leopard Seal"), "Antarctic Midge",
                        "The Antarctic Midge is the largest purely terrestrial animal on the continent, but it is just a tiny, wingless fly."),
                new QuizQuestions("AQ", "What is Antarctica's highest point?",
                        Arrays.asList("Mount Erebus", "Mount Vinson", "Mount Kirkpatrick", "Mount Minto"), "Mount Vinson",
                        "Mount Vinson is part of the Ellsworth Mountains and is located on the Sentinel Range."),
                new QuizQuestions("AQ", "Antarctica is home to what percentage of the world's ice?",
                        Arrays.asList("10%", "50%", "70%", "90%"), "90%",
                        "If all of Antarctica's ice were to melt, global sea levels would rise by about 60 meters."),
                new QuizQuestions("AQ", "What is the largest penguin species native to Antarctica?",
                        Arrays.asList("Adélie Penguin", "Chinstrap Penguin", "King Penguin", "Emperor Penguin"), "Emperor Penguin",
                        "Emperor penguins are the only penguin species that breeds during the Antarctic winter."),
                new QuizQuestions("AQ", "Which of the following describes the Dry Valleys of Antarctica?",
                        Arrays.asList("Extremely humid", "Filled with dense forests", "Coldest deserts on Earth", "Underwater canyons"), "Coldest deserts on Earth",
                        "The McMurdo Dry Valleys are so dry that they are one of the closest environments on Earth to the surface of Mars."),
                new QuizQuestions("AQ", "What is the name of the continent's largest active volcano?",
                        Arrays.asList("Mount Erebus", "Mount Vinson", "Mount Siple", "Mount Takahe"), "Mount Erebus",
                        "Mount Erebus has a constantly active lava lake in its crater, a rare phenomenon on Earth."),
                new QuizQuestions("AQ", "How many countries have a permanent presence on Antarctica?",
                        Arrays.asList("0", "1", "3", "5"), "0",
                        "No country owns Antarctica, and it has no permanent human population. It is dedicated to peaceful, scientific research."),
                new QuizQuestions("AQ", "What is the phenomenon where the sun does not rise during the winter months?",
                        Arrays.asList("Midnight Sun", "Polar Day", "Polar Night", "Aurora Australis"), "Polar Night",
                        "During the polar night, temperatures can drop to their lowest, and the auroras are most visible."),
                new QuizQuestions("AQ", "What percentage of Antarctica is covered by ice?",
                        Arrays.asList("50%", "75%", "98%", "100%"), "98%",
                        "Only about 2% of the continent is ice-free, mostly in the Dry Valleys and coastal areas."),
                new QuizQuestions("AQ", "What is the name of the mountain range that divides Antarctica into East and West?",
                        Arrays.asList("Ellsworth Mountains", "Transantarctic Mountains", "Sentinel Range", "Prince Charles Mountains"), "Transantarctic Mountains",
                        "The Transantarctic Mountains are one of the longest mountain ranges on Earth, stretching for over 3,500 kilometers.")
        );
    }
}

