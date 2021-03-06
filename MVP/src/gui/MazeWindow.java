package gui;


import jaco.mp3.player.MP3Player;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import presenter.Presenter.Command;
import presenter.ClientProperties;
import view.View;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import algorithms.search.State;
import boot.Run;
import boot.RunCLI;
import boot.RunGUI;
import boot.WritePropertiesGUI;
/**
 * 
 * @author Alon,Tomer
 * This class is an extension of the basic windows and is the main window of the program
 * it is also an implementation of the view as part of the MVP system
 *
 */
public class MazeWindow extends BasicWindow implements View {
	/**
	 * a hash map which is safe to work with threads containing string
	 * and there matching command
	 */
	protected ConcurrentHashMap<String, presenter.Presenter.Command>  commands;
	/**
	 * last command that has been used in the mvp system
	 */
	protected Command LastUserCommand =null;
	/**
	 * an instance of mazeDisplay which represents the maze
	 */
	MazeDisplay mazeDisplay;
	/**
	 * properties of the maze
	 * Thread pools Random maze generator or dfs and so...
	 */
	ClientProperties properties;
	/**
	 * name of the maze
	 */
	String mazeName=null;
	/**
	 * number of cols in the maze
	 */
	int cols=0;
	/**
	 * number of rows in the maze
	 */
	int rows =0;
	/**
	 * Represents a timer
	 */
	Timer timer;
	/**
	 * timer task for the thread that will render our animation
	 */
	TimerTask timerTask;
	/**
	 * 
	 */
	/**
	 * true if the data we sent already exists in the database
	 */
	Maze dataRecieved=null; 
	MazeProperties input;
	
