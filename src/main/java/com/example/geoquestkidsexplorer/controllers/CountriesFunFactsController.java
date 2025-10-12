package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.data.AfricaFunFacts;
import com.example.geoquestkidsexplorer.data.AsiaFunFacts;
import com.example.geoquestkidsexplorer.data.EuropeFunFacts;
import com.example.geoquestkidsexplorer.data.NorthAmericaFunFacts;
import com.example.geoquestkidsexplorer.data.SouthAmericaFunFacts;
import com.example.geoquestkidsexplorer.data.OceaniaFunFacts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.util.Objects; // Added for the Null check in createCountryTile

import java.io.IOException;

public class CountriesFunFactsController {

    @FXML
    private FlowPane countryTilesContainer; // NEW: To hold the country tiles dynamically
    @FXML private Label continentFactsWelcomeLabel;
    @FXML
    private VBox factsContainer;

    // A field to hold the name of the continent loaded (Africa, Asia, etc.)
    private String currentContinent;
    // Adding new field to store the correct flag subdirectory path (e.g., "africanflags/")
    private String flagDirectoryPath;

    /**
     * Called by FunFactsController to initialize the view with the correct continent data.
     */
    public void setContinent(String continent) {
        this.currentContinent = continent;
        if (currentContinent == null || currentContinent.trim().isEmpty()) {
            System.err.println("Continent name is null or empty. Skipping quiz load.");
            continentFactsWelcomeLabel.setText("Error: No continent selected.");
            return;
        }
        continentFactsWelcomeLabel.setText("Fun Facts for " + currentContinent + " Continent!");

        // Populate the left-side tiles
        loadContinentCountryTiles();
    }

    /**
     * Populates the left-side FlowPane with country tiles for the current continent.
     * NOTE: This is a simplified example. You'd need a utility to fetch tiles for ALL continents.
     * For now, it just shows a placeholder to prove the dynamic nature.
     */
    private void loadContinentCountryTiles() {
        // *** DYNAMIC TILE GENERATION ***
        // Instead of having static tiles in FXML, the controller now generates them.

        // For demonstration, let's create a placeholder tile for each country you had statically:
        String[][] tiles = getCountryTilesForContinent(currentContinent); // Calls a helper method below
        this.flagDirectoryPath = getFlagDirectoryForContinent(currentContinent); // <--- GETS THE PATH

        // Check for missing data/path
        if (tiles == null || flagDirectoryPath == null) {
            System.err.println("Error: Could not retrieve tiles or flag path for " + currentContinent);
            countryTilesContainer.getChildren().clear();
            countryTilesContainer.getChildren().add(new Label("No country data available."));
            return;
        }

        countryTilesContainer.getChildren().clear();

        // Build the tiles using the dynamically set flagDirectoryPath
        for (String[] tile : tiles) {
            // tile[0] = Country Name, tile[1] = Flag Filename
            VBox countryBox = createCountryTile(tile[0], tile[1]);
            countryTilesContainer.getChildren().add(countryBox);
        }

        // Initial right-side prompt
        factsContainer.getChildren().clear();
        Label promptLabel = new Label("Select a country from " + currentContinent + " to see facts!");
        promptLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #3b82f6; -fx-padding: 20px;");
        promptLabel.setAlignment(Pos.CENTER);
        factsContainer.getChildren().add(promptLabel);
    }

    /**
     * Helper method to map a continent name to its associated background color hex code.
     */
    private String getContinentColor(String continent) {
        return switch (continent) {
            case "Africa" -> "#c8e6c9";
            case "Asia" -> "#ffe0b2";
            case "Europe" -> "#b3e5fc";
            case "North America" -> "#ffcdd2";
            case "South America" -> "#e6ee9c";
            case "Oceania" -> "#b2ebf2";
            default -> "#ffffff"; // Default to white if continent is unknown
        };
    }

    /**
     * Creates a single clickable VBox tile for a country.
     */
    private VBox createCountryTile(String countryName, String flagFileName) {
        VBox countryBox = new VBox(5);
        countryBox.setAlignment(Pos.CENTER);
        countryBox.setPrefHeight(200.0);
        countryBox.setPrefWidth(200.0);
        // --- THE COLOR FIX IS HERE ---
        String backgroundColor = getContinentColor(this.currentContinent);
        countryBox.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 15px; " +
                "-fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        countryBox.setOnMouseClicked(this::loadCountryFacts); // Important: Sets the click handler

        // Load the Image using the resource path
        // The flagFileName parameter should contain the full filename (e.g., "Algeria.png")
        // Construct the resource path using the dynamic flagDirectoryPath field
        String imagePath = "/com/example/geoquestkidsexplorer/" + this.flagDirectoryPath + flagFileName;

        ImageView flagImageView;
        try {
            // Load the image resource
            Image flagImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));

