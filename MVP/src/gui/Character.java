package gui;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

public interface Character {

	public void drawCharacter();
	public ImageData[] getCharacterImagesArray();
	public void setCharacterImagesArray(ImageData[] images);
	public int getCharacterImageIndex();
	public void setCharacterImageIndex(int frameIndex);
}