	public MazeWindow(Display display,Shell shell,String title, int width, int height) {
		super(display,shell,title,width,height);
	}
	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
		
	}

	@Override
	void initWidgets() {
		shell.addListener(SWT.Close,new Listener(){

			@Override
			public void handleEvent(Event arg0) {
				timer.cancel();
				closeCorrect();
				display.dispose();
				LastUserCommand= commands.get("exit");
				setChanged();
				notifyObservers();
				
			}
			
		});
		//sets the background image to white
		shell.setBackgroundImage(new Image(display,".\\resources\\images\\White.jpg"));
		shell.setLayout(new GridLayout(2,false));
		//shell.setLayoutData((new GridData(SWT.FILL,SWT.FILL,true,true,3,3)));
		shell.setText("Maze Generations"); //sets the text of window
		//creates a tool bar
		Menu menuBar = new Menu(shell, SWT.BAR);
		//creates a file category in toolbar
        MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeFileMenu.setText("&File");
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeFileMenu.setMenu(fileMenu);
        
 
        //creates a help category in toolbar
        MenuItem cascadeHelpMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeHelpMenu.setText("&Help");
        Menu HelpMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeHelpMenu.setMenu(HelpMenu);
        
      
        //add item to file menu open properties
		MenuItem item = new MenuItem(fileMenu, SWT.PUSH);
			item.setText("Open Properties");
			item.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd=new FileDialog(shell,SWT.OPEN); //opens a dialog box in which we can select a xml file and load it
				fd.setText("open");
				fd.setFilterPath("C:\\");
				String[] filterExt = { "*.xml"};
				fd.setFilterExtensions(filterExt);
				String filename=fd.open(); //choose the file
				if(filename!=null){
					setProperties(filename);
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							switch(properties.getUi())
							{
								case CLI: //if the properties calls for CLI
									timer.cancel(); //cancel the timer task
									closeCorrect(); //dispose all data
									display.dispose(); //dispose display
									LastUserCommand= commands.get("exit");
									setChanged();
									notifyObservers();//exit correctly
									RunCLI demo=new RunCLI(); //call for a function that works with cli
									demo.startProgram(getProperties());
									break;
								case GUI:
									timer.cancel(); //if the properties call for gui
									closeCorrect();// dispose all and close timer task
									display.dispose();
									LastUserCommand = commands.get("exit"); 
									setChanged();
									notifyObservers();//exit correctly
									RunGUI demoG = new RunGUI(); //calls for a function that recreates a gui window
									demoG.start(getProperties());
									break;
								default:
									return;	
							}
							
						}
					});
				}
			}
	    	
	    });
			//new item to file menu
			item = new MenuItem(fileMenu, SWT.PUSH);
			item.setText("Write Properties");
			item.addSelectionListener(new SelectionListener(){
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
					
					
				}
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					display.asyncExec(new Runnable() {
						
						@Override
						public void run() {//this function works on the same basis as open Properties the only difference is the source of the Properties data here we recieve it directly from the user
							WritePropertiesGUI guiProp=new WritePropertiesGUI();
							if(guiProp.writeProperties(shell)!=-1)
							{
								properties=Run.readProperties();
								switch(properties.getUi())
								{
									case CLI:
										timer.cancel();
										closeCorrect();
										display.dispose();
										LastUserCommand= commands.get("exit");
										setChanged();
										notifyObservers();
										RunCLI demo=new RunCLI();
										demo.startProgram(getProperties());
										break;
									case GUI:
										timer.cancel();
										closeCorrect();
										display.dispose();
										LastUserCommand = commands.get("exit");
										setChanged();
										notifyObservers();
										RunGUI demoG = new RunGUI();
										demoG.start(getProperties());
										break;
									default:
										return;	
								}
							}
						}
					});
				
					
				}
				
			});
			//new item for file menu
			 item = new MenuItem(fileMenu, SWT.PUSH);
			    item.setText("Exit");
			    item.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						
						
					}

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						timer.cancel(); //exits correctly out of the program
						closeCorrect();
						shell.dispose();
						LastUserCommand= commands.get("exit");
						setChanged();
						notifyObservers();
						
						
					}
			    	
			    });
			    //a new item in the help menu prints a msg box telling the user some details about us:)
			    item = new MenuItem(HelpMenu, SWT.PUSH);
			    item.setText("About");
			    item.addSelectionListener(new SelectionListener(){
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
				        messageBox.setText("Information");
				        messageBox.setMessage("This entire software was created by Tomer Cabouly and Alon Orlovsky Enjoy It!");
						messageBox.open();
						
					}
			    	
			    });
			shell.setMenuBar(menuBar);
	    
	   //buttons for generate maze
		Button generateButton=new Button(shell,SWT.PUSH);
		generateButton.setText("Generate Maze");
		generateButton.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
			//button that solves the maze
		//creates an instance of mazeDisplay
		   mazeDisplay=new MazeDisplay(shell, SWT.NONE);
		   mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,3));
		   Button clueButton=new Button(shell,SWT.PUSH);
		   clueButton.setText("Help me solve this!");
		   clueButton.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		   Button solveMaze = new Button (shell ,SWT.PUSH);
		   solveMaze.setText("Solve the maze I give up");
		   solveMaze.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		   clueButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MP3Player player = new MP3Player(); //selected button music starts playing
			    player.addToPlayList(new File(".\\resources\\sounds\\menuselect.mp3"));
			    player.play();
				if(MazeWindow.this.mazeName!=null && !mazeDisplay.won){
				LastUserCommand= commands.get("calculate hint");
				setChanged(); //mvp solve maze
				notifyObservers(" "+MazeWindow.this.mazeName + " "+ mazeDisplay.character.currentCellX+","+ mazeDisplay.character.currentCellY);
				for(int i=0; i<mazeDisplay.boardRows;i++)
					for(int j=0;j<mazeDisplay.boardCols;j++){
						mazeDisplay.board[i][j].redraw();
					}
				mazeDisplay.redraw();
				mazeDisplay.forceFocus();
				}
				else{ //if there is no maze to be solved error
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
			        messageBox.setText("Information");
			        messageBox.setMessage("No hint to show");
					messageBox.open();
				}
				
			}
			   
		   });
		   generateButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				MP3Player player = new MP3Player(); //play sound
			    player.addToPlayList(new File(".\\resources\\sounds\\menuselect.mp3"));
			    player.play();
				/*ArrayList<Object> mazearrayData = new SetMazeData(shell).open(); //set Data of maze in a different window
				if(mazearrayData!=null){ //if data has been accepted 
				MazeWindow.this.mazeName= (String)mazearrayData.get(0); //take the name of the maze
				}*/
			    ClassInputDialog dlg = new ClassInputDialog(shell,MazeProperties.class);
			    input = (MazeProperties) dlg.open();
			    if(input!=null && input.getColGoal()<input.getCols() && input.getRowGoal()<input.getRows()){
			    	MazeWindow.this.mazeName=input.getMazeName();
			    }
				LastUserCommand= commands.get("maze exists");
			    setChanged(); //check if maze already exists
				notifyObservers(MazeWindow.this.mazeName); 
				if(input!=null && MazeWindow.this.mazeName!=null &&  MazeWindow.this.dataRecieved==null ){ //if maze doesnt exist create a new one
					MazeWindow.this.mazeDisplay.won=false;
					MazeWindow.this.rows =(Integer)input.getRows(); //takes the info about rows
					MazeWindow.this.cols=(Integer)input.getCols(); //takes the info about cols
					mazeDisplay.setVisible(true); //makes sure the mazeDisplay is visible
					shell.setBackgroundImage(new Image(display,".\\resources\\images\\White.jpg")); //sets Background images
				LastUserCommand= commands.get("generate maze");
				setChanged();
				String board= "" + MazeWindow.this.mazeName + " "+MazeWindow.this.rows + ","+ MazeWindow.this.cols+ ","+input.getRowSource()+","+input.getColSource()+","+(input.getRowGoal())+","+(input.getColGoal());
				System.out.println(board);
				notifyObservers(" "+ board); //passses data to generate maze in MVP System
				}
				else if(dataRecieved!=null)
				{
					displayMaze(dataRecieved);
				}
				else if(input==null || input.getColGoal()>=input.getCols() || input.getRowGoal()>=input.getRows()){// || !(input.getColGoal()<input.getCols() && input.getRowGoal()<input.getRows())){ //if error has occureed 
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
			        messageBox.setText("Information");
			        messageBox.setMessage("An error has occureed");
					messageBox.open();
				}
				
					
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		   //new button that solves the entire maze same concept as hint
		   
		   solveMaze.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MP3Player player = new MP3Player();
			    player.addToPlayList(new File(".\\resources\\sounds\\menuselect.mp3")); //play sound
			    player.play();
				if(MazeWindow.this.mazeName!=null && !mazeDisplay.won){
				LastUserCommand= commands.get("solve maze");
				setChanged(); //solve the maze
				notifyObservers(" "+MazeWindow.this.mazeName);
				}
				else{ //if there is no maze to solve
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
			        messageBox.setText("Information");
			        messageBox.setMessage("No maze to solve");
					messageBox.open();
				}
			}
			   
		   });
		   
			//mazeDisplay.setVisible(false);
		
		   //timer task to render 
		  timerTask = new TimerTask() {
				
				@Override
				public void run() {
					if(!mazeDisplay.isDisposed()){
					mazeDisplay.getDisplay().syncExec(new Runnable() {
						@Override
						public void run() {
							if(mazeDisplay.character!=null && !mazeDisplay.isDisposed() &&input!=null ){
								if(input.getRowGoal()<mazeDisplay.board.length && input.getColGoal()<mazeDisplay.board[0].length){
							 mazeDisplay.character.setCharacterImageIndex((mazeDisplay.character.getCharacterImageIndex() + 1) % mazeDisplay.character.getCharacterImagesArray().length); //next frame in gifs
							 mazeDisplay.frameIndex =(mazeDisplay.frameIndex+1) % mazeDisplay.images.length; //next frame in gifs
							 (mazeDisplay.board[input.getRowGoal()][input.getColGoal()]).setGoal(new Image(display,mazeDisplay.images[mazeDisplay.frameIndex]));
							 mazeDisplay.board[mazeDisplay.character.currentCellX][mazeDisplay.character.currentCellY].redraw(); //redraw cell in which character now stays
							mazeDisplay.board[input.getRowGoal()][input.getColGoal()].redraw(); //redraw the goal cell
								}
							}
							
						}
					});
					}
				}
			};
			
			timer = new Timer();
			timer.scheduleAtFixedRate(timerTask, 0, 50); //ever 0.05 seconds render display
	}

	protected void setProperties(String filename) {//sets properties from a certain file
		
		FileInputStream in;
		try {
			XMLDecoder d;
			in = new FileInputStream(filename);
			d=new XMLDecoder(in);
			properties=(ClientProperties)d.readObject();
			d.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * starts the MVP system
	 */
	@Override
	public void start() { 
		
		this.run();
		
	}
	/**
	 * 
	 * @param commands a hash map with all the commands
	 * initialize our hash map with the commands
	 */
	@Override
	public void setCommands(ConcurrentHashMap<String, Command> commands) {
		this.commands=commands;
		
	}
	/**
	 * 
	 * @return last user Command we used
	 */
	@Override
	public Command getUserCommand() {
		return this.LastUserCommand;
	}
	/**
	 * display string s
	 * @param s string
 	 */
	@Override
	public void Display(String s) {
		MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
        messageBox.setText("Information");
        messageBox.setMessage(s);
		messageBox.open();
		
	}
	/**
	 * display a maze in a gui way
	 * @param m
	 */
	@Override
	public void displayMaze(Maze m) {
	
		display.syncExec(new Runnable() {
			   public void run() {
				   
				   mazeDisplay.displayMaze(m);
				   mazeDisplay.SetBoardData(input.getRowSource()+" "+input.getColSource() + " "+ input.getRowGoal() + " " + input.getColGoal());
				   mazeDisplay.character = new MazeCharacter(mazeDisplay.board[input.getRowSource()][input.getColSource()],SWT.FILL);
				   mazeDisplay.character.setCurrentCellX(input.getRowSource());
				   mazeDisplay.character.setCurrentCellY(input.getColSource());
				   mazeDisplay.character.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,true,2,2));
				   (mazeDisplay.board[input.getRowSource()][input.getColSource()]).setCharacter(mazeDisplay.character); //set character to the begining of the maze
				   mazeDisplay.board[input.getRowSource()][input.getColSource()].redraw();
				   mazeDisplay.layout(); //draw all the things needed
				   mazeDisplay.forceFocus();
				   
			   }
			}); 
			
		
	}
	/**
	 * disposes all data of the maze
	 */
	public void closeCorrect(){
		if(mazeDisplay.board!=null){
		for(int i=0;i<mazeDisplay.board.length;i++)
		{
			for(int j=0;j<mazeDisplay.board[0].length;j++)
			{	
				 if( mazeDisplay.board[i][j].getCellImage()!=null)
					( mazeDisplay.board[i][j]).getCellImage().dispose();
				 if(( mazeDisplay.board[i][j]).getGoal()!=null)
					 ( mazeDisplay.board[i][j]).getGoal().dispose();
				 if(( mazeDisplay.board[i][j]).getHint()!=null)
					 ( mazeDisplay.board[i][j]).getHint().dispose();
				 if(( mazeDisplay.board[i][j]).getCharacter()!=null)
					 ( mazeDisplay.board[i][j]).getCharacter().dispose();
				
				 if(mazeDisplay.character!=null)
					 mazeDisplay.character.dispose();
					 
				 ( mazeDisplay.board[i][j]).dispose();
			}
		}
		}
		mazeDisplay.dispose();
	}
	/**
	 * display solution
	 * @param s is the solution
	 * we can either show a full solution or just a hint
	 */
	@Override
	public void displaySolution(Solution s) {
		System.out.println(s);
		String Solution = s.toString().substring(9);
		String []path = Solution.split("	");
		Image img = new Image(display,".\\resources\\images\\ring.png"); //hint image
		for(int i=0;i<path.length-1;i++){
			String []indexes = path[i].split(",");
			int xt=Integer.parseInt(indexes[0]);
			int yt=Integer.parseInt(indexes[1]);	
				(mazeDisplay.board[xt][yt]).setHint(img); //put hints all over the solutions path
			}
	
		
			/*final int dx=x;
			final int dy=y;
			display.asyncExec(new Runnable() {
				
				@Override
				public void run() {
					mazeDisplay.board[dx][dy].Hint=img;
					mazeDisplay.board[dx][dy].redraw(); //redraw the hint
				}
			});*/
			
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0; i<mazeDisplay.boardRows;i++)
					for(int j=0;j<mazeDisplay.boardCols;j++){
						mazeDisplay.board[i][j].redraw();
					}
				mazeDisplay.redraw(); //redraw maze
				mazeDisplay.forceFocus();	
			}
		});
			
		
			
		}
	
			
	
	/**
	 * 
	 * @param data checks if data exists in database true if not false if yes
	 */
	@Override
	public void receiveExistsMaze(Maze data) {
		this.dataRecieved=data;
	}
	/**
	 * getter
	 * @return properties
	 */
	@Override
	public ClientProperties getProperties() {
		return properties;
	}

	@Override
	public void displayHint(State h) {
		Image img = new Image(display,".\\resources\\images\\ring.png"); //hint image
		final String[] coordinates=h.getState().split(",");
		if(isNumeric(coordinates[0]) && isNumeric(coordinates[1]))
		{
			
			(mazeDisplay.board[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]).setHint(img); //put hints all over the solutions path
			display.asyncExec(new Runnable() {
				
				@Override
				public void run() {
					mazeDisplay.board[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])].redraw(); //redraw the hint
				}
			});
		}
		
	}
	private static boolean isNumeric(String str)
	  {
	    NumberFormat formatter = NumberFormat.getInstance();
	    ParsePosition pos = new ParsePosition(0);
	    formatter.parse(str, pos);
	    return str.length() == pos.getIndex();
	  }
}
	
	


