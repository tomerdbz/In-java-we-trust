package model;

import presenter.ServerProperties;

/** Remote control TCP server - didn't use. UDP is smarter for this type of communication. but saved it.
 * @author Tomer
 *
 */
public class RemoteControlTCPServer extends MyTCPIPServer {

	public RemoteControlTCPServer(ServerProperties serverProperties,
			ClientHandler clientHandler) {
		super(serverProperties, clientHandler);
	}
	public RemoteControlTCPServer() {
		super(new ServerProperties(5400,1),new RemoteControlHandler()); //1 for the maze server, 1 for the communication with gui
		//but please note Eli's saying - you may need to put numOfClients+2 if mazeServer steals threads
	}

}
