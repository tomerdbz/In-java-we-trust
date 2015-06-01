package gui;

import java.io.File;

import jaco.mp3.player.MP3Player;

import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class CellDisplay extends Canvas{
	Image cellImage;
	String imageName;
	Image Hint=null;
	Character ch = null;
	Image goal =null;
	boolean wasDragged=false;
	public enum Direction {UpRight,UpLeft,DownRight,DownLeft};
	Direction dir;
	public CellDisplay(Composite parent, int style) {//wanna get it from the outside
		super(parent, style);
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
			      /*  try {
			          //image = new Image(getDisplay(), new FileInputStream("C:\\Users\\Tomer\\git\\In-java-we-trust\\MVP\\resources\\images\\1demo.jpg"));
			        } catch (FileNotFoundException e1) {
			          // TODO Auto-generated catch block
			          e1.printStackTrace();
			        }*/
					int width=getSize().x;
					int height=getSize().y;
			        //Rectangle rect = getParent().getBounds();
					if(ch!=null && Hint!=null){
						Hint=null;
						MP3Player player = new MP3Player();
					    player.addToPlayList(new File(".\\resources\\sounds\\ring.mp3"));
					    player.play();

					}
					
					if(cellImage!=null){
			        ImageData data = cellImage.getImageData();
			        e.gc.drawImage(cellImage,0,0,data.width,data.height,0,0,width,height);
					}
			       if(Hint!=null){
			    	   ImageData data2=Hint.getImageData();
			    	   e.gc.drawImage(Hint,0,0,data2.width,data2.height,0,0,width,height);
			       }
			       if(ch!=null){
			    	   Image img= new Image(getDisplay(),ch.images[ch.frameIndex]);
						ImageData data3= img.getImageData();
						//img.setBackground(null);
						//e.gc.drawImage(img,0,0,data3.width,data3.height,10,10,getSize().x/ch.sizeFactor,getSize().y/ch.sizeFactor);
						e.gc.drawImage(img,0,0,data3.width,data3.height,0,0,getSize().x,getSize().y);
			       }
			       if(goal!=null){
			    	   
						ImageData data4= goal.getImageData();
						//img.setBackground(null);
						e.gc.drawImage(goal,0,0,data4.width,data4.height,0,0,width,height);
			       }
			        //cellImage.dispose();
				
			}
		});
		this.addMouseListener(new MouseListener(){

			int[] before=new int[2];
			int[] after=new int[2];
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {	}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				wasDragged=false;
				if(ch!=null){
					String str =getDisplay().getCursorLocation().toString();
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
					after[1]=Integer.parseInt(loc[1].substring(1, loc[1].length()-1));
					if(after[0]> before[0] && after[1]>before[1]){
						dir=Direction.DownRight;
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
				wasDragged=true;
				}
			}
			
		});
		
		
	
		
	}
	public void setImage(Image image)
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
