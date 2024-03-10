package map;

public class PostLoad {

	public void loadMap() {
		System.out.println("map has been loaded");
	}

	public void editCountry() {
		System.out.println("country has been edited");
	}

	public void saveMap() {
		System.out.println("map has been saved");
		// FIXME:
//		d_gameEngine.setPhase(new PlaySetup(d_gameEngine));
	}

	public void next() {
		System.out.println("must save map");
	}

}
