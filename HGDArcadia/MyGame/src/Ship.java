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
	int drillStrengthIndex = -1;
	static Image[] drillSpeed = new Image[8];
	static Image[] drillSpeedUp = new Image[8];
	int drillSpeedIndex = -1;
	
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
			
			drillStrength[0] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgrade1.png"));
			drillStrengthUp[0] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgradeUp1.png"));
			drillStrength[1] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgrade2.png"));
			drillStrengthUp[1] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgradeUp2.png"));
			drillStrength[2] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgrade3.png"));
			drillStrengthUp[2] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgradeUp3.png"));
			drillStrength[3] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgrade4.png"));
			drillStrengthUp[3] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgradeUp4.png"));
			drillStrength[4] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgrade5.png"));
			drillStrengthUp[4] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgradeUp5.png"));
			drillStrength[5] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgrade6.png"));
			drillStrengthUp[5] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgradeUp6.png"));
			drillStrength[6] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgrade7.png"));
			drillStrengthUp[6] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgradeUp7.png"));
			drillStrength[7] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgrade8.png"));
			drillStrengthUp[7] = ImageIO.read(MyGame.class.getResource("images/DrillStrengthUpgradeUp8.png"));
			drillSpeed[0] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgrade1.png"));
			drillSpeedUp[0] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgradeUp1.png"));
			drillSpeed[1] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgrade2.png"));
			drillSpeedUp[1] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgradeUp2.png"));
			drillSpeed[2] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgrade3.png"));
			drillSpeedUp[2] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgradeUp3.png"));
			drillSpeed[3] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgrade4.png"));
			drillSpeedUp[3] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgradeUp4.png"));
			drillSpeed[4] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgrade5.png"));
			drillSpeedUp[4] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgradeUp5.png"));
			drillSpeed[5] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgrade6.png"));
			drillSpeedUp[5] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgradeUp6.png"));
			drillSpeed[6] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgrade7.png"));
			drillSpeedUp[6] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgradeUp7.png"));
			drillSpeed[7] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgrade8.png"));
			drillSpeedUp[7] = ImageIO.read(MyGame.class.getResource("images/DrillSpeedUpgradeUp8.png"));
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
		}
		else if (direction == 'u') {
			if (drillStrengthIndex > -1) {
				g.drawImage(drillStrengthUp[drillStrengthIndex], (WIDTH / 2 - tileSizeW / 2 - 5) - xOffset + 31,
				(HEIGHT / 2 - tileSizeH) - yOffset - 16, scale * 50 + 1, scale * 50, null);
			}
			if (drillSpeedIndex > -1) {
				g.drawImage(drillSpeedUp[drillSpeedIndex], (WIDTH / 2 - tileSizeW / 2 - 5) - xOffset + 31,
					(HEIGHT / 2 - tileSizeH) - yOffset - 16, scale * 50 + 1, scale * 50, null);
			}
		}
	}

	public Ship(int width, int height, int tileH, int tileW) {
		WIDTH = width;
		HEIGHT = height;
		tileSizeH = tileH;
		tileSizeW = tileW;
	
//		for (int i = 0; i < 11; i++) {	//THIS IS GOD MODE INVENTORY SETTINGS
//			inventory[i] += 80;			//THIS IS GOD MODE INVENTORY SETTINGS
//			curInventory += 80;			//THIS IS GOD MODE INVENTORY SETTINGS
//		}								//THIS IS GOD MODE INVENTORY SETTINGS
//		maxInventory = 10000;
		
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
		g.fillRect(WIDTH - (WIDTH / 8), (HEIGHT / 6 )+ 8, WIDTH / 10, HEIGHT / 8);
		
		if (MyGame.ship.consumables[consumable] != null) {
			if (MyGame.ship.consumables[consumable] instanceof Net){
				item = NetItem;
			} else if (MyGame.ship.consumables[consumable] instanceof RepairKit) {
				item = RepairItem;
			} else if (MyGame.ship.consumables[consumable] instanceof FuelCanister) {
				item = FuelItem;
			}
			g.drawImage(item, WIDTH - (WIDTH / 9) + 3, (HEIGHT / 6 )+ 15 , WIDTH / 15, HEIGHT / 10, null);
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
			g.drawString("$" + cash, WIDTH - (WIDTH / 10)-3, HEIGHT / (13) + HEIGHT / 18);
		} else if (money < 1000){
			g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
			g.drawString("$" + cash, WIDTH - (WIDTH / 10)-5, HEIGHT / (13) + HEIGHT / 19);
		} else if (money < 10000){
			g.setFont(new Font("TimesRoman", Font.PLAIN, 26));
			g.drawString("$" + cash, WIDTH - (WIDTH / 10)-5, HEIGHT / (13) + HEIGHT / 20);
		} else {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g.drawString("$" + cash, WIDTH - (WIDTH / 10)-4, HEIGHT / (13) + HEIGHT / 21);
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
