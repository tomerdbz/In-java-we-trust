package view;

import jaco.mp3.player.MP3Player;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import presenter.ServerProperties;

public class ServerWindow extends BasicWindow {
	String [] clients;
	Text status;
	ServerProperties serverProperties;
	public ServerWindow(String title, int width, int height) {
		super(title, width, height);
		shell.setBackgroundImage(new Image(display,".\\resources\\images\\image.png")); //background for winning
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		MP3Player player = new MP3Player();
	    player.addToPlayList(new File(".\\resources\\sounds\\menu.mp3"));
	    player.play();
	    player.setRepeat(true);
	}

	@Override
	void initWidgets() {
		
		shell.addListener(SWT.Close,new Listener(){

			@Override
			public void handleEvent(Event arg0) {
				display.dispose();				
			}
			
		});
		initMenu();
		shell.setLayout(new GridLayout(2,false));
		
		//cool menu here
		
		initMenu();
		
		
		//what startServer and stopServer will do - your job
		Button startServerButton=new Button(shell,SWT.PUSH);
		startServerButton.setText("Start Server");
		startServerButton.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,1,1));
		
		
		Button stopServerButton=new Button(shell,SWT.PUSH);
		stopServerButton.setText("Stop Server");
		stopServerButton.setLayoutData(new GridData(SWT.RIGHT,SWT.CENTER,false,false,1,1));
		
		final Label listLabel=new Label(shell,SWT.CENTER);
		listLabel.setForeground(listLabel.getDisplay().getSystemColor(SWT.COLOR_CYAN));
		listLabel.setText("Connected Clients");
		FontData fontData = listLabel.getFont().getFontData()[0];
		fontData.setHeight(20);
		Font font = new Font(display, new FontData(fontData.getName(), fontData
		    .getHeight(), SWT.ITALIC));
		listLabel.setFont(font);
		listLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		
		final List list = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	    //list.setBounds(40, 20, 220, 100);
		list.setForeground(listLabel.getDisplay().getSystemColor(SWT.COLOR_CYAN));
		list.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,2,1));
	    for (int loopIndex = 0; loopIndex < 20; loopIndex++) { //example to how I would put the data
	      list.add("Client IP: 127.0.0." + loopIndex +" Port: x");
	    }

	    final Text status = new Text(shell, SWT.CENTER | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	    //status.setBounds(300, 130, 160, 25);
	    status.setForeground(listLabel.getDisplay().getSystemColor(SWT.COLOR_CYAN));
	    status.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,true,2,1));
	    list.addSelectionListener(new SelectionListener() {
	      public void widgetSelected(SelectionEvent event) {
	        String[] selectedClients = list.getSelection();
	        String outString = "";
	        if(selectedClients.length>1)
	        {
	        	for (int loopIndex = 0; loopIndex < selectedClients.length; loopIndex++)
	        	{
	        		if(loopIndex!=selectedClients.length-1)
	        			outString += selectedClients[loopIndex].split(" ")[2] + ", ";
	        		else
	        			outString+=selectedClients[loopIndex].split(" ")[2];
	        	}
	        }
	        else if(selectedClients.length==1)
	        	outString=selectedClients[0].split(" ")[2];
	        if(selectedClients.length!=0)
	        	status.setText("Selected Clients: " + outString);//INSTEAD SHOULD BE SOMETHING LIKE THAT
	        else
	        	status.setText("");
	        /* setChanged();
	         * notifyObservers(status for chosen client)
	         * showStatus(params);
	         */
	      }

	      public void widgetDefaultSelected(SelectionEvent event) {
	        int[] selectedClients = list.getSelectionIndices();
	        String outString = "";
	        for (int loopIndex = 0; loopIndex < selectedClients.length; loopIndex++)
	          outString += selectedClients[loopIndex] + " ";
	        System.out.println("Selected Clients: " + outString);
	      }
	    });
		
		
		Button disconnectClientButton=new Button(shell,SWT.PUSH | SWT.CENTER);
		disconnectClientButton.setText("Disconnect Clients");
		disconnectClientButton.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER,false,false,2,1));
		disconnectClientButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String[] unparsedClients = list.getSelection();//clients values in the list - how they are written in the list
				/* You choose how the Presenter shall transfer the clients to the list and how they will be written
				 * Parse it accordingly. if it's written like: "Client: IP PORT ..." You can parse it easily. 
				 */
				/*
				 * In the end:
				 * clients= (parsed unparsedclients)
				 * setChanged();
				 * notifyObservers(I have clients here waiting for diconnections - go pick them up);
				 */
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		 
		
	}
	private void initMenu() {
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
					//setChanged();
					//notifyObservers("properties");
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
							WriteServerPropertiesGUI guiProp=new WriteServerPropertiesGUI();
							if(guiProp.writeProperties(display,shell)!=-1)
							{
								serverProperties=readProperties();
							}
							//setChanged();
							//notifyObservers("properties");
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
						display.dispose();
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
		
	}

	private void showStatus()//params
	{
		//status.setText(params);
	}
	private String[] clientsToDisconnect()
	{
		return clients;
		
	}
	private void setProperties(String filename) {//sets properties from a certain file
		
		FileInputStream in;
		try {
			XMLDecoder d;
			in = new FileInputStream(filename);
			d=new XMLDecoder(in);
			serverProperties=(ServerProperties)d.readObject();
			d.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ServerProperties readProperties()
	{
		XMLDecoder d;
		ServerProperties p=null;
		try {
			FileInputStream in=new FileInputStream("properties.xml");
			d=new XMLDecoder(in);
			p=(ServerProperties)d.readObject();
			System.out.println(p);
			d.close();
		} catch (IOException e) {
			return new ServerProperties();
		}
		return p;
	}
}
