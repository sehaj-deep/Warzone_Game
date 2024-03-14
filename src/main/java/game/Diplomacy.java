package game;

public class Diplomacy {
    // Player class
    Player player;

    // Let assume player name for time being
    String d_issuingPlayer;

    // Let assume target player name for time being
    String d_targetPlayer;

    /**
     * Constructor for Diplomacy class
     *
     * @param d_issuingPlayer name of the player who is using the card
     * @param d_targetPlayer  name of the opponent player
     */
    public Diplomacy(String d_issuingPlayer, String d_targetPlayer) {
        this.d_issuingPlayer = d_issuingPlayer;
        this.d_targetPlayer = d_targetPlayer;
    }

    /**
     * This class return boolean value after checking if the command is valid
     *
     * @return the boolean value true if the command is valid else false
     */
    public boolean checkValidity() {
        Player playerName = new Player(d_issuingPlayer);

        // if the player doesn't have the card
//        if(!playerName.getAvailableCard().contains("diplomacy")) {
//            System.err.println("Diplomacy card is not available to use");
//            return false;
//        }

        // if the player is inputting null name
        if (d_targetPlayer == null || d_targetPlayer.trim().isEmpty()) {
            System.err.println("Target player name cannot be empty");
            return false;
        }

        // if the opponent player doesn't exist
        if (!player.getPlayerName().contains(d_targetPlayer)) {
            System.err.println("Player " + d_targetPlayer + " doesn't exist");
            return false;
        }

        return true;
    }

    /**
     * The class return the boolean value true if target player is ready for negotiate else false
     *
     * @return the boolean value true if target player is ready for negotiate else false
     */
    public boolean checkIfTargetPlayerIsReady() {
        if (checkValidity()) {
//            if (d_targetPlayer.isReadyToNegotiate()) {
//                return true;
//            } else {
//                System.err.println(d_issuingPlayer + " do not want to negotiate");
//                return false;
//            }
        }
        return true;
    }

    /**
     * Execute execution order if the commands are valid
     */
    public void executeNegotiation() {
        if (checkIfTargetPlayerIsReady()){
            System.out.println(d_issuingPlayer + " is negotiated with " + d_targetPlayer);
        }
    }
}
