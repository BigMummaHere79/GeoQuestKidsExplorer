package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.Country;
import com.example.geoquestkidsexplorer.repositories.FunFactsProvider;

import java.util.Arrays;
import java.util.List;

public class OceaniaFunFacts implements FunFactsProvider {
    @Override
    public List<Country> getCountries() {
        return Arrays.asList(
                new Country("Australia", "Oceania", "Australia.png", new String[][] {
                        {"Australia is both a country and a continent, famous for its unique wildlife.", "🦘"},
                        {"Canberra is the capital, while Sydney and Melbourne are major cultural hubs.", "🏙️"},
                        {"The Great Barrier Reef is the world’s largest coral reef system.", "🐠"},
                        {"Australia has vast deserts, tropical rainforests, and snowy mountains.", "⛰️"},
                        {"The country is famous for sports, including cricket, rugby, and surfing.", "🏄‍♂️"}
                }),
                new Country("Fiji", "Oceania", "Fiji.png", new String[][] {
                        {"Fiji is an archipelago of over 300 islands in the South Pacific.", "🏝️"},
                        {"Suva is the capital and economic center, located on the largest island, Viti Levu.", "🏙️"},
                        {"Fiji is famous for its tropical beaches and coral reefs.", "🌊"},
                        {"Traditional Fijian culture includes music, dance, and ceremonies.", "🎶"},
                        {"The economy relies on tourism, sugar exports, and fishing.", "💰"}
                }),
                new Country("Kiribati", "Oceania", "Kiribati.png", new String[][] {
                        {"Kiribati is composed of 33 atolls and reef islands in the central Pacific.", "🏝️"},
                        {"The capital, South Tarawa, is one of the most densely populated areas in Oceania.", "🏙️"},
                        {"Kiribati is known for its coconut palms and marine biodiversity.", "🌴"},
                        {"It is one of the countries most vulnerable to rising sea levels.", "🌊"},
                        {"Fishing and copra production are key economic activities.", "🐟"}
                }),
                new Country("Marshall Islands", "Oceania", "Marshall Islands.png", new String[][] {
                        {"The Marshall Islands consists of 29 atolls and 5 islands.", "🏝️"},
                        {"Majuro is the capital and main port of the country.", "🏙️"},
                        {"The islands were used for nuclear testing during the 1940s and 1950s.", "⚠️"},
                        {"Traditional navigation and canoe building are important cultural practices.", "🛶"},
                        {"Fishing and copra production are main economic activities.", "🐟"}
                }),
                new Country("Micronesia", "Oceania", "Micronesia.png", new String[][] {
                        {"The Federated States of Micronesia includes over 600 islands in the western Pacific.", "🏝️"},
                        {"Palikir is the capital located on the island of Pohnpei.", "🏙️"},
                        {"The country has diverse marine life and tropical forests.", "🌳"},
                        {"Traditional culture includes navigation, dance, and storytelling.", "🎎"},
                        {"Fishing and subsistence agriculture support local communities.", "🐟"}
                }),
                new Country("Nauru", "Oceania", "Nauru.png", new String[][] {
                        {"Nauru is the third smallest country in the world by area.", "📏"},
                        {"Yaren is the de facto capital, as Nauru has no official capital.", "🏙️"},
                        {"The island was once heavily mined for phosphate.", "⛏️"},
                        {"Nauru has unique marine biodiversity and coral reefs.", "🐠"},
                        {"The population is small and closely-knit, with traditional customs maintained.", "👥"}
                }),
                new Country("New Zealand", "Oceania", "New Zealand.png", new String[][] {
                        {"New Zealand is made up of two main islands: North Island and South Island.", "🏝️"},
                        {"Wellington is the capital, and Auckland is the largest city.", "🏙️"},
                        {"The country is famous for its Maori culture and traditions.", "👥"},
                        {"New Zealand has diverse landscapes, from beaches to mountains and glaciers.", "⛰️"},
                        {"It is renowned for outdoor sports, including rugby, hiking, and skiing.", "🏉"}
                }),
                new Country("Palau", "Oceania", "Palau.png", new String[][] {
                        {"Palau is an archipelago of over 500 islands in the western Pacific.", "🏝️"},
                        {"Ngerulmud is the capital, located on the island of Babeldaob.", "🏙️"},
                        {"The country is famous for scuba diving and marine biodiversity.", "🐠"},
                        {"Palauan culture includes traditional storytelling, navigation, and canoe building.", "🛶"},
                        {"Tourism and fishing are key economic activities.", "💰"}
                }),
                new Country("Papua New Guinea", "Oceania", "Papua New Guinea.png", new String[][] {
                        {"Papua New Guinea occupies the eastern half of the island of New Guinea.", "🗺️"},
                        {"Port Moresby is the capital and largest city.", "🏙️"},
                        {"The country has over 800 languages, making it the most linguistically diverse in the world.", "🗣️"},
                        {"Papua New Guinea has rich biodiversity, including rainforests and coral reefs.", "🌳"},
                        {"Subsistence agriculture and mining are central to the economy.", "⛏️"}
                }),
                new Country("Samoa", "Oceania", "Samoa.png", new String[][] {
                        {"Samoa consists of two main islands: Upolu and Savai’i.", "🏝️"},
                        {"Apia is the capital and main port city.", "🏙️"},
                        {"Samoan culture includes dance, tattooing, and communal traditions.", "🎎"},
                        {"The islands have volcanic landscapes, tropical forests, and beaches.", "⛰️"},
                        {"Tourism and agriculture, including coconut and taro, drive the economy.", "🌴"}
                }),
                new Country("Solomon Islands", "Oceania", "Solomon Islands.png", new String[][] {
                        {"The Solomon Islands is an archipelago of nearly 1,000 islands.", "🏝️"},
                        {"Honiara is the capital, located on the island of Guadalcanal.", "🏙️"},
                        {"The islands were a key battleground during World War II.", "⚔️"},
                        {"The country has rich marine biodiversity and coral reefs.", "🐠"},
                        {"Logging and fishing are important economic activities.", "🌴"}
                }),
                new Country("Tonga", "Oceania", "Tonga.png", new String[][] {
                        {"Tonga is known as the 'Friendly Islands' for its warm hospitality.", "🤝"},
                        {"Nuku'alofa is the capital and main port.", "🏙️"},
                        {"The islands are volcanic with beautiful beaches and lagoons.", "🌋"},
                        {"Traditional culture, including dance and tattooing, is widely preserved.", "🎎"},
                        {"Coconut and fishing are important for the local economy.", "🥥"}
                }),
                new Country("Tuvalu", "Oceania", "Tuvalu.png", new String[][] {
                        {"Tuvalu is one of the smallest countries in the world by both area and population.", "📏"},
                        {"Funafuti is the capital and main atoll.", "🏙️"},
                        {"The islands are low-lying and vulnerable to rising sea levels.", "🌊"},
                        {"Traditional culture includes navigation, fishing, and dance.", "🎎"},
                        {"Copra production and fishing are key economic activities.", "🥥"}
                }),
                new Country("Vanuatu", "Oceania", "Vanuatu.png", new String[][] {
                        {"Vanuatu is an archipelago of 82 islands in the South Pacific.", "🏝️"},
                        {"Port Vila is the capital, located on Efate island.", "🏙️"},
                        {"The country is volcanic, with active volcanoes and beautiful lava landscapes.", "🌋"},
                        {"Vanuatu has diverse cultures, with over 100 indigenous languages.", "🗣️"},
                        {"Tourism, agriculture, and fishing are major parts of the economy.", "💰"}
                })
        );
    }
}
