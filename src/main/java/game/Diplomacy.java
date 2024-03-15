package game;

public class Diplomacy implements Order {
    // Player class
    Player player;

    // Target player name
    String d_targetPlayer;

    /**
     * Constructor for Diplomacy class
     *
     * @param d_targetPlayer  name of the opponent player
     */
    public Diplomacy(String d_targetPlayer) {
        this.d_targetPlayer = d_targetPlayer;
    }

    /**
     * This class return boolean value after checking if the command is valid
     *
     * @param p_state       is the current game state storing how many armies in
     *                      country, player, etc
     * @param p_playerId    is the id of the player who gave this order
     * @return the boolean value true if the command is valid else false
     */
    @Override
    public boolean isValidIssue(GameState p_state, int p_playerId) {

        Player d_issuingPlayer = p_state.getPlayers().get(p_playerId);

        // if the player doesn't have the card
        if(d_issuingPlayer.getCardCount("Diplomacy") < 1) {
            System.err.println("Diplomacy card is not available to use");
            return false;
        }

        // if the player is inputting null name
        if (d_targetPlayer == null || d_targetPlayer.trim().isEmpty()) {
            System.err.println("Target player name cannot be empty");
            return false;
        }

        // if the opponent player doesn't exist
        if (!player.getPlayerName().contains(d_targetPlayer.trim())) {
            System.err.println("Player " + d_targetPlayer + " doesn't exist");
            return false;
        }

        return true;
    }

    /**
     * Execute execution order if the commands are valid
     *
     * @param p_state    is the game state at the current moment
     * @param p_playerId the id of player gave this order
     */
    @Override
    public void execute(GameState p_state, int p_playerId) {
        Player l_issuingPlayer = p_state.getPlayers().get(p_playerId);
        Player l_targetPlayer = new Player(d_targetPlayer);

        l_targetPlayer.addNegotiatedPlayer(l_issuingPlayer);
        l_issuingPlayer.addNegotiatedPlayer(l_targetPlayer);
        l_issuingPlayer.getD_listOfCards().remove("Diplomacy");

        System.out.println(l_issuingPlayer.getPlayerName() + " is negotiated with " + d_targetPlayer);
    }

    /**
     * return the current order state
     */
    public String currentOrder() {
        return "Diplomacy";
    }


    @Override
    public boolean isValidExecute(GameState p_state, int p_playerId) {
        return false;
    }

    @Override
    public void changeGameState(GameState p_state, int p_playerId) {
    }

    @Override
    public void addOrderID(String p_id) {
    }

    @Override
    public String toString() {
        return null;
    }
}
