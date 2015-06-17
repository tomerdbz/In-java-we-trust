package gui;

public class MazeProperties {
	private String mazeName;
	private int rows;
	private int cols;
	private int rowSource;
	private int colSource;
	private int rowGoal;
	private int colGoal;
	public String getMazeName() {
		return mazeName;
	}
	public void setMazeName(String mazeName) {
		this.mazeName = mazeName;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
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
