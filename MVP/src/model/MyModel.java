package model;

import java.util.Observable;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class MyModel extends Observable implements Model{

	@Override
	public void generateMaze(int rows, int cols, int rowSource, int colSource,
			int rowGoal, int colGoal) {
		System.out.println("Generating Maze");
		setChanged();
		notifyObservers();
	}

	@Override
	public Maze getMaze() {
		System.out.println("returns maze");
		return null;
	}

	@Override
	public void solveMaze(Maze m) {
		System.out.println("solves maze");
	}

	@Override
	public Solution getSolution() {
		System.out.println("gets maze solution after solving it with solveMaze()");
		return null;
	}

	@Override
	public void stop() {
		System.out.println("stopping...");
	}

}
