package boot;

import model.GUIServer;
import model.GUIUDPServer;

public class SuperServerRun {

	public static void main(String[] args) {
		new GUIUDPServer().run();
	}

}
