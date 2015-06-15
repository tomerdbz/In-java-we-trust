package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;

import presenter.ServerProperties;

public class MyModel extends Observable implements Model {
	//private InputStream inFromServer;
	//private OutputStream outToServer;
	ServerProperties serverProperties;
	DatagramSocket socketSend;
	DatagramSocket socketRec;
	InetAddress address;
	Thread ClientManager=null;
	ConcurrentHashMap<String,String> clientStatus = new ConcurrentHashMap<String, String>();
	//BufferedReader BR;
	public MyModel(ServerProperties serverProperties){
		try {
			/*Socket myServer=new Socket("localhost",5400);
			inFromServer=myServer.getInputStream();
			outToServer=myServer.getOutputStream();
			this.serverProperties=serverProperties;
			BR= new BufferedReader(new InputStreamReader(inFromServer));*/
			socketSend = new DatagramSocket(5401);
			address=InetAddress.getByName("localhost");
			socketRec = new DatagramSocket(7000);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void getStatusClient(String client) {
	
		/*BufferedReader in = new BufferedReader(new InputStreamReader(inFromServer));
		String re;
		try {
			while((re=in.readLine())!=null){
				if(re.split(",")[0]==client){
					setChanged();
					notifyObservers(re.split(",")[2]);
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		setChanged();
		notifyObservers(this.clientStatus.get(client.split(" ")[2]+","+client.split(" ")[4]));
		
		
		
	}

	

	

	@Override
	public void DisconnectServer() {
		/*PrintWriter write =new PrintWriter(outToServer);
		write.println("stop server");
		write.flush();
		if(ClientManager!=null)
			ClientManager.interrupt();*/
		String message="stop server";
		byte[] data=message.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(data,
		data.length, address, 5400);
		try {
			socketSend.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setChanged();
		notifyObservers("msg server has stopped");
	}
	@Override
	public void StartServer() {
		/*PrintWriter write;
		write = new  PrintWriter(outToServer);
		write.println("start server");
		write.flush();
		try {
			ObjectOutputStream os = new ObjectOutputStream(outToServer);
			os.writeObject(serverProperties);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ClientManager = new Thread ( new Runnable(){

			@Override
			public void run() {
				
				
				
				try {
					String line = BR.readLine();
					if(line.split(",").length==3){
						setChanged();
						notifyObservers( "add " +line);
					}
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
				
			}
			
		});*/
		String message="start server";
		byte[] data=message.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(data,
		data.length, address, 5400);
		try {
			socketSend.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ClientManager = new Thread(new Runnable(){

			@Override
			public void run() {
				
				byte info[]=new byte[1000];
				DatagramPacket receivedPacket=new DatagramPacket(info,info.length);
				try {
					socketRec.receive(receivedPacket);
					String line=new String(receivedPacket.getData(), 0,receivedPacket.getLength());
					if(line.split(",").length==3 && line.split(",")[2].equals("has connected")){
						clientStatus.put(line.split(",")[0]+","+ line.split(",")[1],"has connected");
						setChanged();
						notifyObservers( "add " +line);
					}
					else
						if(line.split(",").length==3){
							clientStatus.put(line.split(",")[0]+","+ line.split(",")[1],line.split(",")[2]);
						}
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			
		});
		setChanged();
		notifyObservers("msg server started");
	
		
	}
	@Override
	public void DisconnectClient(String client) {
		/*try {
			ObjectOutputStream write =new ObjectOutputStream(outToServer);
			write.writeObject("disconnect "+client); //ip,port,disconnect
			write.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String message=client.split(" ")[2]+","+ client.split(" ")[4];
		byte[] data=message.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(data,
		data.length, address, 5400);
		try {
			socketSend.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.clientStatus.remove(message);
		setChanged();
		notifyObservers("remove "+client);
		
	}
	@Override
	public void exit() {
		
		String message="exit";
		byte[] data=message.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(data,
		data.length, address, 5400);
		try {
			socketSend.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		socketSend.close();
		socketRec.close();
		setChanged();
		notifyObservers("msg Exiting window now.");
	}
	
}
