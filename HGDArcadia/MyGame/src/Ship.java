import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ship {
	int shipX = 5;
	int shipY = 5;
	int WIDTH;
	int HEIGHT;
	int tileSizeH;
	int tileSizeW;
	int money;
	int[] inventory = new int[17];
	int fuel = 1000;
	int maxFuel = 1000;
	float fuelRatio;
	String s;
	int topOre = 4;
	int maxInventory = 10;
	public static int curInventory = 0;
	
	static Image shipImage;
	static Image shipUpImage;
	static Image shipLeftImage;
	static Image shipRightImage;
	
	static {
		try {
		shipImage = ImageIO.read(MyGame.class.getResource("Drill.png"));
		shipUpImage = ImageIO.read(MyGame.class.getResource("UpDrill.png"));
		shipLeftImage = ImageIO.read(MyGame.class.getResource("LeftDrill.png"));
		shipRightImage = ImageIO.read(MyGame.class.getResource("RightDrill.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void drawShip(char direction, Graphics2D g) {
		Image temp = null;
		if (direction == 'd') {
			temp = shipImage;
		}
		else if (direction == 'u') {
			temp = shipUpImage;
		}
		else if (direction == 'l') {
			temp = shipLeftImage;
		}
		else if (direction == 'r') {
			temp = shipRightImage;
		}
		g.drawImage(temp, WIDTH / 2 - tileSizeW / 2 - 5, HEIGHT / 2 - tileSizeH, tileSizeW + 1, tileSizeH, null);
	}

	public Ship(int width, int height, int tileH, int tileW) {
		WIDTH = width;
		HEIGHT = height;
		tileSizeH = tileH;
		tileSizeW = tileW;
	}
	
	public void drawInterface (Graphics2D g) {
		fuelRatio = (float)(((float)maxFuel-fuel)/maxFuel);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(WIDTH / 18, HEIGHT - (HEIGHT / 9), WIDTH / 3, HEIGHT / 16);
		g.setColor(new Color((int) (255 * fuelRatio), (int) (255 * (1 - fuelRatio)), 0));
		g.fillRect(WIDTH / 18, HEIGHT - (HEIGHT / 9), (int) ((WIDTH / 3) * (float) (1 - fuelRatio)), HEIGHT / 16);

		g.setColor(new Color(20, 20, 20, 200));
		g.fillRect(WIDTH - (WIDTH / 12), HEIGHT / 32, WIDTH / 18, HEIGHT / 12);
		g.fillRect(WIDTH - (WIDTH / 12), HEIGHT / 8 + HEIGHT / 32, WIDTH / 18, HEIGHT / 12);
		g.fillRect(WIDTH - (WIDTH / 12), HEIGHT / 4 + HEIGHT / 32, WIDTH / 18, HEIGHT / 12);
		
		Color color1 = new Color(255, 215, 0, 200);
		Color color2 = new Color(200, 200, 200, 200);
		Color color3 = new Color(255, 100, 40, 200);
		for ( int i = 16; i > 4; i--){
			if (inventory[i] != 0){
				topOre = i;
				break;
			}
		}
		g.setColor(color1);
		g.fillRect(WIDTH - (WIDTH / (72 / 5)), HEIGHT / (64 / 3), WIDTH / 32, HEIGHT / 20);
		
		g.setColor(color2);
		g.fillRect(WIDTH - (WIDTH / (72 / 5)), HEIGHT / 8 + HEIGHT / (64 / 3), WIDTH / 32, HEIGHT / 20);

		g.setColor(color3);
		g.fillRect(WIDTH - (WIDTH / (72 / 5)), HEIGHT / 4 + HEIGHT / (64 / 3), WIDTH / 32, HEIGHT / 20);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		s = Integer.toString(inventory[topOre]);
		g.drawString(s, WIDTH - (WIDTH / (72 / 5)), HEIGHT / (64 / 3) + HEIGHT / 20);
		s = Integer.toString(inventory[topOre-1]);
		g.drawString(s, WIDTH - (WIDTH / (72 / 5)), HEIGHT / (64 / 3) + HEIGHT / 20 + HEIGHT/8);
		s = Integer.toString(inventory[topOre -2]);
		g.drawString(s, WIDTH - (WIDTH / (72 / 5)), HEIGHT / (64 / 3) + HEIGHT / 20 + HEIGHT/4);
	}

	// WIDTH/18 = 1/2 block,

	public void checkInventory() {
		for (int i = 2; i < inventory.length; i++) {
			if (inventory[i] != 0)
				System.out.println("You have this much of type " + i + ": " + inventory[i]);
		}
	}
}
