package boot;

import model.MyModel;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import presenter.Presenter;
import presenter.ServerProperties;
import view.ServerWindow;
import view.WriteServerPropertiesGUI;

public class RunGui {

	public void  loadWindow(ServerProperties sp){
		Display display= new Display();
		Shell shell=new Shell(display);
		ServerWindow SE= new ServerWindow("StarShip phoenix",500,500,display,shell);
		MyModel m = new MyModel(sp);
		Presenter p = new Presenter(m, SE);
		m.addObserver(p);
		SE.addObserver(p);
		SE.run();
	}
}
