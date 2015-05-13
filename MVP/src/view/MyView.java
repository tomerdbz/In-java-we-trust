package view;

import java.util.ArrayList;
import java.util.Observable;

import presenter.Presenter.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;


public class MyView extends Observable implements View {
	MVPRunnableCli cl;
	

	@Override
	public void setCommands(Command c) {
		System.out.println("setting command");
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

	@Override
	public void start() {
		//System.out.println("starting view");
		//Thread t =new Thread(new MVPRunnableCli(null, null, null));
		//t.start();
		setChanged();
		notifyObservers();
		
		}

	@Override
	public void displaySolution(Solution s) {
		System.out.println("displaying solution");
		s.getPath().toString();
		
	}

	@Override
	public void displayMaze(Maze m) {
		System.out.println("displaying maze");
		RegularMazeDisplayer dp = new RegularMazeDisplayer();
		dp.DisplayMaze(m);
		
	}

	@Override
	public Command getUserCommand() {
		System.out.println("gets user command");
		cl.start();
		return null;
	}

	
}
