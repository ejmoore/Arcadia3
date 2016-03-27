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
	int money = 0;
	int[] inventory = new int[17];
	int health = 100;
	int maxHealth = 100;
	float healthRatio;
	int fuel = 100;
	int maxFuel = 100;
	float fuelRatio;
	String s;
	String cash;
	int topOre = 4;

	public int maxInventory = 10;
	public static int curInventory = 0;
	int drill = 0;
	
	public Consumable[] consumables = new Consumable[15];
	
	public int maxItemSlots = 1;
	public static int curItemSlots = 0;


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

	public void drawShip(char direction, Graphics2D g, int scale, int xOffset, int yOffset) {
		Image temp = null;
		if (direction == 'd') {
			temp = shipImage;
		} else if (direction == 'u') {
			temp = shipUpImage;
		} else if (direction == 'l') {
			temp = shipLeftImage;
		} else if (direction == 'r') {
			temp = shipRightImage;
		}
		g.drawImage(temp, (WIDTH / 2 - tileSizeW / 2 - 5) - xOffset, (HEIGHT / 2 - tileSizeH) - yOffset,
				scale * tileSizeW + 1, scale * tileSizeH, null);
	}

	public Ship(int width, int height, int tileH, int tileW) {
		WIDTH = width;
		HEIGHT = height;
		tileSizeH = tileH;
		tileSizeW = tileW;
	}

	public void drawInterface(Graphics2D g) {
		fuelRatio = (float) (((float) maxFuel - fuel) / maxFuel);
		healthRatio = (float) (((float) maxHealth - health) / maxHealth);

		g.setColor(Color.DARK_GRAY);
		g.fillRect(WIDTH / 18, HEIGHT - (HEIGHT / 6), WIDTH / 3, HEIGHT / 16);
		g.fillRect(WIDTH / 18, HEIGHT - (HEIGHT / 10), WIDTH / 3, HEIGHT / 16);
		g.setColor(new Color((int) (255 * fuelRatio), (int) (255 * (1 - fuelRatio)), 0));
		g.fillRect(WIDTH / 18, HEIGHT - (HEIGHT / 10), (int) ((WIDTH / 3) * (float) (1 - fuelRatio)), HEIGHT / 16);

		g.setColor(Color.PINK);
		g.fillRect(WIDTH / 18, HEIGHT - (HEIGHT / 6), (int) ((WIDTH / 3) * (float) (1 - healthRatio)), HEIGHT / 16);

		g.setColor(new Color(20, 20, 20, 200));
		g.fillRect(WIDTH - (WIDTH / 8), HEIGHT / 21, WIDTH / 10, HEIGHT / 8);
		// g.fillRect(WIDTH - (WIDTH / 12), HEIGHT / 8 + HEIGHT / 32, WIDTH /
		// 18, HEIGHT / 12);
		// g.fillRect(WIDTH - (WIDTH / 12), HEIGHT / 4 + HEIGHT / 32, WIDTH /
		// 18, HEIGHT / 12);

		Color color1 = new Color(255, 215, 0, 200);
		Color color2 = new Color(200, 200, 200, 200);
		Color color3 = new Color(255, 100, 40, 200);
		for (int i = 16; i > 4; i--) {
			if (inventory[i] != 0) {
				topOre = i;
				break;
			}
		}
		g.setColor(color1);
		g.fillRect(WIDTH - (WIDTH / 9) + 3, (HEIGHT / 17) + 2, WIDTH / 15, HEIGHT / 10);

		// g.setColor(color2);
		// g.fillRect(WIDTH - (WIDTH / (72 / 5)), HEIGHT / 8 + HEIGHT / (64 /
		// 3), WIDTH / 32, HEIGHT / 20);
		//
		// g.setColor(color3);
		// g.fillRect(WIDTH - (WIDTH / (72 / 5)), HEIGHT / 4 + HEIGHT / (64 /
		// 3), WIDTH / 32, HEIGHT / 20);

		cash = Integer.toString(money);
		
		g.setColor(Color.WHITE);
		
		if (money < 10) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 52));
			g.drawString("$" + cash, WIDTH - (WIDTH / 10), HEIGHT / (13) + HEIGHT / 16);
		} else if (money < 100) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
			g.drawString("$" + cash, WIDTH - (WIDTH / 10)-3, HEIGHT / (13) + HEIGHT / 18);
		} else if (money < 1000){
			g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
			g.drawString("$" + cash, WIDTH - (WIDTH / 10)-5, HEIGHT / (13) + HEIGHT / 19);
		} else {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 26));
			g.drawString("$" + cash, WIDTH - (WIDTH / 10)-5, HEIGHT / (13) + HEIGHT / 20);
		}

		// s = Integer.toString(inventory[topOre]);
		// g.drawString(s, WIDTH - (WIDTH / 15)+1, HEIGHT / (21) + HEIGHT / 20);
		// s = Integer.toString(inventory[topOre - 1]);
		// g.drawString(s, WIDTH - (WIDTH / 15)+1, HEIGHT / (21) + HEIGHT / 20 +
		// HEIGHT / 8);
		// s = Integer.toString(inventory[topOre - 2]);
		// g.drawString(s, WIDTH - (WIDTH / 15)+1, HEIGHT / (21) + HEIGHT / 20 +
		// HEIGHT / 4);

		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g.drawString("HEALTH", WIDTH / 17, (HEIGHT - (HEIGHT / 8) + 4));
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g.drawString("FUEL", WIDTH / 17, HEIGHT - (HEIGHT / 18) + 2);
		

	}

	// WIDTH/18 = 1/2 block,

	public void checkInventory() {
		for (int i = 2; i < inventory.length; i++) {
			if (inventory[i] != 0)
				System.out.println("You have this much of type " + i + ": " + inventory[i]);
		}
	}
}
