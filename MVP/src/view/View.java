package view;

import presenter.Presenter.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public interface View {
	void start();
	void setCommands(Command c);
	Command getUserCommand();
	void doCommand(Command c);
	void Display(String s);
	void displayMaze(Maze m);
	void displaySolution(Solution s);
}
