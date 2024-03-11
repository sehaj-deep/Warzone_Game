package phases;

import game.GameEngineNew;
import game.GameState;
import game.Player;
import map.MapEditor;

import java.util.Calendar;


public class PlaySetup extends Play {

	public PlaySetup(GameEngineNew p_gameEngine) {
		super(p_gameEngine);
	}

	@Override
	public void loadMap() {
		// TODO call respective methods

	}

	@Override
	public void addPlayers(String p_playerName) {
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

		System.out.println("Player: " + p_playerName + " successfully added ");

	}

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
		int playerIdx = d_playerNameList.indexOf(p_playerName);
		d_playerNameList.remove(p_playerName);

		System.out.println("Player: " + p_playerName + " successfully removed");
	}

	@Override
	public void assignCountries() {
	}

	@Override
	public void addCountry() {
		this.printInvalidCommandMessage();
	}

	@Override
	public void removeCountry() {
		this.printInvalidCommandMessage();
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
	public void next() {
		// TODO Uncomment when reinforcement phase is implemented
		// d_gameEngine.setPhase(new PostLoad(d_gameEngine));
	}

}
