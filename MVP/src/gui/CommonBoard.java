package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Composite;

public abstract class CommonBoard extends Composite implements Board {
	/**
	 * a 2 dimension array whicharacter represents the tiles in the board
	 */
	CommonTile[][] board;
	/**
	 * number of rows in the board
	 */
	int boardRows; 
	/**
	 * number of cols in the board
	 */
	int boardCols;
	/**
	 * representation of the characteraracter in the maze
	 */
	CommonCharacter character=null;
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
	boolean checkDragged=false; 
	
	public CommonBoard(Composite parent, int style) {
		super(parent, style);
			addPaintListener(new PaintListener() { 
			
			@Override
			public void paintControl(PaintEvent arg0) {
				drawBoard(arg0);
			}
		});
			this.addKeyListener(new KeyListener(){	
				@Override
				public void keyPressed(KeyEvent e) { //eacharacter of those codes represenets a key on the keyboard in our case up down right left arrows
					if (e.keyCode == 16777217 && character.currentCellX!=0 ){
						applyInputDirection((Direction.UP));
						 //up
						 	
				    } 
					 else 
						 if (e.keyCode == 16777220 && character.currentCellY!=board[0].length-1) {
							 applyInputDirection((Direction.RIGHT));
				    	//right
							 
				    } 
						else 
						if (e.keyCode == 16777219 && character.currentCellY!=0) {
							applyInputDirection(Direction.LEFT);
				    	//left
						
				    } 
						else
						if (e.keyCode == 16777218 &&character.currentCellX!= board.length-1) {
							applyInputDirection(Direction.DOWN);
				    	//down
							 
				    } 
					
					
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					
					
				}
			});
			//allows us to set the size of window by pressing ctrl + scrolling
			this.addMouseWheelListener(new MouseWheelListener(){

				@Override
				public void mouseScrolled(MouseEvent arg0) {
					if((arg0.stateMask& SWT.CTRL)!=0){ //if control is pressed
						if(arg0.count>0){ //and we scroll up
							//up zoom in										//Bonus!!
							setSize(getSize().x+30, getSize().y+30);
						}
						if(arg0.count<0){ //and we scroll down
							setSize(getSize().x-30, getSize().y-30);
							//down zoom out
							
						}
					}
					
					
				}
		});
			// mouse listener
			//addMouseListenerToComposite();
			
				/*	this.addDragDetectListener(new DragDetectListener(){

						@Override
						public void dragDetected(DragDetectEvent arg0) {
							if(character!=null){
							wasDragged=true; //detect if a drag has occureed
							}
						}
						
					});*/
	}




	@Override
	public abstract void  applyInputDirection(Direction direction);
	public abstract void applyDiagonalInputDirection(Direction direction);
	
	public void addMouseListenerToComposite() {
		MouseListener ma=new MouseListener(){

			int[] before=new int[2]; //place of cursor before click
			int[] after=new int[2]; // place of cursor after releasing click
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {	}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				checkDragged=false;
				if(board!=null){
				int sizeOftileX = CommonBoard.this.getSize().x/(board[0].length);
				int sizeOftileY = CommonBoard.this.getSize().y/(board.length);
					String str =CommonBoard.this.toControl(getDisplay().getCursorLocation()).toString(); //calculates mouse location by pixels
					String []loc = str.substring(7).split(",");
					before[0]=Integer.parseInt(loc[0].substring(0));
					before[1]=Integer.parseInt(loc[1].substring(1, loc[1].length()-1));
					int Ycell=((before[0]/sizeOftileX));
					int xcell=((before[1]/sizeOftileY));
					System.out.println(before[0] + " " +before[1]);
					if((character.currentCellX==xcell)&&character.currentCellY==Ycell)
						checkDragged=true;
				}
			}

			@Override
			public void mouseUp(MouseEvent arg0) {
					if(checkDragged){
					String str =CommonBoard.this.toControl(getDisplay().getCursorLocation()).toString();
					String []loc = str.substring(7).split(",");
					after[0]=Integer.parseInt(loc[0].substring(0));
					after[1]=Integer.parseInt(loc[1].substring(1, loc[1].length()-1)); //calculates mouse location by pixels
					System.out.println(after[0] + " " +after[1]);
					if(after[0]> before[0] && after[1]>before[1]){
						double Shipua = (double)((double)(after[1]-before[1])/(double)(after[0]-before[0]));
						if(Shipua<1.5 && Shipua>  0.5 )
						applyDiagonalInputDirection(Direction.DownRight);
						else
							if(Shipua>1.5 && character.currentCellX+1<=board.length-1)
							{
								System.out.println("downright");
							applyInputDirection(Direction.DOWN);
							}
							else
								if( character.currentCellY+1<= board[0].length-1){
									System.out.println("right");
								applyInputDirection(Direction.RIGHT);
								}
						//dir=Direction.DownRight;					//charactereck in whicharacter direction did the mouse move
					}
					if(after[0]> before[0] && after[1]<before[1]){
						double Shipua = ((double)((double)after[1]-before[1])/((double)after[0]-before[0]));
						if(Shipua>-1.5 && Shipua<-0.5)
						applyDiagonalInputDirection(Direction.UpRight);
						else
							if(Shipua<-0.5 && character.currentCellX-1>=0)
							applyInputDirection(Direction.UP);
							else
								if( character.currentCellY+1<= board[0].length-1)
								applyInputDirection(Direction.RIGHT);
						//dir=Direction.UpRight;
					}
					if(after[0]< before[0] && after[1]>before[1]){
						double Shipua = ((double)((double)after[1]-before[1])/((double)after[0]-before[0]));
						if(Shipua>-1.5 && Shipua<-0.5)
							applyDiagonalInputDirection(Direction.DownLeft);
							else
								if(Shipua<-1.5  && character.currentCellX+1<=board.length-1)
								applyInputDirection(Direction.DOWN);
								else
									if(character.currentCellY-1>=0)
									applyInputDirection(Direction.LEFT);
						//dir=Direction.DownLeft;
						
					}
					
					if(after[0]< before[0] && after[1]<before[1]){
						applyDiagonalInputDirection(Direction.UpLeft);
						double Shipua = ((double)((double)after[1]-before[1])/((double)after[0]-before[0]));
						if(Shipua<1.5 && Shipua> 0.5)
						applyDiagonalInputDirection(Direction.UpLeft);
						else
							if(Shipua<0.5 && character.currentCellY-1>=0)
							applyInputDirection(Direction.LEFT);
							else
								if(character.currentCellX-1>=0)
								applyInputDirection(Direction.UP);
						//dir=Direction.UpLeft;
						
					}
					}
					
					
				
						
			}
		};
		
	    addMouseListener(ma);
	    for (int i=0;i<board.length;i++)
	     	for(int j=0;j<board[0].length;j++)
	      		board[i][j].addMouseListener(ma);
	}

}
