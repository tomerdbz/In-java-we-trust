package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.util.SerializationHelper;

import presenter.Properties;
import algorithms.demo.MazeAirDistance;
import algorithms.demo.MazeManhattanDistance;
import algorithms.demo.MazeSearch;
import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.RandomMazeGenerator;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.Solution;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
/**	This class is the MVP Model. It features thread handling with the help of guava lib.
 * 	as part of the MVP - upon every completion of method comes a call to the Observer - Presenter - to take care of things with a proper argument.
 * 	And the sweet cherry - It features HIBERNATE! 
 * @author Tomer
 * @co-author Alon
 *
 */
public class MyModel extends Observable implements Model{
	/**	every solved maze will be inserted to the cache - a HashMap linking between a maze and its solution. 
	 * notice the Concurrent because of the workings with Threads.
	 * 
	 */
	private ConcurrentHashMap<Maze,Solution> cache=new ConcurrentHashMap<Maze,Solution>();
	/**	databaseNames will contain names of mazes which were brought from the database. when we'll write back at the end we won't write them again..
	 * 
	 */
	private ConcurrentLinkedQueue<String> databaseNames=new ConcurrentLinkedQueue<String>();
	/**	Properties defined. see Properties class for more info.
	 * 
	 */
	private Properties prop;
	/** Guava Library class - this service allows registered Callables and Runs to define What should be performed once a thread has succeeded. (or failed)
	 * 
	 */
	private ListeningExecutorService executor;
	/** Any generated maze will be inserted to this field, a HashMap mapping between Maze Names and the mazes themselves.
	 * 
	 */
	private ConcurrentHashMap<String,Maze> generatedMazes=new ConcurrentHashMap<String,Maze>();
	//private ConcurrentLinkedQueue<Maze> mazeQueue=new ConcurrentLinkedQueue<Maze>();
	//private ConcurrentLinkedQueue<Solution> solutionQueue=new ConcurrentLinkedQueue<Solution>();
	/** MyModel Ct'r - it first loads all the records from the database while every record contains a maze name, a serialized maze, a serialized maze solution
	 * 	after all the loading ends - we define the ExecutorService to handle the amount of threads given at the parameter simultaneously.
	 * Property will be saved for future methods.
	 * @param prop - Properties for the model. calcs and actions may vary based on this parameter.
	 * @author Tomer, Alon 
	 */
	public MyModel(Properties prop)
	{
			/*SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
			Session session = factory.openSession();
			Query query = session.createQuery("from model.DBMaze");

			@SuppressWarnings("unchecked")
			List <DBMaze>list = query.list();
			Iterator<DBMaze> it=list.iterator();
			DBMaze dbmaze;
			while(it.hasNext())
			{
				dbmaze=it.next();
				databaseNames.add(dbmaze.getName());
				
				try {
					byte[] mazeBytes;
					Blob bMaze=dbmaze.getMaze();
					int bMazeLength=(int)bMaze.length();
					mazeBytes=bMaze.getBytes(1, bMazeLength);
					if(bMazeLength!=bMaze.length())//data loss for really really big mazes - we will get one byte array and then another - then merge.
					{
						int diff=(int)(bMaze.length()-(long)bMazeLength);//long= 2*int - will only need to do this once!
						byte[] mazeBytes2=bMaze.getBytes(bMazeLength, diff);
						ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
						try {
							outputStream.write( mazeBytes);
							outputStream.write( mazeBytes2 );
							mazeBytes = outputStream.toByteArray( );
						} catch (IOException e) {
							setChanged();
							notifyObservers("error");
						}
						
					}
					Maze maze = ((SerializableMaze) (SerializationHelper.deserialize(mazeBytes))).getOriginalMaze();
					generatedMazes.put(dbmaze.getName(), maze);
					Blob bSolution=dbmaze.getSolution();
					int bSolutionLength=(int)bSolution.length();
					byte[] solutionBytes=bSolution.getBytes(1, bSolutionLength);
					if(bSolutionLength!=bSolution.length())//data loss for really really big mazes - we will get one byte array and then another - then merge.
					{
						int diff=(int)(bSolution.length()-(long)bSolutionLength);//long= 2*int - will only need to do this once!
						byte[] solutionBytes2=bSolution.getBytes(bSolutionLength, diff);
						
						ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
						try {
							outputStream.write( solutionBytes);
							outputStream.write( solutionBytes2 );
							solutionBytes = outputStream.toByteArray( );
						} catch (IOException e) {
							setChanged();
							notifyObservers("exit");
						}
						
					}
					SerializableSolution sol=(SerializableSolution)SerializationHelper.deserialize(solutionBytes);
					cache.put(maze, sol.getOriginalSolution());
				} catch (Exception e1) {
					setChanged();
					notifyObservers("error");
				} 
			}*/
			this.prop=prop;
			executor=MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(prop.getThreadNumber()));
		
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
	 */
	@Override
	public void generateMaze(String name,int rows, int cols, int rowSource, int colSource,
			int rowGoal, int colGoal) {
		if(generatedMazes.containsKey(name))
			return;
		ListenableFuture<Maze> futureMaze=null;
		switch(prop.getMazeGenerator()) 
		{
		case DFS:
			futureMaze=	executor.submit(new Callable<Maze>() {

				@Override
				public Maze call() throws Exception {
					return new DFSMazeGenerator().generateMaze(rows, cols, rowSource, colSource, rowGoal, colGoal);
				}
			});
			break;
		case RANDOM:
			futureMaze=executor.submit(new Callable<Maze>() {

				@Override
				public Maze call() throws Exception {	
					return new RandomMazeGenerator().generateMaze(rows, cols, rowSource, colSource, rowGoal, colGoal);
				}
			});
			break;
		default:
			break;
		}
		if(futureMaze!=null)
		{			
			Futures.addCallback(futureMaze, new FutureCallback<Maze>() {

				@Override
				public void onFailure(Throwable arg0) {
					System.out.println("epic fail!");
					arg0.printStackTrace();
				}

				@Override
				public void onSuccess(Maze arg0) {
					//mazeQueue.add(arg0);
					generatedMazes.put(name, arg0);
					setChanged();
					notifyObservers("maze " + name+  " generated");
				}
			
			});
		}
	}

