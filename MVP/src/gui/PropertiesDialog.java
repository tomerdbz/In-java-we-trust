package gui;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class PropertiesDialog<T> extends TitleAreaDialog {
	
	private T template;
	PropertyDescriptor[] descs;
  private Text txtFirstName;
  private Text lastNameText;
  private String firstName;
  private String lastName;
  HashMap<PropertyDescriptor,Text> txtMap=new HashMap<PropertyDescriptor,Text>();

  public PropertiesDialog(Shell parentShell, T template) {
    super(parentShell);
    this.template=template;
	descs=PropertyUtils.getPropertyDescriptors(template);
  }



@Override
  public void create() {
    super.create();
    setTitle("This is my first custom dialog");
    setMessage("This is a TitleAreaDialog", IMessageProvider.INFORMATION);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Composite area = (Composite) super.createDialogArea(parent);
    Composite container = new Composite(area, SWT.NONE);
    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    GridLayout layout = new GridLayout(2, false);
    container.setLayout(layout);

    //createFirstName(container);
    //createLastName(container);
    for(PropertyDescriptor propDesc: descs)
    	if(!propDesc.getName().equals("class"))
    		createDataField(container,propDesc);
    return area;
  }
  private void createDataField(Composite container, PropertyDescriptor propDesc)
  {
	  Label lbt = new Label(container, SWT.NONE);
	    lbt.setText(propDesc.getName());//"First Name");
	    GridData dataLabel = new GridData();
	    dataLabel.grabExcessHorizontalSpace = true;
	    dataLabel.horizontalAlignment = GridData.FILL;
	    Text txt = new Text(container, SWT.BORDER);
	    txt.setLayoutData(dataLabel);
	    txtMap.put(propDesc,txt);
  }
  private void createFirstName(Composite container) {
    Label lbtFirstName = new Label(container, SWT.NONE);
    lbtFirstName.setText("First Name");

    GridData dataFirstName = new GridData();
    dataFirstName.grabExcessHorizontalSpace = true;
    dataFirstName.horizontalAlignment = GridData.FILL;

    txtFirstName = new Text(container, SWT.BORDER);
    txtFirstName.setLayoutData(dataFirstName);
  }
  
  private void createLastName(Composite container) {
    Label lbtLastName = new Label(container, SWT.NONE);
    lbtLastName.setText("Last Name");
    
    GridData dataLastName = new GridData();
    dataLastName.grabExcessHorizontalSpace = true;
    dataLastName.horizontalAlignment = GridData.FILL;
    lastNameText = new Text(container, SWT.BORDER);
    lastNameText.setLayoutData(dataLastName);
  }



  @Override
  protected boolean isResizable() {
    return true;
  }

  // save content of the Text fields because they get disposed
  // as soon as the Dialog closes
  private void saveInput() {
    firstName = txtFirstName.getText();
    lastName = lastNameText.getText();

  }

  @Override
  protected void okPressed() {
	for(PropertyDescriptor propDesc: txtMap.keySet())
	{
		try {
			//propDesc.getWriteMethod().invoke(template,txtMap.get(propDesc).getText());
			propDesc.setValue(propDesc.getName(), txtMap.get(propDesc).getText());
			System.out.println(propDesc.getValue(propDesc.getName())); //propDesc wasn't saved. check tomorrow what to do!
			//} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
	}
    //saveInput();
	System.out.println(template.toString());
    super.okPressed();
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
} 
