package view;

import java.util.concurrent.ConcurrentHashMap;

import presenter.Presenter.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public interface View {
	void start();
	void setCommands(ConcurrentHashMap<String, presenter.Presenter.Command> commands);
	Command getUserCommand();
	void Display(String s);
	void displayMaze(Maze m);
	void displaySolution(Solution s);
	void exit();
}