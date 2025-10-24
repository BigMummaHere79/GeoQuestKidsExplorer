package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.Country;
import com.example.geoquestkidsexplorer.repositories.FunFactsProvider;

import java.util.Arrays;
import java.util.List;

/**
 * This class provides curated funfacts for the continents "North America"
 * */
public class NorthAmericaFunFacts implements FunFactsProvider {

    /**
     * @return a list of entries for Asia dataset used by the UI
     * */

    @Override
    public List<Country> getCountries() {
        return Arrays.asList(
                new Country("Antigua and Barbuda", "North America", "Antigua and Barbuda.png", new String[][]{
                        {"Antigua has 365 beaches — one for every day of the year!", "🏖️"},
                        {"Barbuda is famous for its pink sand beaches made from crushed coral.", "🌸"},
                        {"The island’s name was given by Christopher Columbus in 1493.", "⛵"},
                        {"Frigate Bird Sanctuary on Barbuda is home to thousands of seabirds.", "🕊️"},
                        {"Antigua celebrates Carnival each summer with colorful parades and music.", "🎉"}
                }),
                new Country("Bahamas", "North America", "Bahamas.png", new String[][]{
                        {"The Bahamas has over 700 islands, but only about 30 are inhabited.", "🏝️"},
                        {"It’s home to one of the world’s clearest waters — you can see 200 feet deep!", "🌊"},
                        {"Pigs really do swim here — on Big Major Cay!", "🐷"},
                        {"Pirates once hid treasure in the Bahamas during the 1700s.", "🏴‍☠️"},
                        {"Nassau’s colorful buildings reflect its lively Caribbean spirit.", "🎨"}
                }),
                new Country("Barbados", "North America", "Barbados.png", new String[][]{
                        {"Barbados is known as the birthplace of rum!", "🍹"},
                        {"The island was once home to one of the oldest parliaments in the world.", "🏛️"},
                        {"Rihanna, the world-famous singer, was born here.", "🎤"},
                        {"Flying fish is the national dish of Barbados.", "🐟"},
                        {"The island is surrounded by beautiful coral reefs full of marine life.", "🐠"}
                }),
                new Country("Belize", "North America", "Belize.png", new String[][]{
                        {"Belize has the second-largest barrier reef in the world.", "🐠"},
                        {"The Great Blue Hole is a giant underwater sinkhole visible from space!", "🔵"},
                        {"English is the official language, a rare trait in Central America.", "🗣️"},
                        {"You can explore ancient Mayan temples deep in the jungle.", "🏯"},
                        {"It’s a haven for jaguars and exotic tropical birds.", "🐆"}
                }),
                new Country("Canada", "North America", "Canada.png", new String[][]{
                        {"Canada has the world’s longest coastline — over 200,000 km long!", "🌊"},
                        {"It’s home to more lakes than the rest of the world combined.", "🏞️"},
                        {"Maple syrup is one of its sweetest exports!", "🍁"},
                        {"Canadians play hockey even on frozen lakes in winter.", "🏒"},
                        {"The Northern Lights can often be seen in northern Canada.", "🌌"}
                }),
                new Country("Costa Rica", "North America", "Costa Rica.png", new String[][]{
                        {"Costa Rica means 'Rich Coast' in Spanish.", "🌴"},
                        {"It has no army — abolished in 1949!", "🕊️"},
                        {"Over 25% of its land is protected rainforest.", "🌳"},
                        {"You can visit both the Pacific Ocean and Caribbean Sea in one day.", "🌊"},
                        {"It’s one of the happiest countries in the world.", "😊"}
                }),
                new Country("Cuba", "North America", "Cuba.png", new String[][]{
                        {"Cuba is the largest island in the Caribbean.", "🏝️"},
                        {"Vintage cars are common due to historical trade restrictions.", "🚗"},
                        {"Cuban music and dance like salsa are known worldwide.", "💃"},
                        {"Havana’s old town is a UNESCO World Heritage Site.", "🏘️"},
                        {"Cigars from Cuba are among the most famous in the world.", "🚬"}
                }),
                new Country("Dominica", "North America", "Dominica.png", new String[][]{
                        {"Dominica is called the ‘Nature Island of the Caribbean’.", "🌿"},
                        {"It’s home to the second-largest boiling lake in the world!", "💨"},
                        {"Rainforests cover most of the island.", "🌧️"},
                        {"You can see both black sand and white sand beaches here.", "🏖️"},
                        {"Sperm whales live year-round in Dominica’s waters.", "🐋"}
                }),
                new Country("Dominican Republic", "North America", "Dominican Republic.png", new String[][]{
                        {"It shares the island of Hispaniola with Haiti.", "🗺️"},
                        {"Baseball is the most popular sport.", "⚾"},
                        {"Punta Cana is known for its stunning beaches.", "🌴"},
                        {"The first cathedral in the Americas was built here.", "⛪"},
                        {"Merengue music originated in the Dominican Republic.", "🎶"}
                }),
                new Country("El Salvador", "North America", "El Salvador.png", new String[][]{
                        {"It’s the smallest country in Central America.", "📏"},
                        {"El Salvador is called the 'Land of Volcanoes'.", "🌋"},
                        {"Surfing is one of its biggest attractions.", "🏄"},
                        {"Pupusas, thick corn tortillas, are the national dish.", "🌽"},
                        {"It’s known for its warm and welcoming people.", "🤝"}
                }),
                new Country("Grenada", "North America", "Grenada.png", new String[][]{
                        {"Grenada is nicknamed the 'Spice Island'.", "🌶️"},
                        {"It produces nutmeg, cinnamon, and cloves.", "🍂"},
                        {"The island is surrounded by colorful coral reefs.", "🐠"},
                        {"St. George’s harbor is one of the most beautiful in the Caribbean.", "⚓"},
                        {"Chocolate lovers rejoice — Grenada grows its own cacao!", "🍫"}
                }),
                new Country("Guatemala", "North America", "Guatemala.png", new String[][]{
                        {"Guatemala is known as the 'Land of Eternal Spring'.", "🌸"},
                        {"Ancient Mayan ruins like Tikal attract explorers from around the world.", "🏯"},
                        {"It’s the birthplace of chocolate — the Mayans drank it first!", "🍫"},
                        {"Colorful textiles are handwoven by local artisans.", "🧵"},
                        {"Lake Atitlán is surrounded by volcanoes and villages.", "🌋"}
                }),
                new Country("Haiti", "North America", "Haiti.png", new String[][]{
                        {"Haiti was the first independent nation in Latin America.", "🏛️"},
                        {"The country shares the island of Hispaniola with the Dominican Republic.", "🗺️"},
                        {"Its culture blends African, French, and Caribbean traditions.", "🎭"},
                        {"Haitian art is vibrant and full of color.", "🎨"},
                        {"The Citadelle Laferrière is one of the largest fortresses in the Americas.", "🏰"}
                }),
                new Country("Honduras", "North America", "Honduras.png", new String[][]{
                        {"Honduras means 'depths' in Spanish — referring to deep waters off its coast.", "🌊"},
                        {"It’s home to the ancient Mayan city of Copán.", "🏯"},
                        {"The Bay Islands are a diving paradise.", "🤿"},
                        {"You’ll find tropical rainforests filled with exotic animals.", "🐒"},
                        {"Soccer is the nation’s favorite sport.", "⚽"}
                }),
                new Country("Jamaica", "North America", "Jamaica.png", new String[][]{
                        {"Jamaica gave the world reggae music and Bob Marley.", "🎶"},
                        {"The island’s Blue Mountains grow world-famous coffee.", "☕"},
                        {"Usain Bolt, the fastest man alive, is Jamaican.", "🏃"},
                        {"Jamaica was the first Caribbean nation to gain independence.", "🇯🇲"},
                        {"The island’s motto is 'Out of Many, One People'.", "🤝"}
                }),
                new Country("Mexico", "North America", "Mexico.png", new String[][]{
                        {"Mexico introduced chocolate, corn, and chili peppers to the world.", "🌶️"},
                        {"The ancient Mayans and Aztecs built great pyramids here.", "🏯"},
                        {"Mexico City is one of the largest cities in the world.", "🏙️"},
                        {"Mariachi music is a symbol of Mexican culture.", "🎺"},
                        {"The Day of the Dead celebrates ancestors with colorful altars.", "💀"}
                }),
                new Country("Nicaragua", "North America", "Nicaragua.png", new String[][]{
                        {"Nicaragua is known as the 'Land of Lakes and Volcanoes'.", "🌋"},
                        {"It has the largest freshwater lake in Central America.", "💧"},
                        {"You can go volcano boarding down Cerro Negro!", "🛷"},
                        {"The country has beautiful colonial towns like Granada.", "🏘️"},
                        {"Its flag colors represent peace and justice.", "💙"}
                }),
                new Country("Panama", "North America", "Panama.png", new String[][]{
                        {"The Panama Canal connects the Atlantic and Pacific Oceans.", "🚢"},
                        {"Panama City is the only capital with a rainforest within city limits.", "🌳"},
                        {"The country uses both the U.S. dollar and its own Balboa currency.", "💵"},
                        {"It’s home to over 1,000 bird species.", "🐦"},
                        {"The Panama Hat actually comes from Ecuador!", "👒"}
                }),
                new Country("Saint Kitts and Nevis", "North Amrica", "Saint Kitts and Nevis.png", new String[][]{
                        {"It’s the smallest country in the Western Hemisphere.", "📏"},
                        {"Saint Kitts and Nevis are volcanic islands.", "🌋"},
                        {"The islands were once called 'Sugar Islands' due to plantations.", "🍬"},
                        {"You can ride a scenic railway around Saint Kitts.", "🚂"},
                        {"They celebrate Carnival with colorful costumes and music.", "🎭"}
                }),
                new Country("Saint Lucia", "North America", "Saint Lucia.png", new String[][] {
                        {"Saint Lucia is famous for its twin volcanic peaks, the Pitons.", "⛰️"},
                        {"It’s the only country named after a woman.", "👩"},
                        {"The island changes hands between the French and British 14 times in history!", "⚔️"},
                        {"Sulphur Springs lets you bathe in warm volcanic mud.", "🫧"},
                        {"It’s home to vibrant Creole culture and music.", "🎶"}
                }),
                new Country("Saint Vincent and Grenadines", "North America", "Saint Vincent and the Grenadines.png",
                        new String[][] {
                                {"It’s made up of one main island and 31 smaller ones.", "🏝️"},
                                {"The movie 'Pirates of the Caribbean' was filmed here.", "🏴‍☠️"},
                                {"You can hike up an active volcano called La Soufrière.", "🌋"},
                                {"The islands are surrounded by turquoise waters ideal for sailing.", "⛵"},
                                {"Locals love cricket, music, and calypso festivals.", "🏏"}
                }),
                new Country("Trinidad and Tobago", "North America", "Trinidad and Tobago.png", new String[][] {
                        {"Trinidad is the birthplace of steelpan drums.", "🥁"},
                        {"Carnival here is one of the biggest in the world.", "🎉"},
                        {"The Scarlet Ibis is a national bird.", "🪶"},
                        {"You can watch baby turtles hatch on Tobago’s beaches.", "🐢"},
                        {"Calypso and soca music started in Trinidad and Tobago.", "🎶"}
                }),
                new Country("United State of America", "North America", "United State of America.png",
                        new String[][] {
                                {"The USA is the third-largest country by land area.", "🗺️"},
                                {"It’s home to the world’s first national park — Yellowstone!", "🏞️"},
                                {"The Statue of Liberty was a gift from France.", "🗽"},
                                {"Route 66 is known as America’s 'Mother Road'.", "🚗"},
                                {"The U.S. flag has 50 stars, one for each state.", "⭐"}
                })
        );
    }
}