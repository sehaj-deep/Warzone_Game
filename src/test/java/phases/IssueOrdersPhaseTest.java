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

}
