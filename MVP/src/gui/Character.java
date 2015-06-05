package gui;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
/**
 * 
 * @author Alon
 * this is a widget representing the character in the maze
 * it extends canvas
 */
public class Character extends Canvas {
	   /**
	 *  Image Loader is used in order to take a gif and turn it into single pictures
	 */
	ImageLoader gifs=new ImageLoader();
	/**
	 * loads gif of character movment
	 */
	   ImageData[] images = gifs.load(".\\resources\\images\\UpAndDown.gif"); 
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
	public Character(Composite parent, int style) {
		super(parent, style);
	
		
	}
}
