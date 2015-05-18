package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;


public class MyView extends Observable implements View,Observer {
	public MVPRunnableCli cl;
	public String arguments;
	public presenter.Presenter.Command c;
	public MyView(BufferedReader in, PrintWriter out)
	{
		this.cl=new MVPRunnableCli(in,out);
	}
	@Override
	public void setCommands(ConcurrentHashMap<String, presenter.Presenter.Command> commands) {
		cl.setCommands(commands);
		
	}


	@Override
	public void Display(String s) {
		this.cl.out.println(s);
		
	}
	
	public void ChangeNotify(String arg){
		setChanged();
		notifyObservers(arg);
		
	}
	@Override
	public void start() {
		Thread t = new Thread(cl);
	    t.start();
		}

	@Override
	public void displaySolution(Solution s) {
		
		RegularSolutionDisplayer sd = new RegularSolutionDisplayer();
		sd.SolutionDisplay(this.cl.out, s);
		
	}

	@Override
	public void displayMaze(Maze m) {
		
		RegularMazeDisplayer dp = new RegularMazeDisplayer();
		dp.DisplayMaze(this.cl.out,m);
		
	}

	@Override
	public presenter.Presenter.Command getUserCommand() {
		return c;
	}

	@Override
	public void exit() {
		cl.out.println("Exiting now");
		setChanged();
		notifyObservers();
	
	}
	@Override
	public void update(Observable o, Object arg) {
		if(o==cl){
			this.c = (presenter.Presenter.Command) arg;
			setChanged();
			notifyObservers(cl.args);
		}
		
	}

	
}