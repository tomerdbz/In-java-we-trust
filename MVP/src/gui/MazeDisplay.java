package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
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
		mazeData=null;
		addPaintListener(new PaintListener() { //Presenter should give paintListeners. for debug only!
			
			@Override
			public void paintControl(PaintEvent e) {
				if(mazeData==null)
				{
					int width=getSize().x;
					int height=getSize().y;
			        //Rectangle rect = getParent().getBounds();
			        ImageData data = new ImageData("C:\\Users\\Tomer\\git\\In-java-we-trust\\MVP\\resources\\images\\sonic.png");
			        e.gc.drawImage(new Image(getDisplay(),data),0,0,data.width,data.height,0,0,width,height);

					
				}
				if(mazeData!=null)
					for(int i=0;i<mazeData.length;i++)
						for(int j=0;j<mazeData[0].length;j++)
							mazeData[i][j].redraw();
			}
		});
	}
	public void displayMaze(Maze m)
	{
		if(mazeData!=null) //wanna get rid of the old maze prior to displaying the new one.
			destructMaze(); //presenter should choose what to do in the form of a listener. for debug only!
		mazeRows=m.getRows();
		mazeCols=m.getCols();
		GridLayout layout=new GridLayout(mazeCols, true);
		layout.horizontalSpacing=0;
		layout.verticalSpacing=0;
		//setLayoutData(layout);
		setLayout(layout);
		mazeData=new CellDisplay[mazeRows][mazeCols];
		for(int i=0;i<mazeRows;i++)
			for(int j=0;j<mazeCols;j++)
			{
				mazeData[i][j]=new CellDisplay(this,SWT.NONE);
				mazeData[i][j].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				mazeData[i][j].setImage(cellImage(m,i,j));
			}
		layout();
		//redraw();
	}
	private void destructMaze()
	{
		for(int i=0;i<mazeData.length;i++)
			for(int j=0;j<mazeData[0].length;j++)
			{
				mazeData[i][j].cellImage.dispose();
				mazeData[i][j].dispose();
			}
	}
	private Image cellImage(Maze m,int i,int j)
	{
		Cell c=m.getCell(i, j);
		String temp="C:\\Users\\Tomer\\git\\In-java-we-trust\\MVP\\resources\\images\\";
		String str="";
		if(c.getHasLeftWall())
		{
			if(j==0 || !m.getCell(i, j-1).getHasRightWall())
				str+="1";
			else
				str+="0";
		}
		else
			str+="0";
		if(c.getHasTopWall())
		{
			if(i==0 || !m.getCell(i-1, j).getHasBottomWall())
				str+="1";
			else
				str+="0";
		}	
		else
			str+="0";
		//if(j==m.getRows()-1 || !m.getCell(i, j+1).getHasLeftWall())
		if(c.getHasRightWall())
			str+="1";
		else
			str+="0";
		if(c.getHasBottomWall())
			str+="1";
		else
			str+="0";
		str+=".jpg";
		return new Image(getDisplay(),new ImageData(temp+str));
		
	}
	

}