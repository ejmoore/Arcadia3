import java.awt.Color;
import java.awt.Graphics2D;

import arcadia.*;

public class GasStation implements Building {

	boolean inside;
	
	@Override
	public void buildingControls(Input p1) {
		if (p1.pressed(Button.B)) {
			MyGame.loopingMusic = "background";
			inside = false;
		}
		if (p1.pressed(Button.A)) {
			MyGame.ship.fuel = MyGame.ship.maxFuel;
		}
	}

	@Override
	public void drawBuilding(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
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
