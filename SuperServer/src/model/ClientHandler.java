package model;

import java.net.Socket;

public interface ClientHandler {
void handleClient(Socket client);
}
