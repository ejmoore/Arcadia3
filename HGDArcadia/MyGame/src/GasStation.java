import java.awt.Color;
import java.awt.Graphics2D;

import arcadia.*;

public class GasStation implements Building {

	boolean inside = false;
	
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
		
	}

	@Override
	public boolean isInside() {
		return inside;
	}

	@Override
	public void enter() {
		if ((MyGame.ship.maxFuel - MyGame.ship.fuel) <= MyGame.ship.money) {
			MyGame.ship.money -= (MyGame.ship.maxFuel - MyGame.ship.fuel)/10;
			MyGame.ship.fuel = MyGame.ship.maxFuel;
		}
		else 
			System.out.println("Too poor/Come back later");
	}

}
