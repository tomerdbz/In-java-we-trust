package boot;

import model.RemoteControlUDPServer;

public class SuperServerRun {

	public static void main(String[] args) {
		new RemoteControlUDPServer().run();
	}

}