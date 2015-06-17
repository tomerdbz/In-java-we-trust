package gui;

import org.eclipse.swt.events.PaintEvent;
/**
 * 
 * @author Alon,Tomer
 *an interface of board
 */
public interface Board {
	public void drawBoard(PaintEvent arg0);
	void applyInputDirection(Direction direction);
	void SetBoardData(String arg);
}
