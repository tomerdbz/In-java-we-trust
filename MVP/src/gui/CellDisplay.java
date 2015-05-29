package gui;

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
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
			        ImageData data = cellImage.getImageData();
			        e.gc.drawImage(cellImage,0,0,data.width,data.height,0,0,width,height);
			       if(Hint!=null){
			    	   ImageData data2=Hint.getImageData();
			    	   e.gc.drawImage(Hint,0,0,data2.width,data2.height,0,0,width,height);
			       }
			       if(ch!=null){
			    	   Image img= new Image(getDisplay(),ch.images[ch.frameIndex]);
						ImageData data3= img.getImageData();
						//img.setBackground(null);
						e.gc.drawImage(img,0,0,data3.width,data3.height,15,15,getSize().x/ch.sizeFactor,getSize().y/ch.sizeFactor);
			       }
			       if(goal!=null){
			    	   
						ImageData data4= goal.getImageData();
						//img.setBackground(null);
						e.gc.drawImage(goal,0,0,data4.width,data4.height,0,0,width,height);
			       }
			        //cellImage.dispose();
				
			}
		});
		//Image i = new Image(getDisplay(),"\\mazeleft.jpg");// "\\" insted of "\" here.
		
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
