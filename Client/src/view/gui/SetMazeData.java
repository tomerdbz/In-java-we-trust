package view.gui;
import jaco.mp3.player.MP3Player;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
/**
 * 
 * @author Tomer,Alon
 * a class in which we will enter data about the maze
 * extends Dialog
 *
 */
public class SetMazeData extends Dialog {
		/**
		 * conatins information about the maze
		 */
	  private ArrayList<Object> input; 

	  public SetMazeData(Shell parent) {
		    this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		  }
	  /**
	   * constructor
	   */
	  public SetMazeData(Shell parent, int style) {
	    super(parent, style);
	  }

	  /**
	   * getter
	   * @return returs data about maze
	   */
	  public ArrayList<Object> getInput() {
	    return input;
	  }
	  /**
	   * setter of the mazedata
	   * @param input data about the maze
	   */
	  public void setInput(ArrayList<Object> input) {
	    this.input = input;
	  }

	  public ArrayList<Object> open() {
	    Shell shell = new Shell(getParent(), getStyle());
	    shell.setText("Maze Generations"); //create the window and its text
	    createContents(shell);
	    shell.pack();
	    shell.open();
	    Display display = getParent().getDisplay();
	    while (!shell.isDisposed()) { //same idea as basic window
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	    return input;
	  }

	  private void createContents(final Shell shell) { //text boxes for data
	    shell.setLayout(new GridLayout(2, true));    
	    Label nameL = new Label(shell, SWT.NONE);
		nameL.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,2,1));
	    nameL.setText("Name of maze");
	   Text name = new Text(shell,SWT.NONE);
	   name.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,2,1));
	   Label rowsL = new Label(shell, SWT.NONE);
	   rowsL.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,2,1));
	    rowsL.setText("Number of rows in maze");
	   Text rows = new Text(shell,SWT.NONE);
	   rows.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,2,1));
	   Label colsL = new Label(shell, SWT.NONE);
	   colsL.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,2,1));
	    colsL.setText("Number of columns in maze");
	   Text cols = new Text(shell,SWT.NONE);
	   cols.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,2,1));


	   
	   
	   Button Ok=new Button(shell,SWT.PUSH);
	   Ok.setText("Ok This is the maze I want!");
	   Ok.setLayoutData(new GridData(SWT.None,SWT.NONE,false,false,1,1));
	   Ok.addSelectionListener(new SelectionListener(){
		   @Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MP3Player player = new MP3Player();
			    player.addToPlayList(new File(".\\resources\\sounds\\menuselect.mp3")); //play sound
			    player.play(); //checks if data is not ok
				if(rows.getText().equals("") || cols.getText().equals("")|| name.getText().equals("")||!isNumeric(rows.getText())||!isNumeric(cols.getText())||Integer.parseInt(rows.getText())<=0||Integer.parseInt(cols.getText())<0){
					MessageBox messageBox = new MessageBox(shell,SWT.ERROR|SWT.OK);
			        messageBox.setText("Error");
			        messageBox.setMessage("Maze data has  not been accepted!");
					messageBox.open();
				}
				else{ //if data is ok
					input = new ArrayList<Object>();
				input.add(name.getText());
				input.add(Integer.parseInt(rows.getText()));
				input.add(Integer.parseInt(cols.getText()));
				MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
		        messageBox.setText("Information");
		        messageBox.setMessage("Maze data has been accepted!");
				messageBox.open();
				shell.dispose();
				}
				
			}
			   
		   });
	   //cancel insertion of data
	    Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("Cancel");
	   // GridData dataCancel = new GridData(GridData.FILL_HORIZONTAL);
	    //cancel.setLayoutData(dataCancel);
	    cancel.setLayoutData(new GridData(SWT.NONE,SWT.NONE,true,true,1,1));
	    cancel.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	    	  MP3Player player = new MP3Player();
			    player.addToPlayList(new File(".\\resources\\sounds\\menuselect.mp3")); //play audio
			    player.play();
	        input = null;
	    
	        shell.dispose();
	      }
	    });

	 
	  }
	  /**'
	   * 
	   * @param str a string of some sort
	   * @return true if numeric if not false
	   */
	  private  boolean isNumeric(String str)
	  {
	    NumberFormat formatter = NumberFormat.getInstance();
	    ParsePosition pos = new ParsePosition(0);
	    formatter.parse(str, pos);
	    return str.length() == pos.getIndex();
	  }
}