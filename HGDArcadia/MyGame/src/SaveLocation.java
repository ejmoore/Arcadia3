import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import arcadia.Button;
import arcadia.Input;

public class SaveLocation implements Building {
	Tile[][] tiles = null;
	int activeButton = 0;
	boolean inside = false;

	public SaveLocation(Tile[][] tile) {
		tiles = tile;
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
		if (activeButton == 1)
			g.setColor(Color.CYAN);
		else
			g.setColor(Color.GRAY);
		g.fillRect(102, 375, 820, 125);
	}

	@Override
	public boolean isInside() {
		return inside;
	}

	public void saveTheGame() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("mapSave.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for (int y = 0; y < 1000; y++) {
			for (int x = 0; x < 54; x++) {
				int next = tiles[x][y].tileType;
				if (next < 10) {
					writer.print("0" + tiles[x][y].tileType + " ");
				} else {
					writer.print(tiles[x][y].tileType + " ");
				}
			}
			writer.println();
		}
	}


	public void loadGame() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("map.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Scanner map = null;
		try {
			map = new Scanner(new File("mapSave.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int x = 0; x < 55; x++) {
			for (int y = 0; y < 1001; y++) {
				writer.println(map.nextInt());
			}
			writer.println();
		}

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
		
		if (p1.pressed(Button.A)) {
			if (activeButton == 0) {
				saveTheGame();
			}
			else if (activeButton == 1) {
				loadGame();
				MyGame.loadingGame = true;
			}
		}
	}

	@Override
	public void enter() {
		inside = true;
	}
}