            // Create and configure the ImageView
            flagImageView = new ImageView(flagImage);
            flagImageView.setFitWidth(100.0);
            flagImageView.setFitHeight(65.0);
            flagImageView.setStyle("-fx-padding: 10px;");

        } catch (Exception e) {
            // Handle error if image file is not found (this caused the Oceania error)
            System.err.println("ERROR: Could not load image for " + countryName + ". Path: " + imagePath);

            // Create a fallback Label instead of an ImageView
            Label errorLabel = new Label("🚩");
            errorLabel.setStyle("-fx-font-size: 50px; -fx-padding: 10px;");
            countryBox.getChildren().addAll(errorLabel, new Label(countryName));
            return countryBox; // Return the tile with the fallback label
        }

        Label nameLabel = new Label(countryName);
        nameLabel.setAlignment(Pos.CENTER);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");

        // Add the ImageView and Label to the VBox
        countryBox.getChildren().addAll(flagImageView, nameLabel);
        return countryBox;
    }

    /**
     * Unified handler for country clicks. Determines which country was clicked based on the source node,
     * fetches the facts from AfricaFunFacts, and displays them in the factsContainer as individual VBoxes.
     */
    @FXML
    private void loadCountryFacts(MouseEvent event) {
        Node source = (Node) event.getSource();
        if (source instanceof VBox) {
            VBox countryBox = (VBox) source;
            Node nameNode = countryBox.getChildren().get(1);
            if (nameNode instanceof Label) {
                String country = ((Label) nameNode).getText();

                // Clear existing facts
                factsContainer.getChildren().clear();

                // Add country title (centered at top)
                Label countryTitle = new Label(country + " Fun Facts");
                countryTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #333333;");
                countryTitle.setAlignment(Pos.CENTER);
                VBox.setMargin(countryTitle, new Insets(0, 0, 5, 0)); // Reduced margin for tighter spacing
                factsContainer.getChildren().add(countryTitle);

                // Fetch facts based on country
                String[][] facts = getFactsForCountry(country);

                if (facts != null) {
                    for (int i = 0; i < facts.length; i++) {
                        String[] fact = facts[i];
                        VBox factBox = createFactVBox(fact[0], fact[1]);
                        factsContainer.getChildren().add(factBox);
                    }
                } else {
                    Label noFactsLabel = new Label("No facts available for " + country);
                    noFactsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #999999; -fx-max-height: none; -fx-max-width: none;");
                    noFactsLabel.setAlignment(Pos.CENTER);
                    factsContainer.getChildren().add(noFactsLabel);
                }
            }
        }
    }

    /**
     * Creates a VBox for a single fact, with icon (emoji) at the top and text below.
     * No fixed height—grows fully with content. Reduced spacing to minimize gap between icon and text.
     * Explicitly forces textLabel to expand without constraints for full visibility.
     */
    private VBox createFactVBox(String text, String icon) {
        VBox factBox = new VBox(0); // Reduced spacing to 5px to minimize gap between icon and text
        factBox.setPrefWidth(380.0);
        factBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px; -fx-border-color: #e0e0e0; " +
                "-fx-border-width: 1px; -fx-border-radius: 10px; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.05), 3, 0, 0, 2);");
        factBox.setAlignment(Pos.CENTER);
        factBox.setFillWidth(true);

        // Icon (emoji) label (large, centered, with minimal padding)
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 50px; -fx-padding: 10px 10px 0 10px;");
        iconLabel.setAlignment(Pos.CENTER);
        iconLabel.setPrefWidth(380.0);
        VBox.setVgrow(iconLabel, Priority.NEVER); // Icon doesn't grow—fixed size

        // Fact text (wrap and fill all remaining space in factBox, no clipping/ellipsis)
        Label textLabel = new Label(text);
        textLabel.setWrapText(true);
        textLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555555; -fx-padding: 5px 15px 15px 15px;");
        textLabel.setAlignment(Pos.CENTER); // Left-align for better readability
        textLabel.setPrefWidth(360.0); // Inner width for padding
        VBox.setVgrow(textLabel, Priority.NEVER); // Takes all available space after icon

        factBox.getChildren().addAll(iconLabel, textLabel);
        // Add margin for separation in the factsContainer
        VBox.setMargin(factBox, new Insets(5, 0, 5, 0)); // 5px top/bottom margin

        return factBox;
    }

    // NEW HELPER METHOD to get the correct flag path based on the continent
    private String getFlagDirectoryForContinent(String continent) {
        // IMPORTANT: The return value must match your folder structure exactly, including the final slash.
        return switch (continent) {
            case "Africa" -> "africanflags/";
            case "Asia" -> "asiaflags/";
            case "Europe" -> "europeflags/";
            case "North America" -> "northamericaflags/";
            case "South America" -> "southamericaflags/";
            case "Oceania" -> "oceaniaflags/";
            default -> null; // Return null if the continent is not recognized (e.g., Antarctica, which uses a different FXML)
        };
    }

    /**
     * Helper method to fetch facts array based on country name.
     * Uses a switch statement for mapping; can be refactored if needed.
     */
    private String[][] getFactsForCountry(String country) {
        switch (country) {
            case "Algeria": return AfricaFunFacts.getalgeriaFacts();
            case "Angola": return AfricaFunFacts.getangolaFacts();
            case "Botswana": return AfricaFunFacts.getbotswanaFacts();
            case "Central African Republic": return AfricaFunFacts.getcentralAfricanRepublicFacts();
            case "Egypt": return AfricaFunFacts.getegyptFacts();
            case "Eritrea": return AfricaFunFacts.geteritreaFacts();
            case "Ethiopia": return AfricaFunFacts.getethiopiaFacts();
            case "Ghana": return AfricaFunFacts.getghanaFacts();
            case "Kenya": return AfricaFunFacts.getkenyaFacts();
            case "Liberia": return AfricaFunFacts.getliberiaFacts();
            case "Libya": return AfricaFunFacts.getlibyaFacts();
            case "Madagascar": return AfricaFunFacts.getmadagascarFacts();
            case "Mauritius": return AfricaFunFacts.getmauritiusFacts();
            case "Morocco": return AfricaFunFacts.getmoroccoFacts();
            case "Nigeria": return AfricaFunFacts.getnigeriaFacts();
            case "Somalia": return AfricaFunFacts.getsomaliaFacts();
            case "South Africa": return AfricaFunFacts.getsouthAfricaFacts();
            case "South Sudan": return AfricaFunFacts.getsouthSudanFacts();
            case "Tanzania": return AfricaFunFacts.gettanzaniaFacts();
            case "Tunisia": return AfricaFunFacts.gettunisiaFacts();
            case "Uganda": return AfricaFunFacts.getugandaFacts();
            case "Zambia": return AfricaFunFacts.getzambiaFacts();
            case "Zimbabwe": return AfricaFunFacts.getzimbabweFacts();

            case "Afghanistan": return AsiaFunFacts.getafghanistanFacts();
            case "Bahrain": return AsiaFunFacts.getbahrainFacts();
            case "Bangladesh": return AsiaFunFacts.getbangladeshFacts();
            case "Brunei": return AsiaFunFacts.getbruneiFacts();
            case "Cambodia": return AsiaFunFacts.getcambodiaFacts();
            case "China": return AsiaFunFacts.getchinaFacts();
            case "India": return AsiaFunFacts.getindiaFacts();
            case "Indonesia": return AsiaFunFacts.getindonesiaFacts();
            case "Israel": return AsiaFunFacts.getisraelFacts();
            case "Japan": return AsiaFunFacts.getjapanFacts();
            case "Lebanon": return AsiaFunFacts.getlebanonFacts();
            case "Malaysia": return AsiaFunFacts.getmalaysiaFacts();
            case "Maldives": return AsiaFunFacts.getmaldivesFacts();
            case "Nepal": return AsiaFunFacts.getnepalFacts();
            case "Philippines": return AsiaFunFacts.getphilippinesFacts();
            case "Qatar": return AsiaFunFacts.getqatarFacts();
            case "Saudi Arabia": return AsiaFunFacts.getsaudiArabiaFacts();
            case "South Korea": return AsiaFunFacts.getsouthKoreaFacts();
            case "Sri Lanka": return AsiaFunFacts.getsriLankaFacts();
            case "Thailand": return AsiaFunFacts.getthailandFacts();
            case "Turkey": return AsiaFunFacts.getturkeyFacts();
            case "United Arab Emirates": return AsiaFunFacts.getUAEFacts();
            case "Vietnam": return AsiaFunFacts.getvietnamFacts();

            case "Austria": return EuropeFunFacts.getAustriaFacts();
            case "Belgium": return EuropeFunFacts.getBelgiumFacts();
            case "Bulgaria": return EuropeFunFacts.getBulgariaFacts();
            case "Croatia": return EuropeFunFacts.getCroatiaFacts();
            case "Denmark": return EuropeFunFacts.getDenmarkFacts();
            case "Finland": return EuropeFunFacts.getFinlandFacts();
            case "France": return EuropeFunFacts.getFranceFacts();
            case "Germany": return EuropeFunFacts.getGermanyFacts();
            case "Ireland": return EuropeFunFacts.getIrelandFacts();
            case "Greece": return EuropeFunFacts.getGreeceFacts();
            case "Italy": return EuropeFunFacts.getItalyFacts();
            case "Luxembourg": return EuropeFunFacts.getLuxembourgFacts();
            case "Malta": return EuropeFunFacts.getMaltaFacts();
            case "Netherlands": return EuropeFunFacts.getNetherlandsFacts();
            case "Norway": return EuropeFunFacts.getNorwayFacts();
            case "Poland": return EuropeFunFacts.getPolandFacts();
            case "Portugal": return EuropeFunFacts.getPortugalFacts();
            case "Romania": return EuropeFunFacts.getRomaniaFacts();
            case "Serbia": return EuropeFunFacts.getSerbiaFacts();
            case "Spain": return EuropeFunFacts.getSpainFacts();
            case "Sweden": return EuropeFunFacts.getSwedenFacts();
            case "Switzerland": return EuropeFunFacts.getSwitzerlandFacts();
            case "United Kingdom": return EuropeFunFacts.getUnitedKingdomFacts();

            case "Antigua And Barbuda": return NorthAmericaFunFacts.getantiguaAndBarbudaFacts();
            case "Bahamas": return NorthAmericaFunFacts.getbahamasFacts();
            case "Barbados": return NorthAmericaFunFacts.getbarbadosFacts();
            case "Belize": return NorthAmericaFunFacts.getbelizeFacts();
            case "Canada": return NorthAmericaFunFacts.getcanadaFacts();
            case "Costa Rica": return NorthAmericaFunFacts.getcostaRicaFacts();
            case "Cuba": return NorthAmericaFunFacts.getcubaFacts();
            case "Dominica": return NorthAmericaFunFacts.getdominicaFacts();
            case "Dominican Republic": return NorthAmericaFunFacts.getdominicanRepublicFacts();
            case "El Salvador": return NorthAmericaFunFacts.getelSalvadorFacts();
            case "Grenada": return NorthAmericaFunFacts.getgrenadaFacts();
            case "Guatemala": return NorthAmericaFunFacts.getguatemalaFacts();
            case "Haiti": return NorthAmericaFunFacts.gethaitiFacts();
            case "Honduras": return NorthAmericaFunFacts.gethondurasFacts();
            case "Jamaica": return NorthAmericaFunFacts.getjamaicaFacts();
            case "Mexico": return NorthAmericaFunFacts.getmexicoFacts();
            case "Nicaragua": return NorthAmericaFunFacts.getnicaraguaFacts();
            case "Panama": return NorthAmericaFunFacts.getpanamaFacts();
            case "Saint Kitts And Nevis": return NorthAmericaFunFacts.getsaintKittsAndNevisFacts();
            case "Saint Lucia": return NorthAmericaFunFacts.getsaintLuciaFacts();
            case "Saint Vincent And The Grenadines": return NorthAmericaFunFacts.getsaintVincentAndTheGrenadinesFacts();
            case "Trinidad And Tobago": return NorthAmericaFunFacts.gettrinidadAndTobagoFacts();
            case "United States of America": return NorthAmericaFunFacts.getunitedStatesFacts();

            case "Argentina": return SouthAmericaFunFacts.getargentinaFacts();
            case "Bolivia": return SouthAmericaFunFacts.getboliviaFacts();
            case "Brazil": return SouthAmericaFunFacts.getbrazilFacts();
            case "Chile": return SouthAmericaFunFacts.getchileFacts();
            case "Colombia": return SouthAmericaFunFacts.getcolombiaFacts();
            case "Ecuador": return SouthAmericaFunFacts.getecuadorFacts();
            case "Guyana": return SouthAmericaFunFacts.getguyanaFacts();
            case "Paraguay": return SouthAmericaFunFacts.getparaguayFacts();
            case "Peru": return SouthAmericaFunFacts.getperuFacts();
            case "Suriname": return SouthAmericaFunFacts.getsurinameFacts();
            case "Uruguay": return SouthAmericaFunFacts.geturuguayFacts();
            case "Venezuela": return SouthAmericaFunFacts.getvenezuelaFacts();

            case "Australia": return OceaniaFunFacts.getaustraliaFacts();
            case "Fiji": return OceaniaFunFacts.getfijiFacts();
            case "Kiribati": return OceaniaFunFacts.getkiribatiFacts();
            case "Marshall Islands": return OceaniaFunFacts.getmarshallIslandsFacts();
            case "Micronesia": return OceaniaFunFacts.getmicronesiaFacts();
            case "Nauru": return OceaniaFunFacts.getnauruFacts();
            case "New Zealand": return OceaniaFunFacts.getnewZealandFacts();
            case "Palau": return OceaniaFunFacts.getpalauFacts();
            case "Papua New Guinea": return OceaniaFunFacts.getpapuaNewGuineaFacts();
            case "Samoa": return OceaniaFunFacts.getsamoaFacts();
            case "Solomon Islands": return OceaniaFunFacts.getsolomonIslandsFacts();
            case "Tonga": return OceaniaFunFacts.gettongaFacts();
            case "Tuvalu": return OceaniaFunFacts.gettuvaluFacts();
            case "Vanuatu": return OceaniaFunFacts.getvanuatuFacts();
            default: return null;
        }
    }

    /**
     * Helper method to fetch country tiles for a continent (MUST BE IMPLEMENTED FOR ALL CONTINENTS).
     * This is needed since the FXML is now dynamic.
     */
    private String[][] getCountryTilesForContinent(String continent) {
        // You MUST implement this to return {CountryName, Emoji} for ALL multi-country continents
        if ("Africa".equals(continent)) {
            // Placeholder: Manually list the tiles you had in the FXML
            return new String[][] {
                    {"Algeria", "Algeria.png"}, {"Angola", "Angola.png"}, {"Botswana", "Botswana.png"},
                    {"Central African Republic", "CAR.png"}, {"Egypt", "Egypt.png"}, {"Eritrea", "Eritrea.png"},
                    {"Ethiopia", "Ethiopia.png"}, {"Ghana", "Ghana.png"}, {"Kenya", "Kenya.png"}, {"Liberia", "Liberia.png"},
                    {"Libya", "Libya.png"}, {"Madagascar", "Madagascar.png"}, {"Mauritius", "Mauritius.png"},
                    {"Morocco", "Morocco.png"}, {"Nigeria", "Nigeria.png"}, {"Somalia", "Somalia.png"},
                    {"South Africa", "South Africa.png"}, {"South Sudan", "South Sudan.png"}, {"Tanzania", "Tanzania.png"},
                    {"Tunisia", "Tunisia.png"}, {"Uganda", "Uganda.png"}, {"Zambia", "Zambia.png"}, {"Zimbabwe", "Zimbabwe.png"}
            };
        } else if ("Asia".equals(continent)) {
            return  new String[][] {
                    {"Afghanistan", "Afghanistan.png"}, {"Bahrain", "Bahrain.png"}, {"Bangladesh", "Bangladesh.png"},
                    {"Brunei", "Brunei.png"}, {"Cambodia", "Cambodia.png"}, {"China", "China.png"}, {"India", "India.png"},
                    {"Indonesia", "Indonesia.png"}, {"Israel", "Israel.png"}, {"Japan", "Japan.png"}, {"Lebanon", "Lebanon.png"},
                    {"Malaysia", "Malaysia.png"}, {"Maldives", "Maldives.png"}, {"Nepal", "Nepal.png"},
                    {"Philippines", "Philippines.png"}, {"Qatar", "Qatar.png"}, {"Saudi Arabia", "Saudi Arabia.png"},
                    {"South Korea", "South Korea.png"}, {"Sri Lanka", "Sri Lanka.png"}, {"Thailand", "Thailand.png"},
                    {"Turkey", "Turkey.png"}, {"United Arab Emirates", "United Arab Emirates.png"}, {"Vietnam", "Vietnam.png"}
            };
        } else if ("Europe".equals(continent)) {
            return new String[][] {
                    {"Austria", "Austria.png"}, {"Belgium", "Belgium.png"}, {"Bulgaria", "Bulgaria.png"}, {"Croatia", "Croatia.png"},
                    {"Denmark", "Denmark.png"}, {"Finland", "Finland.png"}, {"France", "France.png"}, {"Germany", "Germany.png"},
                    {"Greece", "Greece.png"}, {"Ireland", "Ireland.png"}, {"Italy", "Italy.png"}, {"Luxembourg", "Luxembourg.png"},
                    {"Malta", "Malta.png"}, {"Netherlands", "Netherlands.png"}, {"Norway", "Norway.png"}, {"Poland", "Poland.png"},
                    {"Portugal", "Portugal.png"}, {"Romania", "Romania.png"}, {"Serbia", "Serbia.png"}, {"Spain", "Spain.png"},
                    {"Sweden", "Sweden.png"}, {"Switzerland", "Switzerland.png"}, {"United Kingdom", "United Kingdom.png"}
            };
        } else if ("North America".equals(continent)) {
            return new String[][] {
                    {"Antigua And Barbuda", "Antigua and Barbuda.png"}, {"Bahamas", "Bahamas.png"}, {"Barbados", "Barbados.png"},
                    {"Belize", "Belize.png"}, {"Canada", "Canada.png"}, {"Costa Rica", "Costa Rica.png"}, {"Cuba", "Cuba.png"},
                    {"Dominica", "Dominica.png"}, {"Dominican Republic", "Dominican Republic.png"}, {"El Salvador", "El Salvador.png"},
                    {"Grenada", "Grenada.png"}, {"Guatemala", "Guatemala.png"}, {"Haiti", "Haiti.png"}, {"Honduras", "Honduras.png"},
                    {"Jamaica", "Jamaica.png"}, {"Mexico", "Mexico.png"}, {"Nicaragua", "Nicaragua.png"}, {"Panama", "Panama.png"},
                    {"Saint Kitts And Nevis", "Saint Kitts and Nevis.png"}, {"Saint Lucia", "Saint Lucia.png"},
                    {"Saint Vincent And The Grenadines", "Saint Vincent and the Grenadines.png"},
                    {"Trinidad And Tobago", "Trinidad and Tobago.png"}, {"United States of America", "United State of America.png"}
            };
        } else if ("South America".equals(continent)) {
            return new String[][] {
                    {"Argentina", "Argentina.png"}, {"Bolivia", "Bolivia.png"}, {"Brazil", "Brazil.png"}, {"Chile", "Chile.png"},
                    {"Colombia", "Colombia.png"}, {"Ecuador", "Ecuador.png"}, {"Guyana", "Guyana.png"}, {"Paraguay", "Paraguay.png"},
                    {"Peru", "Peru.png"}, {"Suriname", "Suriname.png"}, {"Uruguay", "Uruguay.png"}, {"Venezuela", "Venezuela.png"}
            };
        } else if ("Oceania".equals(continent)) {
            return new String[][] {
                    {"Australia", "Australia.png"}, {"Fiji", "Fiji.png"}, {"Kiribati", "Kiribati.png"},
                    {"Marshall Islands", "Marshall Islands.png"}, {"Micronesia", "Micronesia.png"}, {"Nauru", "Nauru.png"},
                    {"New Zealand", "New Zealand.png"}, {"Palau", "Palau.png"}, {"Papua New Guinea", "Papua New Guinea.png"},
                    {"Samoa", "Samoa.png"}, {"Solomon Islands", "Solomon Islands.png"}, {"Tonga", "Tonga.png"},
                    {"Tuvalu", "Tuvalu.png"}, {"Vanuatu", "Vanuatu.png"}
            };

        }
        // Add cases for Asia, Europe, etc.
        return null;
    }

    /**
     * Handles the "Back to Continents" button action.
     * This method will transition the user back to the fun facts continents page.
     */
    @FXML
    private void backToFunFacts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/funFacts.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}