import java.awt.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

import arcadia.*;
import arcadia.Button;
import dodge.DodgeGame;
import intro.IntroGame;
import shooter.Shooter;

public class MyGame extends Game {

	Image banner;
	public static Tile[][] tiles = new Tile[55][1010];
	int startx = 10;
	int starty = 10;
	int deathx;
	int deathy;
	float deltaX = 0;
	float deltaY = 0;
	float accel = 0.005f;
	float speed = .05f;
	long startTime = System.currentTimeMillis();
	private final int width = 40;
	private final int height = 1010;
	public static final int tileSizeW = WIDTH / 9;
	public static final int tileSizeH = HEIGHT / 8;
	public static Ship ship = new Ship(WIDTH, HEIGHT, tileSizeH, tileSizeW);
	int diggingTime = 0;
	boolean digging = false;
	Tile digTile = null;
	int diggingDirection = 0;
	Building[] buildings = new Building[5];
	Scanner map = null;
	char lastDirection = 'u';
	ArrayList<Particle> particles = new ArrayList<Particle>();
	public static OreData[] tileData = new OreData[20];
	boolean gameStarted = false;
	long movementSoundEnd = -1;
	long backgroundMusicEnd = -1;
	long coinNoiseEnd = -1;
	long menuMusicEnd = -1;
	int depth;
	

	public static String loopingMusic = "";
	String playingMusic = "";

	static ArrayList<Integer> notMineable = new ArrayList<Integer>(10);
	int[] passables = { 0, 25, 95, 96, 97, 99 };

	public static boolean loadingGame = false;

