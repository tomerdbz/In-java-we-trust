package model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;

import presenter.ServerProperties;

/** This class is a UDP Server for the remote control - communication with the remote will be in udp and
 * 	will initiate a TCP clients Server. 
 * it's Observable by the MazeClientHandler for the handler to send it messages and updates what the Maze Server is doing at the moment.
 * It implements Observer since it can signal to disconnect client if the remote wants to.
 * @author Tomer
 *
 */
public class GUIUDPServer extends Observable implements Observer,Runnable{
	/**	The TCP/IP clients Server handler
	 * 
	 */
	MazeClientHandler handler;
	/**	The remote control IP address.
	 * 	Saves it because remote can disconnect and connect again from a different address.
	 * I'd like to save the last address he sent me messages all the time.
	 */
	InetAddress senderIP;
	/**	The remote control port.
	 * 
	 */
	int senderPort;
	/**	UDP server socket.
	 * 
	 */
	DatagramSocket serverSocket;
	/**	the TCP/IP clients Maze Server.
	 * 
	 */
	MazeServer clientsServer;
	/** starts the UDP server.
	 */
	@Override
	public void run() {
		try {
			serverSocket=new DatagramSocket(5400);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		waitForStartSignal();

		initiateClientsServer();
		
		handleClientsServer();
		
	}
	/**	Helper for run() - waits for the remote control to send start signal.
	 * 
	 */
	private void waitForStartSignal()
	{
		
		byte[] receiveData=new byte[1024];
		String input=null;
		if(clientsServer==null)
		{
			do{
				DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
				try {
					serverSocket.receive(receivePacket);
					input=new String(receivePacket.getData());
					senderIP=receivePacket.getAddress();
					senderPort=receivePacket.getPort();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}while(!input.startsWith("start server"));
		}
	}
	/**	Helper for run(). upon getting a start server signal this method reads Maze Server properties - how many clients and port.
	 * 
	 */
	private void initiateClientsServer()
	{
		byte[] receiveData=new byte[1024];
		String buffer=null,input="";
		DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
		try {
			serverSocket.receive(receivePacket);
			buffer=new String(receivePacket.getData());//expect it to be: String= numOfClients+" "+PortToServeClients
			
			for(int i=0;i<buffer.length();i++)
				if(Character.isDigit(buffer.charAt(i)) || buffer.charAt(i)==',')
						input+=buffer.charAt(i);
				else
					break;
			
			ServerProperties clientsServerProperties=new ServerProperties(Integer.parseInt(input.split(",")[1]),Integer.parseInt(input.split(",")[0]));
			handler=new MazeClientHandler(this);
			handler.addObserver(this);
			this.addObserver(handler);
			clientsServer=new MazeServer(clientsServerProperties,handler);
			handler.setServer(clientsServer);
			new Thread(clientsServer).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	/**	handles UDP server - getting disconnect clients signals until remote signals to stop server.
	 * 
	 */
	private void handleClientsServer()
	{
		byte[] receiveData=new byte[1024];
		String input=null;
		do{
			DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
			try {
				serverSocket.receive(receivePacket);
				input=new String(receivePacket.getData());
				senderIP=receivePacket.getAddress();
				senderPort=receivePacket.getPort();
				if(input.startsWith("exit"))
					return;
				else if(input.contains("disconnect"))
				{
					String buffer=input;
					input="";
					for(int i=0;i<buffer.length();i++)
						if(Character.isDigit(buffer.charAt(i)) || buffer.charAt(i)==',')
								input+=buffer.charAt(i);
						else
							break;
					setChanged();
					notifyObservers(input);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*
			 * String to get bytes to sendData
			 * DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,senderIP,senderPort);
			 * serverSocket.send(sendPacket);
			 */
		}while(!input.startsWith("stop server"));
		clientsServer.stoppedServer();
		clientsServer=null;
	}
	/** upon any change in what the clients maze server is doing - it will call here where I send messages to the remote control.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o==handler)
		{
			String allMessages="";
			for(String message: handler.messages)
			{
				allMessages+="\n"+ message;
			}
			DatagramPacket sendPacket=new DatagramPacket(allMessages.getBytes(),allMessages.length(),senderIP,senderPort);
			try {
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
