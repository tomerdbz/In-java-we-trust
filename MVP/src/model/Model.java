package model;

import presenter.Properties;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import algorithms.search.State;

/**	MVP Model interface
 * @author Tomer
 *
 */
public interface Model {
	void generateMaze(String name,int rows, int cols,int rowSource,int colSource,int rowGoal ,int colGoal);
	Maze getMaze(String mazeName);
	void solveMaze(String mazeName, boolean displayAfterSolving);
	Solution getSolution(String mazeName);
	State getHint(String mazeName);
	void calculateHint(String mazeName, int row, int col);
	void setProperties(Properties prop);
	void stop();
}
