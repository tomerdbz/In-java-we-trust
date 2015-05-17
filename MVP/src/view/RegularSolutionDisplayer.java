package view;

import java.io.PrintWriter;

import algorithms.search.Solution;

public class RegularSolutionDisplayer implements SolutionDisplayer {

	@Override
	public void SolutionDisplay(PrintWriter out, Solution s) {
		out.println(s);
		out.flush();
		
	}

	

}