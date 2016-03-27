import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import arcadia.Button;
import arcadia.Input;

public class SaveLocation implements Building {
	Tile[][] tiles = null;
	int activeButton = 0;
	boolean inside = false;
	int height;
	int width;
	static Image storeButton;
	static Font storeFont;
	
	static {
		storeFont = new Font("Jokerman", Font.PLAIN, 105);
		try {
			storeButton = ImageIO.read(MyGame.class.getResource("images/StoreButton.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SaveLocation(Tile[][] tile, int h, int w) {
		tiles = tile;
		height = h;
		width = w;
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
		g.drawString("Save", 402, 225);
		if (activeButton == 1)
			g.setColor(Color.CYAN);
		else
			g.setColor(Color.GRAY);
		g.fillRect(102, 375, 820, 125);
		g.drawImage(storeButton, 112, 385, 800, 105, null);
		g.setColor(Color.WHITE);
		g.setFont(storeFont);
		g.drawString("Load", 402, 475);
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
		for (int j = 0; j < height; j++) {
			for (int i = 0; i <= width + 14; i++) {
				int next = tiles[i][j].tileType;

				System.out.println(i + " " + j + " " + next);
				if (next < 10) {
					writer.print("0" + tiles[i][j].tileType + " ");
				} else {
					writer.print(tiles[i][j].tileType + " ");
				}
			}
			writer.println();
			writer.flush();
		}
	}

	public Tile[][] loadGame() {
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

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width + 15; x++) {
				if (map.hasNextInt()) {
					int tileNum = map.nextInt();
					if (tileNum < 10){
						writer.print("0" + tileNum+ " ");
					}else{
						writer.print(tileNum+ " ");
					}
				}
			}
			writer.println();
		}
		MyGame.loadingGame = true;
		createTiles();
		return tiles;
	}

	@Override
	public void buildingControls(Input p1) {
		if (p1.pressed(Button.D)) {
			activeButton = 1;
		}

		if (p1.pressed(Button.U)) {
			activeButton = 0;
		}

		if (p1.pressed(Button.B)) {
			MyGame.loadingGame = false;
			MyGame.loopingMusic = "background";
			inside = false;
		}

		if (p1.pressed(Button.A)) {
			if (activeButton == 0) {
				saveTheGame();
			} else if (activeButton == 1) {
				MyGame.tiles = loadGame();
			}
		}
	}

	Scanner map = null;
	public void createTiles() {
		try {
			map = new Scanner(new File("map.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width + 15; i++) {
				if(map.hasNext()){
					tiles[i][j] = new Tile(map.nextInt(), i, j, MyGame.tileSizeW,
							MyGame.tileSizeH);
				}
			}
		}
	}
	@Override
	public void enter() {
		inside = true;
	}
}
