package gui;

import org.eclipse.swt.events.PaintEvent;

/*these functions should be general to all kind of tiles - from 2048 to chess to whatever*/
public interface Tile {
	void drawTile(PaintEvent e);
}
