package boot;

import gui.MazeWindow;
import gui.WritePropertiesGUI;

import java.beans.XMLDecoder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import model.Model;
import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import view.MyView;
import view.View;

public class GuiMvpDemo {

	public void startProgram(Properties properties) {
		MyView v=new MyView(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		MyModel m;
		m=new MyModel(properties);
		Presenter p=new Presenter(m,v);
		v.addObserver(p);
		m.addObserver(p);
		v.cl.addObserver(v);
		v.start();
	}
	}