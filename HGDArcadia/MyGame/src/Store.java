import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import arcadia.Button;
import arcadia.Input;

public class Store implements Building {

	int activeButton = 0;
	boolean inside = false;

	static Image storeButton;
	static Image fuel;
	static Image net;
	static Image repair;
	static Font storeFont;
	static Font smallFont;

	static {
		storeFont = new Font("Jokerman", Font.PLAIN, 105);
		try {
			storeButton = ImageIO.read(MyGame.class.getResource("images/StoreButton.png"));
			fuel = ImageIO.read(MyGame.class.getResource("images/FuelItem.png"));
			net = ImageIO.read(MyGame.class.getResource("images/NetItem.png"));
			repair = ImageIO.read(MyGame.class.getResource("images/RepairItem.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static {
		smallFont = new Font("Jokerman", Font.PLAIN, 35);
		try {
			storeButton = ImageIO.read(MyGame.class.getResource("images/StoreButton.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void drawBuilding(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		if (activeButton < 2) {
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
		} else {
			int fcount = 0;
			int rcount = 0;
			int ncount = 0;
			for (Consumable c : MyGame.ship.consumables) {
				if ( c instanceof FuelCanister)
					fcount ++;
				if ( c instanceof RepairKit)
					rcount ++;
				if ( c instanceof Net)
					ncount ++;
			}
			if (activeButton == 2)
				g.setColor(Color.CYAN);
			else
				g.setColor(Color.GRAY);
			g.fillRect(WIDTH/10, 150, WIDTH/5, HEIGHT/2);
			g.drawImage(fuel, WIDTH/10 + 25, 175, WIDTH/5 - 50, HEIGHT/2 - 50, null);
			g.setColor(Color.WHITE);
			g.setFont(smallFont);
			g.drawString("$" + 2*MyGame.ship.maxFuel, WIDTH/10, 200+HEIGHT/2);
			g.drawString("Fuel Canister " + fcount, WIDTH/10, 120);
			if (activeButton == 3)
				g.setColor(Color.CYAN);
			else
				g.setColor(Color.GRAY);
			g.fillRect(2*WIDTH/5, 150, WIDTH/5, HEIGHT/2);
			g.drawImage(repair, 2*WIDTH/5 + 25, 175, WIDTH/5 - 50, HEIGHT/2 - 50, null);
			g.setColor(Color.WHITE);
			g.setFont(smallFont);
			g.drawString("$" + MyGame.ship.maxFuel/2, 2*WIDTH/5, 200+HEIGHT/2);
			g.drawString("Repair Kit " + rcount, 2*WIDTH/5, 120);
			if (activeButton == 4)
				g.setColor(Color.CYAN);
			else
				g.setColor(Color.GRAY);
			g.fillRect(7*WIDTH/10, 150, WIDTH/5, HEIGHT/2);
			g.drawImage(net, 7*WIDTH/10 + 25, 175, WIDTH/5 - 50, HEIGHT/2 - 50, null);
			g.setColor(Color.WHITE);
			g.setFont(smallFont);
			g.drawString("$" + 200.0, 7*WIDTH/10, 200+HEIGHT/2);
			g.drawString("Net " + ncount, 7*WIDTH/10, 120);
		}
	}

	@Override
	public boolean isInside() {
		return inside;
	}

	@Override
	public void buildingControls(Input p1) {
		if (activeButton > 1)
			buyControls(p1);
		else {
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
			MyGame.loopingMusic = "background";
			inside = false;
		}

		if (p1.pressed(Button.A)) {
			if (activeButton == 0) {
				for (int i = 2; i <= 16; i++) {
					if (i ==7) {
						continue;
					} else {
						Sound.coinNoise.play();
						MyGame.ship.money += MyGame.ship.inventory[i] * MyGame.tileData[i].getValue();
						MyGame.ship.curInventory -= MyGame.ship.inventory[i] * MyGame.tileData[i].getStorageSpace();
						MyGame.ship.inventory[i] = 0;
					} 
				}
			} else if (activeButton == 1){
				activeButton = 2;
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//buyControls(p1);
			}
			System.out.println(MyGame.ship.money);
		}
		}
	}

	public void buyControls(Input p1) {
		if (p1.pressed(Button.L)) {
			if (activeButton != 2)
				activeButton --;
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (p1.pressed(Button.R)) {
			if (activeButton != 4)
				activeButton ++;
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (p1.pressed(Button.B)) {
			activeButton = 1;
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (p1.pressed(Button.A)) {
			if (activeButton == 2) {
				if (MyGame.ship.money >= MyGame.ship.maxFuel*2) {
					MyGame.ship.money -= MyGame.ship.maxFuel*2;
					MyGame.ship.consumables[0] = new FuelCanister(0);
				} else 
					System.out.println("Too poor, so sad!");
			}
				
			if (activeButton == 3) {
				if (MyGame.ship.money >= MyGame.ship.maxHealth/2) {
					MyGame.ship.money -= MyGame.ship.maxHealth/2;
					MyGame.ship.consumables[0] = new RepairKit(0);
				} else
					System.out.println("No money?");
			}
				
			if (activeButton == 4) {
				if (MyGame.ship.money >= 200) {
					MyGame.ship.money -= 200;
					MyGame.ship.consumables[0] = new Net(0);
				} else
					System.out.println("No item for you - need money!");
			}
				
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
