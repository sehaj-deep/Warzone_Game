package phases;

import game.GameEngineNew;
import game.Player;

import java.util.*;


public class PlaySetup extends Play {
    public PlaySetup(GameEngineNew p_gameEngine) {
        super(p_gameEngine);
    }

    private List<Player> d_players; // list of all players in the game

    // list of available reinforcements for all players. Order same as d_players
    public List<Integer> d_reinforcements;
    public Map<String, Integer> d_board; // key: country name. value: number of army in the country
    public String[] d_orderInput; // a list of tokens given in the user input command to issue an or

    /**
     * Parameterized Constructor
     *
     * @param p_players is a list of players playing the game
     */
    public void GameState(List<Player> p_players) {
        d_players = p_players;
        d_reinforcements = new ArrayList<>();
        d_board = new HashMap<String, Integer>();
    }

    /**
     * getter function for a list of players playing the game
     *
     * @return d_players a list of players playing the game
     */
    public List<Player> getPlayers() {
        return d_players;
    }

    String p_playerName; // assume
    @Override
    public void addPlayers() {
        if (p_playerName == null || p_playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }

        if (!p_playerName.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("Invalid characters are not allowed");
        }

        if (PlaySetup.d_playerNameList.contains(p_playerName)) {
            throw new IllegalArgumentException("Player " + p_playerName + " already exists");
        }

        PlaySetup.d_playerNameList.add(p_playerName);

        Player l_player = new Player(p_playerName);
        getPlayers().add(l_player);

        System.out.println("Player: " + p_playerName + " successfully added ");

    }

    @Override
    public void removePlayers() {
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

        Player l_player = new Player(p_playerName);
        getPlayers().remove(l_player);

        System.out.println("Player: " + p_playerName + " successfully removed");
    }

    GameEngineNew p_gMap;
    @Override
    public void assignCountries() {

        Set<String> p_countries = p_gMap.getD_countries().keySet();

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