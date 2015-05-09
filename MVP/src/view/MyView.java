package view;

import java.util.ArrayList;
import java.util.Observable;

import presenter.Presenter.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class MyView extends Observable implements View {
	ArrayList<Command> commandsArray=new ArrayList<Command>();
	@Override
	public void start() {
		System.out.println("starting view");
		setChanged();
		notifyObservers();
	}

	@Override
	public void setCommands(Command c) {
		System.out.println("setting command");
		commandsArray.add(c);
	}

	@Override
	public Command getUserCommand() {
		System.out.println("gets user command");
		return commandsArray.get(0);
	}

	@Override
	public void displayMaze(Maze m) {
		System.out.println("displays maze");
	}

	@Override
	public void displaySolution(Solution s) {
		System.out.println("displays maze's solution");
	}

}
