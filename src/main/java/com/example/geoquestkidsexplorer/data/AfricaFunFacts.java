package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.Country;
import com.example.geoquestkidsexplorer.repositories.FunFactsProvider;

import java.util.Arrays;
import java.util.List;

/**
 * This class provides curated funfacts for the continents "Africa"
 * */


public class AfricaFunFacts implements FunFactsProvider {

    /**
     * @return a list of entries for Asia dataset used by the UI
     * */

    @Override
    public List<Country> getCountries() {
        return Arrays.asList(
                new Country("Algeria", "Africa", "Algeria.png", new String[][] {
                        {"Algeria is the largest country in Africa, spanning the Sahara Desert and Mediterranean coastline.", "🗺️"},
                        {"The Sahara Desert covers over 80% of Algeria, featuring dunes, oases, and unique desert landscapes.", "🏜️"},
                        {"Algiers, the capital, is known as the 'White City' due to its white buildings and Mediterranean charm.", "🏙️"},
                        {"Algeria gained independence from France in 1962 after a long liberation war, shaping its modern national identity.", "🎉"},
                        {"The country has a rich mix of Arab and Berber cultures, with languages, music, and traditions reflecting this diversity.", "👥"}
                }),
                new Country("Angola", "Africa", "Angola.png", new String[][] {
                        {"Angola is rich in oil and diamond resources, making natural resources a key part of its economy.", "🛢️"},
                        {"Luanda, the capital city, is known for its vibrant culture, colonial architecture, and busy port.", "🏙️"},
                        {"Portuguese is the official language due to Angola's colonial past with Portugal.", "🗣️"},
                        {"The country has diverse ecosystems, including tropical rainforests and savannas.", "🌳"},
                        {"Angola gained independence in 1975 after decades of anti-colonial struggle.", "🎉"}
                }),
                new Country("Botswana", "Africa", "Botswana.png", new String[][] {
                        {"Botswana is home to the Okavango Delta, a UNESCO World Heritage site and one of the largest inland deltas in the world.", "🌊"},
                        {"It is considered one of Africa's most stable and democratic countries.", "🕊️"},
                        {"Gaborone, the capital city, is a modern hub for business and government.", "🏙️"},
                        {"Botswana has large elephant populations and is famous for wildlife conservation.", "🐘"},
                        {"The country is landlocked in Southern Africa, bordered by Namibia, Zimbabwe, and South Africa.", "🌍"}
                }),
                new Country("Central African Republic", "Africa", "CAR.png", new String[][] {
                        {"Central African Republic is rich in natural resources, including diamonds, gold, and timber.", "💎"},
                        {"Bangui is the capital city, located on the banks of the Ubangi River.", "🏙️"},
                        {"The country is landlocked and has a tropical climate with dense rainforests.", "🌴"},
                        {"CAR has a complex history of political instability and conflicts since independence in 1960.", "⚠️"},
                        {"Despite challenges, the country has diverse ethnic groups and rich traditional cultures.", "👥"}
                }),
                new Country("Egypt", "Africa", "Egypt.png", new String[][] {
                        {"Egypt is famous for the Pyramids of Giza and the Great Sphinx, symbols of ancient civilization.", "🕌"},
                        {"The Nile River, the longest in the world, has been Egypt's lifeline for agriculture and settlements.", "🌊"},
                        {"Cairo, the capital, is a bustling metropolis with ancient and modern landmarks.", "🏙️"},
                        {"Egypt has a desert climate, yet the fertile Nile Delta supports cotton, wheat, and other crops.", "🏜️"},
                        {"Egypt has a long history of achievements in writing, medicine, mathematics, and architecture.", "📜"}
                }),
                new Country("Eritrea", "Africa", "Eritrea.png", new String[][] {
                        {"Asmara, the capital, is known for Italian colonial architecture and urban design.", "🏙️"},
                        {"Eritrea gained independence from Ethiopia in 1993 after a 30-year liberation war.", "🎉"},
                        {"It has a Red Sea coastline with beautiful beaches and ports.", "🌊"},
                        {"Tigrinya and Arabic are among the official languages.", "🗣️"},
                        {"Eritrea has diverse landscapes from coastal plains to highlands.", "⛰️"}
                }),
                new Country("Ethiopia", "Africa", "Ethiopia.png", new String[][] {
                        {"Addis Ababa is the political and cultural capital of Ethiopia and hosts the African Union headquarters.", "🏙️"},
                        {"Ethiopia is the birthplace of coffee and has a rich coffee culture.", "☕"},
                        {"It is one of the few African countries never fully colonized, preserving its ancient traditions.", "🛡️"},
                        {"Ethiopia uses its own calendar and time system, different from the Gregorian calendar.", "📅"},
                        {"The country has over 80 ethnic groups, each with unique languages, music, and festivals.", "👥"}
                }),
                new Country("Ghana", "Africa", "Ghana.png", new String[][] {
                        {"Ghana was the first African country to gain independence from colonial rule in 1957.", "🎉"},
                        {"Accra is the capital, known for its lively markets, cultural centers, and coastal beaches.", "🏙️"},
                        {"Ghana is a top producer of cocoa, contributing significantly to the world chocolate industry.", "🍫"},
                        {"It has a rich cultural heritage with festivals, drumming, and traditional dance.", "🎶"},
                        {"Ghana is politically stable and known as a democratic leader in West Africa.", "🕊️"}
                }),
                new Country("Kenya", "Africa", "Kenya.png", new String[][] {
                        {"Kenya is renowned worldwide for its wildlife safaris and national parks such as the Maasai Mara, which hosts the spectacular annual wildebeest migration.", "🦁"},
                        {"The Great Rift Valley runs through Kenya, creating breathtaking landscapes, lakes, and fertile agricultural regions that support farming and tourism.", "🌄"},
                        {"Kenya has over 40 ethnic groups, each with its own language, traditions, and festivals, including the Maasai, Kikuyu, and Luo communities.", "👥"},
                        {"Mount Kenya, the second-highest peak in Africa at 5,199 meters, is a UNESCO World Heritage site and an important source of freshwater for the country.", "⛰️"},
                        {"Kenya has a strong tradition in long-distance running, producing Olympic champions and world record holders, which is a source of national pride and international recognition.", "🏃‍♂️"}
                }),
                new Country("Liberia", "Africa", "Liberia.png", new String[][] {
                        {"Monrovia, the capital, was founded by freed American slaves in the 19th century.", "🏙️"},
                        {"Liberia is Africa's oldest republic, having declared independence in 1847.", "🎉"},
                        {"English is the official language, used in government, schools, and media.", "🗣️"},
                        {"The country has lush tropical forests and rich biodiversity.", "🌴"},
                        {"Liberia has cultural traditions including music, dance, and storytelling.", "🎶"}
                }),
                new Country("Libya", "Africa", "Libya.png", new String[][] {
                        {"Tripoli is the capital and largest city, with a mix of modern and historical architecture.", "🏙️"},
                        {"Libya is largely covered by the Sahara Desert, creating vast desert landscapes.", "🏜️"},
                        {"The country’s economy depends heavily on oil exports, contributing to its GDP.", "🛢️"},
                        {"It has a Mediterranean coastline with important ports and historic trading cities.", "🌊"},
                        {"Libya was part of the Roman Empire, with archaeological sites like Leptis Magna.", "🏛️"}
                }),
                new Country("Madagascar", "Africa", "Madagascar.png", new String[][] {
                        {"Madagascar is the fourth largest island in the world, located in the Indian Ocean.", "🏝️"},
                        {"It is home to unique wildlife, including lemurs, chameleons, and baobab trees.", "🐒"},
                        {"The capital city, Antananarivo, features historic palaces and markets.", "🏙️"},
                        {"Madagascar’s ecosystems range from rainforests to deserts, supporting rich biodiversity.", "🌳"},
                        {"The island separated from Africa millions of years ago, creating unique species.", "🌍"}
                }),
                new Country("Mauritius", "Africa", "Mauritius.png", new String[][] {
                        {"Mauritius is an island nation in the Indian Ocean known for beaches, lagoons, and reefs.", "🏖️"},
                        {"Port Louis is the capital city and commercial hub with colonial architecture.", "🏙️"},
                        {"Mauritius was once home to the now-extinct dodo bird.", "🐦"},
                        {"The population is culturally diverse with Indian, African, European, and Chinese influences.", "👥"},
                        {"Tourism, sugar, and textiles are important parts of Mauritius’ economy.", "💰"}
                }),
                new Country("Morocco", "Africa", "Morocco.png", new String[][] {
                        {"Rabat is the capital city and Casablanca is the economic hub.", "🏙️"},
                        {"Morocco features diverse landscapes including the Sahara Desert, Atlas Mountains, and Atlantic coast.", "🏜️"},
                        {"The country has a rich tradition of arts, music, and craftsmanship.", "🎨"},
                        {"Moroccan cuisine includes couscous, tagine, and mint tea, reflecting a mix of cultural influences.", "🍲"},
                        {"Morocco’s population is a blend of Arab, Berber, and French heritage.", "👥"}
                }),
                new Country("Nigeria", "Africa", "Nigeria.png", new String[][] {
                        {"Abuja is the capital, while Lagos is the largest city and economic hub.", "🏙️"},
                        {"Nigeria is Africa’s most populous country with over 200 million people.", "👥"},
                        {"The country is famous for Nollywood, one of the largest film industries in the world.", "🎬"},
                        {"Nigeria has over 500 languages spoken, reflecting immense cultural diversity.", "🗣️"},
                        {"Oil is the main export and contributes significantly to the economy.", "🛢️"}
                }),
                new Country("Somalia", "Africa", "Somalia.png", new String[][] {
                        {"Mogadishu is the capital and a historic port city.", "🏙️"},
                        {"Somalia has the longest coastline in mainland Africa along the Indian Ocean.", "🌊"},
                        {"The majority of the population is Muslim, influencing cultural and social life.", "🕌"},
                        {"Somalia has a rich tradition of poetry, storytelling, and oral literature.", "📜"},
                        {"The country gained independence in 1960, uniting British Somaliland and Italian Somaliland.", "🎉"}
                }),
                new Country("South Africa", "Africa", "South Africa.png", new String[][] {
                        {"South Africa has three capital cities: Pretoria (administrative), Cape Town (legislative), Bloemfontein (judicial).", "🏙️"},
                        {"It has 11 official languages, reflecting cultural diversity.", "🗣️"},
                        {"The country is famous for Table Mountain, Kruger National Park, and wildlife safaris.", "⛰️"},
                        {"South Africa is a major producer of gold and diamonds, crucial to the economy.", "💎"},
                        {"Apartheid ended in 1994, transitioning the country to a democratic government.", "✊"}
                }),
                new Country("South Sudan", "Africa", "South Sudan.png", new String[][] {
                        {"Juba is the capital city of South Sudan.", "🏙️"},
                        {"South Sudan gained independence from Sudan in 2011, making it the world’s newest country.", "🎉"},
                        {"The White Nile flows through South Sudan, providing water and supporting agriculture.", "🌊"},
                        {"The country has over 60 ethnic groups, each with distinct cultures and languages.", "👥"},
                        {"South Sudan has a tropical climate with vast wetlands and savannas.", "🌴"}
                }),
                new Country("Tanzania", "Africa", "Tanzania.png", new String[][] {
                        {"Dodoma is the official capital, while Dar es Salaam is the economic hub.", "🏙️"},
                        {"Tanzania is home to Mount Kilimanjaro, the highest peak in Africa.", "⛰️"},
                        {"Serengeti National Park hosts the annual wildebeest migration, a world-famous wildlife event.", "🦒"},
                        {"Lake Victoria, Africa’s largest lake, lies partially in Tanzania.", "🌊"},
                        {"Tanzania has over 120 ethnic groups, with Swahili as the national language.", "👥"}
                }),
                new Country("Tunisia", "Africa", "Tunisia.png", new String[][] {
                        {"Tunis is the capital city, known for its historic medina and modern districts.", "🏙️"},
                        {"Tunisia has Mediterranean beaches and the Sahara Desert in the south.", "🏖️"},
                        {"The country was part of the Carthaginian civilization and later the Roman Empire.", "🏛️"},
                        {"Tunisia gained independence from France in 1956.", "🎉"},
                        {"The economy is diverse, with agriculture, tourism, and petroleum exports.", "💰"}
                }),
                new Country("Uganda", "Africa", "Uganda.png", new String[][] {
                        {"Kampala is the capital city of Uganda.", "🏙️"},
                        {"Uganda is known as the 'Pearl of Africa' due to its natural beauty and biodiversity.", "🌳"},
                        {"Lake Victoria, Africa’s largest lake, borders Uganda.", "🌊"},
                        {"The country has diverse wildlife including mountain gorillas and chimpanzees.", "🦍"},
                        {"Uganda has over 50 ethnic groups, each with unique languages and cultural practices.", "👥"}
                }),
                new Country("Zambia", "Africa", "Zambia.png", new String[][] {
                        {"Lusaka is the capital city and main commercial center.", "🏙️"},
                        {"Zambia is home to Victoria Falls, one of the largest waterfalls in the world.", "🌊"},
                        {"The country has abundant wildlife in national parks like South Luangwa.", "🦓"},
                        {"Zambia’s economy relies on copper mining and agriculture.", "⛏️"},
                        {"Zambia gained independence from the UK in 1964 and is a peaceful democracy.", "🎉"}
                }),
                // Add other countries similarly...
                new Country("Zimbabwe", "Africa", "Zimbabwe.png", new String[][] {
                        {"Harare is the capital city of Zimbabwe.", "🏙️"},
                        {"Zimbabwe is home to Victoria Falls, shared with Zambia, one of the Seven Natural Wonders of the World.", "🌊"},
                        {"The country has diverse wildlife, including elephants, lions, and rhinos.", "🦁"},
                        {"Great Zimbabwe is an ancient city that was the center of a powerful kingdom.", "🏛️"},
                        {"Zimbabwe’s economy relies on agriculture, mining, and tourism.", "💰"}
                })
        );
    }
}