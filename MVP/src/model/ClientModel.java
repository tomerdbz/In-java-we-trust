package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Observable;
import java.util.zip.GZIPInputStream;

import presenter.ClientModelProperties;
import presenter.Properties;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import algorithms.search.State;

public class ClientModel extends Observable implements Model {
	private ClientModelProperties properties;
	public ClientModel(ClientModelProperties properties) {
		this.properties=properties;
		Object[] objs=new Object[2];
		objs[0]="properties";
		objs[1]=properties;
		queryServer(objectToInputStream(objs), inFromServer, outToServer)
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
		Object[] objs=new Object[1];
		objs[0]=notifyArgument+" "+ name+" "+rows+","+cols+","+rowSource+","+colSource+","+rowGoal+","+colGoal;
		queryServer(objectToInputStream(objs), inFromServer, outToServer);
	}

	@Override
	public Maze getMaze(String mazeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void solveMaze(String mazeName, String notifyArgument) {
		Object[] objs=new Object[1];
		objs[0]=notifyArgument+" "+ mazeName;
		queryServer(objectToInputStream(objs), inFromServer, outToServer);
		
	}

	@Override
	public Solution getSolution(String mazeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State getHint(String mazeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void calculateHint(String mazeName, int row, int col,
			String notifyArgument) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProperties(MyModelProperties prop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return value;
	}
	@Override
	public void setProperties(Properties prop) {
		// TODO Auto-generated method stub
		
	}

}
