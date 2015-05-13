package view;

import java.util.ArrayList;
import java.util.Observable;

import presenter.Presenter.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;


public class MyView extends Observable implements View {
	MVPRunnableCli cl;
	@Override
	public void start() {
		System.out.println("starting view");
		setChanged();
		notifyObservers();
		Thread t =new Thread(new MVPRunnableCli(null, null, null, null));
		t.start();
	}

	@Override
	public void setCommands(Command c) {
		System.out.println("setting command");
	}

	@Override
	public Command getUserCommand(String Command) {
		System.out.println("gets user command");
		return null;
	}

	@Override
	public void displayMaze(Maze m) {
		System.out.println("displays maze");
	}

	@Override
	public void displaySolution(Solution s) {
		System.out.println("displays maze's solution");
	}

	@Override
	public void doCommand(Command c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Display(String s) {
		System.out.println(s);
		
	}

	
}
