package gui;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class CellDisplay extends Canvas{
	Image cellImage;
	public CellDisplay(Composite parent, int style) {//wanna get it from the outside
		super(parent, style);
		//this.cellImage=cellImage;
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

}
