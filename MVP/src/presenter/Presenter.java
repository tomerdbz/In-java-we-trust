package presenter;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

import model.Model;
import view.View;

/**NOTE!!! THIS CLASS IS COMPLETE. IT DOES NEED YOUR TOUCH HOWEVER. SEE UPDATE FOR MORE INFO.
 *  ALSO - ALL THE FUNCTIONS CALLING TO VIEW IN COMMANDS SHOULD BE COORELATED WITH YOURS - PLZ NOTICE.
 * @author Tomer
 *
 */
public class Presenter implements Observer {
	public interface Command {
		/** Inspired from the Observer's update() method - doCommand gets the Maze name and special parameters. 
		 * NOTE: FOR ANY COMMAND OTHER THAN GENERATE MAZE PARAMS WILL NOT BE TAKEN INTO CONSIDERATION.
		 * @param name
		 * @param params
		 */
		void doCommand(String name,String params);
	}
	public class GenerateMazeCommand implements Command {

		/**	NOTE: PARAMETERS SHOULD BE GIVEN IN THE FOLLOWING ORDER: Maze Rows, Maze Cols, Row Source, Col Source, Row Goal, Col Goal!
		 * @param Name of Maze
		 * @param Parameters - given at the format of "x,y,z,w,....."
		 */
		@Override
		public void doCommand(String arg,String params) {
			String[] parameters=params.split(",");
			boolean flag=true;
			if(parameters.length>=6)
			{
				for(String s : parameters)
					flag&=isInteger(s);
				if(flag==true)
					m.generateMaze(arg, Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]), Integer.parseInt(parameters[4]), Integer.parseInt(parameters[5]));
			}
		}
		private boolean isInteger(String s)
		{
			if(s.isEmpty()) return false;
		    for(int i = 0; i < s.length(); i++) {
		        if(i == 0 && s.charAt(i) == '-') {
		            if(s.length() == 1) return false;
		            else continue;
		        }
		        if(Character.digit(s.charAt(i),10) < 0) return false;
		    }
		    return true;
		}
		

	}
	public class DisplayMazeCommand implements Command {

		@Override
		public void doCommand(String arg,String params) {
			
			v.displayMaze(m.getMaze(arg));
		}

	}
	public class SolveMazeCommand implements Command {

		@Override
		public void doCommand(String arg, String params) {
			
			m.solveMaze(arg);
		}

	}
	public class DisplaySolutionCommand implements Command {

		@Override
		public void doCommand(String arg, String params) {
			
			v.displaySolution(m.getSolution(arg));
		}

	}
	public class ExitCommand implements Command {

		@Override
		public void doCommand(String arg, String params) {
			m.stop();
			v.exit();
		}

	}
	
	private View v;
	private Model m;
	
	public Presenter(Model m,View v)
	{
		this.m=m;
		this.v=v;
		ConcurrentHashMap<String, Command> commandMap=new ConcurrentHashMap<String, Command>();
		commandMap.put("generate maze",new GenerateMazeCommand());
		commandMap.put("display maze",new DisplayMazeCommand());
		commandMap.put("solve maze",new SolveMazeCommand());
		commandMap.put("display solution",new DisplaySolutionCommand());
		commandMap.put("exit",new ExitCommand());

		v.setCommands(commandMap);//how to convert ConcurrentHashMap to Hash map???
	}
	
	/** FOR MODEL arg1 IS MAZE NAME. FOR VIEW DEFINE FOR YOURSELF....
	 */
	@Override
	public void update(Observable o, Object arg1) {
		if(o == v)
		{
			String Data =(String)arg1;
			String name = Data.split(" ")[0];
			String Params = null;
			for(int i=1; i<Data.split(" ").length;i++)
			{
				Params = Params +  Data.split(" ")[i];
			}
				
			v.getUserCommand().doCommand(name,Params);
		}
		else if (o==m)
		{
			String name;
			if(arg1!=null)
			{//add here
				if((name=modelNotifiedGenerated(arg1.toString()))!=null)
						v.displayMaze(m.getMaze(name));
				else if((name=modelNotifiedSolved(arg1.toString()))!=null)
					v.displaySolution(m.getSolution(name));
			}
		}
	}
	private String modelNotifiedSolved(String arg)
	{
		String[] args=arg.split(" ");
		if(args.length==3)
			if(args[2].equals("solved"))
				return args[1];
		return null;
	}
	private String modelNotifiedGenerated(String arg)
	{
		String[] args=arg.split(" ");
		if(args.length==3)
			if(args[2].equals("generated"))
				return args[1];
		return null;
	}
	
}
