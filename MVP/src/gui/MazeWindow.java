package gui;

import gui.CellDisplay.Direction;
import jaco.mp3.player.MP3Player;

import java.beans.XMLDecoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import boot.GuiMvpDemo;
import boot.Run;
import boot.RunGui;
import presenter.Presenter.Command;
import presenter.Presenter;
import presenter.Properties;
import view.MyView;
import view.View;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class MazeWindow extends BasicWindow implements View {

	protected ConcurrentHashMap<String, presenter.Presenter.Command>  commands;
	protected Command LastUserCommand =null;
	MazeDisplay mazeDisplay;
	Properties properties;
	Boolean isHint;
	String mazeName=null;
	int cols=0;
	int rows =0;
	Timer timer;
	TimerTask timerTask;
	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
		// TODO Auto-generated constructor stub
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
				System.out.println("been here");
				
			}
			
		});
		
		// TODO Auto-generated method stub
		shell.setBackgroundImage(new Image(display,"White.jpg"));
		shell.setLayout(new GridLayout(2,false));
		shell.setText("Maze Generations");
		
		Menu menuBar = new Menu(shell, SWT.BAR);
		
        MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeFileMenu.setText("&File");
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeFileMenu.setMenu(fileMenu);
        
 
        
        MenuItem cascadeHelpMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeHelpMenu.setText("&Help");
        Menu HelpMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeHelpMenu.setMenu(HelpMenu);
        
      
        
		MenuItem item = new MenuItem(fileMenu, SWT.PUSH);
			item.setText("Open Properties");
			item.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd=new FileDialog(shell,SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("C:\\");
				String[] filterExt = { "*.xml"};
				fd.setFilterExtensions(filterExt);
				String filename=fd.open();
				if(filename!=null){
					setProperties(filename);
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							switch(properties.getUi())
							{
								case CLI:
									timer.cancel();
									closeCorrect();
									display.dispose();
									LastUserCommand= commands.get("exit");
									setChanged();
									notifyObservers();
									GuiMvpDemo demo=new GuiMvpDemo();
									demo.startProgram(getProperties());
									break;
								case GUI:
									timer.cancel();
									closeCorrect();
									display.dispose();
									LastUserCommand = commands.get("exit");
									setChanged();
									notifyObservers();
									RunGui demoG = new RunGui();
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
						public void run() {
							WritePropertiesGUI guiProp=new WritePropertiesGUI();
							guiProp.writeProperties(display,shell);	
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
									GuiMvpDemo demo=new GuiMvpDemo();
									demo.startProgram(getProperties());
									break;
								case GUI:
									timer.cancel();
									closeCorrect();
									display.dispose();
									LastUserCommand = commands.get("exit");
									setChanged();
									notifyObservers();
									RunGui demoG = new RunGui();
									demoG.start(getProperties());
									break;
								default:
									return;	
							}
							
						}
					});
				
					
				}
				
			});
			 item = new MenuItem(fileMenu, SWT.PUSH);
			    item.setText("Exit");
			    item.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						
						
					}

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						timer.cancel();
						closeCorrect();
						shell.dispose();
						LastUserCommand= commands.get("exit");
						setChanged();
						notifyObservers();
						
						
					}
			    	
			    });
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
	    
	   
		//Character a = new Character(this.shell,SWT.FILL);
	//	a.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,2,2));
		Button generateButton=new Button(shell,SWT.PUSH);
		generateButton.setText("Generate Maze");
		generateButton.setLayoutData(new GridData(SWT.FILL,SWT.TOP,false,false,1,1));
		
		//MazeDisplay mazeDisplay=new MazeDisplay(shell, SWT.NONE);
		 //  mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
		   Button clueButton=new Button(shell,SWT.PUSH);
		   clueButton.setText("Help me solve this!");
		   clueButton.setLayoutData(new GridData(SWT.FILL,SWT.TOP,false,false,1,1));
		   clueButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MP3Player player = new MP3Player();
			    player.addToPlayList(new File("menuselect.mp3"));
			    player.play();
				if(MazeWindow.this.mazeName!=null){
				isHint=true;
				LastUserCommand= commands.get("solve maze");
				setChanged();
				notifyObservers(MazeWindow.this.mazeName);
				for(int i=0; i<mazeDisplay.mazeRows;i++)
					for(int j=0;j<mazeDisplay.mazeCols;j++){
						mazeDisplay.mazeData[i][j].redraw();
					}
				mazeDisplay.redraw();
				mazeDisplay.forceFocus();
				}
				else{
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
			        messageBox.setText("Information");
			        messageBox.setMessage("No maze to solve");
					messageBox.open();
				}
				
			}
			   
		   });
		   generateButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				MP3Player player = new MP3Player();
			    player.addToPlayList(new File("menuselect.mp3"));
			    player.play();
				ArrayList<Object> mazearrayData = new SetMazeData(shell).open();
				if(mazearrayData!=null){
				MazeWindow.this.mazeName= (String)mazearrayData.get(0);
				MazeWindow.this.rows =(Integer)mazearrayData.get(1);
				MazeWindow.this.cols=(Integer)mazearrayData.get(2);
				}
				//When combinded with tomeriko check if maaze has existed
				if(MazeWindow.this.mazeName!=null){
				LastUserCommand= commands.get("generate maze");
				setChanged();
				String mazeData= "" + MazeWindow.this.mazeName + " "+MazeWindow.this.rows + ","+ MazeWindow.this.cols+ ",0,0,"+(MazeWindow.this.rows-1)+","+(MazeWindow.this.cols-1);
				System.out.println(mazeData);
				notifyObservers(mazeData);
				}
				else{
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
			        messageBox.setText("Information");
			        messageBox.setMessage("No maze to generate enter data below");
					messageBox.open();
				}
				
					
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		   Button solveMaze = new Button (shell ,SWT.PUSH);
		   solveMaze.setText("Solve the maze I give up");
		   solveMaze.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MP3Player player = new MP3Player();
			    player.addToPlayList(new File("menuselect.mp3"));
			    player.play();
				if(MazeWindow.this.mazeName!=null){
				LastUserCommand= commands.get("solve maze");
				isHint =false;
				setChanged();
				notifyObservers(MazeWindow.this.mazeName);
				for(int i=0; i<mazeDisplay.mazeRows;i++)
					for(int j=0;j<mazeDisplay.mazeCols;j++){
						mazeDisplay.mazeData[i][j].redraw();
					}
				mazeDisplay.redraw();
				mazeDisplay.forceFocus();
				}
				else{
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
			        messageBox.setText("Information");
			        messageBox.setMessage("No maze to solve");
					messageBox.open();
				}
			}
			   
		   });
		   mazeDisplay=new MazeDisplay(shell, SWT.NONE);
		   mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,2,2));
			//mazeDisplay.setVisible(false);
		
		   
		  timerTask = new TimerTask() {
				
				@Override
				public void run() {
					if(!mazeDisplay.isDisposed()){
					mazeDisplay.getDisplay().syncExec(new Runnable() {
						
						@Override
						public void run() {
							if(mazeDisplay.Ch!=null && !mazeDisplay.isDisposed() ){
							 mazeDisplay.Ch.frameIndex= (mazeDisplay.Ch.frameIndex + 1) % mazeDisplay.Ch.images.length;
							 mazeDisplay.frameIndex =(mazeDisplay.frameIndex+1) % mazeDisplay.images.length;
							 mazeDisplay.mazeData[mazeDisplay.mazeData.length-1][mazeDisplay.mazeData[0].length-1].goal= new Image(display,mazeDisplay.images[mazeDisplay.frameIndex]);
							 mazeDisplay.mazeData[mazeDisplay.Ch.currentCellX][mazeDisplay.Ch.currentCellY].redraw();
							 System.out.println(rows+" "+ cols);
							 mazeDisplay.mazeData[rows-1][cols-1].redraw();
							 
							 //mazeDisplay.redraw();
							 HasBeenDragged();
							}
							
						}
					});
					}
				}
			};
			
			timer = new Timer();
			timer.scheduleAtFixedRate(timerTask, 0, 50);
	}

	protected void setProperties(String filename) {
		
		FileInputStream in;
		try {
			XMLDecoder d;
			in = new FileInputStream(filename);
			d=new XMLDecoder(in);
			properties=(Properties)d.readObject();
			d.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void start() {
		
		this.run();
		
	}

	@Override
	public void setCommands(ConcurrentHashMap<String, Command> commands) {
		this.commands=commands;
		
	}

	@Override
	public Command getUserCommand() {
		return this.LastUserCommand;
	}

	@Override
	public void Display(String s) {
		MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
        messageBox.setText("Information");
        messageBox.setMessage(s);
		messageBox.open();
		
	}

	@Override
	public void displayMaze(Maze m) {//you made mazeDisplay a data member
		
		/*if(mazeDisplay.mazeData!=null){
			for(int i=0;i<mazeDisplay.mazeData.length;i++)
			{
				
				for(int j=0;j<mazeDisplay.mazeData[0].length;j++)
				{		 
					mazeDisplay.mazeData[i][j].dispose();
				}
			}
		}*/
		display.syncExec(new Runnable() {
			   public void run() {
				   mazeDisplay.displayMaze(m);
				   mazeDisplay.Ch = new Character(mazeDisplay.mazeData[0][0],SWT.FILL);
				   mazeDisplay.Ch.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,true,2,2));
				   mazeDisplay.mazeData[0][0].ch=mazeDisplay.Ch;
				   mazeDisplay.mazeData[0][0].redraw();
				   mazeDisplay.layout();
				   mazeDisplay.forceFocus();
			    
			   }
			}); 
			
		
	}
	public void closeCorrect(){
		if(mazeDisplay.mazeData!=null){
		for(int i=0;i<mazeDisplay.mazeData.length;i++)
		{
			for(int j=0;j<mazeDisplay.mazeData[0].length;j++)
			{	
				 if(mazeDisplay.mazeData[i][j].cellImage!=null)
					 mazeDisplay.mazeData[i][j].cellImage.dispose();
				 if(mazeDisplay.mazeData[i][j].goal!=null)
					 mazeDisplay.mazeData[i][j].goal.dispose();
				 if(mazeDisplay.mazeData[i][j].Hint!=null)
					 mazeDisplay.mazeData[i][j].Hint.dispose();
				 if(mazeDisplay.mazeData[i][j].ch!=null)
					 mazeDisplay.mazeData[i][j].ch.dispose();
				
				 if(mazeDisplay.Ch!=null)
					 mazeDisplay.Ch.dispose();
					 
				 mazeDisplay.mazeData[i][j].dispose();
			}
		}
		}
		mazeDisplay.dispose();
	}

	@Override
	public void displaySolution(Solution s) {
		String Solution = s.toString().substring(9);
		String []path = Solution.split("	");
		Image img = new Image(display,"ring.png");
		if(!isHint){
		for(int i=0;i<path.length-1;i++){
			String []indexes = path[i].split(",");
			int xt=Integer.parseInt(indexes[0]);
			int yt=Integer.parseInt(indexes[1]);	
				mazeDisplay.mazeData[xt][yt].Hint=img;
			}
	}
		else{
			int min=(Math.abs(mazeDisplay.Ch.currentCellX-(path[0].charAt(0)-48)) + Math.abs(mazeDisplay.Ch.currentCellY-(path[0].charAt(2)-48)));
			String []indexes;
			int x=0;
			int y=0;
			for(int i=0;i<path.length-1;i++){
				indexes = path[i].split(",");
				int xt=Integer.parseInt(indexes[0]);
				int yt=Integer.parseInt(indexes[1]);
				int temp=(Math.abs(mazeDisplay.Ch.currentCellX-(xt)) + Math.abs(mazeDisplay.Ch.currentCellY-(yt)));
				if(temp<min){
					temp= min;
					x=xt;
					y=yt;;
					
				}
			}
			mazeDisplay.mazeData[x][y].Hint=img;
			/*if(mazeDisplay.mazeData!=null){
			for(int i=0;i<mazeDisplay.mazeData.length;i++)
				for(int j=0;j<mazeDisplay.mazeData[0].length;j++)
					mazeDisplay.mazeData[i][j].redraw();
			}*/
			
		}
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				mazeDisplay.redraw();
				
			}
		});
			
		
			
		}
	public void HasBeenDragged(){
		if(mazeDisplay.mazeData[mazeDisplay.Ch.currentCellX][mazeDisplay.Ch.currentCellY].ch==null){
			Direction dir = mazeDisplay.mazeData[mazeDisplay.Ch.currentCellX][mazeDisplay.Ch.currentCellY].dir;
			int x = mazeDisplay.Ch.currentCellX;
			int y = mazeDisplay.Ch.currentCellY;
			if(dir == Direction.UpRight){
				if(x-1 >=0 && y+1<= mazeDisplay.mazeData[0].length-1)
				if((mazeDisplay.HasPathRight(x, y)&& mazeDisplay.HasPathUp(x, y+1))||(mazeDisplay.HasPathUp(x,y)&&mazeDisplay.HasPathRight(x-1, y)))
				{mazeDisplay.Ch = new Character(mazeDisplay.mazeData[x-1][y+1],SWT.FILL);
		    	mazeDisplay.Ch.currentCellX=x-1;
		    	mazeDisplay.Ch.currentCellY=y+1;
				mazeDisplay.Ch.frameIndex=0;
				mazeDisplay.mazeData[x-1][y+1].ch=mazeDisplay.Ch;
				if(mazeDisplay.Ch.currentCellX== mazeDisplay.mazeData.length-1 && mazeDisplay.Ch.currentCellY == mazeDisplay.mazeData[0].length-1 && mazeDisplay.mazeData!=null){
					 
					 shell.setBackgroundImage(new Image(display,"winnericon.png"));
					 MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
				        messageBox.setText("Winner");
				        messageBox.setMessage("You're the winner! This song is for you <3");
						messageBox.open();
						MP3Player player = new MP3Player();
					    player.addToPlayList(new File("win.mp3"));
					    player.play();
						
				 }
				return;
				}
			}
			else
				
			if(dir == Direction.UpLeft){
					if(x-1 >=0 && y-1>=0)
					if((mazeDisplay.HasPathLeft(x, y)&& mazeDisplay.HasPathUp(x, y-1))||(mazeDisplay.HasPathUp(x,y)&&mazeDisplay.HasPathLeft(x-1, y)))
					{mazeDisplay.Ch = new Character(mazeDisplay.mazeData[x-1][y-1],SWT.FILL);
			    	mazeDisplay.Ch.currentCellX=x-1;
			    	mazeDisplay.Ch.currentCellY=y-1;
					mazeDisplay.Ch.frameIndex=0;
					mazeDisplay.mazeData[x-1][y-1].ch=mazeDisplay.Ch;
					if(mazeDisplay.Ch.currentCellX== mazeDisplay.mazeData.length-1 && mazeDisplay.Ch.currentCellY == mazeDisplay.mazeData[0].length-1 && mazeDisplay.mazeData!=null){
						 
						 shell.setBackgroundImage(new Image(display,"winnericon.png"));
						 MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
					        messageBox.setText("Winner");
					        messageBox.setMessage("You're the winner! This song is for you <3");
							messageBox.open();
							MP3Player player = new MP3Player();
						    player.addToPlayList(new File("win.mp3"));
						    player.play();
							
					 }
					return;
					}
			}
			else
			if(dir == Direction.DownLeft){
				if(x+1 <=mazeDisplay.mazeData.length-1 && y-1>=0)
					if((mazeDisplay.HasPathLeft(x, y)&& mazeDisplay.HasPathDown(x, y-1))||(mazeDisplay.HasPathDown(x,y)&&mazeDisplay.HasPathLeft(x+1, y)))
					{mazeDisplay.Ch = new Character(mazeDisplay.mazeData[x+1][y-1],SWT.FILL);
			    	mazeDisplay.Ch.currentCellX=x+1;
			    	mazeDisplay.Ch.currentCellY=y-1;
					mazeDisplay.Ch.frameIndex=0;
					mazeDisplay.mazeData[x+1][y-1].ch=mazeDisplay.Ch;
					if(mazeDisplay.Ch.currentCellX== mazeDisplay.mazeData.length-1 && mazeDisplay.Ch.currentCellY == mazeDisplay.mazeData[0].length-1 && mazeDisplay.mazeData!=null){
						 
						 shell.setBackgroundImage(new Image(display,"winnericon.png"));
						 MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
					        messageBox.setText("Winner");
					        messageBox.setMessage("You're the winner! This song is for you <3");
							messageBox.open();
							MP3Player player = new MP3Player();
						    player.addToPlayList(new File("win.mp3"));
						    player.play();
							
					 }
					return;
					}
				
			}
			else
			if(dir == Direction.DownRight){
				if(x+1 <=mazeDisplay.mazeData.length-1 && y+1<=mazeDisplay.mazeData[0].length-1)
					if((mazeDisplay.HasPathRight(x, y)&& mazeDisplay.HasPathDown(x, y+1))||(mazeDisplay.HasPathDown(x,y)&&mazeDisplay.HasPathRight(x+1, y)))
					{mazeDisplay.Ch = new Character(mazeDisplay.mazeData[x+1][y+1],SWT.FILL);
			    	mazeDisplay.Ch.currentCellX=x+1;
			    	mazeDisplay.Ch.currentCellY=y+1;
					mazeDisplay.Ch.frameIndex=0;
					mazeDisplay.mazeData[x+1][y+1].ch=mazeDisplay.Ch;
					if(mazeDisplay.Ch.currentCellX== mazeDisplay.mazeData.length-1 && mazeDisplay.Ch.currentCellY == mazeDisplay.mazeData[0].length-1 && mazeDisplay.mazeData!=null){
						 
						 shell.setBackgroundImage(new Image(display,"winnericon.png"));
						 MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
					        messageBox.setText("Winner");
					        messageBox.setMessage("You're the winner! This song is for you <3");
							messageBox.open();
							MP3Player player = new MP3Player();
						    player.addToPlayList(new File("win.mp3"));
						    player.play();
							
					 }
					return;
					}
			}
			
				mazeDisplay.mazeData[x][y].ch=mazeDisplay.Ch;
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
	}

	@Override
	public void receiveData(String data) {
		if(data==null)
		{
			
		}
		else
		{
			
		}
	}

	@Override
	public Properties getProperties() {
		return properties;
	}
}
	
	


