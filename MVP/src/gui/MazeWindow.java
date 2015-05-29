package gui;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import presenter.Presenter.Command;
import view.View;
import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class MazeWindow extends BasicWindow implements View {

	protected ConcurrentHashMap<String, presenter.Presenter.Command>  commands;
	protected Command LastUserCommand =null;
	MazeDisplay mazeDisplay;
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
		// TODO Auto-generated method stub
		shell.setBackgroundImage(new Image(display,"White.jpg"));
		shell.setLayout(new GridLayout(2,false));
		shell.setText("Maze Generations");
		
		Menu menuBar = new Menu(shell, SWT.BAR);
		
        MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeFileMenu.setText("&File");
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeFileMenu.setMenu(fileMenu);
        
        MenuItem cascadeLoadMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeLoadMenu.setText("&Load");
        Menu LoadMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeLoadMenu.setMenu(LoadMenu);
        
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
				String[] filterExt = { ".xml", "*.*" };
				fd.setFilterExtensions(filterExt);
				fd.open();

				
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
						LastUserCommand= commands.get("exit");
						if(LastUserCommand==null)
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
				ArrayList<Object> mazearrayData = new SetMazeData(shell).open();
				if(mazearrayData!=null){
				MazeWindow.this.mazeName= (String)mazearrayData.get(0);
				MazeWindow.this.rows =(Integer)mazearrayData.get(1);
				MazeWindow.this.cols=(Integer)mazearrayData.get(2);
				}
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
							if(mazeDisplay.Ch!=null && !mazeDisplay.isDisposed()){
							 mazeDisplay.Ch.frameIndex= (mazeDisplay.Ch.frameIndex + 1) % mazeDisplay.Ch.images.length;
							 mazeDisplay.frameIndex =(mazeDisplay.frameIndex+1) % mazeDisplay.images.length;
							 mazeDisplay.mazeData[mazeDisplay.mazeData.length-1][mazeDisplay.mazeData[0].length-1].goal= new Image(display,mazeDisplay.images[mazeDisplay.frameIndex]);
							 mazeDisplay.redraw();
							}
							
						}
					});
					}
				}
			};
			
			timer = new Timer();
			timer.scheduleAtFixedRate(timerTask, 0, 100);
			
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
			
		   mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
		   mazeDisplay.displayMaze(m);
		   mazeDisplay.Ch = new Character(mazeDisplay.mazeData[0][0],SWT.FILL);
			mazeDisplay.Ch.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,true,2,2));
			mazeDisplay.mazeData[0][0].ch=mazeDisplay.Ch;
			mazeDisplay.mazeData[0][0].redraw();
			mazeDisplay.redraw();
			mazeDisplay.forceFocus();
			
		
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
		}
				
			
		}
	}
	


