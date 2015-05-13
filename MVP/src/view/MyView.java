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
		Thread t =new Thread(new MVPRunnableCli(null, null, null));
		t.start();
	}

	@Override
	public void setCommands(Command c) {
		System.out.println("setting command");
	}

	@Override
	public Command getUserCommand(String Command) {
		System.out.println("gets user command");
		setChanged();
		notifyObservers();
		return null;
	}


	@Override
	public void doCommand(Command c) {
		setChanged();
		notifyObservers();
		
	}

	@Override
	public void Display(String s) {
		System.out.println(s);
		
	}

	
}
