package gui;

import jaco.mp3.player.MP3Player;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

public class MazeDisplay extends Composite {
	int mazeRows;
	int mazeCols;
	boolean won=false;
	CellDisplay[][] mazeData;
	Character Ch=null;
	ImageLoader gifs=new ImageLoader(); 
	ImageData[] images = gifs.load("ChaosEmerald.gif");
	int frameIndex=0;
	
	public MazeDisplay(Composite parent, int style) {
		super(parent, style);
		addPaintListener(new PaintListener() { 
			
			@Override
			public void paintControl(PaintEvent arg0) {
				if(mazeData==null && won==false){
					int width=parent.getSize().x;
					int height=parent.getSize().y;
					ImageData data = new ImageData(".\\resources\\images\\Wallpaper.png");
					arg0.gc.drawImage(new Image(getDisplay(),".\\resources\\images\\Wallpaper.png"),0,0,data.width,data.height,0,0,width,height);
				}
				else if(won==true)
				{
					setVisible(false);
					won=false;
					//won=false;
					int width=parent.getSize().x;
					int height=parent.getSize().y;
					/*ImageData data = new ImageData(".\\resources\\images\\sonicwon.png");
					arg0.gc.drawImage(new Image(getDisplay(),".\\resources\\images\\sonicwon.png"),0,0,data.width,data.height,0,0,width,height);*/
				}
				else if(mazeData!=null)
					for(int i=0;i<mazeData.length;i++)
						for(int j=0;j<mazeData[0].length;j++)
							mazeData[i][j].redraw();
			}
		});
	
		this.addKeyListener(new KeyListener(){	
			@Override
			public void keyPressed(KeyEvent e) {
				 if (e.keyCode == 16777217 && Ch.currentCellX!=0 ){
					 boolean cond1 = mazeData[Ch.currentCellX][Ch.currentCellY].getImageName().charAt(1)=='0';
					 boolean cond2 =mazeData[Ch.currentCellX-1][Ch.currentCellY].getImageName().charAt(3)=='0';
					 	if(cond1 && cond2){
						int row=Ch.currentCellX;
				    	int col = Ch.currentCellY;
				    	mazeData[row][col].ch=null;
				    	Ch.setVisible(false);
				    	Ch = new Character(mazeData[row-1][col],SWT.FILL);
				    	Ch.currentCellX=row-1;
				    	Ch.currentCellY=col;
				    	//Ch.images = Ch.gifs.load("UpAndDown.gif");
						Ch.frameIndex=0;
						//Ch.pack();
						mazeData[row-1][col].ch=Ch;
						mazeData[Ch.currentCellX+1][Ch.currentCellY].redraw();
						mazeData[Ch.currentCellX][Ch.currentCellY].redraw();
					 System.out.println("up1");
					 	}
			    } 
				 else 
					 if (e.keyCode == 16777220 && Ch.currentCellY!=mazeData[0].length-1) {
						 boolean cond1 = mazeData[Ch.currentCellX][Ch.currentCellY].getImageName().charAt(2)=='0';
						 boolean cond2 =mazeData[Ch.currentCellX][Ch.currentCellY+1].getImageName().charAt(0)=='0';
					if(cond1&&cond2){
			    	int row=Ch.currentCellX;
			    	int col = Ch.currentCellY;
			    	mazeData[row][col].ch=null;
			    	Ch.setVisible(false);
			    	Ch = new Character(mazeData[row][col+1],SWT.FILL);
			    	Ch.currentCellX=row;
			    	Ch.currentCellY=col+1;
			    	//Ch.images = Ch.gifs.load("Right.gif");
					Ch.frameIndex=0;
					mazeData[row][col+1].ch=Ch;
					//Ch.pack();
					mazeData[Ch.currentCellX][Ch.currentCellY-1].redraw();
					mazeData[Ch.currentCellX][Ch.currentCellY].redraw();
			    	System.out.println("right");
						 }
			    } 
					else 
					if (e.keyCode == 16777219 && Ch.currentCellY!=0) {
						 boolean cond1 = mazeData[Ch.currentCellX][Ch.currentCellY].getImageName().charAt(0)=='0';
						 boolean cond2 =mazeData[Ch.currentCellX][Ch.currentCellY-1].getImageName().charAt(2)=='0';
					if(cond1&&cond2){	 
			    	int row=Ch.currentCellX;
			    	int col = Ch.currentCellY;
			    	mazeData[row][col].ch=null;
			    	Ch.setVisible(false);
			    	Ch = new Character(mazeData[row][col-1],SWT.FILL);
			    	Ch.currentCellX=row;
			    	Ch.currentCellY=col-1;
			    	//Ch.images = Ch.gifs.load("Left.gif");
					Ch.frameIndex=0;
					//Ch.setLayoutData(new GridData(SWT.FILL,SWT.TOP,true,true,2,2));
					mazeData[row][col-1].ch=Ch;
					//Ch.pack(true);
					mazeData[Ch.currentCellX][Ch.currentCellY+1].redraw();
					mazeData[Ch.currentCellX][Ch.currentCellY].redraw();
			    	System.out.println("left");
					}
			    } 
					else
					if (e.keyCode == 16777218 &&Ch.currentCellX!= mazeData.length-1) {
						 boolean cond1 = mazeData[Ch.currentCellX][Ch.currentCellY].getImageName().charAt(3)=='0';
						 boolean cond2 =mazeData[Ch.currentCellX+1][Ch.currentCellY].getImageName().charAt(1)=='0';	
						 if(cond1&&cond2){
			    	int row=Ch.currentCellX;
			    	int col = Ch.currentCellY;
			    	mazeData[row][col].ch=null;
			    	Ch.setVisible(false);
			    	Ch = new Character(mazeData[row+1][col],SWT.FILL);
			    	Ch.currentCellX=row+1;
			    	Ch.currentCellY=col;
			    	//Ch.images = Ch.gifs.load("UpAndDown.gif");
					Ch.frameIndex=0;
					mazeData[row+1][col].ch=Ch;
					//Ch.pack();
					mazeData[Ch.currentCellX-1][Ch.currentCellY].redraw();
					mazeData[Ch.currentCellX][Ch.currentCellY].redraw();
			    	System.out.println("down");
						 }
			    }
				 if(Ch.currentCellX== mazeData.length-1 && Ch.currentCellY == mazeData[0].length-1 && mazeData!=null){
					 won=true;
					 redraw();
					 getShell().setBackgroundImage(new Image(getDisplay(),".\\resources\\images\\sonicwon.png"));
					// MessageBox messageBox = new MessageBox(getShell(),SWT.ICON_INFORMATION|SWT.OK);
				       // messageBox.setText("Winner");
				       // messageBox.setMessage("You're the winner! This song is for you <3");
						//messageBox.open();
						MP3Player player = new MP3Player();
					    player.addToPlayList(new File(".\\resources\\sounds\\win.mp3"));
					    player.play();
						
						
				 }
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				
				
			}
		});
		this.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseScrolled(MouseEvent arg0) {
				if((arg0.stateMask& SWT.CTRL)!=0){
					if(arg0.count>0){
						System.out.println("up");
						//up zoom in										//Bonus ya habibi
						setSize(getSize().x+30, getSize().y+30);
					}
					if(arg0.count<0){
						System.out.println("down");
						setSize(getSize().x-30, getSize().y-30);
						//down zoom out
						
					}
				}
				
				
			}
	});
	}
	public void displayMaze(Maze m)
	{
		MazeDisplay a =this;
		getDisplay().syncExec(new Runnable() {
			   public void run() {
				   if(mazeData!=null)
				   a.destructMaze();
				   mazeRows=m.getRows();
				   mazeCols=m.getCols();
				   GridLayout layout=new GridLayout(mazeCols, true);
				   layout.horizontalSpacing=0;
				   layout.verticalSpacing=0;
				   setLayout(layout);
				   mazeData=new CellDisplay[mazeRows][mazeCols];
				   for(int i=0;i<mazeRows;i++)
					   for(int j=0;j<mazeCols;j++)
					   {
						   mazeData[i][j]=new CellDisplay(a,SWT.NONE);
						   mazeData[i][j].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
						   mazeData[i][j].setImage(cellImage(m,i,j));
					   }
				   getShell().layout();
			   }
			}); 
		
	}
	//was private
	public void destructMaze()
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
		String temp=".\\resources\\images\\";
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
		
		mazeData[i][j].setImageName(str);
		return new Image(getDisplay(),new ImageData(temp+str));
		
	}
	boolean HasPathRight(int Row,int Col){
		 boolean cond1 = mazeData[Row][Col].getImageName().charAt(2)=='0';
		 boolean cond2 =mazeData[Row][Col+1].getImageName().charAt(0)=='0';
	if(cond1&&cond2)
		return true;
	return false;
		
	}
	boolean HasPathLeft(int Row,int Col){
		 boolean cond1 = mazeData[Row][Col].getImageName().charAt(0)=='0';
		 boolean cond2 =mazeData[Row][Col-1].getImageName().charAt(2)=='0';
	if(cond1&&cond2)
		return true;
	
		return false;
	}
	boolean HasPathUp(int Row,int Col){
		
		 boolean cond1 = mazeData[Row][Col].getImageName().charAt(1)=='0';
		 boolean cond2 =mazeData[Row-1][Col].getImageName().charAt(3)=='0';
		 	if(cond1 && cond2)
		 		return true;
		return false;
	}
	boolean HasPathDown(int Row,int Col){
		
		boolean cond1 = mazeData[Row][Col].getImageName().charAt(3)=='0';
		 boolean cond2 =mazeData[Row+1][Col].getImageName().charAt(1)=='0';	
		 if(cond1&&cond2)
			 return true;
		return false;
	}
	//void SetCharacter(Character Ch){
	//	this.Ch=Ch;
	//}
	

}
