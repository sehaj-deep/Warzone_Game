//package game;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * This class is designed to do unit test of features in Deploy class (inherits
// * from Order)
// */
//public class DeployTest {
//	// Used one player for players list to test the deploy order class for
//	// simplicity
//	static Deploy d_deployOrder;
//	static List<Player> d_players;
//	static List<Integer> d_reinforcements;
//	static int d_plyrId = 0;
//	static GameState d_state;
//	static GameEngineNew d_gameEngine;
//
//	/**
//	 * This is the common setup for all test cases and will be run before each test
//	 */
//	@Before
//	public void before() {
//		System.out.println("Testing Deploy class Setup: 4 armies to country Korea");
////		d_deployOrder = new Deploy(4, "korea");
//		// FIXME: Changed as per my understanding
//		d_gameEngine = new GameEngineNew();
//		d_deployOrder = new Deploy("korea", 4, d_gameEngine);
//		d_players = new ArrayList<>();
//		d_reinforcements = new ArrayList<>();
//		d_players.add(new Player("0"));
//		d_state = new GameState(d_players);
//	}
//
//	/**
//	 * test isValid methods of Deploy class test one valid case where expecting
//	 * output true from isValid test three invalid cases where expecting output
//	 * false from isValid
//	 */
//	@Test
//	public void testIsValidIssue() {
//		System.out.println("Testing isValid method of Deploy class");
//
//		// own countries 3, 7, and 9, and 8 armies available for the player
//		System.out.println("Testing valid deployment");
//		Set<String> l_ownedCountries = new HashSet<>(Arrays.asList("korea", "usa", "italy"));
//		d_players.get(0).setOwnership(l_ownedCountries);
//		d_reinforcements = Collections.singletonList(8);
//		d_state.setReinforcements(d_reinforcements);
//		assertTrue(d_deployOrder.isValidIssue(d_state, d_plyrId));
//
//		// Invalid case 1: country not occupied by the player
//		System.out.println("Testing invalid deployment: deploy to country not owned");
//		Set<String> l_ownedCountries2 = new HashSet<>(Arrays.asList("usa", "italy"));
//		d_players.get(0).setOwnership(l_ownedCountries2);
//		assertFalse(d_deployOrder.isValidIssue(d_state, d_plyrId));
//
//		// Invalid case 2: negative number of armies for deploy
//		System.out.println("Testing invalid deployment: negative number of armies for deply");
//		d_players.get(0).setOwnership(l_ownedCountries);
//		// FIXME: Changed as per my understanding (below line)
//		assertFalse(new Deploy("korea", -1, d_gameEngine).isValidIssue(d_state, d_plyrId));
//
//		// Invalid case 3: more armies to be deployed than the armies available for the
//		// player
//		System.out.println("Testing invalid deployment: deploying more armies than availabe for the player");
//		d_reinforcements = Collections.singletonList(1);
//		d_state.setReinforcements(d_reinforcements);
//		assertFalse(d_deployOrder.isValidIssue(d_state, d_plyrId));
//
//		// Invalid case 4: same case as 4 but checking validity in execution order phase
//		// i.e., validity must be true because execution of deploy implies that number
//		// of armies for deploy < reinforcements
//		System.out.println("Testing invalid deployment: deploying more armies than availabe for the player");
//		d_reinforcements = Collections.singletonList(1);
//		d_state.setReinforcements(d_reinforcements);
//		assertTrue(d_deployOrder.isValidExecute(d_state, d_plyrId));
//
//		System.out.println("Testing deploy.isValid method PASSED!");
//	}
//
//	/**
//	 * Test execute method check whether number of army in the destination country
//	 * reflect the update after execution of order
//	 */
//	@Test
//	public void testExecute() {
//		d_state.getGameBoard().put("korea", 8);
//		d_state.getGameBoard().put("italy", 9);
//		System.out.println("Testing execute deploy order");
//		System.out.println("Game board before execution:\n" + d_state.getGameBoard());
//		System.out.println("Deploy 4 armies to korea");
//		d_deployOrder.execute(d_state, d_plyrId);
//		System.out.println("Game board after execution:\n" + d_state.getGameBoard());
//		int l_num_army = d_state.getGameBoard().get("korea");
//		assertEquals(12, l_num_army);
//		System.out.println("Testing deploy.execute method PASSED!");
//	}
//
//	/**
//	 * test changeGameState method check whether number of reinforcement armies
//	 * decreased by numArmy in Deploy as changeGameState method is invoked
//	 */
//	@Test
//	public void testChangeGameState() {
//		d_reinforcements.add(8);
//		d_state.setReinforcements(d_reinforcements);
//		System.out.println("Testing deploy.changeGameState method");
//		System.out.println("Reinforcement Available players before change: " + d_state.getReinforcements());
//		d_deployOrder.changeGameState(d_state, d_plyrId);
//		System.out.println("Reinforcement Available players after change: " + d_state.getReinforcements());
//		int l_modifiedReinforcement = d_state.getReinforcements().get(d_plyrId);
//		assertEquals(4, l_modifiedReinforcement);
//		System.out.println("Testing deploy.changeGameState method PASSED!");
//	}
//}