	public MyGame() {

		try {
			banner = ImageIO.read(MyGame.class.getResource("images/banner.png"));
		} catch (IOException e) {
			System.out.println("NO BANNER FOUND");
			e.printStackTrace();
		}
		createMap();
		try {
			map = new Scanner(new File("map.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		createTiles();
		createOres();

		ship.consumables[0] = new Net(3);
		ship.consumables[1] = new RepairKit(3);
		ship.consumables[2] = new FuelCanister(3);
		
		buildings[0] = new Store();
		buildings[1] = new SaveLocation(tiles, height, width, ship);
		buildings[2] = new CraftingBuilding();
		buildings[3] = new InventoryScreen();
		buildings[4] = new GasStation();

		notMineable.add(7);
		notMineable.add(98);
		for (int i = 3; i < 20; i++) {
			notMineable.add(i);
		}

	}

	/*
	 * Checks to see if, how, where, and when the ship can dig or move
	 * 
	 * @param p1 Player 1 controls
	 * 
	 * @param p2 Player 2 controls
	 * 
	 * @param s Sound to be played while digging or moving
	 */
	public void checkMovement(Input p1, arcadia.Sound s) {
		Tile player = tiles[startx + 5][starty + 4];
		if (ship.maxDepth < depth) {
			ship.health -= ship.maxHealth * .001;
		}
		if (!digging) {

			if (p1.pressed(Button.A)) {
				if (starty > 20)
					ship.consumables[0].use(ship, player);
			}

			if (player.tileType == 25) {
				if (speed > 0) {
					speed = .01f;
				} else {
					speed = -.01f;
				}
			}
			if (player.tileType == 99) {
				if (p1.pressed(Button.D)) {
					playSound("menu");
					buildings[0].enter();

				}
			} else if (player.tileType == 97) {
				if (p1.pressed(Button.D)) {
					playSound("menu");
					buildings[1].enter();
				}
			} else if (player.tileType == 96) {
				if (p1.pressed(Button.D)) {
					playSound("menu");
					buildings[2].enter();
				}
			} else if (player.tileType == 95) {
				if (p1.pressed(Button.D)) {
					buildings[4].enter();
				}
			}

			updateMove(p1);

			if (p1.pressed(Button.C)) {
				playSound("menu");
				buildings[3].enter();
			}
			if (p1.pressed(Button.L)) {
				lastDirection = 'l';
				moveLeft();

			} // Move left if player hit left
			else if (p1.pressed(Button.R)) {
				lastDirection = 'r';
				moveRight();

			} // Move right if player hit right
			else if (p1.pressed(Button.D)) {
				lastDirection = 'd';
				moveDown();

			}
			if (p1.pressed(Button.U)) {
				lastDirection = 'u';
				if (particles.size() >= 106) {
					for (int i = particles.size() - 5; i < particles.size(); i++) {
						particles.remove(i);
					}
				}
				for (int i = 0; i < 5; i++) {
					particles.add(0, new Particle(550, 260));
					particles.add(0, new Particle(455, 260));
				}
				moveUp();

			} // Move up if player hit up
		} else {
			digging = dig(digTile, diggingDirection);
		}
	}

	// function to move left
	public void moveLeft() {
		Tile upleft = tiles[startx + 4][starty + 3];
		Tile downleft = tiles[startx + 4][starty + 5];
		Tile left = tiles[startx + 4][starty + 4];
		Tile down = tiles[startx + 5][starty + 5];

		if (deltaX < 0) { // space before block
			deltaX += .1;
			if (deltaX > 0) {
				deltaX = 0;
				ship.fuel -= ship.fuelCost;
			}
		} else
			if (isPassable(left.tileType) && ((Math.abs(deltaY) < .1) || (isPassable(upleft.tileType) && deltaY >= 0.1)
					|| (isPassable(downleft.tileType) && deltaY <= -0.1))) {
			// open space to the left
			deltaX += .1;
			if (deltaX > 0.5) {
				startx--;
				deltaX = -0.5f;
				ship.fuel -= ship.fuelCost;

			}
		} else if (isMineable(left.tileType) && !isPassable(down.tileType) && Math.abs(deltaX) < 0.01
				&& Math.abs(deltaY) < 0.01) {
			// mine to the left
			digTile = left;
			diggingDirection = 1;
			digging = dig(digTile, diggingDirection);
			ship.fuel -= ship.fuelCost;
		}

	}

	// function to move right
	public void moveRight() {
		Tile upright = tiles[startx + 6][starty + 3];
		Tile downright = tiles[startx + 6][starty + 5];
		Tile right = tiles[startx + 6][starty + 4];
		Tile down = tiles[startx + 5][starty + 5];

		if (deltaX > 0) {// space before block
			deltaX -= .1;
			if (deltaX < 0) {
				deltaX = 0;
				ship.fuel -= ship.fuelCost;
			}
		} else if (isPassable(right.tileType)
				&& ((Math.abs(deltaY) < .1) || (isPassable(upright.tileType) && deltaY >= 0.1)
						|| (isPassable(downright.tileType) && deltaY <= -0.1))) {
			// open space to the right
			deltaX -= .1;
			if (deltaX < -0.5) {
				startx++;
				deltaX = 0.5f;
				ship.fuel -= ship.fuelCost;
			}
		} else if (isMineable(right.tileType) && !isPassable(down.tileType) && Math.abs(deltaX) < 0.01
				&& Math.abs(deltaY) < 0.01) {
			// dig tile to the right
			digTile = right;
			diggingDirection = 2;
			digging = dig(digTile, diggingDirection);
			ship.fuel -= ship.fuelCost;
		}
	}

	// function to move down (only allowed to dig)
	public void moveDown() {
		Tile down = tiles[startx + 5][starty + 5];
		if (!isPassable(down.tileType) && isMineable(down.tileType) && Math.abs(deltaX) < 0.5
				&& Math.abs(deltaY) < 0.1) {
			digTile = down;
			diggingDirection = 3;
			digging = dig(digTile, diggingDirection);
			ship.fuel -= ship.fuelCost;
		}
	}

	// function to move up
	public void moveUp() {
		Tile upleft = tiles[startx + 4][starty + 3];
		Tile upright = tiles[startx + 6][starty + 3];
		Tile up = tiles[startx + 5][starty + 3];
		if (deltaY < 0) { // space between block
			speed += accel;
		} else if (isPassable(up.tileType) && (Math.abs(deltaX) < .1 || (isPassable(upleft.tileType) && deltaX >= 0.1)
				|| (isPassable(upright.tileType) && deltaX <= -0.1))) {
			// open space above, not allowed to mine up
			speed += accel;
			if (speed < 0)
				speed += accel;
		}

	}

	// function that updates position based on speed and inertia
	public void updateMove(Input p1) {
		Tile upleft = tiles[startx + 4][starty + 3];
		Tile downleft = tiles[startx + 4][starty + 5];
		Tile upright = tiles[startx + 6][starty + 3];
		Tile downright = tiles[startx + 6][starty + 5];
		Tile down = tiles[startx + 5][starty + 5];
		Tile up = tiles[startx + 5][starty + 3];

		float maxSpeed = 1f;
		float minSpeed = -1f;

		if (!isPassable(up.tileType) || starty == 1 || (!isPassable(upleft.tileType) && deltaX >= 0.2)
				|| (!isPassable(upright.tileType) && deltaX <= -0.2)) {
			if ((deltaY + speed) > 0) {
				// hit a ceiling
				speed = 0;
				deltaY = 0;
			}
		}
		if (!isPassable(down.tileType)) {
			if ((deltaY + speed) < 0) {
				if (Math.abs(speed) > .15)
					ship.health -= (Math.abs(speed) * ship.maxHealth);
				if (ship.health < 0)
					ship.health = 0;
				speed = 0;
				deltaY = 0;
			}
		}

		if (!p1.pressed(Button.U) && (isPassable(down.tileType) && (Math.abs(deltaX) < .2
				|| (isPassable(downleft.tileType) && deltaX >= .2) || (isPassable(downright.tileType) && deltaX <= -.2))
				|| deltaY > 0)) {
			speed -= accel;
		}

		speed = speed > maxSpeed ? maxSpeed : speed;
		speed = speed < minSpeed ? minSpeed : speed;

		if (speed < 0) {
			if ((isPassable(down.tileType) && (Math.abs(deltaX) < .2 || (isPassable(downleft.tileType) && deltaX >= .2)
					|| (isPassable(downright.tileType) && deltaX <= -.2)) || deltaY > 0)) {
				deltaY += speed;
			}
		} else if (speed > 0) {
			if (isPassable(up.tileType) && (Math.abs(deltaX) < .2 || (isPassable(upleft.tileType) && deltaX >= .2)
					|| (isPassable(upright.tileType) && deltaX <= -.2)) || deltaY < 0) {
				deltaY += speed;
			}
		}

		if (deltaY > 1) {
			starty--;
			deltaY = 0;
			if (p1.pressed(Button.U))
				ship.fuel -= ship.fuelCost;
		} else if (deltaY < -1) {
			starty++;
			deltaY = 0;
			if (p1.pressed(Button.U))
				ship.fuel -= ship.fuelCost;
		}

	}

	public void death() {
		Tile grave = tiles[startx + 5][starty + 4];
		Tile adjGrave = tiles[startx + 6][starty + 4];
		ship.fuel = ship.maxFuel;
		ship.health = ship.maxHealth;
		speed = 0;
		deathx = startx;
		deathy = starty;
		startx = 10;
		starty = 10;
		deltaX = 0;
		deltaY = 0;
		if (grave.tileType < 50)
			grave.tileType = 21;
		else
			adjGrave.tileType = 21;
		ship.money = ship.money/2;
		ship.curInventory = 0;
		for (int i = 0; i < ship.inventory.length; i++) {
			ship.deathInventory[i] = ship.inventory[i] / 2;
			ship.inventory[i] = 0;
		}
		Sound.Death.play();
	}

	public void createTiles() {
		try {
			map = new Scanner(new File("map.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width + 15; i++) {
				if (map.hasNext()) {
					tiles[i][j] = new Tile(map.nextInt(), i, j, tileSizeW, tileSizeH);
				}
			}
		}
	}

	/*
	 * Draws all the tiles on the map within view range of the ship
	 * 
	 * @param g Graphics Object
	 */
	public void drawTiles(Graphics2D g) {
		float k = -1 + deltaX;
		for (int i = startx; i <= startx + 10; i++) {
			float h = -1 + deltaY;
			for (int j = starty; j <= starty + 10; j++) {
				tiles[i][j].drawTile(g, (k), (h));
				h++;
			}
			k++;
		}
	}

	/*
	 * Creates a new map.txt file
	 */
	public void createMap() {
		InitializeMap map1 = new InitializeMap(width, height);
	}

	float moveDeltaX = 0; // Used in dig method to find out how far to move
							// across each tile
	float moveDeltaY = 0;

	int digtime;

	/*
	 * Removes the tile being dug out and moves the ship accordingly
	 * 
	 * @param tile Tile being dug out
	 * 
	 * @param d Direction ship is moving
	 */
	public boolean dig(Tile tile, int d) {
		digging = true;

		if (diggingTime == 0) {

			if (tile.tileType != 21) {
				digtime = (tileData[tile.tileType].getTough() - ship.drill < 10 ? 10
						: tileData[tile.tileType].getTough() - ship.drill);
			} else
				digtime = 10;

			playSound("movement");
			if (tile.tileType != 1 && tile.tileType != 21) {
				if (ship.curInventory + tileData[tile.tileType].getStorageSpace() <= ship.maxInventory) {
					ship.inventory[tile.tileType]++;
					ship.curInventory += tileData[tile.tileType].getStorageSpace();
					System.out.println(
							"Current Inventory: " + ship.curInventory + ", Max Inventory: " + ship.maxInventory);
				} else {
					System.out.println("Ship's Inventory was too full to store ore");
				}
			} else if (tile.tileType == 21) {
				int tempSize = ship.maxInventory;
				for (int i = ship.inventory.length - 1; i > 0; i--) {
					if (ship.inventory[i] + ship.deathInventory[i] > tempSize) {
						ship.inventory[i] = tempSize;
						tempSize = 0;
					} else {
						ship.inventory[i] += ship.deathInventory[i];
						tempSize -= ship.inventory[i];
					}
					if (tempSize == 0) {
						for (int j = i - 1; j > 0; j--) {
							ship.inventory[j] = 0;
						}
						ship.curInventory = ship.maxInventory;
						break;
					}
				}
				ship.curInventory = (ship.maxInventory - tempSize);
			}
			tile.tileType = 0;
			if (d == 3) { // down
				if(deltaX > .1)
					moveDeltaX = (float) (-deltaX / (float) digtime);
				else if (deltaX < -.1)
					moveDeltaX = (float) (-deltaX / (float) digtime);
				moveDeltaY = (float) (-1 / (float) digtime);
			} else if (d == 2) { // right
				moveDeltaX = (float) (-1 / (float) digtime);
			} else { // left
				moveDeltaX = (float) (1 / (float) digtime);
			}
		}
		diggingTime++;

		deltaX += moveDeltaX;
		deltaY += moveDeltaY;

		if (diggingTime == digtime - 1) {
			moveDeltaX = 0;
			moveDeltaY = 0;
			if (d == 3) { // down
				starty++;
				deltaY = 0;
			} else if (d == 2) { // right
				startx++;
				deltaX = 0;
			} else { // left
				startx--;
				deltaX = 0;
			}
			diggingTime = 0;
			return false;
		}
		return true;
	}

	public boolean isMineable(int tile) {
		boolean mineable = true;
		for (int i = 0; i < notMineable.size(); i++) {
			if (notMineable.get(i) == tile) {
				mineable = false;
			}
		}
		return mineable;
	}

	public boolean isPassable(int tile) {
		boolean passable = false;
		for (int i = 0; i < passables.length; i++) {
			if (passables[i] == tile) {
				passable = true;
			}
		}
		return passable;
	}

	public void createOres() {
		OreData air = new OreData(0, 0, 0, 0);
		OreData dirt = new OreData(0, 0, 1, 50);
		OreData grave = new OreData(0, 0, 21, 0);
		OreData ore1 = new OreData(5, 1, 2, 70);
		OreData ore2 = new OreData(10, 1, 3, 90);
		OreData ore3 = new OreData(20, 1, 4, 110);
		OreData ore4 = new OreData(40, 1, 5, 130);
		OreData ore5 = new OreData(80, 1, 6, 150);
		OreData ore6 = new OreData(160, 1, 8, 170);
		OreData ore7 = new OreData(320, 1, 9, 190);
		OreData ore8 = new OreData(640, 1, 10, 210);
		OreData ore9 = new OreData(1280, 1, 11, 250);
		OreData ore10 = new OreData(2560, 1, 12, 250);
		OreData ore11 = new OreData(5120, 1, 13, 250);
		OreData ore12 = new OreData(10240, 1, 14, 250);
		OreData ore13 = new OreData(20480, 1, 15, 250);
		OreData ore14 = new OreData(40960, 1, 16, 250);
		OreData ore15 = new OreData(81920, 1, 17, 250);
		tileData[0] = air;
		tileData[1] = dirt;
		tileData[2] = ore1;
		tileData[3] = ore2;
		tileData[4] = ore3;
		tileData[5] = ore4;
		tileData[6] = ore5;
		tileData[8] = ore6;
		tileData[9] = ore7;
		tileData[10] = ore8;
		tileData[11] = ore9;
		tileData[12] = ore10;
		tileData[13] = ore11;
		tileData[14] = ore12;
		tileData[15] = ore13;
		tileData[16] = ore14;
		tileData[17] = ore15;
	}

	/*
	 * Banner being displayed on the Arcadia launcher
	 */
	@Override
	public Image cover() {
		// 512x128
		return banner;
	}

	public void playSound(String soundType) {

		long cur = System.currentTimeMillis();
		if (soundType.compareTo("movement") == 0) {
			double diggingtime = digtime / 30.0;
			movementSoundEnd = (long) (cur + (diggingtime * (900.0)));
			Sound.Movement.play();
		} else if (soundType.compareTo("background") == 0) {
			backgroundMusicEnd = cur + 30000;
			menuMusicEnd = -1;
			Sound.backgroundMusic.play();
			loopingMusic = "background";
			playingMusic = "background";
		} else if (soundType.compareTo("coin") == 0) {
			Sound.coinNoise.play();
		} else if (soundType.compareTo("menu") == 0) {

			menuMusicEnd = cur + 68000;
			backgroundMusicEnd = -1;
			Sound.MenuMusic.play();
			loopingMusic = "menu";
			playingMusic = "menu";

		}

	}

	public static void main(String[] args) {
		Arcadia.display(new Arcadia(new Game[] { new MyGame(), new IntroGame(), new DodgeGame(), new Shooter() }));
	}

	@Override
	public void tick(Graphics2D g, Input p1, arcadia.Sound s) {
		long cur = System.currentTimeMillis() + 2000;
		g.setColor(Color.WHITE); // Set the background color and draw it
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 52));

		String d = Integer.toString(depth);
		depth = (starty - 13) * 10;
		if (loadingGame)
			createTiles();

		if (!gameStarted) {
			gameStarted = true;
			backgroundMusicEnd = cur + 30000;
			menuMusicEnd = -1;
			Sound.backgroundMusic.play();
			loopingMusic = "background";
			playingMusic = "background";
		}
		for (int i = 0; i <= 5; i++) {
			if (i == 5) {
				if (ship.fuel <= 0 || ship.health <= 0) {
					death();
				}
				checkMovement(p1, s); // Executes all code involving
										// movement and digging
				drawTiles(g); // Draws all the tiles
				ship.drawShip(lastDirection, g, 1, 0, 0); // Draws the ship
				ship.drawInterface(g);// Draws the interface`
				g.drawString(d + "M", WIDTH / 2, HEIGHT / 13);
			} else if (buildings[i].isInside()) {
				buildings[i].buildingControls(p1);
				buildings[i].drawBuilding(g);
				break;
			}
		}
		Particle.drawParticles(particles, g);

		if (System.currentTimeMillis() > movementSoundEnd) {
			Sound.Movement.stop();
		}
		if (cur > backgroundMusicEnd) {
			if (loopingMusic.compareTo("background") == 0) {
				playSound("background");
			} else {
				Sound.backgroundMusic.stop();
			}
		}
		if (cur > menuMusicEnd) {
			if (loopingMusic.compareTo("menu") == 0) {
				playSound("menu");
			} else {
				Sound.MenuMusic.stop();
			}
		}
		if (playingMusic.compareTo(loopingMusic) != 0) {
			playSound(loopingMusic);
		}
	}
}