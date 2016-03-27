import java.awt.Color;
import java.awt.Graphics2D;

import arcadia.Button;
import arcadia.Input;

public class CraftingBuilding implements Building {

	boolean inside = false;
	public SubMenu currentMenu = null;
	int activeButton = 1;

	int[] cargoInventory = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150 }; int curInventory = 0;
	int[] itemSlots = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 }; int curItemSlots = 0;
	int[] drillSpeed = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150 }; int curSpeed = 0;
	int[] mineable = { 3, 4, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 }; int curMinable = 0;

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
			upgrade1 = up1;
			upgrade2 = up2;
		}

		public void drawMenu1(Graphics2D g) { // Hull Upgrade
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			g.setColor(Color.WHITE);
			for (int i = 1; i <= 3; i += 2) { // Depth and Health
				if (activeButton == i) {
					g.setColor(Color.BLUE);
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)), HEIGHT - 300, 100, 100);
				g.setColor(Color.GRAY);
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)) + 100, HEIGHT - 300, 200, 100);
				g.setColor(Color.MAGENTA);
				if (i == 1) {
					g.drawString(upgrade1 + "", (int) (i * ((float) WIDTH / 5.0f) + 50), HEIGHT - 250);
				} else {
					g.drawString(upgrade2 + "", (int) (i * ((float) WIDTH / 5.0f) + 50), HEIGHT - 250);
				}
			}
			g.drawString("Increase the max depth you can reach", 305, HEIGHT - 250);
			g.drawString("Increase your maximun health", 715, HEIGHT - 250);

			g.setColor(Color.WHITE);
			g.fillRect(WIDTH / 2 - 113, 50, 226, 144); // Preview
			MyGame.ship.drawShip('r', g, 2, 52, 166);

		}

		public void drawMenu2(Graphics2D g) { // Drill Upgrade
			curSpeed = upgrade1;
			curMinable = upgrade2;
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			g.setColor(Color.WHITE);
			for (int i = 1; i <= 3; i += 2) { // Speed and Toughness
				if (activeButton == i) {
					g.setColor(Color.BLUE);
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)), HEIGHT - 300, 100, 100);
				g.setColor(Color.GRAY);
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)) + 100, HEIGHT - 300, 200, 100);
				g.setColor(Color.MAGENTA);
				if (i == 1) {
					g.drawString(curSpeed + "", (int) (i * ((float) WIDTH / 5.0f) + 50), HEIGHT - 250);
				} else {
					g.drawString(curMinable + "", (int) (i * ((float) WIDTH / 5.0f) + 50), HEIGHT - 250);
				}
			}
			g.drawString("Increase the drill's speed", 305, HEIGHT - 250);
			g.drawString("Increase the Best Ore you can mine", 715, HEIGHT - 250);

			g.setColor(Color.WHITE);
			g.fillRect(WIDTH / 2 - 113, 50, 226, 144); // Preview
			MyGame.ship.drawShip('r', g, 2, 52, 166);
		}

		public void drawMenu3(Graphics2D g) { // Fuel Upgrade
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			g.setColor(Color.WHITE);
			for (int i = 1; i <= 3; i += 2) { // Capacity and Efficiency
				if (activeButton == i) {
					g.setColor(Color.BLUE);
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)), HEIGHT - 300, 100, 100);
				g.setColor(Color.GRAY);
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)) + 100, HEIGHT - 300, 200, 100);
				g.setColor(Color.MAGENTA);
				if (i == 1) {
					g.drawString(upgrade1 + "", (int) (i * ((float) WIDTH / 5.0f) + 50), HEIGHT - 250);
				} else {
					g.drawString(upgrade2 + "", (int) (i * ((float) WIDTH / 5.0f) + 50), HEIGHT - 250);
				}
			}
			g.drawString("Increase maximum fuel capacity", 305, HEIGHT - 250);
			g.drawString("Increase your fuel efficiency", 715, HEIGHT - 250);

			g.setColor(Color.WHITE);
			g.fillRect(WIDTH / 2 - 113, 50, 226, 144); // Preview
			MyGame.ship.drawShip('r', g, 2, 52, 166);
		}

		public void drawMenu4(Graphics2D g) { // Cargo Bay upgrade
			curInventory = upgrade1;
			curItemSlots = upgrade2;
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			g.setColor(Color.WHITE);
			for (int i = 1; i <= 3; i += 2) { // Storage and item slots
				if (activeButton == i) {
					g.setColor(Color.BLUE);
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)), HEIGHT - 300, 100, 100);
				g.setColor(Color.GRAY);
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)) + 100, HEIGHT - 300, 200, 100);
				g.setColor(Color.MAGENTA);
				if (i == 1) {
					g.drawString(curInventory + "", (int) (i * ((float) WIDTH / 5.0f) + 50), HEIGHT - 250);
				} else {
					g.drawString(curItemSlots + "", (int) (i * ((float) WIDTH / 5.0f) + 50), HEIGHT - 250);
				}
			}
			g.drawString("Increase the max ore you can hold", 305, HEIGHT - 250);
			g.drawString("Increase your max number of item slots", 715, HEIGHT - 250);

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
			} else if (p1.pressed(Button.U)) {
				if (activeButton == 1 && upgrade1 < 15) {
					upgrade1++;
				} else if (activeButton == 3 && upgrade2 < 15) {
					upgrade2++;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (p1.pressed(Button.D)) {
				if (activeButton == 1 && upgrade1 > 0) {
					upgrade1--;
				} else if (activeButton == 3 && upgrade2 > 0) {
					upgrade2--;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (p1.pressed(Button.A)) {
				upgradeCount++;
				if (upgradeCount >= 60) {
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
					currentMenu = new SubMenu(1, "Hull", 0, 0);
				} else if (activeButton == 2) {
					currentMenu = new SubMenu(2, "Drill", curSpeed, curMinable);
				} else if (activeButton == 3) {
					currentMenu = new SubMenu(3, "Fuel", 0, 0);
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
			if (p1.pressed(Button.L) && activeButton > 0) {
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
		if (currentMenu == null) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			g.setColor(Color.WHITE);
			if (activeButton == 1) {
				g.setColor(Color.BLUE);
				g.fillRect(102, 250, 118, 125);// Hull
			} else {
				g.setColor(Color.WHITE);
				g.fillRect(102, 250, 118, 125);
			}
			g.setColor(Color.MAGENTA);
			g.drawString("Hull upgrades", 112, 313);
			if (activeButton == 2) {
				g.setColor(Color.BLUE);
				g.fillRect(352, 250, 117, 125);// Drill
			} else {
				g.setColor(Color.WHITE);
				g.fillRect(352, 250, 117, 125);
			}
			g.setColor(Color.MAGENTA);
			g.drawString("Drill upgrades", 362, 313);
			if (activeButton == 3) {
				g.setColor(Color.BLUE);
				g.fillRect(586, 250, 117, 125);// Fuel
			} else {
				g.setColor(Color.WHITE);
				g.fillRect(586, 250, 117, 125);
			}
			g.setColor(Color.MAGENTA);
			g.drawString("Fuel upgrades", 596, 313);
			if (activeButton == 4) {
				g.setColor(Color.BLUE);
				g.fillRect(820, 250, 117, 125);// Cargo
			} else {
				g.setColor(Color.WHITE);
				g.fillRect(820, 250, 117, 125);
			}
			g.setColor(Color.MAGENTA);
			g.drawString("Cargo upgrades", 830, 313);
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
//		if (upgrade.equals("Drill")) {
//			MyGame.ship.drill = drillSpeed[upgrade1];
//			MyGame.notMineable.remove(mineable[upgrade2]);
//		}
		if (upgrade.equals("Cargo Bay")) {
			if (upgradeNum == 1) {
				System.out.println(MyGame.ship.inventory[curInventory+1] + " : " + curInventory);
				if (MyGame.ship.inventory[curInventory+1] < 10) {
					System.out.println("YOU'RE TOO POOR");
					return;
				}
				MyGame.ship.maxInventory = cargoInventory[++curInventory-1]; System.out.println(MyGame.ship.maxInventory);
				MyGame.ship.curInventory -= 10; MyGame.ship.inventory[curInventory] -= 10;
			} else {
				if (MyGame.ship.inventory[curItemSlots+1] < 10) {
					System.out.println("YOU'RE TOO POOR");
					return;
				}
				MyGame.ship.maxItemSlots = itemSlots[++curItemSlots-1]; System.out.println(MyGame.ship.maxItemSlots);
				MyGame.ship.curInventory -= 10; MyGame.ship.inventory[curInventory] -= 10;
			}
		}
		if (upgrade.equals("Drill")) {
			if (upgradeNum == 1) {
				System.out.println(MyGame.ship.inventory[curSpeed+1] + " : " + curSpeed);
				if (MyGame.ship.inventory[curSpeed+1] < 10) {
					System.out.println("YOU'RE TOO POOR");
					return;
				}
				MyGame.ship.drill = drillSpeed[++curSpeed-1]; System.out.println(MyGame.ship.drill);
				MyGame.ship.curInventory -= 10; MyGame.ship.inventory[curSpeed] -= 10;
			}
		}
		System.out.println("UPGRADE PURCHASED");
	}

}
