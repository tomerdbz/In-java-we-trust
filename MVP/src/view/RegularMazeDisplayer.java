package view;

import algorithms.mazeGenerators.Maze;

public class RegularMazeDisplayer implements MazeDisplayer{

	@Override
	public void DisplayMaze(Maze m) {
		m.print();
		
	}



}
