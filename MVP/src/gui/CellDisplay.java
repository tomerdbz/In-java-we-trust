package gui;

import jaco.mp3.player.MP3Player;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
/**
 * 
 * @author Alon,Tomer
 *	this class extends canvas and is a representation of a cell inside of the maze
 *
 */
public class CellDisplay extends CommonTile{
	
	boolean wasDragged=false; 
	/**
	 * 
	 * @author Alon
	 *enum that represnsets the direction of the drag
	 */ 
	/**
	 * an instance of the enum above
	 */
	public CellDisplay(Composite parent, int style) {//wanna get it from the outside
		super(parent, style | SWT.DOUBLE_BUFFERED);
		// mouse listener
		
		
		
	
		
	}
	public void setImage(Image image) 		//getters and setters of images
	{
		if(this.cellImage!=null)
			this.cellImage.dispose();
		this.cellImage=image;
		//change image
		redraw();
	}
	@Override
	public void drawTile(PaintEvent e) {
		int width=getSize().x; //get width of window
		int height=getSize().y; //get height of window
        //Rectangle rect = getParent().getBounds();
		if(ch!=null && Hint!=null){ //character is on the tile and a hint is given
			Hint=null;
			MP3Player player = new MP3Player();
		    player.addToPlayList(new File(".\\resources\\sounds\\ring.mp3")); //if a hint has been reached play a sound
		    player.play();

		}
		
		if(cellImage!=null){ //display image of the tile
        ImageData data = cellImage.getImageData();
        e.gc.drawImage(cellImage,0,0,data.width,data.height,0,0,width,height);
		}
       if(Hint!=null){ //display hint if it has been given
    	   ImageData data2=Hint.getImageData();
    	   e.gc.drawImage(Hint,0,0,data2.width,data2.height,0,0,width,height);
       } 
       if(ch!=null){ //if a character is on the tile display it 
    	   Image img= new Image(getDisplay(),ch.images[ch.frameIndex]);
			ImageData data3= img.getImageData();
			e.gc.drawImage(img,0,0,data3.width,data3.height,0,0,getSize().x,getSize().y);
       }
       if(goal!=null){ // draw the goal if it is on the tile
    	   
			ImageData data4= goal.getImageData();
			
			e.gc.drawImage(goal,0,0,data4.width,data4.height,0,0,width,height);
       }
   
	
}
		
	}
	
	
	


