package presenter;

import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

public class Presenter implements Observer {
	public interface Command {
		void doCommand(String arg);
	}
	public class TestMVPCommand implements Command {

		@Override
		public void doCommand(String arg) {
			System.out.println("doing MVP TEST COMMAND");
			m.generateMaze(0, 0, 0, 0, 4, 4);
		}

	}
	
	private View v;
	private Model m;
	
	public Presenter(Model m,View v)
	{
		this.m=m;
		this.v=v;
		v.setCommands(new TestMVPCommand());
	}
	
	@Override
	public void update(Observable o, Object arg1) {
		if(o == v)
		{
			v.getUserCommand().doCommand(""); //PLEASE LOOK AT THIS THE ARGUMENTS HAVE CHANGED
		}
		else if (o==m)
		{
			v.displayMaze(m.getMaze());
		}
	}
}
