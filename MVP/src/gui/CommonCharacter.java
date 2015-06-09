package gui;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class CommonCharacter extends Canvas implements Character{

	   /**
		 *  Image Loader is used in order to take a gif and turn it into single pictures
		 */
		ImageLoader gifs=new ImageLoader();
		/**
		 * loads gif of character movment
		 */
		   ImageData[] images; 
		   /**
		    * frame of the gif at this point.
		    */
		   int frameIndex=0; 
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

	public ImageLoader getGifs() {
		return gifs;
	}

	public void setGifs(ImageLoader gifs) {
		this.gifs = gifs;
	}

	public ImageData[] getImages() {
		return images;
	}

	public void setImages(ImageData[] images) {
		this.images = images;
	}

	public int getFrameIndex() {
		return frameIndex;
	}

	public void setFrameIndex(int frameIndex) {
		this.frameIndex = frameIndex;
	}

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
