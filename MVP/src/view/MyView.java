package view;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class MyView implements View {

	@Override
	public void start() {
		System.out.println("starting view");
	}

	@Override
	public void setCommands(UserCommands uc) {
		System.out.println("setting commands");
	}

	@Override
	public Command getUserCommand() {
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

}
