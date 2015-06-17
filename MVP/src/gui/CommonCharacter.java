package gui;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
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

	public abstract ImageData[] getImages();
	public abstract void setImages(ImageData[] images);
	public abstract int getFrameIndex();
	public abstract void setFrameIndex(int frameIndex);

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
