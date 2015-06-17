package gui;

public class MazeProperties {
	private String MazeName;
	private int Rows;
	private int Cols;
	private int rowSource;
	private int colSource;
	private int rowGoal;
	private int colGoal;
	public String getMazeName() {
		return MazeName;
	}
	public void setMazeName(String mazeName) {
		this.MazeName = mazeName;
	}
	public int getRows() {
		return Rows;
	}
	public void setRows(int rows) {
		this.Rows = rows;
	}
	public int getCols() {
		return Cols;
	}
	public void setCols(int cols) {
		this.Cols = cols;
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
