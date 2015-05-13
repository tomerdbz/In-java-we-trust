package view;

import presenter.Presenter.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public interface View {
	void start();
	void setCommands(Command c);
	Command getUserCommand(String Command);
	void doCommand(Command c);
	void Display(String s);
}
