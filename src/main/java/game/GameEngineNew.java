package game;

import java.util.Scanner;

import map.Preload;
import phases.Phase;
import phases.PlaySetup;

public class GameEngineNew {
	private Phase d_gamePhase;

	public void setPhase(Phase p_phase) {
		d_gamePhase = p_phase;
	}

	public void start() {
		try (Scanner l_scanner = new Scanner(System.in)) {
			System.out.println("1. Edit Map");
			System.out.println("2. Start Game");
			System.out.print("Enter your choice: ");
			String choice = l_scanner.nextLine();

			switch (choice) {
			case "1":
				setPhase(new Preload(this));
				break;
			case "2":
				setPhase(new PlaySetup(this));
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		}
	}

}
