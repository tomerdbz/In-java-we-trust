package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class CellDisplay extends Canvas{
	int cellStyle;//SWT.RIGHT, SWT.LEFT, SWT.TOP, SWT.BOTTOM.
	Image cellImage;
	public CellDisplay(Composite parent, int style) {//wanna get it from the outside
		super(parent, style);
		//this.cellImage=cellImage;
		//add
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setBackground(new Color(null,0,0,0));
				int width=getSize().x;
				int height=getSize().y;
				e.gc.drawImage(cellImage, width, height);
				
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
