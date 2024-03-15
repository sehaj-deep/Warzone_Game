package phases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.Deploy;
import game.GameEngineNew;
import game.GameState;
import game.Order;
import game.Player;
import map.MapEditor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Test class for the IssueOrdersPhase class.
 */
public class IssueOrdersPhaseTest {

    private IssueOrdersPhase issueOrdersPhase;

    /**
     * Sets up the test environment.
     *
     * @throws Exception if an error occurs during setup
     */
    @Before
    public void setUp() throws Exception {
        issueOrdersPhase = new IssueOrdersPhase(new GameEngineNew());
        String fakeInput = "deploy countryName 10\n";
        InputStream inputStream = new ByteArrayInputStream(fakeInput.getBytes());
        Field scannerField = IssueOrdersPhase.class.getDeclaredField("d_scanner");
        scannerField.setAccessible(true);
        scannerField.set(issueOrdersPhase, new Scanner(inputStream));
    }

    /**
     * Tests the getPlayerOrderTokens method.
     */
    @Test
    public void testGetPlayerOrderTokens() {
        String[] expectedTokens = {"deploy", "countryName", "10"};
        String[] tokens = issueOrdersPhase.getPlayerOrderTokens(0);
        assertNotNull(tokens);
        assertEquals(3, tokens.length);
        assertArrayEquals(expectedTokens, tokens);
    }

    /**
     * Tests the createAndValidateOrder method with invalid deploy order format.
     */
    @Test
    public void testCreateAndValidateOrder_InvalidDeployOrderFormat() {
        GameState gameState = new GameState(new ArrayList<>());
        MapEditor mapEditor = new MapEditor();
        Player player = new Player("Player1");
        gameState.getPlayers().add(player);
        gameState.getReinforcements().add(10);

        // Invalid format: Missing number of armies
        String[] tokens = {"deploy", "countryName"};
        Order order = issueOrdersPhase.createAndValidateOrder(tokens, gameState, 0);

        assertNull(order);
    }

    /**
     * Tests the createAndValidateOrder method with invalid deploy order format.
     */
    @Test
    public void testCreateAndValidateOrder_InvalidDeployOrderNumberFormat() {
        GameState gameState = new GameState(new ArrayList<>());
        MapEditor mapEditor = new MapEditor();
        Player player = new Player("Player1");
        gameState.getPlayers().add(player);
        gameState.getReinforcements().add(10);

        // Invalid number format for armies
        String[] tokens = {"deploy", "countryName", "invalidNumber"};
        Order order = issueOrdersPhase.createAndValidateOrder(tokens, gameState, 0);

        assertNull(order);
    }

    /**
     * Tests the createAndValidateOrder method with invalid order type.
     */
    @Test
    public void testCreateAndValidateOrder_InvalidOrderType() {
        GameState gameState = new GameState(new ArrayList<>());
        MapEditor mapEditor = new MapEditor();
        Player player = new Player("Player1");
        gameState.getPlayers().add(player);
        gameState.getReinforcements().add(10);

        // Invalid order type
        String[] tokens = {"invalidOrderType", "countryName", "10"};
        Order order = issueOrdersPhase.createAndValidateOrder(tokens, gameState, 0);

        assertNull(order);
    }

    /**
     * Tests the createAndValidateOrder method with valid deploy order.
     */
    @Test
    public void testCreateAndValidateOrder_ValidDeployOrder() {
        GameState gameState = new GameState(new ArrayList<>());
        MapEditor mapEditor = new MapEditor();
        Player player = new Player("Player1");
        player.conquerCountry("countryName"); // Adding the country to the player's ownership
        gameState.getPlayers().add(player);
        gameState.getReinforcements().add(10);

        String[] tokens = {"deploy", "countryName", "5"};
        Order order = issueOrdersPhase.createAndValidateOrder(tokens, gameState, 0);

        assertNotNull(order);
        assertTrue(order instanceof Deploy);
        assertEquals(5, ((Deploy) order).getNumArmy());
        assertEquals("countryName", ((Deploy) order).getCountryName());
    }

}
