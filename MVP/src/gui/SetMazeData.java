package gui;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SetMazeData extends Dialog {

	  private ArrayList<Object> input; //instead of object write the type you want to return

	  public SetMazeData(Shell parent) {
		    this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		  }

	  public SetMazeData(Shell parent, int style) {
	    super(parent, style);
	  }


	  public ArrayList<Object> getInput() {
	    return input;
	  }

	  public void setInput(ArrayList<Object> input) {
	    this.input = input;
	  }

	  public ArrayList<Object> open() {
	    Shell shell = new Shell(getParent(), getStyle());
	    shell.setText("Maze Generations");
	    createContents(shell);
	    shell.pack();
	    shell.open();
	    Display display = getParent().getDisplay();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	    //display.dispose();
	    return input;
	  }

	  private void createContents(final Shell shell) {
	    shell.setLayout(new GridLayout(2, true));
	    //set text boxes and labels below. example as a comment.    
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
	   Ok.setLayoutData(new GridData(SWT.None,SWT.NONE,false,false,2,1));
	   Ok.addSelectionListener(new SelectionListener(){
		   @Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(rows.getText().equals("") || cols.getText().equals("")|| name.getText().equals("")||!isNumeric(rows.getText())||!isNumeric(cols.getText())||Integer.parseInt(rows.getText())<=0||Integer.parseInt(cols.getText())<0){
					MessageBox messageBox = new MessageBox(shell,SWT.ERROR|SWT.OK);
			        messageBox.setText("Error");
			        messageBox.setMessage("Maze data has  not been accepted!");
					messageBox.open();
				}
				else{
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
	  /*  Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("OK");
	    GridData dataOK = new GridData(GridData.FILL_HORIZONTAL);
	    ok.setLayoutData(dataOK);
	    ok.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        //what should happen when pressing ok
		input=//get text boxes data gather and save here.
	  		
	        shell.close();
	      }
	    });*/

	    Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("Cancel");
	    GridData dataCancel = new GridData(GridData.FILL_HORIZONTAL);
	    cancel.setLayoutData(dataCancel);
	    cancel.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        input = null;
	    
	        shell.dispose();
	      }
	    });

	 
	  }
	  private  boolean isNumeric(String str)
	  {
	    NumberFormat formatter = NumberFormat.getInstance();
	    ParsePosition pos = new ParsePosition(0);
	    formatter.parse(str, pos);
	    return str.length() == pos.getIndex();
	  }
}