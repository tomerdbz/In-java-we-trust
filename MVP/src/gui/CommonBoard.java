package gui;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Composite;

public class CommonBoard extends Composite implements Board {
	/**
	 * a 2 dimension array which represents the tiles in the board
	 */
	CommonTile[][] board;
	/**
	 * number of rows in the board
	 */
	int boardRows; 
	/**
	 * number of cols in the board
	 */
	int boardCols;
	/**
	 * representation of the character in the maze
	 */
	CommonCharacter Ch=null;
	/**
	 * Helps load gif to single images
	 */
	ImageLoader gifs=new ImageLoader();
	/**
	 * an array of images which represent the gif of the goal
	 */
	ImageData[] images;
	/**
	 * frame of the gif
	 */
	int frameIndex=0;
	
	public CommonBoard(Composite parent, int style) {
		super(parent, style);
	}


	@Override
	public void drawBoard() {
		this.redraw();
		
	}


	@Override
	public void applyInputDirection(Direction direction) {
		
		
	}

}
