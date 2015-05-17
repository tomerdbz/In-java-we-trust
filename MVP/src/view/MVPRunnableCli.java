package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;


public class MVPRunnableCli extends CLI implements Runnable {
	
	MyView view;
	public void SetView(MyView view){
		this.view=view;
	}

	protected ConcurrentHashMap<String, presenter.Presenter.Command>  commands;
	public void setCommands(ConcurrentHashMap<String, presenter.Presenter.Command> commands2){
		this.commands=commands2;
	}
	public MVPRunnableCli(BufferedReader in, PrintWriter out) {
		
		super(in, out, null);
		
	}
	@Override
	public void start()
	{
		out.print("Enter command: ");
		out.flush();
		try {
			String line = in.readLine();
			
			while (!line.equals("exit"))
			{
				String[] sp = line.split(" ");
				if(sp.length>2)
				{
				String commandName = sp[0]+" "+sp[1];
				String arg= "";
				for(int i =2;i<sp.length;i++){
					 arg = arg+ " "+ sp[i];
				}
				presenter.Presenter.Command c= this.commands.get(commandName);
				this.view.c=c;
				this.view.ChangeNotify(arg);
				}
				out.print("Enter command: ");
				out.flush();
				line = in.readLine();
			}
			view.exit();
						
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