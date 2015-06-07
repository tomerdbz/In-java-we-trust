package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

//import Model.AsciiMaker;

public class TemporaryHandler implements ClientHandler {

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) throws IOException {
		BufferedReader inputFromClient=new BufferedReader(new InputStreamReader(inFromClient));
		PrintWriter outputToClient=new PrintWriter(new OutputStreamWriter(outToClient));
		if(inputFromClient.readLine().startsWith("get image"))
		{
			//AsciiMaker am=new AsciiMaker();
			//am.convertToAscii(inFromClient,outToClient);
			outputToClient.flush();
		}

		
	}

}