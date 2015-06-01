package boot;

import gui.MazeWindow;
import gui.WritePropertiesGUI;

import java.beans.XMLDecoder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import view.MyView;

/** ID'S: 208415513, 318507209 - Tomer Cabouly, Alon Orlovsky 
 * Run WriteProperties and ConfigureDB FIRST!!!
 * @author Tomer
 *
 */
public class Run {
	public static void main(String []args)
	{
		//MazeWindow v=new MazeWindow("Check the Sonic Maze Runner Edition", 600, 600);
				WritePropertiesGUI guiProp=new WritePropertiesGUI();
				Display display=new Display();
				Shell shell=new Shell(display);
				guiProp.writeProperties(display,shell);
				MyModel m;
				Properties prop;
				if((prop=readProperties())!=null)
				{
					m=new MyModel(prop);
					
					switch(prop.getUi())
					{
						case CLI:
							MyView v=new MyView(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
							Presenter p=new Presenter(m,v);
							v.addObserver(p);
							m.addObserver(p);
							v.start();
							break;
						case GUI:
							MazeWindow vMaze=new MazeWindow("Maze Generations", 600, 600);
							Presenter pMaze=new Presenter(m,vMaze);
							vMaze.addObserver(pMaze);
							m.addObserver(pMaze);
							vMaze.start();
							break;
						default:
							return;	
					}
				}
				else
					return;
			

		}
			public static Properties readProperties()
			{
				XMLDecoder d;
				Properties p=null;
				try {
					FileInputStream in=new FileInputStream("properties.xml");
					d=new XMLDecoder(in);
					p=(Properties)d.readObject();
					System.out.println(p);
					d.close();
				} catch (IOException e) {
					return new Properties();
				}
				return p;
			}
		}
