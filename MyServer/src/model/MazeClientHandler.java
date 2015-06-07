package model;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import algorithms.demo.MazeAirDistance;
import algorithms.demo.MazeManhattanDistance;
import algorithms.demo.MazeSearch;
import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.RandomMazeGenerator;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.Solution;
import database.management.SerializableMaze;
import database.management.SerializableSolution;

public class MazeClientHandler implements ClientHandler {

	MazeServer server;
	ClientProperties clientProperties;
	public MazeClientHandler(MazeServer server) {
		this.server=server;
	}
	
	
	public void setClientProperties(ClientProperties clientProperties) {
		this.clientProperties = clientProperties;
	}
	
	/** Please note: data should be sent to client compressed by GZIP!
	 */
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient)
	{
		try {
		ObjectInputStream inputFromClient=new ObjectInputStream(inFromClient);
		ObjectOutputStream outputFromClient=new ObjectOutputStream(outToClient);
		String command;
		while((command=(String)inputFromClient.readObject()).contains("exit"))
		{
			if(command.contains("properties"))
				setClientProperties();
			else if(command.contains("generate maze"))
				generateMaze();
			else if(command.contains("solve maze"))
				solveMaze();
			else if(command.contains("calculate hint"))
				calculateHint();
		}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** This method generates a maze! of course, it checks if the maze name was not asked before - don't try to fool it.
	 * generating algorithm will be based on the one given at Ct'r by Property.
	 * @param name - the Maze name given.
	 * @param rows - number of rows in generated maze
	 * @param - cols - number of cols in generated maze
	 * @param - rowSource - Source row of generated maze
	 * @param - colSource - Source col of generated maze
	 * @param - rowGoal - Goal row of generated maze
	 * @param - colGoal - Goal col of generated maze
	 * @return - a SerializableMaze to send to client
	 */
	public SerializableMaze generateMaze(String name,int rows, int cols, int rowSource, int colSource,
			int rowGoal, int colGoal,String notifyArgument) {
		if(this.clientProperties==null)
		{
			setChanged();
			notifyObservers("Properties are not set");
			return;
		}
		if(server.generatedMazes.containsKey(name))
			return;
		Maze maze=null;
		switch(clientProperties.getMazeGenerator()) 
		{
		case DFS:
			maze=new DFSMazeGenerator().generateMaze(rows, cols, rowSource, colSource, rowGoal, colGoal);
			break;
		case RANDOM:
			maze=new RandomMazeGenerator().generateMaze(rows, cols, rowSource, colSource, rowGoal, colGoal);;
			break;
		default:
			break;
		}
		server.generatedMazes.put(name, maze);
		return new SerializableMaze(maze);
				
	}
	
	
	/** solves a generated maze. upon solving - saves in cache.
	 * @param - mazeName - maze name to solve.
	 * @return - a SerializableSolution to send to the client.
	 */
	@Override
	public SerializableSolution solveMaze(String mazeName,String notifyArgument) {
		if(this.clientProperties==null)
		{
			setChanged();
			notifyObservers("Properties are not set");
			return;
		}
		Maze m=server.generatedMazes.get(mazeName);
		if(m==null)
		{
			setChanged();
			notifyObservers("error");
			return;
		}
		if(server.cache.containsKey(m))
		{
			//solutionQueue.add(cache.get(m));
			setChanged();
			notifyObservers("maze solved display "+mazeName);
			return;
		}
		Solution solution=null;
		switch(clientProperties.getMazeSolver())
		{
		case BFS:
			solution=new BFS().search(new MazeSearch(m,properties.getMovement(),properties.getMovementCost(),properties.getDiagonalMovementCost()));
			break;
		case AIR_DISTANCE_ASTAR:
			solution=new AStar(new MazeAirDistance(m.getRowGoal(),m.getColGoal(),properties.getMovementCost())).search(new MazeSearch(m,properties.getMovement(),properties.getMovementCost(),properties.getDiagonalMovementCost()));
			break;
		case MANHATTAN_DISTANCE_ASTAR:
			solution=new AStar(new MazeManhattanDistance(m.getRowGoal(),m.getColGoal(),properties.getMovementCost())).search(new MazeSearch(m,properties.getMovement(),properties.getMovementCost(),properties.getDiagonalMovementCost()));;
			break;
			
		default:
			break;
		}
		server.cache.put(m, solution);
		return new SerializableSolution(solution);
		
	}

}
