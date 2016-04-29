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
	Ship ship;
	static {
		storeFont = new Font("Jokerman", Font.PLAIN, 105);
		try {
			storeButton = ImageIO.read(MyGame.class.getResource("images/StoreButton.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SaveLocation(Tile[][] tile, int h, int w, Ship s) {
		tiles = tile;
		height = h;
		width = w;
		ship = s;
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
		Sound.PositiveFeedback.play();
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("mapSave.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// inventory
		for (int i = 0; i < ship.inventory.length; i++){
			writer.print(ship.inventory[i] + " ");
		}
		
		writer.println();
		writer.println(ship.maxInventory);
		writer.println(ship.curInventory);
		writer.println(ship.topOre);
		writer.println(ship.drill);
		writer.println();
		writer.flush();
		
		// fuel
		writer.println((int)ship.fuel);
		writer.println(ship.maxFuel);
		writer.println(ship.fuelCost);
		writer.println();
		writer.flush();
		
		// health
		writer.println((int)ship.health);
		writer.println(ship.maxHealth);
		writer.println(ship.maxDepth);
		writer.println();
		writer.flush();
		
		// money 
		writer.println((int)ship.money);
		writer.println();
		writer.flush();

		//consumables
			
		for (int i = 0; i < ship.maxItemSlots; i++){
			if(ship.consumables[i] instanceof Net){
				writer.print("1 ");
			}else if (ship.consumables[i] instanceof RepairKit){
				writer.print("2 ");
			}else if(ship.consumables[i] instanceof FuelCanister){
				writer.print("3 ");
			}else{
				writer.print("0 ");
			}
		}
		writer.println();
		
		//upgrades
		writer.println(ship.drillSpeedIndex);
		writer.println(ship.drillStrengthIndex);
		writer.println(ship.maxItemSlots);
		writer.println(ship.fuelCapacityIndex);
		writer.println(ship.fuelEfficiencyIndex);
		writer.println(ship.hullDepthIndex);
		writer.println(ship.hullHealthIndex);
		writer.println(ship.cargoItemSlotsIndex);
		writer.println(ship.cargoInventoryIndex);
		writer.println();
		
		// death inventory
		for (int i = 0; i < ship.deathInventory.length; i++){
			writer.print(ship.deathInventory[i] + " ");
		}
		
		writer.println();
		for (int j = 0; j < height; j++) {
			for (int i = 0; i <= width + 14; i++) {
				int next = tiles[i][j].tileType;

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
		Sound.PositiveFeedback.play();
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("map.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		Scanner map = null;
		try {
			map = new Scanner(new File("mapSave.txt"));
			// inventory
			for (int i = 0; i < ship.inventory.length; i++){
				ship.inventory[i] = map.nextInt();
			}
			
			ship.maxInventory = map.nextInt();
			ship.curInventory = map.nextInt();
			ship.topOre = map.nextInt();
			ship.drill = map.nextInt();
			
			// fuel

			ship.fuel = map.nextInt();
			ship.maxFuel = map.nextDouble();
			ship.fuelCost = map.nextDouble();
			
			
			// health
			ship.health = map.nextInt();
			ship.maxHealth = map.nextDouble();
			ship.maxDepth = map.nextInt();
					
			// money 
			ship.money = map.nextInt();
			
			//consumables
			for (int i = 0; i < ship.maxItemSlots; i++){
				int next = map.nextInt();
				if (next == 1){
					ship.consumables[i] = new Net(i);
				}else if(next == 2){
					ship.consumables[i] = new RepairKit(i);
				}else if(next == 3){
					ship.consumables[i] = new FuelCanister(i);
				}else{
					ship.consumables[i] = null;
				}
			}
			
			//upgrades
			CraftingBuilding upgrades = (CraftingBuilding) MyGame.buildings[2];
			ship.drillSpeedIndex=map.nextInt();
			upgrades.curSpeed=ship.drillSpeedIndex+1;
			ship.drillStrengthIndex=map.nextInt();
			upgrades.curMinable=ship.drillStrengthIndex+1;
			ship.maxItemSlots = map.nextInt();
			ship.fuelCapacityIndex = map.nextInt();
			upgrades.curMaxFuel = ship.fuelCapacityIndex+1;
			ship.fuelEfficiencyIndex = map.nextInt();
			upgrades.curFuelEff = ship.fuelEfficiencyIndex+1;
			ship.hullDepthIndex = map.nextInt();
			upgrades.curMaxDepth = ship.hullDepthIndex+1;
			ship.hullHealthIndex = map.nextInt();
			upgrades.curMaxHealth = ship.hullHealthIndex+1;
			ship.cargoItemSlotsIndex = map.nextInt();
			upgrades.curItemSlots = ship.cargoItemSlotsIndex+1;
			ship.cargoInventoryIndex = map.nextInt();
			upgrades.curInventory = ship.cargoInventoryIndex+1;
			
			// death inventory
			for (int i = 0; i < ship.deathInventory.length; i++){
				ship.deathInventory[i] = map.nextInt();
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
