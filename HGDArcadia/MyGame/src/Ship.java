import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Ship {
	int shipX = 5;
	int shipY = 5;
	int WIDTH;
	int HEIGHT;
	int tileSizeH;
	int tileSizeW;
	int money;
	int[] inventory = new int[17];
	int fuel = 100;
	int maxFuel = 100;
	float fuelRatio;
	String s;

	public Ship(int width, int height, int tileH, int tileW) {
		WIDTH = width;
		HEIGHT = height;
		tileSizeH = tileH;
		tileSizeW = tileW;
	}

	public void drawInterface(Graphics2D g) {
		fuelRatio = (float) (((float) maxFuel - fuel) / maxFuel);
		// System.out.println(fuel);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(WIDTH / 18, HEIGHT - (HEIGHT / 9), WIDTH / 3, HEIGHT / 16);
		g.setColor(new Color((int) (255 * fuelRatio), (int) (255 * (1 - fuelRatio)), 0));
		g.fillRect(WIDTH / 18, HEIGHT - (HEIGHT / 9), (int) ((WIDTH / 3) * (float) (1 - fuelRatio)), HEIGHT / 16);

		g.setColor(new Color(20, 20, 20, 200));
		g.fillRect(WIDTH - (WIDTH / 12), HEIGHT / 32, WIDTH / 18, HEIGHT / 12);
		g.fillRect(WIDTH - (WIDTH / 12), HEIGHT / 8 + HEIGHT / 32, WIDTH / 18, HEIGHT / 12);
		g.fillRect(WIDTH - (WIDTH / 12), HEIGHT / 4 + HEIGHT / 32, WIDTH / 18, HEIGHT / 12);
		
		g.setColor(new Color(220, 0, 220, 200));
		g.fillRect(WIDTH - (WIDTH / (72 / 5)), HEIGHT / (64 / 3), WIDTH / 32, HEIGHT / 20);
		
		g.setColor(new Color(255, 0, 0, 200));
		g.fillRect(WIDTH - (WIDTH / (72 / 5)), HEIGHT / 8 + HEIGHT / (64 / 3), WIDTH / 32, HEIGHT / 20);

		g.setColor(new Color(0, 0, 255, 200));
		g.fillRect(WIDTH - (WIDTH / (72 / 5)), HEIGHT/4 + HEIGHT / (64 / 3), WIDTH / 32, HEIGHT / 20);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		s = Integer.toString(inventory[4]);
		g.drawString(s, WIDTH - (WIDTH / (72 / 5)) + WIDTH / 145, HEIGHT / (64 / 3) + HEIGHT / 20);
		s = Integer.toString(inventory[3]);
		g.drawString(s, WIDTH - (WIDTH / (72 / 5)) + WIDTH / 145, HEIGHT / (64 / 3) + HEIGHT / 20 + HEIGHT/8);
		s = Integer.toString(inventory[2]);
		g.drawString(s, WIDTH - (WIDTH / (72 / 5)) + WIDTH / 145, HEIGHT / (64 / 3) + HEIGHT / 20 + HEIGHT/4);
	}

	// WIDTH/18 = 1/2 block,

	public void drawShip(Graphics2D g) {
		g.setColor(Color.MAGENTA);
		// drawImage(g, ship, WIDTH / 2, HEIGHT / 2, 0);
		g.fillOval(WIDTH / 2 - tileSizeW / 2 - 5, HEIGHT / 2 - tileSizeH, tileSizeW + 1, tileSizeH);
	}

	public void checkInventory() {
		for (int i = 2; i < inventory.length; i++) {
			if (inventory[i] != 0)
				System.out.println("You have this much of type " + i + ": " + inventory[i]);
		}
	}
}
