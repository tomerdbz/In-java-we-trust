package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import presenter.ClientProperties;
import view.cli.MyView;

/** This class is used when the client decides to change in the middle of the program to CLI interface.
 * @author Tomer,Alon
 *
 */
public class RunCLI {

	public void startProgram(ClientProperties properties) {
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