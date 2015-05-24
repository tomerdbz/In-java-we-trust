package gui;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PropertiesWindow extends BasicWindow{
	PropertyDescriptor[] descs;
	Class c;
	Class n;
	HashMap<String,Text> txtMap=new HashMap<String,Text>();
	public PropertiesWindow(String title, int width, int height) {
		super(title, width, height);
	}

	@Override
	void initWidgets() {
		GridLayout layout = new GridLayout(2, false);
	    layout.marginRight = 5;
	    shell.setLayout(layout);
	    for(PropertyDescriptor desc: descs)
	    {
	    	Label lbl=new Label(shell,SWT.BORDER);
	    	lbl.setText(desc.getName()+": ");
	    	Text txt=new Text(shell,SWT.BORDER);
	    	GridData gridData = new GridData();
		    gridData.horizontalAlignment = SWT.FILL;
		    gridData.grabExcessHorizontalSpace = true;
		    txt.setLayoutData(gridData);
		    txtMap.put(desc.getName(),txt);
	    }
	    /*Label firstLabel = new Label(shell, SWT.NONE);
	    firstLabel.setText("Firstname: ");
	    firstName = new Text(shell, SWT.BORDER);

	    GridData gridData = new GridData();
	    gridData.horizontalAlignment = SWT.FILL;
	    gridData.grabExcessHorizontalSpace = true;
	    firstName.setLayoutData(gridData);

	    Label ageLabel = new Label(shell, SWT.NONE);
	    ageLabel.setText("Age: ");
	    ageText = new Text(shell, SWT.BORDER);

	    gridData = new GridData();
	    gridData.horizontalAlignment = SWT.FILL;
	    gridData.grabExcessHorizontalSpace = true;
	    ageText.setLayoutData(gridData);

	    Label marriedLabel = new Label(parent, SWT.NONE);
	    marriedLabel.setText("Married: ");
	    marriedButton = new Button(parent, SWT.CHECK);

	    Label genderLabel = new Label(parent, SWT.NONE);
	    genderLabel.setText("Gender: ");
	    genderCombo = new Combo(parent, SWT.NONE);
	    genderCombo.add("Male");
	    genderCombo.add("Female");

	    Label countryLabel = new Label(parent, SWT.NONE);
	    countryLabel.setText("Country");
	    countryText = new Text(parent, SWT.BORDER);
	*/
	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("OK");
	    ok.addSelectionListener(new SelectionListener() {

	      @Override
	      public void widgetSelected(SelectionEvent e) {
	    	  
	    	  try{
	    	
	    	  for(String property: txtMap.keySet())
	    	  {
	    		  for(PropertyDescriptor desc: descs)
	    			  if(desc.getName().equals(property))
							BeanUtils.setProperty(c, desc.getName(), txtMap.get(property).getText());
						
	    	  }
	    	  } catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	  
	      }

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	    });

	}
	public void setPropertyDescriptors(PropertyDescriptor[] descs)
	{
		this.descs=descs;
	}
	public void setClass(Class c)
	{
		this.c=c;
	}
	public void setOutputInstance(Class n)
	{
		this.n=n;
	}
}
