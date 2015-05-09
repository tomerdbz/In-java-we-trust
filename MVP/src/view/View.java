package view;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public interface View {
	void start();
	void setCommands(UserCommands uc);
	Command getUserCommand();
	void displayMaze(Maze m);
	void displaySolution(Solution s);
}
