package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;


public class MyView extends Observable implements View {
	MVPRunnableCli cl;
	public String arguments;
	public presenter.Presenter.Command c;
	public MyView(BufferedReader in, PrintWriter out)
	{
		this.cl=new MVPRunnableCli(in,out);
	}
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
		cl.SetView(this);
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
		return c;
	}

	@Override
	public void exit() {
		System.out.println("Exiting now");
		
	}

	
}
