package view.gui;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public abstract class CommonCharacter extends Canvas implements Character{

	  
		   /**
		    * row in the maze
		    */
		   int currentCellX=0;
		   /**
		    * col in the maze
		    */
		   int currentCellY=0;
		   boolean Redraw=true;

	   /**
	    * constructor
	    * 
	    * 
	    */
	public CommonCharacter(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void drawCharacter() {
		this.redraw();
		
	}

	public abstract ImageData[] getCharacterImagesArray();
	public abstract void setCharacterImagesArray(ImageData[] images);
	public abstract int getCharacterImageIndex();
	public abstract void setCharacterImageIndex(int frameIndex);

	public int getCurrentCellX() {
		return currentCellX;
	}

	public void setCurrentCellX(int currentCellX) {
		this.currentCellX = currentCellX;
	}

	public int getCurrentCellY() {
		return currentCellY;
	}

	public void setCurrentCellY(int currentCellY) {
		this.currentCellY = currentCellY;
	}

	public boolean isRedraw() {
		return Redraw;
	}

	public void setRedraw(boolean redraw) {
		Redraw = redraw;
	}

}
