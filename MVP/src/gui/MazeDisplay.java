package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

public class MazeDisplay extends Composite {
	int mazeRows;
	int mazeCols;
	CellDisplay[][] mazeData;
	
	public MazeDisplay(Composite parent, int style) {
		super(parent, style);
		setBackgroundImage(new Image(getDisplay(),new ImageData("C:\\Users\\Tomer\\git\\In-java-we-trust\\MVP\\resources\\images\\1demo.jpg")));
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent arg0) {
				
			}
		});
	}
	public void displayMaze(Maze m)
	{
		mazeRows=m.getRows();
		mazeCols=m.getCols();
		setLayoutData(new GridLayout(mazeCols,true));
		mazeData=new CellDisplay[mazeRows][mazeCols];
		for(int i=0;i<mazeRows;i++)
			for(int j=0;j<mazeCols;j++)
			{
				mazeData[i][j]=new CellDisplay(this,SWT.NONE,cellImage(m.getCell(i, j)));
			}
	}
	private Image cellImage(Cell c)
	{
		String str="";
		if(c.getHasTopWall())
			str+="top";
		if(c.getHasRightWall())
			str+="right";
		if(c.getHasBottomWall())
			str+="bottom";
		if(c.getHasLeftWall())
			str+="left";
		return new Image(getDisplay(),new ImageData(str));
		
	}
	

}