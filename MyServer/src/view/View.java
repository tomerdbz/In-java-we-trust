package view;

import java.util.concurrent.ConcurrentHashMap;

import presenter.ServerCommand;

public interface View {
	
	public ServerCommand getCommand();
	public void setCommands(ConcurrentHashMap<String, ServerCommand> commandMap);
	public void Display(String msg);
	public void saveData(String data);

}
