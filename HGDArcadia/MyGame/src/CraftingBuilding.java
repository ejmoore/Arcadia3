import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import arcadia.Button;
import arcadia.Input;

public class CraftingBuilding implements Building {

	boolean inside = false;
	public SubMenu currentMenu = null;
	int activeButton = 1;

	int[] cargoInventory = { 20, 30, 40, 50, 60, 70,0, 80, 90, 100, 110, 120, 130, 140, 150 }; static int curInventory = 0;
	int[] itemSlots = { 2, 3, 4, 5, 6, 7,0, 8, 9, 10, 11, 12, 13, 14, 15 }; static int curItemSlots = 0;
	int[] drillSpeed = { 10, 20, 30, 40, 50, 60,0, 70, 80, 90, 100, 110, 120, 130, 140, 150 }; static int curSpeed = 0;
	int[] mineable = { 3, 4, 5, 6, 8, 9,0, 10, 11, 12, 13, 14, 15, 16, 17, 18 }; static int curMinable = 0;
	int[] shipFuel = { 200, 300, 400, 500, 600, 700,0, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600 }; static int curMaxFuel = 0;
	double[] fuelEff = { .9, .8, .7, .6, .5, .4,0, .3, .2, .45, .4, .35, .3, .25, .2, .1 }; static int curFuelEff = 0;
	int[] maxDepth = { 2500, 3750, 5000, 6250, 7500, 8750,0, 10000, 11250, 1500, 1650, 1800, 1950, 2100, 2250 }; static int curMaxDepth = 0;
	int[] maxHealth = { 200, 300, 400, 500, 600, 700,0, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600 }; static int curMaxHealth = 0;
	
	public void drawString(Graphics2D g, String text, int x, int y) {
		for (String line : text.split("\n")) {
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
		}
	}

	public class SubMenu {
		int menu = 0;
		int activeButton = 0; // 1 for Depth 3 for Health
		int upgrade1 = 0;
		int upgrade2 = 0;
		int upgradeCount = 0;
		String name;

		public SubMenu(int m, String s, int up1, int up2) {
			menu = m;
			name = s;
			upgrade1 = up1 + 1;
			upgrade2 = up2 + 1;
		}

		public void drawMenu1(Graphics2D g) { // Hull Upgrade
			upgrade1 = (curMaxDepth == 5) ? curMaxDepth+2:curMaxDepth+1;
			upgrade2 = (curMaxHealth == 5) ? curMaxHealth+2:curMaxHealth+1;
			
			
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			g.setColor(Color.WHITE);
			for (int i = 1; i <= 3; i += 2) { // Depth and Health
				if (activeButton == i) {
					g.setColor(Color.BLUE);
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)-13), HEIGHT - 300, 113, 100);
				g.setColor(Color.GRAY);
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)) + 100, HEIGHT - 300, 200, 100);
				g.setColor(Color.MAGENTA);
				if (i == 1) {
					Tile temp = new Tile(upgrade1+1,0,0,1,1);
					temp.drawTile(g,(int) (i * ((float) WIDTH / 5.0f)-13), HEIGHT - 286);
					g.setColor(Color.MAGENTA);
				} else {
					Tile temp = new Tile(upgrade2+1,0,0,1,1);
					temp.drawTile(g,(int) (i * ((float) WIDTH / 5.0f)-13), HEIGHT - 286);
					g.setColor(Color.MAGENTA);
				}
			}
			//g.drawString("Increase the max depth\n you can reach", 305, HEIGHT - 250);
			//g.drawString("Increase your maximum\n health", 715, HEIGHT - 250);
			drawString(g,"Increase the max\ndepth you can\nreach",305,HEIGHT-300);
			drawString(g,"Increase your \nmaximum health",715,HEIGHT-300);

			g.setColor(Color.WHITE);
			g.fillRect(WIDTH / 2 - 113, 50, 226, 144); // Preview
			MyGame.ship.drawShip('r', g, 2, 52, 166);

		}

		public void drawMenu2(Graphics2D g) { // Drill Upgrade
			upgrade1 = (curSpeed == 5) ? curSpeed+2:curSpeed+1;
			upgrade2 = (curMinable == 5) ? curMinable+2:curMinable+1;
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			g.setColor(Color.WHITE);
			for (int i = 1; i <= 3; i += 2) { // Speed and Toughness
				if (activeButton == i) {
					g.setColor(Color.BLUE);
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)-13), HEIGHT - 300, 113, 100);
				g.setColor(Color.GRAY);
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)) + 100, HEIGHT - 300, 200, 100);
				g.setColor(Color.MAGENTA);
				if (i == 1) {
					Tile temp = new Tile(upgrade1+1,0,0,1,1);
					temp.drawTile(g,(int) (i * ((float) WIDTH / 5.0f)-13), HEIGHT - 286);
					g.setColor(Color.MAGENTA);
				} else {
					Tile temp = new Tile(upgrade2+1,0,0,1,1);
					temp.drawTile(g,(int) (i * ((float) WIDTH / 5.0f)-13), HEIGHT - 286);
					g.setColor(Color.MAGENTA);
				}
			}
