import java.awt.Color;
import java.awt.Graphics2D;

import arcadia.Button;
import arcadia.Input;

public class Store implements Building{

	int x = -1;
	int y = -1;
	int activeButton = 0;
	boolean inside = false;
	
	public Store(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void drawBuilding(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,0,WIDTH,HEIGHT);
		if (activeButton == 0)
			g.setColor(Color.GRAY);
		else
			g.setColor(Color.CYAN);
		g.fillRect(102, 125, 820, 125);
		if (activeButton == 1)
			g.setColor(Color.GRAY);
		else
			g.setColor(Color.CYAN);
		g.fillRect(102, 375, 820, 125);
	}

	@Override
	public boolean isInside() {
		return inside;
	}

	@Override
	public void buildingControls(Input p1, Input p2) {
		if (p1.pressed(Button.D)) {
			activeButton = 1;
		}
		
		if (p1.pressed(Button.U)) {
			activeButton = 0;
		}
		
		if (p1.pressed(Button.B)) {
			inside = false;
		}
	}

	@Override
	public void enter() {
		inside = true;
	}

}
