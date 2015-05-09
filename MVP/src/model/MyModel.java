package model;

import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
//when waiting for a result - how to wait and do other things? need to think this through! 
public class MyModel extends Observable implements Model{
	ExecutorService executor=Executors.newCachedThreadPool();
	ConcurrentLinkedQueue<Future<Maze>> mazeQueue=new ConcurrentLinkedQueue<Future<Maze>>();
	ConcurrentLinkedQueue<Future<Solution>> solutionQueue=new ConcurrentLinkedQueue<Future<Solution>>();
	@Override
	public void generateMaze(int rows, int cols, int rowSource, int colSource,
			int rowGoal, int colGoal) {
		mazeQueue.add(executor.submit(new MazeGeneratorCallable(16,16)));
		
	}

	@Override
	public Maze getMaze() {
		System.out.println("returns maze");
		return null;
	}

	@Override
	public void solveMaze(Maze m) {
		System.out.println("solves maze");
		solutionQueue.add(executor.submit(new BFSMazeSolverCallable(m, 10)));
	}

	@Override
	public Solution getSolution() {
		System.out.println("gets maze solution after solving it with solveMaze()");
		return null;
	}

	@Override
	public void stop() {
		System.out.println("stopping...");
		executor.shutdown();
	}

}
