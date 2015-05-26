package gui;


public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*MazeWindow w=new MazeWindow("gogo",1000,1000);
		w.run();*/
		/*Employee e=new Employee();
		e.setId(1);
		e.setName("bobo");
		BeanPropertiesSetter setter=new BeanPropertiesSetter(e.getClass());
		Employee e1=(Employee) setter.createWindow().cast(Employee.class);
		System.out.println(e1.age);*/
		//Display display=new Display();
		//Shell shell=new Shell(display);
		/*Employee e=new Employee();
		PropertiesDialog<Employee> prop=new PropertiesDialog(shell,e.getClass());
		prop.open();
		prop.
		*/
		/* Display display = new Display();
		    Shell shell = new Shell(display);

		    ClassInputDialog dlg = new ClassInputDialog(shell,Properties.class);
		    Properties input = (Properties) dlg.open();
		    if (input != null) {
		      // User clicked OK; set the text into the label
		      System.out.println(input);
		      System.out.println(input.getMovement());
		      System.out.println(input.getMazeGenerator());
		      System.out.println(input.getMazeSolver());
		    }*/
		new WritePropertiesGUI().writeProperties();

		    /*while (!shell.isDisposed()) {
		      if (!display.readAndDispatch()) {
		        display.sleep();
		      }
		    }
		    display.dispose();*/
		
	}

}
