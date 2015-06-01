package presenter;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

import model.Model;
import view.View;

/*
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
			else
				v.Display("An Error Has Occured.");
		}
		/**
		 * @param s - String that will be checked if represents a number
		 * @return
		 */
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
	public class MazeExistsCommand implements Command {

		@Override
		public void doCommand(String arg, String params) {
			if(m.getMaze(arg)==null)
				v.receiveData(null);
			else
				v.receiveData("1");
		}

	}
	public class WritePropertiesCommand implements Command {

		@Override
		public void doCommand(String arg, String params) {
			m.setProperties(v.getProperties());
		}

	}
	
	public class ExitCommand implements Command {

		@Override
		public void doCommand(String arg, String params) {
			m.stop();
		}

	}
	
	/** MVP View 
	 * 
	 */
	private View v;
	/**MVP Model
	 * 
	 */
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
		commandMap.put("properties", new WritePropertiesCommand());
		commandMap.put("maze exists", new MazeExistsCommand());
		v.setCommands(commandMap);
		}
	
	/** FOR MODEL arg1 IS MAZE NAME. FOR VIEW arg1 is COMMAND ARGUMENTS.
	 * 	NOTICE: IF ARG1 = "error" Presenter evaluates and knows there was something wrong. model and view doesn't think for themselves. 
	 */
	@Override
	public void update(Observable o, Object arg1) {
		if(o == v)
		{
			String name=null;
			String Params=null;
			if(arg1!=null && !arg1.toString().equals("error")) //by making sure the arg1 is not a reprentation of error we avoid errors
			{
				String data =(String)arg1; //we convert the data which is the arguments transfered to us to a String
				data=data.substring(1); //we remove the first char which is a space
				name = data.split(" ")[0]; //we split the array by spaces the first place is not the name
				Params = "";
				for(int i=1; i<data.split(" ").length;i++)
				{
					Params = Params +  data.split(" ")[i]; //we combine all the other arguments
				} 
			}	
			if(v.getUserCommand()!=null) //we make sure that the command is valid and not null
			v.getUserCommand().doCommand(name,Params); //we tell the presenter to handle the command now
			else
				v.Display("An Error Has Occurred."); //error managment
		}
		else if (o==m)
		{
			String name;
			if(arg1!=null && !arg1.toString().equals("error"))
			{
				if((name=modelNotifiedGenerated(arg1.toString()))!=null)
						v.displayMaze(m.getMaze(name));
				else if((name=modelNotifiedSolved(arg1.toString()))!=null)
					v.displaySolution(m.getSolution(name));
			}
			else
				if(arg1!=null)
					v.Display("Error: " + arg1.toString());
				else
					v.Display("An Error Has Occured.");
		}
	}
	/** Helper for update - checks if Model notified solve
	 * @param arg from the model
	 * @return name of maze to solve if it notified solve, else null
	 */
	private String modelNotifiedSolved(String arg)
	{
		String[] args=arg.split(" ");
		if(args.length==3)
			if(args[2].equals("solved"))
				return args[1];
		return null;
	}
	/** Helper for update - checks if Model notified generated
	 * @param arg from the model
	 * @return name of maze to generate if it notified generated, else null
	 */
	private String modelNotifiedGenerated(String arg)
	{
		String[] args=arg.split(" ");
		if(args.length==3)
			if(args[2].equals("generated"))
				return args[1];
		return null;
	}
	
}
