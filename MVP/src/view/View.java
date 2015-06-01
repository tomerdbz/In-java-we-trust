package view;

import java.util.concurrent.ConcurrentHashMap;

import presenter.Presenter.Command;
import presenter.Properties;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

/**	the interface defines what each View in this MVP architecture has to do!
 * @author Alon
 *
 */
public interface View {
	void start();
	void receiveData(String data);
	Properties getProperties();
	void setCommands(ConcurrentHashMap<String, presenter.Presenter.Command> commands);
	Command getUserCommand();
	void Display(String s);
	void displayMaze(Maze m);
	void displaySolution(Solution s);
	//void exit();
}