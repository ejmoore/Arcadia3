import java.awt.Color;
import java.awt.Graphics2D;

import arcadia.Input;


public class CraftingBuilding implements Building {

	boolean inside = false;
	SubMenu currentMenu = null;
	int activeButton = 0;
	
	public class SubMenu {
		int activeButton = 0;
		public void drawMenu1(Graphics2D g) {
			g.setColor(Color.BLACK);
			g.fillRect(0,0,WIDTH,HEIGHT);
			
			g.setColor(Color.WHITE);
			g.fillRect(102, 125, 820, 125);
			
			
		}
		public void menuControls(Input p1, Input p2) {
			
		}
	}
	
	@Override
	public void buildingControls(Input p1, Input p2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawBuilding(Graphics2D g) { //1024x576
		g.setColor(Color.BLACK);
		g.fillRect(0,0,WIDTH,HEIGHT);
		
		g.setColor(Color.WHITE);
		g.fillRect(102, 125, 118, 125);
		g.fillRect(352, 125, 117, 125);
		g.fillRect(586, 125, 117, 125);
		g.fillRect(820, 125, 117, 125);
		
		g.fillRect(102, 375, 820, 125);
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
