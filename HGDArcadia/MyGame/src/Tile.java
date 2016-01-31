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
	
	public void drawTile(Graphics2D g, int x, int y) {
		if (tileType == 0) {
			g.setColor(Color.WHITE);
		} else if(tileType == 1 ){
			g.setColor(Color.GREEN);
		} else if(tileType == 2){
			g.setColor(Color.BLUE);
		} else if(tileType == 7){
			g.setColor(Color.BLACK);
		}
		g.fillRect(x * tileSizeW,y * tileSizeH, tileSizeW, tileSizeH);
	}
}
