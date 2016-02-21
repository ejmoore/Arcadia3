import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import arcadia.Button;
import arcadia.Input;

public class Store implements Building{
	
	int activeButton = 0;
	boolean inside = false;
	
	static Image storeButton;
	static Font storeFont;
	
	static {
		storeFont = new Font("Jokerman", Font.PLAIN, 105);
		try {
			storeButton = ImageIO.read(MyGame.class.getResource("StoreButton.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void drawBuilding(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,0,WIDTH,HEIGHT);
		if (activeButton == 0)
			g.setColor(Color.CYAN);
		else
			g.setColor(Color.GRAY);
		g.fillRect(102, 125, 820, 125);
		g.drawImage(storeButton, 112, 135, 800, 105, null);
		g.setColor(Color.WHITE);
		g.setFont(storeFont);
		g.drawString("Sell", 402, 225);
		if (activeButton == 1)
			g.setColor(Color.CYAN);
		else
			g.setColor(Color.GRAY);
		g.fillRect(102, 375, 820, 125);
		g.drawImage(storeButton, 112, 385, 800, 105, null);
		g.setColor(Color.WHITE);
		g.setFont(storeFont);
		g.drawString("Buy", 402, 475);
	}

	@Override
	public boolean isInside() {
		return inside;
	}

	@Override
	public void buildingControls(Input p1, Input p2) {
		if (p1.pressed(Button.D)) {
			activeButton = 1;
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if (p1.pressed(Button.U)) {
			activeButton = 0;
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if (p1.pressed(Button.B)) {
			inside = false;
		}
		
		if (p1.pressed(Button.A)) {
			if (activeButton == 0) {
				for (int i = 2; i <= 16; i++) {
					MyGame.ship.money += MyGame.ship.inventory[i] * i;
					MyGame.ship.inventory[i] = 0;
				}
			}
			System.out.println(MyGame.ship.money);
		}
	}

	@Override
	public void enter() {
		activeButton = 0;
		inside = true;
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
