package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.Country;
import com.example.geoquestkidsexplorer.repositories.FunFactsProvider;

import java.util.Arrays;
import java.util.List;

public class SouthAmericaFunFacts implements FunFactsProvider {
    @Override
    public List<Country> getCountries() {
        return Arrays.asList(
                new Country("Argentina", "South America", "Argentina.png", new String[][] {
                        {"Argentina is home to the southernmost city in the world, Ushuaia.", "❄️"},
                        {"Buenos Aires is known as the 'Paris of South America' for its architecture and culture.", "🏙️"},
                        {"Argentina is the birthplace of tango music and dance.", "🎶"},
                        {"The country has diverse landscapes, including Patagonia, Andes, and Pampas.", "⛰️"},
                        {"Argentina is famous for its beef, which is among the highest quality in the world.", "🥩"}
                }),
                new Country("Bolivia", "South America", "Bolivia.png", new String[][] {
                        {"Bolivia has two capitals: La Paz (administrative) and Sucre (constitutional).", "🏛️"},
                        {"The Salar de Uyuni is the largest salt flat on Earth.", "🧂"},
                        {"Lake Titicaca, shared with Peru, is the highest navigable lake in the world.", "🌊"},
                        {"The country has rich indigenous cultures including Aymara and Quechua peoples.", "👥"},
                        {"Bolivia is rich in minerals like silver, tin, and lithium.", "⛏️"}
                }),
                new Country("Brazil", "South America", "Brazil.png", new String[][] {
                        {"Brazil is the largest country in South America by area and population.", "🗺️"},
                        {"The Amazon Rainforest, home to immense biodiversity, is mostly in Brazil.", "🌳"},
                        {"Rio de Janeiro’s Carnival is one of the biggest festivals in the world.", "🎉"},
                        {"Portuguese is the official language, making Brazil the largest Lusophone country.", "🗣️"},
                        {"Brazil is famous for football (soccer) legends like Pelé and Neymar.", "⚽"}
                }),
                new Country("Chile", "South America", "Chile.png", new String[][] {
                        {"Chile is a long, narrow country stretching along South America's west coast.", "🗺️"},
                        {"It has the driest desert in the world — the Atacama Desert.", "🏜️"},
                        {"Santiago is the modern capital with nearby Andes mountains.", "🏙️"},
                        {"Chile produces most of the world’s copper.", "⛏️"},
                        {"The country is famous for its wine, especially red wines from the Central Valley.", "🍷"}
                }),
                new Country("Colombia", "South America", "Colombia.png", new String[][] {
                        {"Colombia is the world’s largest source of emeralds.", "💎"},
                        {"Bogotá, the capital, sits high in the Andes mountains.", "🏙️"},
                        {"The country is famous for coffee — considered among the best in the world.", "☕"},
                        {"Colombia has coasts on both the Pacific and Atlantic Oceans.", "🌊"},
                        {"Carnival of Barranquilla is a UNESCO-recognized cultural festival.", "🎉"}
                }),
                new Country("Ecuador", "South America", "Ecuador.png", new String[][] {
                        {"Ecuador is named after the Equator, which runs through the country.", "🌍"},
                        {"It includes the Galápagos Islands, home to unique wildlife.", "🦎"},
                        {"Quito, the capital, has one of the best-preserved colonial centers in the Americas.", "🏛️"},
                        {"Ecuador produces bananas and cocoa for export.", "🍌"},
                        {"The Andes mountains run through the country, including active volcanoes.", "⛰️"}
                }),
                new Country("Guyana", "South America", "Guyana.png", new String[][] {
                        {"Guyana is the only English-speaking country in South America.", "🗣️"},
                        {"Kaieteur Falls is one of the world’s tallest single-drop waterfalls.", "🌊"},
                        {"It has dense rainforests home to jaguars and giant river otters.", "🐆"},
                        {"Georgetown, the capital, has colonial Dutch architecture.", "🏛️"},
                        {"Gold and bauxite are major exports of Guyana.", "⛏️"}
                }),
                new Country("Paraguay", "South America", "Paraguay.png", new String[][] {
                        {"Paraguay is landlocked but has rivers connecting to the Atlantic Ocean.", "🌊"},
                        {"Asunción, the capital, is one of South America’s oldest cities.", "🏙️"},
                        {"The country has a strong Guarani cultural heritage.", "👥"},
                        {"Yerba mate is a traditional drink enjoyed daily.", "🍵"},
                        {"Paraguay produces hydroelectric power from the Itaipú Dam.", "⚡"}
                }),
                new Country("Peru", "South America", "Peru.png", new String[][] {
                        {"Peru is home to Machu Picchu, the famous Incan citadel.", "🏯"},
                        {"Lima, the capital, is known for its culinary scene.", "🍲"},
                        {"The Andes mountains dominate the landscape, with high-altitude plains called Altiplano.", "⛰️"},
                        {"Peru is a major producer of potatoes, quinoa, and coffee.", "🥔"},
                        {"Lake Titicaca, shared with Bolivia, is sacred in Incan culture.", "🌊"}
                }),
                new Country("Suriname", "South America", "Suriname.png", new String[][] {
                        {"Suriname is the smallest country in South America.", "📏"},
                        {"Paramaribo, the capital, has unique Dutch colonial architecture.", "🏛️"},
                        {"It has dense rainforests covering over 90% of the country.", "🌳"},
                        {"The population is diverse, including Indian, Javanese, Creole, and Indigenous groups.", "👥"},
                        {"Bauxite mining is a major part of Suriname’s economy.", "⛏️"}
                }),
                new Country("Uruguay", "South America", "Uruguay.png", new String[][] {
                        {"Uruguay has one of the highest literacy rates in South America.", "📚"},
                        {"Montevideo, the capital, is known for its beaches and cultural life.", "🏖️"},
                        {"The country is famous for beef and mate tea.", "🥩"},
                        {"It was the first country to legalize marijuana nationwide.", "🌿"},
                        {"Uruguay is politically stable and often ranked as the safest country in the region.", "🕊️"}
                }),
                new Country("Venezuela", "South America", "Venezuela.png", new String[][] {
                        {"Venezuela is home to Angel Falls, the world’s tallest waterfall.", "🌊"},
                        {"Caracas is the modern capital surrounded by mountains.", "🏙️"},
                        {"The country has the largest oil reserves in the world.", "🛢️"},
                        {"Venezuelan are known for joropo music and dance.", "🎶"},
                        {"It has diverse ecosystems, from Andes mountains to Caribbean beaches.", "⛰️"}
                })
        );
    }
}
