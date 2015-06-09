package model;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class MyTCPIPServer {
	private int port;
	ClientHandler clientHandler;
	private volatile boolean stopped;

	public MyTCPIPServer(int port,ClientHandler clientHandler) {
		this.port=port;
		stopped=false;
		this.clientHandler=clientHandler;
		
		// TODO Auto-generated constructor stub
	}
	public void startServer(int numOfClients) throws IOException
	{
		ServerSocket server=new ServerSocket(port);
		ListeningExecutorService threadPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(numOfClients));
		server.setSoTimeout(500);
		while(!stopped)
		{
			try {
				final Socket someClient=server.accept();
				threadPool.execute(new Runnable() {
					
					@Override
					public void run() {
						try {
							/*byte[] arr=new byte[5000];
							System.out.println(someClient.getInputStream().read(arr));
							System.out.println(arr);*/
							InputStream inputFromClient=someClient.getInputStream();
							OutputStream outputToClient=someClient.getOutputStream();
							clientHandler.handleClient(inputFromClient,outputToClient);
							inputFromClient.close();
							outputToClient.close();
							someClient.close();
						} catch (IOException e) {
						}
					}
				});
				
			} catch (SocketTimeoutException e) {
			}
		}
		server.close();
		
	}
	public void stoppedServer()
	{
		stopped=true;
	}
}
