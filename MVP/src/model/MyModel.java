package model;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

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

/**
 * @author Tomer
 *
 */
public class MyModel extends Observable implements Model{
	private ConcurrentHashMap<Maze,Solution> cache=new ConcurrentHashMap<Maze,Solution>();
	private ConcurrentLinkedQueue<String> databaseNames=new ConcurrentLinkedQueue<String>();
	private Properties prop;
	private ListeningExecutorService executor;
	private ConcurrentHashMap<String,Maze> generatedMazes=new ConcurrentHashMap<String,Maze>();
	//private ConcurrentLinkedQueue<Maze> mazeQueue=new ConcurrentLinkedQueue<Maze>();
	//private ConcurrentLinkedQueue<Solution> solutionQueue=new ConcurrentLinkedQueue<Solution>();
	public MyModel(Properties prop)
	{
			SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
			Session session = factory.openSession();
			DBMaze check=(DBMaze)session.get(DBMaze.class,"bobo");
			SerializableMaze chk=check.getMaze();
			Query query = session.createQuery("from model.DBMaze");

			List <DBMaze>list = query.list();
			Iterator<DBMaze> it=list.iterator();
			DBMaze dbmaze;
			while(it.hasNext())
			{
				dbmaze=it.next();
				databaseNames.add(dbmaze.getName());
				generatedMazes.put(dbmaze.getName(), dbmaze.getMaze());
				cache.put(dbmaze.getMaze(), dbmaze.getSolution());
			}
			this.prop=prop;
			executor=MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(prop.getThreadNumber()));
		
	}
	private AnnotationConfiguration getConfiguration()
	{
		AnnotationConfiguration config=null;
		try{
			ObjectInputStream in=new ObjectInputStream(new FileInputStream("HibernateConfig.cfg"));
			config=(AnnotationConfiguration)in.readObject();
			in.close();
		}catch(Exception e)
		{
			
		}
		return config;
		
	}
	@Override
	public void generateMaze(String name,int rows, int cols, int rowSource, int colSource,
			int rowGoal, int colGoal) {
		ListenableFuture<Maze> futureMaze=null;
		switch(prop.getMazeGenerator()) //it worked with anonymous class!!!!! dont know why but do this for all and investigate further!!!!!
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

	@Override
	public Maze getMaze(String name) {
		System.out.println("returns maze");
		return generatedMazes.get(name);
	}

	@Override
	public void solveMaze(String mazeName) {
		Maze m=generatedMazes.get(mazeName);
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
					return new AStar(new MazeAirDistance(prop.getRowGoal(),prop.getColGoal(),prop.getMovementCost())).search(new MazeSearch(m,prop.getMovement(),prop.getMovementCost(),prop.getDiagonalMovementCost()));
				}
			});
			break;
		case MANHATTAN_DISTANCE_ASTAR:
			futureSolution=executor.submit(new Callable<Solution>() {

				@Override
				public Solution call() throws Exception {
					return new AStar(new MazeManhattanDistance(prop.getRowGoal(),prop.getColGoal(),prop.getMovementCost())).search(new MazeSearch(m,prop.getMovement(),prop.getMovementCost(),prop.getDiagonalMovementCost()));
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
	@Override
	public Solution getSolution(String name) {
		System.out.println("gets maze solution after solving it with solveMaze()");
		return cache.get(generatedMazes.get(name));
	}

	@Override
	public void stop() {
		System.out.println("stopping...");
		SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		ConcurrentLinkedQueue<String> names=namesToWriteToDB();
		for(String str : names)
		{
			if(cache.containsKey(generatedMazes.get(str)))
			{
				DBMaze data=new DBMaze(str,new SerializableMaze(generatedMazes.get(str)),new SerializableSolution(cache.get(generatedMazes.get(str))));
				session.save(data);//add flush every once in a while - 20?
			}
		}
		session.getTransaction().commit();
		executor.shutdown();
	}
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