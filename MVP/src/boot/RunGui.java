package boot;

import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import gui.MazeWindow;

public class RunGui {
	public void start(Properties properties){
	MazeWindow v=new MazeWindow("Maze Generations", 600, 600);
	MyModel m;
	m=new MyModel(properties);
	Presenter p=new Presenter(m,v);
	v.addObserver(p);
	m.addObserver(p);
	v.start();
	}
}
