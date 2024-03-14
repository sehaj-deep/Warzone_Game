package phases;

import game.GameEngineNew;
import game.GameState;
import game.Player;

import java.util.*;


public class PlaySetup extends Play {
    public PlaySetup(GameEngineNew p_gameEngine) {
        super(p_gameEngine);
    }

    private final List<String> d_playerNameList = new ArrayList<>();

    /**
     * Adds a player to the player list.
     *
     * @param p_playerName The name of the player to add.
     */
    @Override
    public void addPlayers( String p_playerName) {
        if (p_playerName == null || p_playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }

        if (!p_playerName.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("Invalid characters are not allowed");
        }

        if (d_playerNameList.contains(p_playerName)) {
            throw new IllegalArgumentException("Player " + p_playerName + " already exists");
        }

        d_playerNameList.add(p_playerName);

        Player l_player = new Player(p_playerName);
        d_gameEngine.getD_players().add(l_player);

        System.out.println("Player: " + p_playerName + " successfully added ");

    }

    /**
     * Removes a player from the player list.
     *
     * @param p_playerName The name of the player to remove.
     */
    @Override
    public void removePlayers(String p_playerName) {
        if (p_playerName == null || p_playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }

        if (!d_playerNameList.contains(p_playerName)) {
            System.out.println(d_playerNameList.size());
            for (String p : d_playerNameList) {
                System.out.println(p);
            }
            throw new IllegalArgumentException("Player " + p_playerName + " not found");
        }

        d_playerNameList.remove(p_playerName);

        d_playerNameList.add(p_playerName);

        Player l_player = new Player(p_playerName);
        d_gameEngine.getD_players().remove(l_player);

        System.out.println("Player: " + p_playerName + " successfully removed");
    }

    /**
     * Checks validity of assigned countries if a player has 2 or more countries
     * assigned than another player has, then this is invalid
     *
     * @param p_state current game state
     * @return true if valid. false if invalid
     */
    public boolean isAssignCountriesValid(GameState p_state) {
        int l_minSize = p_state.getPlayers().get(0).getOwnership().size(); // min of number of player's owned countries
        int l_maxSize = p_state.getPlayers().get(0).getOwnership().size(); // max of number of player's owned countries

        for (int i = 1; i < p_state.getPlayers().size(); i++) {
            Player l_player = p_state.getPlayers().get(i);
            int l_numCountriesOwned = l_player.getOwnership().size();
            if (l_numCountriesOwned < l_minSize) {
                l_minSize = l_numCountriesOwned;
            }
            if (l_numCountriesOwned > l_maxSize) {
                l_maxSize = l_numCountriesOwned;
            }
        }
        return (l_maxSize - l_minSize) <= 1;
    }

    /**
     * Assigns countries to the specified player.
     */
    @Override
    public void assignCountries() {

        Set<String> p_countries = d_gameEngine.getD_countries().keySet();

        List<String> l_countriesList = new ArrayList<>(p_countries);

        // Shuffle the list of available countries
        Collections.shuffle(l_countriesList);

        for (int i = 0; i < l_countriesList.size(); i++) {
            // Add assigned country to player's countries (d_ownership)
            int l_idx = i % (d_gameEngine.getD_players().size());
            Player l_player = d_gameEngine.getD_players().get(l_idx);
            l_player.conquerCountry(l_countriesList.get(i));
        }

        System.out.println("Assign Countries Completed");
    }


    @Override
    public void reinforce() {
        this.printInvalidCommandMessage();
    }

    @Override
    public void attack() {
        this.printInvalidCommandMessage();
    }

    @Override
    public void fortify() {
        this.printInvalidCommandMessage();
    }

    @Override
    public void loadMap(String p_filename) {
        this.printInvalidCommandMessage();
    }

    @Override
    public void editMap(String p_filename) {
        this.printInvalidCommandMessage();
    }

    @Override
    public void addContinent(String p_continentName, int p_bonusArmies) {
        this.printInvalidCommandMessage();
    }

    @Override
    public void removeContinent(String p_continentName) {
        this.printInvalidCommandMessage();
    }

    @Override
    public void addCountry(String p_countryName, String p_continent) {
        this.printInvalidCommandMessage();
    }

    @Override
    public void removeCountry(String p_countryName) {
        this.printInvalidCommandMessage();
    }

    @Override
    public void addNeighbor(String p_country, String p_neighbor) {
        this.printInvalidCommandMessage();
    }

    @Override
    public void removeNeighbor(String p_country, String p_neighbor) {
        this.printInvalidCommandMessage();
    }

    @Override
    public void saveMap(String p_filename) {
        this.printInvalidCommandMessage();
    }

    @Override
    public void next() {
        // TODO Uncomment when reinforcement phase is implemented
        // d_gameEngine.setPhase(new PostLoad(d_gameEngine));
    }

}