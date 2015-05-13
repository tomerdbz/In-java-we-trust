package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class MVPRunnableCli extends CLI implements Runnable {

	MyView v;
	public void SetView(MyView v){
		this.v=v;
	}
	public MVPRunnableCli(BufferedReader in, PrintWriter out, UserCommands uc) {
		
		super(in, out, uc);
		
	}
	@Override
	public void start(){
		v.Display("Enter command: ");
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
				// Invoke the command
				presenter.Presenter.Command command = v.getUserCommand(commandName);
				if(command!=null && arg!=null)
					v.doCommand(command);
				out.flush();
				
				v.Display("Enter command: ");
				out.flush();
				line = in.readLine();
			}
			v.Display("Goodbye");
						
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
		
	}

	@Override
	public void run() {

		this.start();
	}


}
