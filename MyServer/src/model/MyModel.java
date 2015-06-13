package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.zip.GZIPInputStream;

public class MyModel extends Observable implements Model {
	private InputStream inFromServer;
	private OutputStream outToServer;
	public MyModel(){
		try {
			Socket myServer=new Socket("localhost",5400);
			inFromServer=myServer.getInputStream();
			outToServer=myServer.getOutputStream();
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
	public void DisconnectClient(String client) {
		PrintWriter write =new PrintWriter(outToServer);
		write.println("disconnect "+client);
		write.flush();
		setChanged();
		notifyObservers("msg client disconnected "+client);
	}

	@Override
	public void StartServer() {
		PrintWriter write =new PrintWriter(outToServer);
		write.println("start server");
		write.flush();
		setChanged();
		notifyObservers("msg server started");
		
	}

	@Override
	public void DisconnectServer() {
		PrintWriter write =new PrintWriter(outToServer);
		write.println("start server");
		write.flush();
		setChanged();
		notifyObservers("msg server has stopped");
	}
	
}
