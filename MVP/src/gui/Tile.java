package gui;
/*these functions should be general to all kind of tiles - from 2048 to chess to whatever*/
public interface Tile {
	void drawTile(/*parameters: Character? Note that in the defense he could ask us to do chess tiles, so maybe character is good...think of more parameters if any*/);
	/*drag does not seem to have any connection to a tile. more for the character. think about it*/
}
