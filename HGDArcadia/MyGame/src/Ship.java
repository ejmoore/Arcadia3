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

	int money = 300;
	int[] inventory = new int[17];
	int[] deathInventory = new int[17];

	double health = 100;
	double maxHealth = 100;
	float healthRatio;
	double fuel = 100;
	double maxFuel = 100;
	double fuelCost = 1;

	float fuelRatio;
	String s;
	String cash;
	int topOre = 4;
	int maxDepth = 1250;
	int consumable = 0;

	public int maxInventory = 10;
	public static int curInventory = 0;
	int drill = 0;

	public Consumable[] consumables = new Consumable[15];

	public int maxItemSlots = 1;

	static Image shipImage;
	static Image shipUpImage;
	static Image shipLeftImage;
	static Image shipRightImage;


	static Image[] drillStrength = new Image[8];
	static Image[] drillStrengthUp = new Image[8];
	static Image[] drillStrengthLeft = new Image[8];
	static Image[] drillStrengthRight = new Image[8];
	int drillStrengthIndex = -1;
	static Image[] drillSpeed = new Image[8];
	static Image[] drillSpeedUp = new Image[8];
	static Image[] drillSpeedLeft = new Image[8];
	static Image[] drillSpeedRight = new Image[8];
	int drillSpeedIndex = -1;
	

	static Image[] fuelCapacity = new Image[8];
	static Image[] fuelCapacityUp = new Image[8];
	int fuelCapacityIndex = -1;
	static Image[] fuelEfficiency = new Image[8];
	static Image[] fuelEfficiencyUp = new Image[8];
	int fuelEfficiencyIndex = -1;

	static Image NetItem;
	static Image FuelItem;
	static Image RepairItem;
	Image item = null;

	static {
		try {
			shipImage = ImageIO.read(MyGame.class.getResource("images/Drill.png"));
			shipUpImage = ImageIO.read(MyGame.class.getResource("images/UpDrill.png"));
			shipLeftImage = ImageIO.read(MyGame.class.getResource("images/LeftDrill.png"));
			shipRightImage = ImageIO.read(MyGame.class.getResource("images/RightDrill.png"));
			NetItem = ImageIO.read(MyGame.class.getResource("images/NetItem.png"));
			FuelItem = ImageIO.read(MyGame.class.getResource("images/FuelItem.png"));
			RepairItem = ImageIO.read(MyGame.class.getResource("images/RepairItem.png"));

			for (int i = 1; i <= 8; i++) {
				String strength = "images/DrillStrengthUpgrade" + i + ".png";
				String strengthUp = "images/DrillStrengthUpgradeUp" + i + ".png";
				String strengthLeft = "images/DrillStrengthUpgradeLeft" + i + ".png";
				String strengthRight = "images/DrillStrengthUpgradeRight" + i + ".png";
				drillStrength[i - 1] = ImageIO.read(MyGame.class.getResource(strength));
				drillStrengthUp[i - 1] = ImageIO.read(MyGame.class.getResource(strengthUp));
				drillStrengthLeft[i - 1] = ImageIO.read(MyGame.class.getResource(strengthLeft));
				drillStrengthRight[i - 1] = ImageIO.read(MyGame.class.getResource(strengthRight));
				String speed = "images/DrillSpeedUpgrade" + i + ".png";
				String speedUp = "images/DrillSpeedUpgradeUp" + i + ".png";
				String speedLeft = "images/DrillSpeedUpgradeLeft" + i + ".png";
				String speedRight = "images/DrillSpeedUpgradeRight" + i + ".png";
				drillSpeed[i - 1] = ImageIO.read(MyGame.class.getResource(speed));
				drillSpeedUp[i - 1] = ImageIO.read(MyGame.class.getResource(speedUp));
				drillSpeedLeft[i - 1] = ImageIO.read(MyGame.class.getResource(speedLeft));
				drillSpeedRight[i - 1] = ImageIO.read(MyGame.class.getResource(speedRight));
				String cap = "images/FuelCapacityUpgrade" + i + ".png";
				String capUp = "images/FuelCapacityUpgradeUp" + i + ".png";
				fuelCapacity[i - 1] = ImageIO.read(MyGame.class.getResource(cap));
				fuelCapacityUp[i - 1] = ImageIO.read(MyGame.class.getResource(capUp));
				String eff = "images/FuelEfficiencyUpgrade" + i + ".png";
				String effUp = "images/FuelEfficiencyUpgradeUp" + i + ".png";
				fuelEfficiency[i - 1] = ImageIO.read(MyGame.class.getResource(eff));
				fuelEfficiencyUp[i - 1] = ImageIO.read(MyGame.class.getResource(effUp));
			}
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
		if (direction == 'd') {
			if (drillStrengthIndex > -1) {
				g.drawImage(drillStrength[drillStrengthIndex], (WIDTH / 2 - tileSizeW / 2 - 5) - xOffset + 31,
						(HEIGHT / 2 - tileSizeH) - yOffset + 38, scale * 50 + 1, scale * 50, null);
			}
			if (drillSpeedIndex > -1) {
				g.drawImage(drillSpeed[drillSpeedIndex], (WIDTH / 2 - tileSizeW / 2 - 5) - xOffset + 31,
						(HEIGHT / 2 - tileSizeH) - yOffset + 38, scale * 50 + 1, scale * 50, null);
			}
		} else if (direction == 'u') {
			if (drillStrengthIndex > -1) {
				g.drawImage(drillStrengthUp[drillStrengthIndex], (WIDTH / 2 - tileSizeW / 2 - 5) - xOffset + 31,
						(HEIGHT / 2 - tileSizeH) - yOffset - 16, scale * 50 + 1, scale * 50, null);
			}
			if (drillSpeedIndex > -1) {
				g.drawImage(drillSpeedUp[drillSpeedIndex], (WIDTH / 2 - tileSizeW / 2 - 5) - xOffset + 31,
						(HEIGHT / 2 - tileSizeH) - yOffset - 16, scale * 50 + 1, scale * 50, null);
			}
		} else if (direction == 'l') {
			if (drillStrengthIndex > -1) {
				g.drawImage(drillStrengthLeft[drillStrengthIndex], (WIDTH / 2 - tileSizeW / 2 - 5) - xOffset - 1,
						(HEIGHT / 2 - tileSizeH) - yOffset + 4, scale * 28 + 1, scale * 62, null);
			}
			if (drillSpeedIndex > -1) {
				g.drawImage(drillSpeedLeft[drillSpeedIndex], (WIDTH / 2 - tileSizeW / 2 - 5) - xOffset - 1,
						(HEIGHT / 2 - tileSizeH) - yOffset + 4, scale * 28 + 1, scale * 62, null);
			}
		} else if (direction == 'r') {
			if (drillStrengthIndex > -1) {
				g.drawImage(drillStrengthRight[drillStrengthIndex], (WIDTH / 2 - tileSizeW / 2 - 5) - xOffset + 86,
						(HEIGHT / 2 - tileSizeH) - yOffset + 6, scale * 28 + 1, scale * 62, null);
			}
			if (drillSpeedIndex > -1) {
				g.drawImage(drillSpeedRight[drillSpeedIndex], (WIDTH / 2 - tileSizeW / 2 - 5) - xOffset + 86,
						(HEIGHT / 2 - tileSizeH) - yOffset + 6, scale * 28 + 1, scale * 62, null);
			}
		}
	}

	public Ship(int width, int height, int tileH, int tileW) {
		WIDTH = width;
		HEIGHT = height;
		tileSizeH = tileH;
		tileSizeW = tileW;

		// for (int i = 0; i < 11; i++) { //THIS IS GOD MODE INVENTORY SETTINGS
		// inventory[i] += 80; //THIS IS GOD MODE INVENTORY SETTINGS
		// curInventory += 80; //THIS IS GOD MODE INVENTORY SETTINGS
		// } //THIS IS GOD MODE INVENTORY SETTINGS
		// maxInventory = 10000;


	}

	public void drawInterface(Graphics2D g) {
		fuelRatio = (float) (((float) maxFuel - Math.abs(fuel)) / maxFuel);
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
		g.fillRect(WIDTH - (WIDTH / 8), (HEIGHT / 6) + 8, WIDTH / 10, HEIGHT / 8);

		if (MyGame.ship.consumables[consumable] != null) {
			if (MyGame.ship.consumables[consumable] instanceof Net) {
				item = NetItem;
			} else if (MyGame.ship.consumables[consumable] instanceof RepairKit) {
				item = RepairItem;
			} else if (MyGame.ship.consumables[consumable] instanceof FuelCanister) {
				item = FuelItem;
			}
			g.drawImage(item, WIDTH - (WIDTH / 9) + 3, (HEIGHT / 6) + 15, WIDTH / 15, HEIGHT / 10, null);
		}

		Color color1 = new Color(255, 215, 0, 200);
		for (int i = 16; i > 4; i--) {
			if (inventory[i] != 0) {
				topOre = i;
				break;
			}
		}
		g.setColor(color1);
		g.fillRect(WIDTH - (WIDTH / 9) + 3, (HEIGHT / 17) + 2, WIDTH / 15, HEIGHT / 10);

		cash = Integer.toString(money);

		g.setColor(Color.WHITE);

		if (money < 10) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 52));
			g.drawString("$" + cash, WIDTH - (WIDTH / 10), HEIGHT / (13) + HEIGHT / 16);
		} else if (money < 100) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
			g.drawString("$" + cash, WIDTH - (WIDTH / 10) - 3, HEIGHT / (13) + HEIGHT / 18);
		} else if (money < 1000) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
			g.drawString("$" + cash, WIDTH - (WIDTH / 10) - 5, HEIGHT / (13) + HEIGHT / 19);
		} else if (money < 10000) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 26));
			g.drawString("$" + cash, WIDTH - (WIDTH / 10) - 5, HEIGHT / (13) + HEIGHT / 20);
		} else {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g.drawString("$" + cash, WIDTH - (WIDTH / 10) - 4, HEIGHT / (13) + HEIGHT / 21);
		}

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
