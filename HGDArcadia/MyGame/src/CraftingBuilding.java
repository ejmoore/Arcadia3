import java.awt.Color;
import java.awt.Graphics2D;

import arcadia.Button;
import arcadia.Input;

public class CraftingBuilding implements Building {

	boolean inside = false;
	SubMenu currentMenu = null;
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
				g.fillRect(i * (WIDTH / 5), HEIGHT - 150, 100, 100);
			}

			g.setColor(Color.WHITE);
			g.fillRect(WIDTH / 2 - 16, 50, 113, 72); // Preview

		}

		public void menuControls(Input p1, Input p2) {
			if (p1.pressed(Button.R)) {
				activeButton = 3;
			} else if (p1.pressed(Button.L)) {
				activeButton = 1;
			}
		}
	}

	@Override
	public void buildingControls(Input p1, Input p2) {
		if (p1.pressed(Button.A)) {
			if (activeButton == 1) {
				currentMenu = new SubMenu(1);
			}
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
				g.fillRect(102, 125, 118, 125);// Hull
				g.setColor(Color.WHITE);
			}
			if (activeButton == 2) {
				g.setColor(Color.BLUE);
				g.fillRect(352, 125, 117, 125);// Drill
				g.setColor(Color.WHITE);
			}
			if (activeButton == 3) {
				g.setColor(Color.BLUE);
				g.fillRect(586, 125, 117, 125);// Fuel
				g.setColor(Color.WHITE);
			}
			if (activeButton == 4) {
				g.setColor(Color.BLUE);
				g.fillRect(820, 125, 117, 125);// Cargo
				g.setColor(Color.WHITE);
			}

			g.fillRect(102, 375, 820, 125);
		} else if (currentMenu.menu == 1) {
			currentMenu.drawMenu1(g);
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
