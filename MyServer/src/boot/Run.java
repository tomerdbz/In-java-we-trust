package boot;

import presenter.Presenter;
import model.MyModel;
import view.ServerWindow;

public class Run {

	public static void main(String[] args) {
		ServerWindow SE= new ServerWindow("StarShip phoenix",500,500);
		MyModel m = new MyModel();
		Presenter p = new Presenter(m, SE);
		m.addObserver(p);
		SE.addObserver(p);
		SE.run();

	}
}
