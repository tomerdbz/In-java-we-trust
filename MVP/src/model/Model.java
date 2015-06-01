package model;

import presenter.Properties;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

/**	MVP Model interface
 * @author Tomer
 *
 */
public interface Model {
	void generateMaze(String name,int rows, int cols,int rowSource,int colSource,int rowGoal ,int colGoal);
	Maze getMaze(String mazeName);
	void solveMaze(String mazeName);
	Solution getSolution(String mazeName);
	void setProperties(Properties prop);
	void stop();
}
