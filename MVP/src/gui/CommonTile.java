package gui;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class CommonTile extends Canvas implements Tile{
	
	boolean hasCharacter; //looked at your character in CellDisplay. wasteful - it only serves you to know if it's in the tile - more correct to be a boolean.
	
	public CommonTile(Composite parent, int style) {
		super(parent, style);
	}

}
