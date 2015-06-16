package presenter;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

import model.Model;
import view.ServerWindow;
import view.View;



public class Presenter implements Observer{
	public class ConnectionStatus implements ServerCommand {

		@Override
		public void doCommand(String params) {
			m.getStatusClient(params);
			
		}

	}
	public class DisconnectUser implements ServerCommand{

		@Override
		public void doCommand(String params) {
			m.DisconnectClient(params);
			
		}
		
	}
	public class StartServer implements ServerCommand{

		@Override
		public void doCommand(String params) {
			m.StartServer();
			
		}
		
	}
	public class StopServer implements ServerCommand{

		@Override
		public void doCommand(String params) {
			m.DisconnectServer();
			
		}
		
	}
	public class ExitWindow implements ServerCommand{

		@Override
		public void doCommand(String params) {
			m.exit();
		}
		
	}
	
	Model m;
	View v;
	
	public Presenter(Model m,View v)
	{
		this.m=m;
		this.v=v;
		ConcurrentHashMap<String, ServerCommand> commandMap=new ConcurrentHashMap<String, ServerCommand>();
		commandMap.put("connection status", new ConnectionStatus());
		commandMap.put("disconnect user", new DisconnectUser());
		commandMap.put("start server",new StartServer());
		commandMap.put("stop server", new StopServer());
		commandMap.put("exit", new ExitWindow());
			v.setCommands(commandMap);
		}
	@Override
	public void update(Observable o, Object arg) {
		if(o == v){
			String data = (String)(arg);
			if(data!=null)
			v.getCommand().doCommand(data);
			else
				v.getCommand().doCommand("");
			
		}
		if(o == m ){
			String data = (String)(arg);
			if(data.split(" ")[0].equals("msg")){
			v.Display(data.substring(4));
			}
			else
				if(data.split(" ")[0].equals("add"))
					v.addClient(data.substring(4));
				else
					if(data.split(" ")[0].equals("remove"))
						v.removeClient(data.substring(7));
				else
			v.saveData(data);
		}
		
		
	}
}