//			g.drawString("Increase the drill's speed", 305, HEIGHT - 250);
//			g.drawString("Increase the Best Ore you can mine", 715, HEIGHT - 250);
			drawString(g,"Increase the \ndrill's speed",305,HEIGHT-300);
			drawString(g,"Increase the Best \nOre you can mine",715,HEIGHT-300);
			
			g.setColor(Color.WHITE);
			g.fillRect(WIDTH / 2 - 113, 50, 226, 144); // Preview
			MyGame.ship.drawShip('r', g, 2, 52, 166);
		}

		public void drawMenu3(Graphics2D g) { // Fuel Upgrade
			upgrade1 = (curMaxFuel == 5) ? curMaxFuel+2:curMaxFuel+1;
			upgrade2 = (curFuelEff == 5) ? curFuelEff+2:curFuelEff+1;
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			g.setColor(Color.WHITE);
			for (int i = 1; i <= 3; i += 2) { // Capacity and Efficiency
				if (activeButton == i) {
					g.setColor(Color.BLUE);
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)-13), HEIGHT - 300, 113, 100);
				g.setColor(Color.GRAY);
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)) + 100, HEIGHT - 300, 200, 100);
				g.setColor(Color.MAGENTA);
				if (i == 1) {
					Tile temp = new Tile(upgrade1+1,0,0,1,1);
					temp.drawTile(g,(int) (i * ((float) WIDTH / 5.0f)-13), HEIGHT - 286);
					g.setColor(Color.MAGENTA);
				} else {
					Tile temp = new Tile(upgrade2+1,0,0,1,1);
					temp.drawTile(g,(int) (i * ((float) WIDTH / 5.0f)-13), HEIGHT - 286);
					g.setColor(Color.MAGENTA);
				}
			}
//			g.drawString("Increase maximum fuel capacity", 305, HEIGHT - 250);
//			g.drawString("Increase your fuel efficiency", 715, HEIGHT - 250);
			drawString(g,"Increase \nmaximum fuel \ncapacity",305,HEIGHT-300);
			drawString(g,"Increase your fuel \nefficiency",715,HEIGHT-300);

			g.setColor(Color.WHITE);
			g.fillRect(WIDTH / 2 - 113, 50, 226, 144); // Preview
			MyGame.ship.drawShip('r', g, 2, 52, 166);
		}

		public void drawMenu4(Graphics2D g) { // Cargo Bay upgrade
			upgrade1 = (curInventory == 5) ? curInventory+2:curInventory+1;
			upgrade2 = (curItemSlots == 5) ? curItemSlots+2:curItemSlots+1;
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			g.setColor(Color.WHITE);
			for (int i = 1; i <= 3; i += 2) { // Storage and item slots
				if (activeButton == i) {
					g.setColor(Color.BLUE);
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)-13), HEIGHT - 300, 113, 100);
				g.setColor(Color.GRAY);
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)) + 100, HEIGHT - 300, 200, 100);
				g.setColor(Color.MAGENTA);
				if (i == 1) {
					Tile temp = new Tile(upgrade1+1,0,0,1,1);
					temp.drawTile(g,(int) (i * ((float) WIDTH / 5.0f)-13), HEIGHT - 286);
					g.setColor(Color.MAGENTA);
				} else {
					Tile temp = new Tile(upgrade2+1,0,0,1,1);
					temp.drawTile(g,(int) (i * ((float) WIDTH / 5.0f)-13), HEIGHT - 286);
					g.setColor(Color.MAGENTA);
				}
			}
