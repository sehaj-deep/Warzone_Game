package nonJunit.phases;
import java.util.*;
import game.Player;
import game.GameState;
import phases.IssueOrdersPhase;
import map.MapEditor;

/** Non-Junit testing for IssueOrderPhase
 *
 *  This is more similar to integration testing
 *  because IssueOrderPhase has dependency on many other classes
 */
public class TestIssueOrdersPhase {
    private static GameState d_state;
    private static MapEditor d_gMap;


    /** Set up the testing game environment
     * 
     */
    public static void setUp() {
        System.out.println("Setting up fake game state to test IssueOrderPhase");
        List<Player> players = new ArrayList<>();
        for(int i=0; i < 5; i++) {
            Player player = new Player(i);
            player.setPlayerName(Integer.toString(i));
            player.setOwnership(new HashSet<String>(Arrays.asList(Integer.toString(5*i + 1), Integer.toString(5*i + 2),
                    Integer.toString(5*i + 3), Integer.toString(5*i + 4), Integer.toString(5*i + 5))));
            System.out.println("Player"+i+" has countries: " + player.getOwnership());
            players.add(player);
        }
        /*
        players.add(new Player(0)); players.add(new Player(1));
        players.add(new Player(2)); players.add(new Player(3)); players.add(new Player(4));
        */
        List<Integer> reinforcements = new ArrayList<>(Arrays.asList(5,0,7,8,9));
        // This test case made player 1 with 0 reinforcement army left to ensure skipping such player works
        /*
        reinforcements.add(5); reinforcements.add(0);
        reinforcements.add(7); reinforcements.add(8); reinforcements.add(9);
         */
        System.out.println("Reinforcements available to Player0 to 4: " + reinforcements);
        d_state = new GameState(players);
        d_state.setReinforcements(reinforcements);
    }

    /** run the issue order testing as a function call in the given game environment in setUp() function
     * 
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Testing Issue Order Phase");
        setUp();
        System.out.println(d_state.getPlayers());
        IssueOrdersPhase issueOrdersPhase = new IssueOrdersPhase();
        issueOrdersPhase.run(d_state, d_gMap);
        System.out.println("Phase Finished");
        List<Integer> reinforcementsAfterPhase = new ArrayList<>();
        reinforcementsAfterPhase.add(0); reinforcementsAfterPhase.add(0);
        reinforcementsAfterPhase.add(0); reinforcementsAfterPhase.add(0); reinforcementsAfterPhase.add(0);
        for(int i = 0; i < d_state.getPlayers().size(); i++) {
            // print list of orders per player to check whether new orders are stored correctly
            Player player = d_state.getPlayers().get(i);
            System.out.println("Player" + i + " order list: " + player.getListOrders());
            assert d_state.getReinforcements().get(i) == reinforcementsAfterPhase.get(i);
        }
        System.out.println("Testing Issue Order Phase PASSED!");
    }
}