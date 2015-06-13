package model;

public class GUIServer extends MyTCPIPServer {

	public GUIServer(ServerProperties serverProperties,
			ClientHandler clientHandler) {
		super(serverProperties, clientHandler);
	}
	public GUIServer() {
		super(new ServerProperties(5400,1),new GUIHandler()); //1 for the maze server, 1 for the communication with gui
		//but please note Eli's saying - you may need to put numOfClients+2 if mazeServer steals threads
	}

}