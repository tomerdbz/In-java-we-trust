package view.gui;

import jaco.mp3.player.MP3Player;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
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
	
	MazeProperties input;
	/**
	 * Helps load gif to single images
	 */
	ImageLoader gifs=new ImageLoader();
	/**
	 * an array of images which represent the gif of the goal
	 */
	ImageData[] images;
	/**
	 * frame of the gif
	 */
	int frameIndex=0;
	int rowSource;
	int rowGoal;
	int colGoal;
	int colSource;
	MP3Player player;

	
	public MazeDisplay(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		images=gifs.load(".\\resources\\images\\ChaosEmerald.gif");
		player = new MP3Player();
		player.addToPlayList(new File(".\\resources\\sounds\\startmenu.mp3"));
	    player.play();
	    player.setRepeat(true);
	  //timer task to render 
		TimerTask  timerTask = new TimerTask() {
				
				@Override
				public void run() {
					if(!isDisposed()){
					getDisplay().syncExec(new Runnable() {
						@Override
						public void run() {
							if(character!=null && !isDisposed() &&input!=null){
								if(input.getRowGoal()<board.length && input.getColGoal()<board[0].length){
							 character.setCharacterImageIndex((character.getCharacterImageIndex() + 1) % character.getCharacterImagesArray().length); //next frame in gifs
							 frameIndex =(frameIndex+1) % images.length; //next frame in gifs
							 //(board[input.getRowGoal()][input.getColGoal()]).setGoal(new Image(display,images[frameIndex]));
							 (board[rowGoal][colGoal]).setGoal(new Image(getDisplay(),images[frameIndex]));
							 board[character.currentCellX][character.currentCellY].redraw(); //redraw cell in which character now stays
							board[rowGoal][colGoal].redraw();
							//redraw the goal cell
								}
							}
							
						}
					});
					}
				}
			};
			
			this.timer = new Timer();
			timer.scheduleAtFixedRate(timerTask, 0, 50); //ever 0.05 seconds render display
	
		
	
	}
	/**
	 * displays the maze in a gui way
	 * @param m the maze to be displayed
	 */
	@Override
	public void setBoardData(Object o)
	{
		Maze m=(Maze)o;
		player.stop();
		player=new MP3Player();
		player.addToPlayList(new File(".\\resources\\sounds\\mazemenu.mp3"));
	    player.play();
	    player.setRepeat(true);
	    
		MazeDisplay a =this; //for our convenience
		getDisplay().syncExec(new Runnable() {
			   public void run() {
				   if(board!=null)
				   a.destructBoard(); // destory previous maze if exists 
				   boardRows=m.getRows(); //gets maze rows and cols
				   boardCols=m.getCols();
				   rowSource=m.getRowSource();
				   colSource=m.getColSource();
				   rowGoal=m.getRowGoal();
				   colGoal=m.getColGoal();
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
	public void destructBoard()
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
	@Override
	public boolean hasPathRIGHT(int Row,int Col){
		 boolean cond1 = board[Row][Col].getImageName().charAt(2)=='0';
		 boolean cond2 =board[Row][Col+1].getImageName().charAt(0)=='0';
	if(cond1&&cond2)
		return true;
	return false;
		
	}
	// a function to calculate if cell has a left wall
	@Override
	public boolean hasPathLEFT(int Row,int Col){
		 boolean cond1 = board[Row][Col].getImageName().charAt(0)=='0';
		 boolean cond2 =board[Row][Col-1].getImageName().charAt(2)=='0';
	if(cond1&&cond2)
		return true;
	
		return false;
	}
	// a function to calculate if cell has a up wall
	@Override
	public boolean hasPathUP(int Row,int Col){
		
		 boolean cond1 = board[Row][Col].getImageName().charAt(1)=='0';
		 boolean cond2 =board[Row-1][Col].getImageName().charAt(3)=='0';
		 	if(cond1 && cond2)
		 		return true;
		return false;
	}
	// a function to calculate if cell has a down wall
	@Override
	public boolean hasPathDOWN(int Row,int Col){
		
		boolean cond1 = board[Row][Col].getImageName().charAt(3)=='0';
		 boolean cond2 =board[Row+1][Col].getImageName().charAt(1)=='0';	
		 if(cond1&&cond2)
			 return true;
		return false;
	}
	@Override
	public void drawBoard(PaintEvent arg0) {
		if(board==null && won==false){ //displays the photo in the begining of the program as an intro
			
		    int width=getParent().getSize().x;
			int height=getParent().getSize().y;
			ImageData data = new ImageData(".\\resources\\images\\Wallpaper.png");
			arg0.gc.drawImage(new Image(getDisplay(),".\\resources\\images\\Wallpaper.png"),0,0,data.width,data.height,0,0,width,height);
		}
		else if(won==true)
		{
			//player.stop();
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
		int dRow=0;
		int dCol =0;
		switch (direction){
		case UP:
				dRow=1;
			 	break;
		case RIGHT:
				dCol=-1;
				break;
		case LEFT:
				dCol=1;
				break;
		case DOWN: 
				dRow=-1;
				break;
		case UPRIGHT:
				dRow =1;
				dCol = -1;
				break;
		case UPLEFT:
				dRow =1;
				dCol=1;
				break;	
		case DOWNLEFT:
			 	dRow=-1;
			 	dCol =1;
			 	break;
		case DOWNRIGHT:
				dRow =-1;
				dCol=-1;
				break;
		default:
			break;
			
		}	
		int row=character.currentCellX;  //this function redraws the character
    	int col = character.currentCellY; //in the new place as needed
    	board[row][col].setCharacter(null);
    	character.setVisible(false);
    	character = new MazeCharacter( board[row-dRow][col-dCol],SWT.FILL);
    	character.currentCellX=row-dRow;
    	character.currentCellY=col-dCol;
		character.setCharacterImageIndex(0);
		board[row-dRow][col-dCol].setCharacter(character);
		board[character.currentCellX+dRow][character.currentCellY+dCol].redraw();
		board[character.currentCellX][character.currentCellY].redraw(); //redrawing the character
		
		if(character.currentCellX== this.rowGoal && character.currentCellY == this.colGoal && board!=null){
			 //if we have reacharactered the destination
			 won=true; 
			 redraw(); //play a sound :)
			 getShell().setBackgroundImage(new Image(getDisplay(),".\\resources\\images\\sonicwon.png"));
		 }
		
	
	}
	@Override
	public void setBoardProperties(Object tempInput) {
		input=(MazeProperties)tempInput;
	}

	
	}
	
	
		


