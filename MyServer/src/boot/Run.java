package boot;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import presenter.Presenter;
import presenter.ServerProperties;
import model.MyModel;
import view.ServerWindow;
import view.WriteServerPropertiesGUI;

public class Run {

	public static void main(String[] args) {
		WriteServerPropertiesGUI sp = new WriteServerPropertiesGUI();
		Display display= new Display();
		Shell shell=new Shell(display);
		sp.writeProperties(display, shell);
		ServerWindow SE= new ServerWindow("StarShip phoenix",500,500,display,shell);
		MyModel m = new MyModel(ServerWindow.readProperties());
		Presenter p = new Presenter(m, SE);
		m.addObserver(p);
		SE.addObserver(p);
		SE.run();

	}
}
