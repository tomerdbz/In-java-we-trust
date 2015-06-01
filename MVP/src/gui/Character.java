package gui;




import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class Character extends Canvas {
	   ImageLoader gifs=new ImageLoader();
	   ImageData[] images = gifs.load("UpAndDown.gif");
	   int frameIndex=0;
	   int sizeFactor=2;
	   int currentCellX=0;
	   int currentCellY=0;
	   boolean Redraw=true;
	   Thread thread =null;


	public Character(Composite parent, int style) {
		super(parent, style);
		this.addPaintListener(new PaintListener(){
			@Override
			public void paintControl(PaintEvent arg0) {
				System.out.println("trolol");
				Image img= new Image(getDisplay(),images[frameIndex]);
				ImageData data= img.getImageData();
				//img.setBackground(null);
				arg0.gc.drawImage(img,0,0,data.width,data.height,5,5,parent.getSize().x/sizeFactor,parent.getSize().y/sizeFactor);
				
			}
			
		});
		
		
		
		/*this.addDragDetectListener(new DragDetectListener(){

			@Override
			public void dragDetected(DragDetectEvent arg0) {
			
					System.out.println("what");
			}	
			});*/
		
		
		/*this.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseScrolled(MouseEvent arg0) {
				if((arg0.stateMask& SWT.CTRL)!=0){
					if(arg0.count>0){
						System.out.println("up");
						//up zoom in										//Bonus ya habibi
						//setSize(getSize().x+30, getSize().y+30);
						if(sizeFactor-1!=0)
						sizeFactor--;
					}
					if(arg0.count<0){
						System.out.println("down");
						//setSize(getSize().x-30, getSize().y-30);
						//down zoom out
						sizeFactor++;
					}
				}
				
				
			}
			
		});*/
		/*this.addKeyListener(new KeyListener(){	
			@Override
			public void keyPressed(KeyEvent e) {
				 if (e.keyCode == 16777217) {
						images = gifs.load("UpAndDown.gif");
						frameIndex=0;
						removeall();
					 System.out.println("up");
			    } else if (e.keyCode == 16777220 ) {
			    	images = gifs.load("Right.gif");
			    	frameIndex=0;
			    	removeall();
			    	System.out.println("right");
			    } else if (e.keyCode == 16777219 ) {
			    	images = gifs.load("Left.gif");
			    	frameIndex=0;
			    	removeall();
			    	System.out.println("left");
			    } else if (e.keyCode == 16777218 ) {
					images = gifs.load("UpAndDown.gif");
					frameIndex=0;
					removeall();
			    	System.out.println("down");
			    }
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				System.out.println("left it");
				
			}
			
		});*/
	/*thread = new Thread() {
	 public void run() {
		 	boolean flag = true;
		     while (!isInterrupted()) {
		    if(frameIndex!=images.length){
			 frameIndex %= images.length;
			 flag =true;
		    }
		    else{
		    	frameIndex =0;
		    	flag=false;
		    }
		     getDisplay().asyncExec(new Runnable() {
				 public void run() {
				 removeall();
				 }
			 });
				try {
				 // delay
				Thread.sleep(images[frameIndex].delayTime * 10);
				 } catch (InterruptedException e) {
					 return;
				 }
		if(flag)
		 frameIndex += 1;
			} 
	}
	};
    getShell().addShellListener(new ShellAdapter() {
	public void shellClosed(ShellEvent e) {
		Redraw=false;
		thread.interrupt();
	}
	});
    	thread.start();
	*/
	}

	/*protected void removeall() {
		if(Redraw){
		this.redraw();
		this.update();
		}
		
	}*/
	
}
