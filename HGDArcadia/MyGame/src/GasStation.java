import java.awt.Color;
import java.awt.Graphics2D;

import arcadia.*;

public class GasStation implements Building {

	boolean inside = false;
	
	@Override
	public void buildingControls(Input p1) {
		
	}

	@Override
	public void drawBuilding(Graphics2D g) {
		
	}

	@Override
	public boolean isInside() {
		return inside;
	}

	@Override
	public void enter() {
		if ((MyGame.ship.maxFuel - MyGame.ship.fuel) <= MyGame.ship.money) {
			MyGame.ship.money -= (MyGame.ship.maxFuel - MyGame.ship.fuel);
			MyGame.ship.fuel = MyGame.ship.maxFuel;
		}
		else 
			System.out.println("Too poor/Come back later");
	}

}
