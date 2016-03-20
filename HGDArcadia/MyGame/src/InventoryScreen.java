import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import arcadia.Button;
import arcadia.Input;

public class InventoryScreen implements Building{
	
	boolean inside = false;
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
	
	public void buildingControls(Input p1) {
		if(p1.pressed(Button.B)){
			inside=false;
		}
		
	}

	@Override
	public void drawBuilding(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.drawImage(blueOre, 100, 100, 113, 72, null);
		g.drawImage(greenOre, 100, 180, 113, 72, null);
		g.drawImage(pinkOre, 100, 260, 113, 72, null);
		
		g.setColor(Color.white);
		g.fillRect(220, 100, 113, 72);
		g.fillRect(220, 180, 113, 72);
		g.fillRect(220, 260, 113, 72);
		
		g.setColor(Color.black);
		g.drawString(MyGame.ship.inventory[2]+"", 260, 140);
		g.drawString(MyGame.ship.inventory[3]+"", 260, 220);
		g.drawString(MyGame.ship.inventory[4]+"", 260, 300);
	}

	@Override
	public boolean isInside() {
		return inside;
	}

	@Override
	public void enter() {
		inside = true;
	}

}
