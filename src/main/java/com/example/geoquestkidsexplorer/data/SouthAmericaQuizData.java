package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.QuizQuestions;
import java.util.Arrays;
import java.util.List;

public class SouthAmericaQuizData {

    public static List<QuizQuestions> getPracticeQuestions() {
        return Arrays.asList(
                new QuizQuestions("BR", "Which country is home to the Amazon Rainforest, the largest tropical rainforest in the world?",
                        Arrays.asList("Argentina", "Peru", "Brazil", "Colombia"), "Brazil",
                        "The Amazon River, which flows through Brazil, is the world's largest river by discharge volume."),
                new QuizQuestions("PE", "Which famous lost city of the Incas is located in the Andes Mountains of Peru?",
                        Arrays.asList("Cusco", "Chan Chan", "Lima", "Machu Picchu"), "Machu Picchu",
                        "Machu Picchu is considered one of the 'New Seven Wonders of the World' and was built in the 15th century."),
                new QuizQuestions("AR", "What popular dance style originated in the city of Buenos Aires, Argentina?",
                        Arrays.asList("Salsa", "Tango", "Samba", "Flamenco"), "Tango",
                        "The tango is a beautiful and passionate dance that originated in the late 19th century in Argentina and Uruguay."),
                new QuizQuestions("VE", "Home to Angel Falls, the world's tallest uninterrupted waterfall, which country is this?",
                        Arrays.asList("Brazil", "Colombia", "Venezuela", "Guyana"), "Venezuela",
                        "Angel Falls is so tall that the water evaporates into mist before it even reaches the ground."),
                new QuizQuestions("EC", "Which country gets its name from a word that means 'equator'?",
                        Arrays.asList("Colombia", "Ecuador", "Peru", "Chile"), "Ecuador",
                        "Ecuador is also home to the Galápagos Islands, famous for their unique wildlife."),
                new QuizQuestions("CL", "This country is the longest and narrowest in the world. What is it?",
                        Arrays.asList("Peru", "Bolivia", "Chile", "Argentina"), "Chile",
                        "At its widest point, Chile is only 217 miles wide!"),
                new QuizQuestions("CO", "This country is famous for its high-quality coffee and emeralds. What is it?",
                        Arrays.asList("Brazil", "Colombia", "Ecuador", "Venezuela"), "Colombia",
                        "Colombia is the world's third-largest coffee producer."),
                new QuizQuestions("BO", "This country has two capitals: Sucre and La Paz. What is it?",
                        Arrays.asList("Paraguay", "Bolivia", "Uruguay", "Suriname"), "Bolivia",
                        "La Paz is the world's highest administrative capital, located at over 3,650 meters above sea level."),
                new QuizQuestions("UY", "Which South American country is the smallest Spanish-speaking country by area?",
                        Arrays.asList("Uruguay", "Paraguay", "Ecuador", "Suriname"), "Uruguay",
                        "Uruguay is home to more sheep than people."),
                new QuizQuestions("PY", "This country has a unique flag that features a different emblem on each side. What is it?",
                        Arrays.asList("Paraguay", "Argentina", "Brazil", "Bolivia"), "Paraguay",
                        "The reverse side of the Paraguayan flag features the national treasury seal.")
        );
    }
}

