package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.zip.GZIPInputStream;

import presenter.ClientProperties;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import algorithms.search.State;

public class ClientModel extends Observable implements Model {
	private ClientProperties properties;
	private Maze maze;
	private Solution solution;
	private State hint;
	
	public ClientModel(ClientProperties properties) {
		this.properties=properties;
	}
	@Override
	public void generateMaze(String name, int rows, int cols, int rowSource,
			int colSource, int rowGoal, int colGoal, String notifyArgument) {
		String property=null;
		switch(properties.getMazeGenerator())
		{
			case DFS:
				property="DFS";
				break;
			case RANDOM:
				property="RANDOM";
				break;
			default:
				return;
		}
		SerializableMaze serializableMaze=(SerializableMaze)queryServer(properties.getServerIP(),properties.getServerPort(),"generate maze",name+" "+rows+","+cols+","+rowSource+","+colSource+","+rowGoal+","+colGoal,property);
		maze=serializableMaze.getOriginalMaze();
		setChanged();
		notifyObservers(notifyArgument+" "+name);
	}

	@Override
	public Maze getMaze(String mazeName) {
		if(maze==null)
		{
			setChanged();
			notifyObservers("error");
		}
		Maze retMaze=maze;
		maze=null;
		return retMaze;
	}

	@Override
	public void solveMaze(String mazeName, String notifyArgument) {
		String property=null;
		switch(properties.getMazeSolver())
		{
			case BFS:
				property="BFS";
				break;
			case MANHATTAN_DISTANCE_ASTAR:
				property="MANHATTAN_DISTANCE_ASTAR";
				break;
			case AIR_DISTANCE_ASTAR:
				property="AIR_DISTANCE_ASTAR";
				break;
			default:
				return;
		}
		switch(properties.getMovement())
		{
		case DIAGONAL:
			property+=" DIAGONAL";
			break;
		case NONDIAGONAL:
			property+=" NONDIAGONAL";
		}
		property+=" "+properties.getMovementCost()+" "+properties.getDiagonalMovementCost();
		SerializableSolution serializableSolution=(SerializableSolution)queryServer(properties.getServerIP(),properties.getServerPort(),"solve maze",mazeName,property);
		solution=serializableSolution.getOriginalSolution();
		System.out.println(solution);
		setChanged();
		notifyObservers(notifyArgument+" "+mazeName);
	}

	@Override
	public Solution getSolution(String mazeName) {
		if(solution==null)
		{
			setChanged();
			notifyObservers("error");
		}
		Solution retSol=solution;
		solution=null;
		return retSol;
	}

	@Override
	public State getHint(String mazeName) {
		if(hint==null)
		{
			setChanged();
			notifyObservers("error");
		}
		State retHint=hint;
		hint=null;
		return retHint;
	}

	@Override
	public void calculateHint(String mazeName, int row, int col,
			String notifyArgument) {
		String property=null;
		switch(properties.getMazeSolver())
		{
			case BFS:
				property="BFS";
				break;
			case MANHATTAN_DISTANCE_ASTAR:
				property="MANHATTAN_DISTANCE_ASTAR";
				break;
			case AIR_DISTANCE_ASTAR:
				property="AIR_DISTANCE_ASTAR";
				break;
			default:
				return;
		}
		switch(properties.getMovement())
		{
		case DIAGONAL:
			property+=" DIAGONAL";
			break;
		case NONDIAGONAL:
			property+=" NONDIAGONAL";
		}
		property+=" "+properties.getMovementCost()+" "+properties.getDiagonalMovementCost();
		SerializableState serializableHint=(SerializableState)queryServer(properties.getServerIP(),properties.getServerPort(),"calculate hint",mazeName+" "+row+","+col,property);
		hint=serializableHint.getOriginalState();
		setChanged();
		notifyObservers(notifyArgument+ " "+mazeName);
		//what to notify check in MyModel//notifyObservers(notifyArgument);
	}

	@Override
	public void stop() {
	
		
	}
	
	
	private Object queryServer(String serverIP,int serverPort,String command,String data,String property)
	{
		Object result=null;
			Socket server;			
			try {
				server = new Socket(serverIP,serverPort);
				PrintWriter writerToServer=new PrintWriter(new OutputStreamWriter(server.getOutputStream()));
				writerToServer.println(command);
				writerToServer.flush();
				writerToServer.println(property);
				writerToServer.flush();
				writerToServer.println(data);
				writerToServer.flush();
				ObjectInputStream inputDecompressed;
				inputDecompressed = new ObjectInputStream(new GZIPInputStream(server.getInputStream()));
				result=inputDecompressed.readObject();
				writerToServer.close();
				inputDecompressed.close();
				server.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		return result;
		
	}
	@Override
	public void setProperties(ClientProperties properties) {
		this.properties=properties;
	}

}
