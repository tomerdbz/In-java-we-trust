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
	
	 void setBoardProperties(Object tempInput);
	 boolean hasPathUP(int characterRow,int characterCol);
	 boolean hasPathRIGHT(int characterRow,int characterCol);
	 boolean hasPathDOWN(int characterRow,int characterCol);
	 boolean hasPathLEFT(int characterRow,int characterCol);
	 void destructBoard();
	
}
