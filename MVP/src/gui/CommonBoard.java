package gui;

import org.eclipse.swt.widgets.Composite;

public class CommonBoard extends Composite implements Board {
	/**
	 * a 2 dimension array which represents the tiles in the board
	 */
	Tile[][] board;
	/**
	 * number of rows in the board
	 */
	int boardRows; 
	/**
	 * number of cols in the board
	 */
	int boardCols;
	
	
	public CommonBoard(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

}
