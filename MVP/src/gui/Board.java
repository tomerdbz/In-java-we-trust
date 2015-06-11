package gui;

import org.eclipse.swt.events.PaintEvent;

public interface Board {
	public void drawBoard(PaintEvent arg0);
	void applyInputDirection(Direction direction/*,Character?*/);
}
