package presenter;

import java.io.Serializable;

public class ServerProperties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int port;
	private int numOfClients;
	private int listeningPort;
	/**	Default Properties.
	 * 
	 */
	public ServerProperties() {
		port=5400;
		numOfClients=32;
		listeningPort = 1234;
	}
	public ServerProperties(int port,int numOfClients,int listeningPort) {
		this.port=port;
		this.numOfClients=numOfClients;
		this.listeningPort=listeningPort;
	}
	
	public int getListeningPort() {
		return listeningPort;
	}
	public void setListeningPort(int listeningPort) {
		this.listeningPort = listeningPort;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getNumOfClients() {
		return numOfClients;
	}
	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}
}
