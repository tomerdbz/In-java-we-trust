package model;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class TemporaryClient {
	public void loop(InputStream inFromUser,InputStream inFromServer, OutputStream outToServer) throws IOException
	{
		BufferedReader input=new BufferedReader(new InputStreamReader(inFromUser));
		PrintWriter output=new PrintWriter(new OutputStreamWriter(outToServer));
		BufferedReader inServer=new BufferedReader(new InputStreamReader(inFromServer));

		String line;
		while(!(line=input.readLine()).equals("tanks"))
		{
			String[] fields=line.split(" ");
			
			if(fields.length==4 && fields[0].equals("convert") && fields[1].equals("image"))
			{
				FileInputStream fin=new FileInputStream(fields[2]);
				FileOutputStream fout=new FileOutputStream(fields[3]);
				output.println("get image");
				output.flush();
				String data;
				int i;
				ByteArrayOutputStream outie=new ByteArrayOutputStream();
				while((i=fin.read())!=-1)
				{
					outie.write(fin.read());
					outie.flush();

				}
				outie.flush();
				outToServer.write(outie.toByteArray());
				System.out.println(outie.toString());
				
				while((data=inServer.readLine())!=null)
				{
					System.out.println(data);
					fout.write(data.getBytes());
					fout.flush();
				}
				
			}		
		}
		output.println("exit");
		output.flush();
	}
}
