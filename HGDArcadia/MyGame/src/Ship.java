import java.awt.Color;
import java.awt.Graphics2D;

public class Ship {
	int shipX = 5;
	int shipY = 5;
	int WIDTH;
	int HEIGHT;
	int tileSizeH;
	int tileSizeW;
	int[] inventory = new int[17];
	int fuel = 100;
	int maxFuel = 100;
	
	
	public Ship(int width, int height, int tileH, int tileW){
		WIDTH = width;
		HEIGHT = height;
		tileSizeH = tileH;
		tileSizeW = tileW;
	}
	
	void drawShip (Graphics2D g) {
		g.setColor(Color.MAGENTA);
		// drawImage(g, ship, WIDTH / 2, HEIGHT / 2, 0);
		g.fillOval(WIDTH / 2 - tileSizeW/2 - 5, HEIGHT / 2 - tileSizeH, tileSizeW+1, tileSizeH);
	}
	
	public void checkInventory() {
		for (int i = 2; i < inventory.length; i++) {
			if (inventory[i] != 0) System.out.println("You have this much of type " + i + ": " + inventory[i]);
		}
	}
}