//			g.drawString("Increase the max ore you can hold", 305, HEIGHT - 250);
//			g.drawString("Increase your max number of item slots", 715, HEIGHT - 250);
			drawString(g,"Increase the max \nore you can hold",305,HEIGHT-300);
			drawString(g,"Increase your \nmax number of \nitem slots",715,HEIGHT-300);

			g.setColor(Color.WHITE);
			g.fillRect(WIDTH / 2 - 113, 50, 226, 144); // Preview
			MyGame.ship.drawShip('r', g, 2, 52, 166);
		}

		public void menuControls(Input p1) {
			if (p1.pressed(Button.R)) {
				activeButton = 3;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (p1.pressed(Button.L)) {
				activeButton = 1;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (p1.pressed(Button.B)) {
				currentMenu = null;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}  else if (p1.pressed(Button.A)) {
				upgradeCount++;
				if (upgradeCount >= 30) {
					if (activeButton == 1) buyUpgrade(name, 1);
					else buyUpgrade(name, 2);
					upgradeCount = 0;
				}
			}
		}
	}

	@Override
	public void buildingControls(Input p1) {

		if (currentMenu == null) {
			if (p1.pressed(Button.A)) {
				if (activeButton == 1) {
					currentMenu = new SubMenu(1, "Hull", curMaxDepth, curMaxHealth);
				} else if (activeButton == 2) {
					currentMenu = new SubMenu(2, "Drill", curSpeed, curMinable);
				} else if (activeButton == 3) {
					currentMenu = new SubMenu(3, "Fuel", curMaxFuel, curFuelEff);
				} else if (activeButton == 4) {
					currentMenu = new SubMenu(4, "Cargo Bay", curInventory, curItemSlots);
				}
			}
			if (p1.pressed(Button.R) && activeButton < 4) {
				activeButton += 1;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (p1.pressed(Button.L) && activeButton > 1) {
				activeButton -= 1;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (p1.pressed(Button.B)) {

				MyGame.loopingMusic="background";
				inside = false;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else if (currentMenu != null) {
			currentMenu.menuControls(p1);
		}

	}

	@Override
	public void drawBuilding(Graphics2D g) { // 1024x576
		g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		if (currentMenu == null) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			g.setColor(Color.WHITE);
			if (activeButton == 1) {
				g.setColor(Color.BLUE);
				//g.fillRect(x, y, width, height);
				g.fillRect(102, 255, 160, 105);// Hull
			} else {
				g.setColor(Color.WHITE);
				g.fillRect(102, 255, 160, 105);
			}
			g.setColor(Color.MAGENTA);
			g.drawString("Hull upgrades", 112, 313);
			if (activeButton == 2) {
				g.setColor(Color.BLUE);
				g.fillRect(352, 255, 160, 105);// Drill
			} else {
				g.setColor(Color.WHITE);
				g.fillRect(352, 255, 160, 105);
			}
			g.setColor(Color.MAGENTA);
			g.drawString("Drill upgrades", 362, 313);
			if (activeButton == 3) {
				g.setColor(Color.BLUE);
				g.fillRect(586, 255, 160, 105);// Fuel
			} else {
				g.setColor(Color.WHITE);
				g.fillRect(586, 255, 160, 105);
			}
			g.setColor(Color.MAGENTA);
			g.drawString("Fuel upgrades", 596, 313);
			if (activeButton == 4) {
				g.setColor(Color.BLUE);
				g.fillRect(820, 255, 160, 105);// Cargo
			} else {
				g.setColor(Color.WHITE);
				g.fillRect(820, 255, 160, 105);
			}
			g.setColor(Color.MAGENTA);
			g.drawString("Cargo upgrades", 823, 313);
		} else if (currentMenu.menu == 1) {
			currentMenu.drawMenu1(g);
		} else if (currentMenu.menu == 2) {
			currentMenu.drawMenu2(g);
		} else if (currentMenu.menu == 3) {
			currentMenu.drawMenu3(g);
		} else if (currentMenu.menu == 4) {
			currentMenu.drawMenu4(g);
		}
	}

	@Override
	public boolean isInside() {
		return inside;
	}

	@Override
	public void enter() {
		inside = true;
	}

	private void buyUpgrade(String upgrade, int upgradeNum) {
		//System.out.println(MyGame.ship.inventory[0] + ", " + MyGame.ship.inventory[1] + ", " + MyGame.ship.inventory[2] + ", " + MyGame.ship.inventory[3] + ", " + MyGame.ship.inventory[4]);
		//System.out.println(curSpeed);
		if (upgrade.equals("Cargo Bay")) {
			if (upgradeNum == 1) {
				//System.out.println(MyGame.ship.inventory[curInventory+1] + " : " + curInventory);
				if (curInventory >= 8 || MyGame.ship.inventory[curInventory+2] < 10) {
					//System.out.println("YOU'RE TOO POOR");
					Sound.Warning1.play();
					return;
				}
				MyGame.ship.maxInventory = cargoInventory[++curInventory-1]; //System.out.println(MyGame.ship.maxInventory);
				MyGame.ship.curInventory -= 10; MyGame.ship.inventory[curInventory+1] -= 10;
				MyGame.ship.cargoInventoryIndex++;
				if (curInventory == 5) {
					curInventory++;
				}
			} else {
				if (curItemSlots >=8 || MyGame.ship.inventory[curItemSlots+2] < 10) {
					//System.out.println("YOU'RE TOO POOR");
					Sound.Warning1.play();
					return;
				}
				MyGame.ship.maxItemSlots = itemSlots[++curItemSlots-1]; //System.out.println(MyGame.ship.maxItemSlots);
				MyGame.ship.curInventory -= 10; MyGame.ship.inventory[curItemSlots+1] -= 10;
				MyGame.ship.cargoItemSlotsIndex++;
				if (curItemSlots == 5) {
					curItemSlots++;
				}
			}
		}
		if (upgrade.equals("Drill")) {
			if (upgradeNum == 1) {
				//System.out.println(MyGame.ship.inventory[curSpeed+1] + " : " + curSpeed);
				if (curSpeed >=10 || MyGame.ship.inventory[curSpeed+2] < 10) {
					//System.out.println("YOU'RE TOO POOR");
					Sound.Warning1.play();
					return;
				}
				MyGame.ship.drill = drillSpeed[++curSpeed-1]; //System.out.println(MyGame.ship.drill);
				MyGame.ship.curInventory -= 10; MyGame.ship.inventory[curSpeed+1] -= 10;
				MyGame.ship.drillSpeedIndex++;
				if (curSpeed == 5) {
					curSpeed++;
				}
			} else {
				if (curMinable >=10 || MyGame.ship.inventory[curMinable+2] < 10) {
					//System.out.println("YOU'RE TOO POOR");
					Sound.Warning1.play();
					return;
				}
				MyGame.notMineable.remove((Integer)mineable[curMinable]);
				MyGame.ship.curInventory -= 10; MyGame.ship.inventory[++curMinable+1] -= 10;
				MyGame.ship.drillStrengthIndex++;
				if (curMinable == 5) {
					curMinable++;
				}
			}
		}
		if (upgrade.equals("Fuel")) {
			if (upgradeNum == 1) {
				if (curMaxFuel >=10 || MyGame.ship.inventory[curMaxFuel+2] < 10) {
					//System.out.println("YOU'RE TOO POOR");
					return;
				}
				MyGame.ship.maxFuel = shipFuel[++curMaxFuel-1];
				MyGame.ship.fuel = MyGame.ship.maxFuel;
				MyGame.ship.curInventory -= 10; MyGame.ship.inventory[curMaxFuel+1] -= 10;
				MyGame.ship.fuelCapacityIndex++;
				if (curMaxFuel == 5) {
					curMaxFuel++;
				}
			} else {
				if (curFuelEff >=10 || MyGame.ship.inventory[curFuelEff+2] < 10) {
					//System.out.println("YOU'RE TOO POOR");
					Sound.Warning1.play();
					return;
				}
				MyGame.ship.fuelCost = fuelEff[++curFuelEff-1];
				MyGame.ship.fuel = MyGame.ship.maxFuel;
				MyGame.ship.curInventory -= 10; MyGame.ship.inventory[curFuelEff+1] -= 10;
				MyGame.ship.fuelEfficiencyIndex++;
				if (curFuelEff == 5) {
					curFuelEff++;
				}
			}
		}
		if (upgrade.equals("Hull")) {
			if (upgradeNum == 1) {
				if (curMaxDepth >=10 || MyGame.ship.inventory[curMaxDepth+2] < 10) {
					//System.out.println("YOU'RE TOO POOR");
					Sound.Warning1.play();
					return;
				}
				MyGame.ship.maxDepth = maxDepth[++curMaxDepth-1];
				MyGame.ship.curInventory -= 10; MyGame.ship.inventory[curMaxDepth+1] -= 10;
				MyGame.ship.hullDepthIndex++;
				if (curMaxDepth == 5) {
					curMaxDepth++;
				}
			} else {
				if (curMaxHealth >=10 || MyGame.ship.inventory[curMaxHealth+2] < 10) {
					//System.out.println("YOU'RE TOO POOR");
					Sound.Warning1.play();
					return;
				}
				MyGame.ship.maxHealth = maxHealth[++curMaxHealth-1];
				MyGame.ship.health = MyGame.ship.maxHealth;
				MyGame.ship.curInventory -= 10; MyGame.ship.inventory[curMaxHealth+1] -= 10;
				MyGame.ship.hullHealthIndex++;
				if (curMaxHealth == 5) {
					curMaxHealth++;
				}
			}
		}
		//System.out.println("UPGRADE PURCHASED");
		Sound.Upgrade.play();
	}

}
