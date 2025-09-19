package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.QuizQuestions;

import java.util.Arrays;
import java.util.List;

public class AfricaQuizData {

    public static List<QuizQuestions> getPracticeQuestions() {
        return Arrays.asList(
                new QuizQuestions("EG", "What is the capital of Egypt?",
                        Arrays.asList("Cairo", "Nairobi", "Lagos", "Accra"), "Cairo",
                        "The Great Pyramid of Giza is the oldest of the Seven Wonders of the Ancient World."),
                new QuizQuestions("KE", "What country is home to the Maasai people and Mount Kenya?",
                        Arrays.asList("Tanzania", "Kenya", "Uganda", "Sudan"), "Kenya",
                        "Kenya has a vast savanna that is home to a wide variety of animals, including the 'Big Five'."),
                new QuizQuestions("ZA", "Which country is known for its iconic Table Mountain?",
                        Arrays.asList("South Africa", "Botswana", "Zambia", "Namibia"), "South Africa",
                        "South Africa has three capital cities: Pretoria, Bloemfontein, and Cape Town."),
                new QuizQuestions("NG", "What is the most populous country in Africa?",
                        Arrays.asList("Ethiopia", "Egypt", "Nigeria", "DR Congo"), "Nigeria",
                        "Lagos is one of the largest and fastest-growing cities in Africa."),
                new QuizQuestions("GH", "The country of Ghana is famous for its golden beaches and...",
                        Arrays.asList("Pyramids", "Rainforests", "Cocoa exports", "Ski resorts"), "Cocoa exports",
                        "Ghana is the second-largest producer of cocoa beans in the world."),
                new QuizQuestions("MA", "What country is located on the northwestern tip of Africa, " +
                        "with coastlines on both the Atlantic and Mediterranean?",
                        Arrays.asList("Tunisia", "Algeria", "Morocco", "Libya"), "Morocco",
                        "Morocco is the only African country that is not a member of the African Union."),
                new QuizQuestions("SD", "Which country has more pyramids than Egypt?",
                        Arrays.asList("Sudan", "Mali", "Senegal", "Chad"), "Sudan",
                        "Sudan has over 200 pyramids, far more than Egypt."),
                new QuizQuestions("TN", "What country is home to the ancient city of Carthage?",
                        Arrays.asList("Algeria", "Libya", "Tunisia", "Morocco"), "Tunisia",
                        "Tunisia has 13 million people in its population."),
                new QuizQuestions("DZ", "Which is the largest country in Africa by area?",
                        Arrays.asList("Egypt", "Nigeria", "Algeria", "Sudan"), "Algeria",
                        "The Sahara Desert covers more than 80% of Algeria's territory."),
                new QuizQuestions("ET", "What is the capital of Ethiopia?",
                        Arrays.asList("Addis Ababa", "Nairobi", "Khartoum", "Djibouti"), "Addis Ababa",
                        "Ethiopia is the only African country that has never been colonized by a European power.")
        );
    }
}
