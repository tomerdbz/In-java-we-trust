package model;

import java.util.concurrent.Callable;

import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MazeGenerator;

/** Chose working with DFS. much smarter.
 * @author Tomer
 *
 */
public class MazeGeneratorCallable implements Callable<Maze> {
	private MazeGenerator gen;
	private int rows;
	private int cols;
	public MazeGeneratorCallable(int rows, int cols)
	{
		this.rows=rows;
		this.cols=cols;
		gen=new DFSMazeGenerator();
	}
	@Override
	public Maze call() throws Exception {
		return gen.generateMaze(rows, cols, 0, 0, rows, cols);
	}

}
