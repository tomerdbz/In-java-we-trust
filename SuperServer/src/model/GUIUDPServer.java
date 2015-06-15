package model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;

import presenter.ServerProperties;

public class GUIUDPServer extends Observable implements Observer,Runnable{
	MazeClientHandler handler;
	InetAddress senderIP;
	int senderPort;
	DatagramSocket serverSocket;
	MazeServer clientsServer;
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
	private void waitForStartSignal()
	{
		
		byte[] receiveData=new byte[1024];
		byte[] sendData=new byte[1024];
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
				
			}while(!input.equals("start server"));
		}
	}
	private void initiateClientsServer()
	{
		byte[] receiveData=new byte[1024];
		String input=null;
		DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
		try {
			serverSocket.receive(receivePacket);
			input=new String(receivePacket.getData());//expect it to be: String= numOfClients+" "+PortToServeClients
			ServerProperties clientsServerProperties=new ServerProperties(Integer.parseInt(input.split(" ")[1]),Integer.parseInt(input.split(" ")[0]));
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
				if(input.equals("exit"))
					return;
				else if(input.contains("disconnect"))
				{
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
		}while(!input.equals("stop server"));
		clientsServer.stoppedServer();
		clientsServer=null;
	}
	@Override
	public void update(Observable o, Object arg) {
		if(o==handler)
		{
			String allMessages="";
			for(String message: handler.messages)
			{
				allMessages+=message;
			}
			DatagramPacket sendPacket=new DatagramPacket(allMessages.getBytes(),allMessages.length(),senderIP,senderPort);
			try {
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
