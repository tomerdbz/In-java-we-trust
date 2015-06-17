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
/**
 * 
 * @author Alon,Tomer
 *	this is a represenation of the maze whicharacter extends Composite
 *
 */
public class MazeDisplay extends CommonBoard {
	
	/**
	 * Gives true if we beat the maze
	 */
	boolean won=false;
	/**
	 * Helps load gif to single images
	 */
	ImageLoader gifs=new ImageLoader();
	/**
	 * an array of images whicharacter represent the gif of the goal
	 */
	ImageData[] images;
	/**
	 * frame of the gif
	 */
	int frameIndex=0;
	
	MP3Player player;

	
	public MazeDisplay(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		images=gifs.load(".\\resources\\images\\ChaosEmerald.gif");
		player = new MP3Player();

		
	
		
	
	}
	/**
	 * displays the maze in a gui way
	 * @param m the maze to be displayed
	 */
	public void displayMaze(Maze m)
	{
		player.stop();
		player=new MP3Player();
		player.addToPlayList(new File(".\\resources\\sounds\\mazemenu.mp3"));
	    player.play();
	    player.setRepeat(true);
	    
		MazeDisplay a =this; //for our convenience
		getDisplay().syncExec(new Runnable() {
			   public void run() {
				   if(board!=null)
				   a.destructMaze(); // destory previous maze if exists 
				   boardRows=m.getRows(); //gets maze rows and cols
				   boardCols=m.getCols();
				   GridLayout layout=new GridLayout(boardCols, true); //defines the grid layout
				   layout.horizontalSpacing=0;
				   layout.verticalSpacing=0;
				   setLayout(layout); //sets the layout
				   board=new CellDisplay[boardRows][boardCols]; //creates a new maze array
				   for(int i=0;i<boardRows;i++)
					   for(int j=0;j<boardCols;j++)
					   {
						   board[i][j]=new CellDisplay(a,SWT.NONE);
						   board[i][j].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
						   board[i][j].setCellImage(cellImage(m,i,j)); //creates a cell and sets the correct image
					   }
				   addMouseListenerToComposite();
				   getShell().layout();
			   }
			}); 
		
	}
	/**
	 * disposes of maze cells
	 */
	public void destructMaze()
	{
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board[0].length;j++)
			{
				board[i][j].getCellImage().dispose();
				board[i][j].dispose();
			}
	}
	/**
	 * 
	 * @param m the maze 
	 * @param i row index
	 * @param j col index
	 * @return the correct image
	 * this function calculates whicharacter image should be returned according to the walls surrounding the cell
	 */
	private Image cellImage(Maze m,int i,int j)
	{
		Cell c=m.getCell(i, j);
		String temp=".\\resources\\images\\";
		String str="";
		if(c.getHasLeftWall())
		{
			if(j==0 || !m.getCell(i, j-1).getHasRightWall()) //if it has left wall image must have 1 at the begining
				str+="1";
			else
				str+="0";
		}
		else
			str+="0";
		if(c.getHasTopWall())
		{
			if(i==0 || !m.getCell(i-1, j).getHasBottomWall()) //if it has Top wall image must have 1 at the second place
				str+="1";
			else
				str+="0";
		}	
		else
			str+="0";
		if(c.getHasRightWall()) //if it has right wall image must have 1 at the third place
			str+="1";
		else
			str+="0";
		if(c.getHasBottomWall()) // if it has bottom wall image must have 1 at the fourth place
			str+="1";
		else
			str+="0";
		str+=".jpg";
		
		board[i][j].setImageName(str); //sets the image name
		return new Image(getDisplay(),new ImageData(temp+str)); //returns the image
		
	}
	// a function to calculate if cell has a right wall
	boolean HasPathRight(int Row,int Col){
		 boolean cond1 = board[Row][Col].getImageName().charAt(2)=='0';
		 boolean cond2 =board[Row][Col+1].getImageName().charAt(0)=='0';
	if(cond1&&cond2)
		return true;
	return false;
		
	}// a function to calculate if cell has a left wall
	boolean HasPathLeft(int Row,int Col){
		 boolean cond1 = board[Row][Col].getImageName().charAt(0)=='0';
		 boolean cond2 =board[Row][Col-1].getImageName().charAt(2)=='0';
	if(cond1&&cond2)
		return true;
	
		return false;
	}
	// a function to calculate if cell has a up wall
	boolean HasPathUp(int Row,int Col){
		
		 boolean cond1 = board[Row][Col].getImageName().charAt(1)=='0';
		 boolean cond2 =board[Row-1][Col].getImageName().charAt(3)=='0';
		 	if(cond1 && cond2)
		 		return true;
		return false;
	}
	// a function to calculate if cell has a down wall
	boolean HasPathDown(int Row,int Col){
		
		boolean cond1 = board[Row][Col].getImageName().charAt(3)=='0';
		 boolean cond2 =board[Row+1][Col].getImageName().charAt(1)=='0';	
		 if(cond1&&cond2)
			 return true;
		return false;
	}
	@Override
	public void drawBoard(PaintEvent arg0) {
		if(board==null && won==false){ //displays the photo in the begining of the program as an intro

			player.addToPlayList(new File(".\\resources\\sounds\\startmenu.mp3"));
		    player.play();
		    player.setRepeat(true);
			
		    int width=getParent().getSize().x;
			int height=getParent().getSize().y;
			ImageData data = new ImageData(".\\resources\\images\\Wallpaper.png");
			arg0.gc.drawImage(new Image(getDisplay(),".\\resources\\images\\Wallpaper.png"),0,0,data.width,data.height,0,0,width,height);
		}
		else if(won==true)
		{
			player.stop();
			player=new MP3Player();
		    player.addToPlayList(new File(".\\resources\\sounds\\win.mp3"));
		    player.play();
			setVisible(false);
			//won=false;
			//won=false;

			/*ImageData data = new ImageData(".\\resources\\images\\sonicwon.png");
			arg0.gc.drawImage(new Image(getDisplay(),".\\resources\\images\\sonicwon.png"),0,0,data.width,data.height,0,0,width,height);*/
		}
		else if(board!=null)
			for(int i=0;i<board.length;i++)
				for(int j=0;j<board[0].length;j++)
					board[i][j].redraw();
		
	}
	@Override
	public void applyInputDirection(Direction direction) {
		boolean cond1 = false;
		boolean cond2=false;
		int x = character.currentCellX; //currenct row
		int y = character.currentCellY; //current col 
		int rowT=0;
		int colT =0;
		switch (direction){
		case UP:
			 cond1 = ( board[character.currentCellX][character.currentCellY]).getImageName().charAt(1)=='0';
			 cond2 =( board[character.currentCellX-1][character.currentCellY]).getImageName().charAt(3)=='0'; //charactereck if a path is possible
			 	if(cond1 && cond2){ //if so redraws the characteraracter in the new location
			 		rowT =1;
			 //up
			 	}
			 	break;
	    
		case RIGHT:
				 cond1 = ( board[character.currentCellX][character.currentCellY]).getImageName().charAt(2)=='0';
				 cond2 =( board[character.currentCellX][character.currentCellY+1]).getImageName().charAt(0)=='0';
			if(cond1&&cond2){
				colT=-1;
	    	//right
				 }
			break;
	    
		case LEFT:
			cond1 = board[character.currentCellX][character.currentCellY].getImageName().charAt(0)=='0';
			cond2 =board[character.currentCellX][character.currentCellY-1].getImageName().charAt(2)=='0';
			if(cond1&&cond2){	 
				colT=1;
	    	//left
			}
			break;
		case DOWN: 
				  cond1 = board[character.currentCellX][character.currentCellY].getImageName().charAt(3)=='0';
				 cond2 =board[character.currentCellX+1][character.currentCellY].getImageName().charAt(1)=='0';	
				 if(cond1&&cond2){
					 rowT=-1;
	    	//down
				 }
				 break;
		case UpRight:
			if(x-1 >=0 && y+1<=  board[0].length-1) //not out of bounds
				if(( HasPathRight(x, y)&&  HasPathUp(x, y+1))||( HasPathUp(x,y)&& HasPathRight(x-1, y))) //check if we have path to that location
				{ rowT =-1;
					colT = +1;
				}
				break;
		case UpLeft:
			if(x-1 >=0 && y-1>=0)
				if(( HasPathLeft(x, y)&&  HasPathUp(x, y-1))||( HasPathUp(x,y)&& HasPathLeft(x-1, y)))
				{ rowT =-1;
					colT=-1;
				
				}
				break;	
		case DownLeft:
			if(x+1 <= board.length-1 && y-1>=0)
				if(( HasPathLeft(x, y)&&  HasPathDown(x, y-1))||( HasPathDown(x,y)&& HasPathLeft(x+1, y)))
				{ rowT=+1;
					colT =-1;
				
				}
			break;
		case DownRight:
			if(x+1 <= board.length-1 && y+1<= board[0].length-1)
				if(( HasPathRight(x, y)&&  HasPathDown(x, y+1))||( HasPathDown(x,y)&& HasPathRight(x+1, y)))
				{ rowT =1;
					colT=1;
				}
			break;
			
		default:
			break;
			
		}	
	if((direction == Direction.UP || direction == Direction.DOWN || direction == Direction.RIGHT || direction == Direction.LEFT)){
		int row=character.currentCellX;
    	int col = character.currentCellY;
    	board[row][col].setCharacter(null);
    	character.setVisible(false);
    	character = new MazeCharacter( board[row-rowT][col-colT],SWT.FILL);
    	character.currentCellX=row-rowT;
    	character.currentCellY=col-colT;
		character.setFrameIndex(0);
		board[row-rowT][col-colT].setCharacter(character);
		board[character.currentCellX+rowT][character.currentCellY+colT].redraw();
		board[character.currentCellX][character.currentCellY].redraw();
			
	    }	
	else{
		character = new MazeCharacter(( board[x+1][y+1]),SWT.FILL);
   	 character.currentCellX=x+rowT;
   	 character.currentCellY=y+colT;
   	 character.setFrameIndex(0);
		board[x][y].setCharacter(null);
		( board[x+rowT][y+colT]).setCharacter( character);
		board[x][y].redraw();
		
	}
		 if(character.currentCellX== board.length-1 && character.currentCellY == board[0].length-1 && board!=null){
			 //if we have reacharactered the destination
			 won=true; 
			 redraw(); //play a sound :)
			 getShell().setBackgroundImage(new Image(getDisplay(),".\\resources\\images\\sonicwon.png"));
		 }
		
	
	}
	}
	
	
		


