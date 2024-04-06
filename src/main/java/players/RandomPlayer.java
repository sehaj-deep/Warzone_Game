package players;

import java.util.*;

import game.GameEngine;
import map.Country;
import orders.*;

/**
 * The RandomPlayer class represents a player strategy that makes random decisions
 * for deploying, attacking, moving armies, and issuing random card orders.
 * It extends the abstract class PlayerStrategy and implements methods for creating
 * orders based on the current game state.
 */
public class RandomPlayer extends PlayerStrategy {

    /**
     * Boolean representing whether issuing Deploy order is allowed currently
     */
    private boolean d_canDeploy = true;

    /**
     * Boolean representing whether issuing attacking type Advance order is allowed
     * currently
     */
    private boolean d_canAttack = true;

    /**
     * Random method
     */
    private final Random random = new Random();

    /**
     * Queue of orders waiting to be added to player's order's list
     */
    private final Queue<Order> d_waitingOrders = new LinkedList<>();

    /**
     * Generates a random order based on the current game state and player's
     * available actions.
     *
     * @param p_player     The player for whom the order is generated.
     * @param p_tokens     The array of command tokens.
     * @param p_gameEngine The game engine object representing the current game state.
     * @return An Order object representing the generated order, or null if no valid
     * order can be generated.
     */
    @Override
    protected Order createOrder(Player p_player, String[] p_tokens, GameEngine p_gameEngine) {

        int playerIdx = p_gameEngine.getPlayers().indexOf(p_player);
        Order order = null;

        if (d_canDeploy) {
            order = deployRandomly(p_player, p_gameEngine);

        } else if (d_canAttack) {
            order = attackRandomly(p_player, p_gameEngine);

        } else if (getHasOrder()) {
            int choice = random.nextInt(2);

            if (d_waitingOrders.isEmpty()) {
                switch (choice) {
                    case 0:
                        moveArmiesRandomly(p_player, p_gameEngine);
                        break;
                    case 1:
                        createRandomCardOrder(p_player, p_gameEngine);
                        break;
                }
            }

            if (!d_waitingOrders.isEmpty()) {
                order = d_waitingOrders.remove();
            }

            if (d_waitingOrders.isEmpty()) {
                setHasOrder(false);
            }

        }

        if (order != null && order.isValidIssue(playerIdx)) {
            return order;
        } else {
            return null;
        }
    }

    /**
     * Generates a deployment order targeting a randomly selected country owned by the player.
     *
     * @param player     The player for whom the order is generated.
     * @param gameEngine The game engine object representing the current game state.
     * @return A Deploy order object.
     */
    private Order deployRandomly(Player player, GameEngine gameEngine) {
        List<String> l_ownedCountries = new ArrayList<>(player.getOwnership());
        String l_randomCountry = l_ownedCountries.get(random.nextInt(l_ownedCountries.size()));
        int l_reinforcements = gameEngine.getReinforcements().get(gameEngine.getPlayers().indexOf(player));

        return new Deploy(l_randomCountry, l_reinforcements, gameEngine);
    }

    /**
     * Generates an Advance order targeting a randomly selected enemy neighbor country.
     *
     * @param player     The player for whom the order is generated.
     * @param gameEngine The game engine object representing the current game state.
     * @return An Advance order object.
     */
    private Order attackRandomly(Player player, GameEngine gameEngine) {
        List<String> l_ownedCountries = new ArrayList<>(player.getOwnership());
        String l_attackingCountry = l_ownedCountries.get(random.nextInt(l_ownedCountries.size()));
        Country country = gameEngine.getD_countries(l_attackingCountry);
        List<Country> enemyNeighbors = new ArrayList<>();

        for (Country neighbor : country.getNeighbors()) {
            if (!player.getOwnership().contains(neighbor.getD_name())) {
                enemyNeighbors.add(neighbor);
            }
        }

        if (!enemyNeighbors.isEmpty()) {
            Country l_targetCountry = enemyNeighbors.get(random.nextInt(enemyNeighbors.size()));
            int l_numArmies = gameEngine.getGameBoard().get(l_attackingCountry) - 1;
            return new Advance(l_attackingCountry, l_targetCountry.getD_name(), l_numArmies, gameEngine);
        }
        return null;
    }

