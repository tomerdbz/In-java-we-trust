package boot;

import gui.MazeWindow;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import view.MyView;

public class GuiMvpDemo {

	public static void main(String[] args) {
		MazeWindow v=new MazeWindow("Check the Sonic Maze Runner Edition", 600, 600);
		MyModel m;
		Properties prop;
		if((prop=readProperties())!=null)
			m=new MyModel(prop);
		else
			m=new MyModel(new Properties());
		Presenter p=new Presenter(m,v);
		v.addObserver(p);
		m.addObserver(p);
		v.start();

}
	private static Properties readProperties()
	{
		XMLDecoder d;
		Properties p=null;
		try {
			FileInputStream in=new FileInputStream("properties.xml");
			d=new XMLDecoder(in);
			p=(Properties)d.readObject();
			d.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
}
