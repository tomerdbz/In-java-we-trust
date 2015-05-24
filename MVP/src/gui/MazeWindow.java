package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;

import algorithms.mazeGenerators.DFSMazeGenerator;

public class MazeWindow extends BasicWindow {

	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	void initWidgets() {
		// TODO Auto-generated method stub
		shell.setLayout(new GridLayout(2,false));;
		shell.setText("Maze Runner");
		Button generateButton=new Button(shell,SWT.PUSH);
		generateButton.setText("Generate Maze");
		generateButton.setLayoutData(new GridData(SWT.FILL,SWT.TOP,false,false,1,1));
		
		MazeDisplay mazeDisplay=new MazeDisplay(shell, SWT.NONE);
		   mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
		   Button clueButton=new Button(shell,SWT.PUSH);
		   clueButton.setText("Help me solve this!");
		   clueButton.setLayoutData(new GridData(SWT.FILL,SWT.TOP,false,false,1,1));
		   generateButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
					mazeDisplay.displayMaze(new DFSMazeGenerator().generateMaze(4, 4, 0, 0, 3, 3));
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
