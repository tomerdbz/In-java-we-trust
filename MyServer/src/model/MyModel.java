package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.zip.GZIPInputStream;

import presenter.ServerProperties;

public class MyModel extends Observable implements Model {
	private InputStream inFromServer;
	private OutputStream outToServer;
	ServerProperties serverProperties;
	public MyModel(ServerProperties serverProperties){
		try {
			Socket myServer=new Socket("localhost",5400);
			inFromServer=myServer.getInputStream();
			outToServer=myServer.getOutputStream();
			this.serverProperties=serverProperties;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void getStatusClient(String client) {
	
		BufferedReader in = new BufferedReader(new InputStreamReader(inFromServer));
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
		}
		
		
		
	}

	

	

	@Override
	public void DisconnectServer() {
		PrintWriter write =new PrintWriter(outToServer);
		write.println("stop server");
		write.flush();
		setChanged();
		notifyObservers("msg server has stopped");
	}
	@Override
	public void StartServer() {
		PrintWriter write;
		write = new  PrintWriter(outToServer);
		write.println("start server");
		write.flush();
		try {
			ObjectOutputStream os = new ObjectOutputStream(outToServer);
			os.writeObject(serverProperties);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setChanged();
		notifyObservers("msg server started");
	
		
	}
	@Override
	public void DisconnectClient(String client) {
		try {
			ObjectOutputStream write =new ObjectOutputStream(outToServer);
			write.writeObject("disconnect "+client);
			write.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setChanged();
		notifyObservers("msg client disconnected "+client);
		
	}
	
}
