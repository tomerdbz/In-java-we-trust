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
public class CellDisplay extends Canvas{
	/**
	 * image of the cell (the way the cell looks walls up down right or left 
	 */
	Image cellImage; 
	String imageName;
	/**
	 *  hint represnets a special image that shows the user
	 */
	Image Hint=null; 
	/**
	 * a represenation of character if not null the character is in the cell
	 */
	Character ch = null; 
	/**
	 * does the tile represent the goal or not
	 */
	Image goal =null; 
	/**
	 * true if drag has occureed
	 */
	boolean wasDragged=false; 
	/**
	 * 
	 * @author Alon
	 *enum that represnsets the direction of the drag
	 */
	public enum Direction {UpRight,UpLeft,DownRight,DownLeft}; 
	/**
	 * an instance of the enum above
	 */
	Direction dir; 
	public CellDisplay(Composite parent, int style) {//wanna get it from the outside
		super(parent, style | SWT.DOUBLE_BUFFERED);
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
			   
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
		});// mouse listener
		this.addMouseListener(new MouseListener(){

			int[] before=new int[2]; //place of cursor before click
			int[] after=new int[2]; // place of cursor after releasing click
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {	}
			
			@Override
			public void mouseDown(MouseEvent arg0) { 
				wasDragged=false;
				if(ch!=null){
					String str =getDisplay().getCursorLocation().toString(); //calculates mouse location by pixels
					String []loc = str.substring(7).split(",");
					before[0]=Integer.parseInt(loc[0].substring(0));
					before[1]=Integer.parseInt(loc[1].substring(1, loc[1].length()-1));
				}
			}

			@Override
			public void mouseUp(MouseEvent arg0) {

				if(wasDragged){
					String str =getDisplay().getCursorLocation().toString();
					String []loc = str.substring(7).split(",");
					after[0]=Integer.parseInt(loc[0].substring(0));
					after[1]=Integer.parseInt(loc[1].substring(1, loc[1].length()-1)); //calculates mouse location by pixels
					if(after[0]> before[0] && after[1]>before[1]){
						dir=Direction.DownRight;					//check in which direction did the mouse move
					}
					if(after[0]> before[0] && after[1]<before[1]){
						dir=Direction.UpRight;
					}
					if(after[0]< before[0] && after[1]>before[1]){
						dir=Direction.DownLeft;
						
					}
					if(after[0]< before[0] && after[1]<before[1]){
						dir=Direction.UpLeft;
						
					}
					ch=null;
					
				}
						
			}
		});
		this.addDragDetectListener(new DragDetectListener(){

			@Override
			public void dragDetected(DragDetectEvent arg0) {
				if(ch!=null){
				wasDragged=true; //detect if a drag has occureed
				}
			}
			
		});
		
		
	
		
	}
	public void setImage(Image image) 		//getters and setters of images
	{
		if(this.cellImage!=null)
			this.cellImage.dispose();
		this.cellImage=image;
		//change image
		redraw();
	}
	public Image getCellImage(){
		return cellImage;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
