package presenter;

import java.io.Serializable;

import algorithms.search.Movement;

/**
 * @author Tomer
 *
 */
public class Properties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum MazeGenerator{DFS,RANDOM};
	public enum MazeSolver{MANHATTAN_DISTANCE_ASTAR,AIR_DISTANCE_ASTAR,BFS};
	private int threadNumber;
	private MazeSolver mazeSolver;
	private MazeGenerator mazeGenerator;
	private Movement movement;
	private double movementCost;
	private double diagonalMovementCost;
	private int mazeRows;
	private int mazeCols;
	private int rowSource;
	private int colSource;
	private int rowGoal;
	private int colGoal;
	//tomorrow add generated maze rows, cols, source and dest
	
	public Properties()
	{
		this.threadNumber=16;
		this.mazeSolver=MazeSolver.MANHATTAN_DISTANCE_ASTAR;
		this.mazeGenerator=MazeGenerator.DFS;
		this.movement=Movement.DIAGONAL;
		this.movementCost=10;
		this.diagonalMovementCost=15;
		this.mazeRows=10;
		this.mazeCols=10;
		this.rowSource=0;
		this.colSource=0;
		this.rowGoal=9;
		this.colGoal=9;
		//more to come...
	}
	public Properties(MazeSolver mazeSolver,int threadNumber,MazeGenerator mazeGenerator, Movement movement,double mCost,double mdCost
			,int mazeRows,int mazeCols,int rowSource,int colSource,int rowGoal,int colGoal)
	{
		this.threadNumber=threadNumber;
		this.mazeSolver=mazeSolver;
		this.mazeGenerator=mazeGenerator;
		this.movement=movement;
		this.movementCost=mCost;
		this.diagonalMovementCost=mdCost;
		this.mazeRows=mazeRows;
		this.mazeCols=mazeCols;
		this.rowSource=rowSource;
		this.colSource=colSource;
		this.rowGoal=rowGoal;
		this.colGoal=colGoal;
		//more to come...
	}

	public int getThreadNumber() {
		return threadNumber;
	}
	public void setThreadNumber(int threadNumber) {
		this.threadNumber = threadNumber;
	}
	public MazeSolver getMazeSolver() {
		return mazeSolver;
	}
	public void setMazeSolver(MazeSolver mazeSolver) {
		this.mazeSolver = mazeSolver;
	}
	public MazeGenerator getMazeGenerator() {
		return mazeGenerator;
	}
	public void setMazeGenerator(MazeGenerator mazeGenerator) {
		this.mazeGenerator = mazeGenerator;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Movement getMovement() {
		return movement;
	}
	public void setMovement(Movement movement) {
		this.movement = movement;
	}
	public double getMovementCost() {
		return movementCost;
	}
	public void setMovementCost(double movementCost) {
		this.movementCost = movementCost;
	}
	public double getDiagonalMovementCost() {
		return diagonalMovementCost;
	}
	public void setDiagonalMovementCost(double diagonalMovementCost) {
		this.diagonalMovementCost = diagonalMovementCost;
	}
	public int getMazeRows() {
		return mazeRows;
	}
	public void setMazeRows(int mazeRows) {
		this.mazeRows = mazeRows;
	}
	public int getMazeCols() {
		return mazeCols;
	}
	public void setMazeCols(int mazeCols) {
		this.mazeCols = mazeCols;
	}
	public int getRowSource() {
		return rowSource;
	}
	public void setRowSource(int rowSource) {
		this.rowSource = rowSource;
	}
	public int getColSource() {
		return colSource;
	}
	public void setColSource(int colSource) {
		this.colSource = colSource;
	}
	public int getRowGoal() {
		return rowGoal;
	}
	public void setRowGoal(int rowGoal) {
		this.rowGoal = rowGoal;
	}
	public int getColGoal() {
		return colGoal;
	}
	public void setColGoal(int colGoal) {
		this.colGoal = colGoal;
	}
}
