import java.awt.Color;
import java.awt.Graphics2D;

public class Tile {
	
	int tileType;
	int row;
	int col;
	int tileSizeW;
	int tileSizeH;
	
	public Tile(int type, int col, int row, int tileSizeW, int tileSizeH) {
		tileType = type;
		this.row = row;
		this.col = col;
		this.tileSizeW = tileSizeW;
		this.tileSizeH = tileSizeH;
	}
	
	public void drawTile(Graphics2D g) {
		if (tileType == 0) {
			g.setColor(Color.WHITE);
		} else if(tileType == 1 ){
			g.setColor(Color.GREEN);
		} else if(tileType == 2){
			g.setColor(Color.BLUE);
		}
		
		
		g.fillRect(col * tileSizeW,row * tileSizeH, tileSizeW, tileSizeH);
	}
}
