package gui;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

public interface Character {

	public void drawCharacter();
	public ImageLoader getGifs();
	public void setGifs(ImageLoader gifs);
	public ImageData[] getImages();
	public void setImages(ImageData[] images);
	public int getFrameIndex();
	public void setFrameIndex(int frameIndex);
}
