package model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.AStar;
import algorithms.search.Heuristic;
import algorithms.search.Movement;
import algorithms.search.Solution;

public class AStarMazeSolverCallable extends CommonMazeSolverCallable{

	public AStarMazeSolverCallable(Maze m,Movement movement, double mCost,double mDiagonalCost, Heuristic h) {
		super(m,movement, mCost,mDiagonalCost);
		setSolver(new AStar(h));
	}
	
	public AStarMazeSolverCallable(Maze m, double mCost,Heuristic h) {
		super(m, mCost);
		setSolver(new AStar(h));
	}
	@Override
	public Solution call() throws Exception {
		return getSolver().search(getMazeToSolve());
	}

}
