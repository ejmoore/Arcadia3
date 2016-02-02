import java.awt.Color;
import java.awt.Graphics2D;

public class Tile {
	
	int tileType;
	int row;
	int col;
	int tileSizeW;
	int tileSizeH;
	
	public Tile(int type, int row, int col, int tileSizeW, int tileSizeH) {
		tileType = type;
		this.row = row;
		this.col = col;
		this.tileSizeW = tileSizeW;
		this.tileSizeH = tileSizeH;
	}
	
	public void drawTile(Graphics2D g, float x, float y) {
		if (tileType == 0) {
			g.setColor(Color.WHITE);
		} else if(tileType == 1 ){
			g.setColor(Color.GREEN);
		} else if(tileType == 2){
			g.setColor(Color.BLUE);
		} else if(tileType == 3) {
			g.setColor(Color.RED);
		} else if(tileType == 4) {
			g.setColor(Color.MAGENTA);
		} else if(tileType == 5) {
			g.setColor(Color.CYAN);
		} else if(tileType == 6) {
			g.setColor(Color.DARK_GRAY);
		} else if(tileType == 8) {
			g.setColor(Color.GRAY);
		} else if(tileType == 9) {
			g.setColor(Color.LIGHT_GRAY);
		} else if(tileType == 10) {
			g.setColor(Color.ORANGE);
		} else if(tileType == 11) {
			g.setColor(Color.PINK);
		} else if(tileType == 7){
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.YELLOW);
		}
		g.fillRect((int)(x * tileSizeW),(int)(y * tileSizeH), tileSizeW, tileSizeH);
	}
}
