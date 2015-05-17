package view;

import java.io.PrintWriter;

import algorithms.mazeGenerators.Maze;

public interface MazeDisplayer {
	
	public void DisplayMaze(PrintWriter out,Maze m);

}