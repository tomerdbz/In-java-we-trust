package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

import presenter.Presenter.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;


public class MyView extends Observable implements View {
	MVPRunnableCli cl;
	public String arguments;
	public presenter.Presenter.Command c;
	@Override
	public void setCommands(ConcurrentHashMap<String, presenter.Presenter.Command> commands) {
		cl.setCommands(commands);
		System.out.println("setting command");
	}


	@Override
	public void Display(String s) {
		System.out.println(s);
		
	}

	@Override
	public void start() {
		Thread t = new Thread(cl);
	    t.start();
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
	public presenter.Presenter.Command getUserCommand() {
		//System.out.println("gets user command");
		//Thread t = new Thread(cl);
		//presenter.Presenter.Command c=	t.start();
		return c;
	}

	@Override
	public void exit() {
		System.out.println("Exiting now");
		
	}

	
}
