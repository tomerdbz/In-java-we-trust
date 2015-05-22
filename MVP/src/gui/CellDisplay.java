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
	public CellDisplay(Composite parent, int style, Image cellImage) {//wanna get it from the outside
		super(parent, style);
		this.cellImage=cellImage;
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setBackground(new Color(null,0,0,0));

				int width=getSize().x;
				int height=getSize().y;
				//e.gc.drawImage("same image...save it maybe as a field", width, height);
				
			}
		});
		//Image i = new Image(getDisplay(),"\\mazeleft.jpg");// "\\" insted of "\" here.
		
	}
	public void setCellStyle(int cellStyle)
	{
		this.cellStyle=cellStyle;
		//change image
		redraw();
	}

}
