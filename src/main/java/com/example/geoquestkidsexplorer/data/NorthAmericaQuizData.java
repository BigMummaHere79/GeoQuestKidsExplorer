package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.QuizQuestions;
import java.util.Arrays;
import java.util.List;

public class NorthAmericaQuizData {

    public static List<QuizQuestions> getPracticeQuestions() {
        return Arrays.asList(
                new QuizQuestions("US", "Which country is home to the Grand Canyon, one of the world's most impressive natural formations?",
                        Arrays.asList("Mexico", "Canada", "United States", "Cuba"), "United States",
                        "The Grand Canyon is over a mile deep and was carved by the Colorado River."),
                new QuizQuestions("CA", "What is the largest country in North America by land area?",
                        Arrays.asList("United States", "Mexico", "Canada", "Greenland"), "Canada",
                        "Canada is the second-largest country in the world, with a vast and diverse landscape."),
                new QuizQuestions("MX", "Which country is famous for its ancient Mayan and Aztec pyramids?",
                        Arrays.asList("United States", "Cuba", "Mexico", "Panama"), "Mexico",
                        "The Pyramid of Kukulcan in Chichen Itza is one of the most famous Mayan ruins in Mexico."),
                new QuizQuestions("GR", "This is the world's largest island, and it's an autonomous territory of Denmark. What is it?",
                        Arrays.asList("Cuba", "Iceland", "Greenland", "Puerto Rico"), "Greenland",
                        "Greenland is almost completely covered by a massive ice sheet."),
                new QuizQuestions("PA", "Which Central American country connects North and South America?",
                        Arrays.asList("Costa Rica", "Nicaragua", "Panama", "Honduras"), "Panama",
                        "The Panama Canal is an artificial waterway that connects the Atlantic and Pacific oceans."),
                new QuizQuestions("CU", "This island country is known for its vintage cars and rich musical history. What is it?",
                        Arrays.asList("Jamaica", "Haiti", "Cuba", "Dominican Republic"), "Cuba",
                        "Many of the old cars you see in Cuba are from the 1950s and are kept running by their owners."),
                new QuizQuestions("HN", "Which country's flag features five stars, representing the five member states of the former Federal Republic of Central America?",
                        Arrays.asList("El Salvador", "Guatemala", "Honduras", "Nicaragua"), "Honduras",
                        "The stars on the Honduran flag are arranged in a specific pattern, representing the nation's history."),
                new QuizQuestions("JM", "What country is famous for its reggae music, bob-sledding team, and beautiful beaches?",
                        Arrays.asList("Barbados", "Jamaica", "Trinidad and Tobago", "Haiti"), "Jamaica",
                        "Reggae music, born in Jamaica, became famous worldwide thanks to artists like Bob Marley."),
                new QuizQuestions("GT", "This country is home to the ancient Mayan city of Tikal, with its magnificent pyramids rising above the rainforest canopy. What is it?",
                        Arrays.asList("Belize", "El Salvador", "Guatemala", "Mexico"), "Guatemala",
                        "Tikal was one of the largest and most powerful cities of the Maya civilization."),
                new QuizQuestions("CR", "Which country is known for its 'Pura Vida' philosophy and commitment to protecting its abundant wildlife and rainforests?",
                        Arrays.asList("Nicaragua", "Panama", "Costa Rica", "Honduras"), "Costa Rica",
                        "Costa Rica is a global leader in ecotourism and conservation efforts.")
        );
    }
}
