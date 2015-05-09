package model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.BFS;
import algorithms.search.Movement;
import algorithms.search.Solution;

public class BFSMazeSolverCallable extends CommonMazeSolverCallable {

	public BFSMazeSolverCallable(Maze m, Movement movement, double mCost,
			double mDiagonalCost) {
		super(m, movement, mCost, mDiagonalCost);
		setSolver(new BFS());
	}
	public BFSMazeSolverCallable(Maze m, double mCost) {
		super(m, mCost);
		setSolver(new BFS());
	}

	@Override
	public Solution call() throws Exception {
		return getSolver().search(getMazeToSolve());
	}

}