    /**
     * Generates an Advance order to move armies from one friendly country to another.
     *
     * @param player     The player for whom the order is generated.
     * @param gameEngine The game engine object representing the current game state.
     * @return An Advance order object.
     */
    private Order moveArmiesRandomly(Player player, GameEngine gameEngine) {
        List<String> l_ownedCountries = new ArrayList<>(player.getOwnership());
        String l_fromCountry = l_ownedCountries.get(random.nextInt(l_ownedCountries.size()));
        Country l_country = gameEngine.getD_countries(l_fromCountry);
        List<String> l_friendlyNeighbors = new ArrayList<>();

        for (Country neighbor : l_country.getNeighbors()) {
            if (player.getOwnership().contains(neighbor.getD_name())) {
                l_friendlyNeighbors.add(neighbor.getD_name());
            }
        }

        if (!l_friendlyNeighbors.isEmpty()) {
            String l_toCountry = l_friendlyNeighbors.get(random.nextInt(l_friendlyNeighbors.size()));
            int l_numArmies = random.nextInt(gameEngine.getGameBoard().get(l_fromCountry));
            return new Advance(l_fromCountry, l_toCountry, l_numArmies, gameEngine);
        }
        return null;
    }

    /**
     * Generates a random card order based on the player's available cards and game state.
     *
     * @param player     The player for whom the order is generated.
     * @param gameEngine The game engine object representing the current game state.
     * @return A card order object, or null if no valid card order can be generated.
     */
    private Order createRandomCardOrder(Player player, GameEngine gameEngine) {
        List<String> cardOrderTypes = Arrays.asList("Bomb", "Airlift", "Blockade", "Negotiate");
        String randomCardOrderType = cardOrderTypes.get(random.nextInt(cardOrderTypes.size()));

        if (player.getCardCount(randomCardOrderType.toLowerCase()) == 0) {
            return null;
        }

        String targetCountry = "";
        String playerId = "";
        int numberOfArmies = 0;

        switch (randomCardOrderType.toLowerCase()) {
            case "bomb", "blockade":
                targetCountry = getRandomCountry(player, gameEngine);
                break;
            case "airlift":
                String sourceCountry = getRandomCountry(player, gameEngine);
                String destinationCountry = getRandomCountry(player, gameEngine);
                numberOfArmies = random.nextInt(gameEngine.getGameBoard().get(sourceCountry));
                return new Airlift(sourceCountry, destinationCountry, numberOfArmies, gameEngine);
            case "negotiate":
                playerId = getRandomPlayerId(gameEngine);
                break;
            default:
                // Invalid card order type
                return null;
        }

        return switch (randomCardOrderType.toLowerCase()) {
            case "bomb" -> new Bomb(targetCountry, gameEngine);
            case "blockade" -> new Blockade(targetCountry, gameEngine);
            case "negotiate" -> new Diplomacy(playerId, gameEngine);
            default -> null;
        };
    }

    /**
     * Retrieves a randomly selected country owned by the player.
     *
     * @param player     The player for whom the country is selected.
     * @param gameEngine The game engine object representing the current game state.
     * @return A string representing the name of the selected country.
     */
    private String getRandomCountry(Player player, GameEngine gameEngine) {
        List<String> ownedCountries = new ArrayList<>(player.getOwnership());
        return ownedCountries.get(random.nextInt(ownedCountries.size()));
    }

    /**
     * Retrieves the ID of a randomly selected player from the game engine.
     *
     * @param gameEngine The game engine object representing the current game state.
     * @return A string representing the ID of the selected player.
     */
    private String getRandomPlayerId(GameEngine gameEngine) {
        List<Player> players = gameEngine.getPlayers();
        Player randomPlayer = players.get(random.nextInt(players.size()));
        return randomPlayer.getPlayerName();
    }

    /**
     * Resets the player strategy.
     */
    @Override
    public void reset() {
    }
}
