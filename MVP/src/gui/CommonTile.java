package gui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public abstract class CommonTile extends Canvas implements Tile{
	
	/**
	 * image of the cell (the way the cell looks walls up down right or left 
	 */
	Image cellImage; 
	String imageName;
	/**
	 *  hint represnets a special image that shows the user
	 */
	Image Hint=null; 
	/**
	 * a represenation of character if not null the character is in the cell
	 */
	CommonCharacter ch = null;  
	/**
	 * does the tile represent the goal or not
	 */
	Image goal =null; 
	Direction dir;
	public CommonTile(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void drawTile() {
		this.redraw();
		
	}
	public  void setHint(Image img){
		this.Hint=img;
	}
	public Image getHint(){
		return this.Hint;
	}
	public  void setGoal(Image img){
		this.goal=img;
	}
	public  Image getGoal(){
		return this.goal;
	}
	public void setCellImage(Image img){
		this.cellImage=img;
	}
	public  void setCharacter(CommonCharacter character){
		this.ch=character;
	}
	public  String getImageName()
	{
		return this.imageName;
	}
	public void setImageName(String name)
	{
		this.imageName=name;
	}
	public  Image getCellImage(){
		return this.cellImage;
	}
	public  CommonCharacter getCharacter(){
		return this.ch;
	}
	

}
