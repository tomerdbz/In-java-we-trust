package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class MVPRunnableCli extends CLI implements Runnable {

	protected HashMap<String, Command> commands;
	public MVPRunnableCli(BufferedReader in, PrintWriter out, UserCommands uc) {
		
		super(in, out, uc);
		
	}
	public Command startnew(){
		System.out.println("Enter command: ");
		out.flush();
		try {
			String line = in.readLine();
			String[] sp = line.split(" ");
			
			String commandName = sp[0];
			String arg = null;
			if (sp.length > 1)
				arg = sp[1];
			
			return this.commands.get(commandName);
		}catch(IOException e){
			
		}
		
	return null;
	}
	/*
	@Override
	public void start(){
		System.out.println("Enter command: ");
		out.flush();
		try {
			String line = in.readLine();
			
			while (!line.equals("exit"))
			{
				String[] sp = line.split(" ");
								
				String commandName = sp[0];
				String arg = null;
				if (sp.length > 1)
					arg = sp[1];
				presenter.Presenter.Command command = v.getUserCommand(commandName);
				if(command!=null && arg!=null)
					v.doCommand(command);
				out.flush();
				
				System.out.println("Enter command: ");
				out.flush();
				line = in.readLine();
			}
			System.out.println("Goodbye");
						
		} catch (IOException e) {			
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}			
		}	
		
	}*/

	@Override
	public void run() {

		this.start();
	}


}
