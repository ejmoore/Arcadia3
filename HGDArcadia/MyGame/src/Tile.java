import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {

	int tileType;
	int row;
	int col;
	int tileSizeW;
	int tileSizeH;
	static Image store;
	static Image saveLocation;
	static Image craftingBuilding;
	static Image dirt;
	static Image blueOre;
	static Image greenOre;
	static Image pinkOre;
	static Image dirtBackground;
	static Image skyBackground;

	static {
		try {
			dirt = ImageIO.read(MyGame.class.getResource("dirt.png"));
			blueOre = ImageIO.read(MyGame.class.getResource("BlueOre.png"));
			greenOre = ImageIO.read(MyGame.class.getResource("GreenOre.png"));
			pinkOre = ImageIO.read(MyGame.class.getResource("PinkOre.png"));
			store = ImageIO.read(MyGame.class.getResource("Store.png"));
			saveLocation = ImageIO.read(MyGame.class.getResource("SaveLocation.png"));
			dirtBackground = ImageIO.read(MyGame.class.getResource("DirtBackground.png"));
			skyBackground = ImageIO.read(MyGame.class.getResource("SkyBackground.png"));
			craftingBuilding = ImageIO.read(MyGame.class.getResource("CraftingBuilding.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
		} else if (tileType == 1) {
			g.setColor(Color.BLACK);
		} else if (tileType == 2) {
			g.setColor(Color.BLUE);
		} else if (tileType == 3) {
			g.setColor(Color.RED);
		} else if (tileType == 4) {
			g.setColor(Color.MAGENTA);
		} else if (tileType == 5) {
			g.setColor(Color.CYAN);
		} else if (tileType == 6) {
			g.setColor(Color.DARK_GRAY);
		} else if (tileType == 8) {
			g.setColor(Color.GRAY);
		} else if (tileType == 9) {
			g.setColor(Color.LIGHT_GRAY);
		} else if (tileType == 10) {
			g.setColor(Color.ORANGE);
		} else if (tileType == 11) {
			g.setColor(Color.PINK);
		} else if (tileType == 7) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.WHITE);
		}
		g.fillRect((int) (x * tileSizeW), (int) (y * tileSizeH), tileSizeW,
				tileSizeH+4);
		if (tileType == 1 || tileType == 98) {
			g.drawImage(dirt, (int) (x * tileSizeW), (int) (y * tileSizeH), 113, 72, null);
		} else if (tileType == 2) {
			g.drawImage(blueOre, (int) (x * tileSizeW), (int) (y * tileSizeH), 113, 72, null);
		} else if (tileType == 3) {
			g.drawImage(greenOre, (int) (x * tileSizeW), (int) (y * tileSizeH), 113, 72, null);
		} else if (tileType == 4) {
			g.drawImage(pinkOre, (int) (x * tileSizeW), (int) (y * tileSizeH), 113, 72, null);
		} else if (tileType == 99) {
			g.drawImage(store, (int) (x * tileSizeW), (int) (y * tileSizeH), 113, 72, null);
		} else if (tileType == 97) {
			g.drawImage(skyBackground, (int) (x * tileSizeW), (int) (y * tileSizeH), 113, 72, null);
			g.drawImage(saveLocation, (int) (x * tileSizeW), (int) (y * tileSizeH), 113, 72, null);
		} else if (tileType == 96) {
			g.drawImage(skyBackground, (int) (x * tileSizeW), (int) (y * tileSizeH), 113, 72, null);
			g.drawImage(craftingBuilding, (int) (x * tileSizeW), (int) (y * tileSizeH), 113, 72, null);
		} else if (tileType == 0) {
			if (col < 18) {
				g.drawImage(skyBackground, (int) (x * tileSizeW), (int) (y * tileSizeH), 113, 72, null);
			} else {
				g.drawImage(dirtBackground, (int) (x * tileSizeW), (int) (y * tileSizeH), 113, 72, null);
			}
		}
	}
}
