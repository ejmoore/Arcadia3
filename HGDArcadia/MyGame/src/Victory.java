import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import arcadia.Button;
import arcadia.Input;

public class Victory implements Building {

	boolean inside = false;
	boolean won = false;

	@Override
	public void buildingControls(Input p1) {
		if (p1.pressed(Button.B)) {
			MyGame.loopingMusic = "background";
			inside = false;
		}
		if (p1.pressed(Button.A) && MyGame.ship.money >= 20000 && !won) {
			MyGame.ship.money -= 20000;
			won = true;
			for (int i = 0; i < MyGame.tiles.length; i++) {
				for (int j = 0; j < MyGame.tiles[i].length; j++) {
					MyGame.tiles[i][j] = new Tile(94, i, j, WIDTH / 9, HEIGHT / 8);
				}
			}

		}
	}

	@Override
	public void drawBuilding(Graphics2D g) {
		if (won) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 70));
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.GRAY);
			g.fillRect(102, 125, 820, 250);
			g.setColor(Color.BLUE);
			g.drawString("Congratulations! You Suck!", 125, 275);
		} else {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.GRAY);
			g.fillRect(102, 125, 820, 125);
			g.setColor(Color.BLUE);
			g.drawString("Spend $20,000 to win", 200, 225);
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
