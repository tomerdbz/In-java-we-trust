package view.gui;

import org.eclipse.swt.events.PaintEvent;
/**
 * 
 * @author Alon,Tomer
 *an interface of board
 */
public interface Board {
	/**	drawBoard for the paintListener - how would one board likes to be drawn and under which conditions.
	 * 	This drawBoard will be given to the new Paint Listener
	 * @param arg0 - the paintEvent
	 */
	void drawBoard(PaintEvent arg0);
	void applyInputDirection(Direction direction);
	/**	This method gives the Specific board an option to set its specific data
	 * @param arg - an Object which is the data to set in the specific board
	 */
	void setBoardData(Object arg);
	/**
	 * This function allows us to set the Properties of the board
	 * if it was a maze it was mazeProperties and so on	
	 * @param tempInput represents some kind of a properties
	 */
	 void setBoardProperties(Object tempInput);
	 /**
	  * Checks if there is a path to go Up in a board
	  * @param characterRow Row of character
	  * @param characterCol Column of character
	  * @return true if there is
	  */
	 boolean hasPathUP(int characterRow,int characterCol);
	 /**
 * Checks if there is a path to go Right in a board
	  * @param characterRow Row of character
	  * @param characterCol Column of character
	  * @return true if there is
	  */
	 boolean hasPathRIGHT(int characterRow,int characterCol);
	 /**
	 * Checks if there is a path to go Down in a board
	  * @param characterRow Row of character
	  * @param characterCol Column of character
	  * @return true if there is
	  * */
	 boolean hasPathDOWN(int characterRow,int characterCol);
	 /**
	 * Checks if there is a path to go Left in a board
	  * @param characterRow Row of character
	  * @param characterCol Column of character
	  * @return true if there is
	  * 
	  */
	 boolean hasPathLEFT(int characterRow,int characterCol);
	 /***
	  * Disposes of all things created in board
	  */
	 void destructBoard();
	
}
