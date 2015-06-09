package gui;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
/**
 * 
 * @author Alon
 * this is a widget representing the character in the maze
 * it extends canvas
 */
public class MazeCharacter extends CommonCharacter {
	

   /**
    * constructor
    * 
    * 
    */
	public MazeCharacter(Composite parent, int style) {
		super(parent, style);
		images=gifs.load(".\\resources\\images\\UpAndDown.gif"); 
		
	}
}
