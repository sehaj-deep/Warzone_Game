package phases;

import game.GameEngineNew;
import game.Player;

import java.util.*;


public class PlaySetup extends Play {
    public PlaySetup(GameEngineNew p_gameEngine) {
        super(p_gameEngine);
    }

    GameEngineNew gameEngineNew;

    private List<Player> d_players; // list of all players in the game

    /**
     * getter function for a list of players playing the game
     *
     * @return d_players a list of players playing the game
     */
    public List<Player> getPlayers() {
        return d_players;
    }

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

        if (gameEngineNew.getD_playerNameList().contains(p_playerName)) {
            throw new IllegalArgumentException("Player " + p_playerName + " already exists");
        }

        gameEngineNew.setD_playerNameList(p_playerName, true);

        Player l_player = new Player(p_playerName);
        getPlayers().add(l_player);

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

        if (!gameEngineNew.getD_playerNameList().contains(p_playerName)) {
            System.out.println(gameEngineNew.getD_playerNameList().size());
            for (String p : gameEngineNew.getD_playerNameList()) {
                System.out.println(p);
            }
            throw new IllegalArgumentException("Player " + p_playerName + " not found");
        }

        gameEngineNew.setD_playerNameList(p_playerName, false);

        Player l_player = new Player(p_playerName);
        getPlayers().remove(l_player);

        System.out.println("Player: " + p_playerName + " successfully removed");
    }

    /**
     * Assigns countries to the specified player.
     */
    @Override
    public void assignCountries() {

        Set<String> p_countries = gameEngineNew.getD_countries().keySet();

        // check if countries is valid
        int l_minSize = getPlayers().get(0).getOwnership().size(); // min of number of player's owned countries
        int l_maxSize = getPlayers().get(0).getOwnership().size(); // max of number of player's owned countries

        for (int i = 1; i < getPlayers().size(); i++) {
            Player l_player = getPlayers().get(i);
            int l_numCountriesOwned = l_player.getOwnership().size();
            if (l_numCountriesOwned < l_minSize) {
                l_minSize = l_numCountriesOwned;
            }
            if (l_numCountriesOwned > l_maxSize) {
                l_maxSize = l_numCountriesOwned;
            }
        }

        boolean isValid = (l_maxSize - l_minSize) <= 1;

        List<String> l_countriesList = new ArrayList<>(p_countries);

        // Shuffle the list of available countries
        Collections.shuffle(l_countriesList);

        for (int i = 0; i < l_countriesList.size(); i++) {
            // Add assigned country to player's countries (d_ownership)
            int l_idx = i % (getPlayers().size());
            Player l_player = getPlayers().get(l_idx);
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