	/** gets a maze that was generated.
	 * @param - name - generated maze name.
	 */
	@Override
	public Maze getMaze(String name) {
		Maze m;
		if((m=generatedMazes.get(name))==null)
		{
			setChanged();
			notifyObservers("error");
			return null;
		}
		return m;
	}

	/** solves a generated maze. upon solving - saves in cache.
	 * @param - mazeName - maze name to solve.
	 */
	@Override
	public void solveMaze(String mazeName) {
		Maze m=generatedMazes.get(mazeName);
		if(m==null)
		{
			setChanged();
			notifyObservers("error");
			return;
		}
		if(cache.containsKey(m))
		{
			//solutionQueue.add(cache.get(m));
			setChanged();
			notifyObservers("maze " + mazeName+ " solved");
			return;
		}
		ListenableFuture<Solution> futureSolution=null;
		switch(prop.getMazeSolver())
		{
		case BFS:
			futureSolution=executor.submit(new Callable<Solution>() {

				@Override
				public Solution call() throws Exception {
					return new BFS().search(new MazeSearch(m,prop.getMovement(),prop.getMovementCost(),prop.getDiagonalMovementCost()));
				}
			});
			break;
		case AIR_DISTANCE_ASTAR:
			futureSolution=executor.submit(new Callable<Solution>() {

				@Override
				public Solution call() throws Exception {
					return new AStar(new MazeAirDistance(m.getRowGoal(),m.getColGoal(),prop.getMovementCost())).search(new MazeSearch(m,prop.getMovement(),prop.getMovementCost(),prop.getDiagonalMovementCost()));
				}
			});
			break;
		case MANHATTAN_DISTANCE_ASTAR:
			futureSolution=executor.submit(new Callable<Solution>() {

				@Override
				public Solution call() throws Exception {
					return new AStar(new MazeManhattanDistance(m.getRowGoal(),m.getColGoal(),prop.getMovementCost())).search(new MazeSearch(m,prop.getMovement(),prop.getMovementCost(),prop.getDiagonalMovementCost()));
				}
			});
			break;
			
		default:
			break;
		}
		if(futureSolution!=null)
			Futures.addCallback(futureSolution, new FutureCallback<Solution>() {

				@Override
				public void onFailure(Throwable arg0) {
				
				}

				@Override
				public void onSuccess(Solution arg0) {
					//solutionQueue.add(arg0);
					cache.put(m, arg0);
					setChanged();
					notifyObservers("maze " + mazeName + " solved");
				}
			
			});
		
	}
	/** gets a maze Solution.
	 * @param - name - Maze name.
	 */
	@Override
	public Solution getSolution(String name) {
		if(!generatedMazes.containsKey(name))
		{
			setChanged();
			notifyObservers("error");
			return null;
		}
		return cache.get(generatedMazes.get(name));
	}

	/** Stops the Model job. first thing it does - saves new mazes THAT WERE SOLVED to the database. (no point in saving unsolved mazes...)
	 * then shuts down cleanly the ExecutorService.
	 */
	@Override
	public void stop() {
		/*SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		ConcurrentLinkedQueue<String> names=namesToWriteToDB();
		for(String str : names)
		{
			if(cache.containsKey(generatedMazes.get(str)))
			{
				byte[] mazeToWrite=SerializationHelper.serialize(new SerializableMaze(generatedMazes.get(str)));
				byte[] solutionToWrite=SerializationHelper.serialize(new SerializableSolution(cache.get(generatedMazes.get(str))));
				DBMaze data=new DBMaze(str,Hibernate.createBlob(mazeToWrite),Hibernate.createBlob(solutionToWrite));
				session.save(data);//add flush every once in a while - 20?
			}
		}
		session.getTransaction().commit();*/
		executor.shutdown();
	}
	/**Helper to stop - to know based on the field databaseNames what mazes should be written to DB. (don't wanna write them again)
	 * @return a concurrent linked queue containing mazes' names that should be written to DB. 
	 */
	private ConcurrentLinkedQueue<String> namesToWriteToDB()
	{
		ConcurrentLinkedQueue<String> names=new ConcurrentLinkedQueue<String>(); 
		for(String str : generatedMazes.keySet())
		{
			if(!databaseNames.contains(str))
				names.add(str);
		}
		return names;
	}

}