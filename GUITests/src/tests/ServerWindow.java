package tests;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class ServerWindow extends BasicWindow {
	String [] clients;
	Text status;
	public ServerWindow(String title, int width, int height) {
		super(title, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	void initWidgets() {
		// TODO Auto-generated method stub

		shell.addListener(SWT.Close,new Listener(){

			@Override
			public void handleEvent(Event arg0) {
				display.dispose();				
			}
			
		});
		
		shell.setLayout(new GridLayout(2,false));
		
		//cool menu here
		
		
		
		
		//what startServer and stopServer will do - your job
		Button startServerButton=new Button(shell,SWT.PUSH);
		startServerButton.setText("Start Server");
		startServerButton.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,1,1));
		
		
		Button stopServerButton=new Button(shell,SWT.PUSH);
		stopServerButton.setText("Stop Server");
		stopServerButton.setLayoutData(new GridData(SWT.RIGHT,SWT.CENTER,false,false,1,1));
		
		
		final List list = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	    //list.setBounds(40, 20, 220, 100);
		list.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,2,1));
	    for (int loopIndex = 0; loopIndex < 9; loopIndex++) { //example to how I would put the data
	      list.add("Client IP: 127.0.0." + loopIndex +" Port: x");
	    }

	    final Text status = new Text(shell, SWT.CENTER | SWT.BORDER);
	    //status.setBounds(300, 130, 160, 25);
	    status.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,true,2,1));
	    list.addSelectionListener(new SelectionListener() {
	      public void widgetSelected(SelectionEvent event) {
	        String[] selectedClients = list.getSelection();
	        String outString = "";
	        for (int loopIndex = 0; loopIndex < selectedClients.length; loopIndex++)
	          outString += selectedClients[loopIndex].split(" ")[2] + " ";
	        status.setText("Selected Clients: " + outString);//INSTEAD SHOULD BE SOMETHING LIKE THAT
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
		disconnectClientButton.setText("Disconnect Client");
		disconnectClientButton.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER,false,false,2,1));
		disconnectClientButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String[] unparsedclients = list.getSelection();//clients values in the list - how they are written in the list
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
				// TODO Auto-generated method stub
				
			}
		});
		 
		
	}
	private void showStatus()//params
	{
		//status.setText(params);
	}
	private String[] clientsToDisconnect()
	{
		return clients;
		
	}

}
