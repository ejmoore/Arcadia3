import java.awt.Color;
import java.awt.Graphics2D;

import arcadia.Button;
import arcadia.Input;

public class CraftingBuilding implements Building {

	boolean inside = false;
	public SubMenu currentMenu = null;
	int activeButton = 1;

	public class SubMenu {
		int menu = 0;
		int activeButton = 0; // 1 for Depth 3 for Health

		public SubMenu(int m) {
			menu = m;
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
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)), HEIGHT - 300,
						100, 100);
			}

			g.setColor(Color.WHITE);
			g.fillRect(WIDTH / 2 - 113, 50, 226, 144); // Preview

		}

		public void drawMenu2(Graphics2D g) { // Drill Upgrade
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			g.setColor(Color.WHITE);
			for (int i = 1; i <= 3; i += 2) { // Speed and Toughness
				if (activeButton == i) {
					g.setColor(Color.BLUE);
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)), HEIGHT - 300,
						100, 100);
			}

			g.setColor(Color.WHITE);
			g.fillRect(WIDTH / 2 - 113, 50, 226, 144); // Preview
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
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)), HEIGHT - 300,
						100, 100);
			}

			g.setColor(Color.WHITE);
			g.fillRect(WIDTH / 2 - 113, 50, 226, 144); // Preview
		}

		public void drawMenu4(Graphics2D g) { // Cargo Bay upgrade
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			g.setColor(Color.WHITE);
			for (int i = 1; i <= 3; i += 2) { // Storage and item slots
				if (activeButton == i) {
					g.setColor(Color.BLUE);
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect((int) (i * ((float) WIDTH / 5.0f)), HEIGHT - 300,
						100, 100);
			}

			g.setColor(Color.WHITE);
			g.fillRect(WIDTH / 2 - 113, 50, 226, 144); // Preview
		}
		
		public void menuControls(Input p1, Input p2) {
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
			}
		}
	}

	@Override
	public void buildingControls(Input p1, Input p2) {

		if (currentMenu == null) {
			if (p1.pressed(Button.A)) {
				if (activeButton == 1) {
					currentMenu = new SubMenu(1);
				} else if (activeButton == 2) {
					currentMenu = new SubMenu(2);
				} else if (activeButton == 3) {
					currentMenu = new SubMenu(3);
				} else if (activeButton == 4) {
					currentMenu = new SubMenu(4);
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
				inside = false;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else if (currentMenu != null) {
			currentMenu.menuControls(p1, p2);
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
				g.setColor(Color.WHITE);
			} else {
				g.fillRect(102, 250, 118, 125);
			}
			if (activeButton == 2) {
				g.setColor(Color.BLUE);
				g.fillRect(352, 250, 117, 125);// Drill
				g.setColor(Color.WHITE);
			} else {
				g.fillRect(352, 250, 117, 125);
			}
			if (activeButton == 3) {
				g.setColor(Color.BLUE);
				g.fillRect(586, 250, 117, 125);// Fuel
				g.setColor(Color.WHITE);
			} else {
				g.fillRect(586, 250, 117, 125);
			}
			if (activeButton == 4) {
				g.setColor(Color.BLUE);
				g.fillRect(820, 250, 117, 125);// Cargo
				g.setColor(Color.WHITE);
			} else {
				g.fillRect(820, 250, 117, 125);
			}
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

}
