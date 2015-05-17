package view;

import java.io.PrintWriter;

import algorithms.mazeGenerators.Maze;

public class RegularMazeDisplayer implements MazeDisplayer{



	@Override
	public void DisplayMaze(PrintWriter out, Maze m) {
		out.println(m.toString());
		out.flush();
	}



}
