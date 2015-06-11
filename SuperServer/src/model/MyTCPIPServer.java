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
	/**	Properties defined. see Properties class for more info.
	 * 
	 */
	ServerProperties serverProperties;
	ClientHandler clientHandler;
	private volatile boolean stopped;

	public MyTCPIPServer(ServerProperties serverProperties,ClientHandler clientHandler) {
		this.serverProperties=serverProperties;
		stopped=false;
		this.clientHandler=clientHandler;
		
		// TODO Auto-generated constructor stub
	}
	public void startServer()
	{
		ServerSocket server;
		try {
			server = new ServerSocket(serverProperties.getPort());
			ListeningExecutorService threadPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(serverProperties.getNumOfClients()));
			server.setSoTimeout(500);
			while(!stopped)
			{
				try {
					final Socket someClient=server.accept();
					threadPool.execute(new Runnable() {
						
						@Override
						public void run() {
							try {
								InputStream inputFromClient=someClient.getInputStream();
								OutputStream outputToClient=someClient.getOutputStream();
								clientHandler.handleClient(inputFromClient,outputToClient);
								inputFromClient.close();
								outputToClient.close();
								someClient.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
				}
			}
			server.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}
	public void stoppedServer()
	{
		stopped=true;
	}
}
