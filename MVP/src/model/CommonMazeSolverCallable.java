package model;

import java.util.concurrent.Callable;

import algorithms.demo.MazeSearch;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Movement;
import algorithms.search.Searcher;
import algorithms.search.Solution;

public  abstract class CommonMazeSolverCallable implements Callable<Solution> {
	private Searcher solver;
	private MazeSearch mazeToSolve;
	public Searcher getSolver() {
		return solver;
	}
	public void setSolver(Searcher solver) {
		this.solver = solver;
	}
	public MazeSearch getMazeToSolve() {
		return mazeToSolve;
	}
	public void setMazeToSolve(MazeSearch mazeToSolve) {
		this.mazeToSolve = mazeToSolve;
	}
	public CommonMazeSolverCallable(Maze m, Movement movement,double mCost,double mDiagonalCost)
	{
		mazeToSolve=new MazeSearch(m,movement,mCost,mDiagonalCost);
	}
	public CommonMazeSolverCallable(Maze m,double mCost)
	{
		mazeToSolve=new MazeSearch(m,mCost);
	}
	public abstract Solution call() throws Exception;

}
