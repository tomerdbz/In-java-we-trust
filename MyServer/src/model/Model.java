package model;

import presenter.ServerProperties;

public interface Model {
	public void getStatusClient(String client);
	public void DisconnectClient(String client);
	public void StartServer();
	public void DisconnectServer();
}
