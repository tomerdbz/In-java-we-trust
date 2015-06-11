package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.zip.GZIPInputStream;

import presenter.ClientProperties;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import algorithms.search.State;

public class ClientModel extends Observable implements Model {
	private ClientProperties properties;
	private Socket myServer;
	private InputStream inFromServer;
	private OutputStream outToServer;
	private Maze maze;
	private Solution solution;
	private State hint;
	public ClientModel() {
		try {
			myServer=new Socket(properties.getServerIP(),properties.getServerPort());
			inFromServer=myServer.getInputStream();
			outToServer=myServer.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public ClientModel(ClientProperties properties) {
		this.properties=properties;
		try {
			myServer=new Socket(properties.getServerIP(),properties.getServerPort());
			inFromServer=myServer.getInputStream();
			outToServer=myServer.getOutputStream();
			Object[] objs=new Object[2];
			objs[0]="properties";
			objs[1]=properties;
			queryServer(objectToInputStream(objs), inFromServer, outToServer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private InputStream objectToInputStream(Object[] objs)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			for(int i=0;i<objs.length;i++)
				oos.writeObject(objs[i]);

		    oos.flush();
		    oos.close();

		    InputStream is = new ByteArrayInputStream(baos.toByteArray());
		    return is;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;


	}
	@Override
	public void generateMaze(String name, int rows, int cols, int rowSource,
			int colSource, int rowGoal, int colGoal, String notifyArgument) {
		Object[] objs=new Object[2];
		objs[0]="generate maze";
		objs[1]=name+" "+rows+","+cols+","+rowSource+","+colSource+","+rowGoal+","+colGoal;
		SerializableMaze serializableMaze=(SerializableMaze)queryServer(objectToInputStream(objs), inFromServer, outToServer);
		maze=serializableMaze.getOriginalMaze();
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
		Object[] objs=new Object[2];
		objs[0]="solve maze"; 
		objs[1]=mazeName;
		SerializableSolution serializableSolution=(SerializableSolution)queryServer(objectToInputStream(objs), inFromServer, outToServer);
		solution=serializableSolution.getOriginalSolution();
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
		Solution retSolution=solution;
		solution=null;
		return retSolution;
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
		Object[] objs=new Object[2];
		objs[0]="calculate hint";
		objs[1]=mazeName+" "+row+","+col;
		SerializableState serializableHint=(SerializableState)queryServer(objectToInputStream(objs),inFromServer,outToServer);
		hint=serializableHint.getOriginalState();
	}

	@Override
	public void stop() {
		try {
			inFromServer.close();
			outToServer.close();
			myServer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private Object queryServer(InputStream modelInput,InputStream inFromServer, OutputStream outToServer)
	{
		Object value=null;
		ObjectInputStream input;
		ObjectOutputStream outputToServer;
		try {
			input=new ObjectInputStream((modelInput));
			outputToServer = new ObjectOutputStream((outToServer));
			ObjectInputStream inputFromServer=new ObjectInputStream((inFromServer));
			outputToServer.writeObject(input.readObject());
			outputToServer.writeObject(input.readObject());
			//getting data - agreed protocol is that the data is compressed by ZIP
			GZIPInputStream inputCompressedFromServer=new GZIPInputStream(inputFromServer);
			value=new ObjectInputStream(inputCompressedFromServer).readObject();
			if(value.toString().equals("error"))
				return null;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return value;
	}
	@Override
	public void setProperties(ClientProperties properties) {
		this.properties=properties;
		Object[] objs=new Object[2];
		objs[0]="properties";
		objs[1]=properties;
		queryServer(objectToInputStream(objs), inFromServer, outToServer);
	}

}
