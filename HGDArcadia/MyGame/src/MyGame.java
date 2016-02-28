import java.awt.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

import arcadia.*;
import arcadia.Button;
import basicGame.BasicGame;
import intro.IntroGame;
import shooter.Shooter;

public class MyGame extends Game {

	Image banner;
	public static Tile[][] tiles = new Tile[55][1001];
	int startx = 10;
	int starty = 0;
	float deltaX = 0;
	float deltaY = 0;
	float accel = 0.01f;
	float upSpeed = 0;
	float downSpeed = 0;
	long startTime = System.currentTimeMillis();
	private final int width = 40;
	private final int height = 1000;
	public static final int tileSizeW = WIDTH / 9;
	public static final int tileSizeH = HEIGHT / 8;
	public static Ship ship = new Ship(WIDTH, HEIGHT, tileSizeH, tileSizeW);
	int diggingTime = 0;
	boolean digging = false;
	Tile digTile = null;
	int diggingDirection = 0;
	Building[] buildings = new Building[3];
	Scanner map = null;
	char lastDirection;
	ArrayList<Particle> particles = new ArrayList<Particle>();
	public static OreData[] tileData = new OreData[20];

	ArrayList<Integer> notMineable = new ArrayList<Integer>(10);
	int[] passables = { 0, 96, 97, 99 };

	public static boolean loadingGame = false;

	public MyGame() {
		// System.out.println(tileSizeW + " : " + tileSizeH);
		try {
			banner = ImageIO.read(MyGame.class.getResource("banner.png"));
		} catch (IOException e) {
			System.out.println("NO BANNER FOUND");
			e.printStackTrace();
		}
		createMap();
		try {
			map = new Scanner(new File("map.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		createTiles();
		createOres();

		buildings[0] = new Store();
		buildings[1] = new SaveLocation(tiles, height, width);
		buildings[2] = new CraftingBuilding();
		
		notMineable.add(7);
		notMineable.add(98);
		for(int i = 3; i<20; i++){
		notMineable.add(i);
		}

	}

	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		g.setColor(Color.WHITE); // Set the background color and draw it
		g.fillRect(0, 0, WIDTH, HEIGHT);

		if (loadingGame)
			createTiles();

		for (int i = 0; i <= 3; i++) {
			if (i == 3) {
				if (ship.fuel != 0)
					checkMovement(p1, p2, s); // Executes all code involving movement and digging
				drawTiles(g); // Draws all the tiles
				ship.drawShip(lastDirection, g, 1, 0, 0); // Draws the ship
				ship.drawInterface(g); // Draws the interface
			}
			else if (buildings[i].isInside()) {
				buildings[i].buildingControls(p1,p2);
				buildings[i].drawBuilding(g);
				break;
			}
		}
		
//		if (buildings[0].isInside()) {
//			buildings[0].buildingControls(p1, p2);
//			buildings[0].drawBuilding(g);
//		} else if (buildings[1].isInside()) {
//			buildings[1].buildingControls(p1, p2);
//			buildings[1].drawBuilding(g);
//		} else if (buildings[2].isInside()) {
//			buildings[2].buildingControls(p1,p2);
//			buildings[2].drawBuilding(g);
//		} else {
//			if (ship.fuel != 0)
//				checkMovement(p1, p2, s); // Executes all code involving movement and digging
//			drawTiles(g); // Draws all the tiles
//			ship.drawShip(lastDirection, g, 1, 0, 0); // Draws the ship
//			ship.drawInterface(g); // Draws the interface
//		}

		Particle.drawParticles(particles, g);
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

	public void checkMovement(Input p1, Input p2, Sound s) {
		Tile upleft = tiles[startx + 4][starty + 3];
		Tile downleft = tiles[startx + 4][starty + 5];
		Tile upright = tiles[startx + 6][starty + 3];
		Tile downright = tiles[startx + 6][starty + 5];
		Tile left = tiles[startx + 4][starty + 4];
		Tile right = tiles[startx + 6][starty + 4];
		Tile down = tiles[startx + 5][starty + 5];
		Tile up = tiles[startx + 5][starty + 3];
		Tile player = tiles[startx + 5][starty + 4];

		if (!digging) {
			if (player.tileType == 99) {
				if (p1.pressed(Button.D)) {
					buildings[0].enter();
				}
			}
			else if (player.tileType == 97) {
				if (p1.pressed(Button.D)) {
					buildings[1].enter();
				}
			}
			else if (player.tileType == 96) {
				if (p1.pressed(Button.D)) {
					buildings[2].enter();
				}
			}

			if (!isPassable(down.tileType) && deltaY == 0)
				downSpeed = 0;
			
if (!p1.pressed(Button.U)
		&& isPassable(down.tileType)
		&& (Math.abs(deltaX) < .2 || ((isPassable(downleft.tileType) && deltaX >= .2) || (isPassable(downright.tileType) && deltaX <= -.2)))) {
				upSpeed = 0;
				lastDirection = 'u';
				deltaY -= (downSpeed);
				downSpeed += accel;
				if (deltaY < -1) {
					starty++;
					deltaY = 0;
				}
			} else if (deltaY > 0) {
				deltaY -= .1;
				if (deltaY < 0) {
					deltaY = 0;
				}
			} //else if (player.tileType == 97) {
				//if (p1.pressed(Button.D)) {
					//buildings[1].enter();
				//}
			//}

			if (p1.pressed(Button.L)) {
				lastDirection = 'l';
				if (deltaX < 0) {
					deltaX += .1;
					if (deltaX > 0) {
						deltaX = 0;
						ship.fuel--;
						
					}
				} else if (isMineable(left.tileType) && (Math.abs(deltaY) < .1 || (upleft.tileType == 0 && deltaY > 0)
						|| (downleft.tileType == 0 && deltaY < 0))) {
					if (isPassable(left.tileType)) {
						deltaX += .1;
						if (deltaX > 0.5) {
							startx--;
							deltaX = -0.5f;
							ship.fuel--;
						}
					} else if (down.tileType != 0 && Math.abs(deltaX) < 0.01 && Math.abs(deltaY) < 0.01) {
						digTile = left;
						diggingDirection = 1;
						digging = dig(digTile, diggingDirection);
						ship.fuel--;
					}
				}

			} // Move left if player hit left
			if (p1.pressed(Button.R)) {
				lastDirection = 'r';
				if (deltaX > 0) {
					deltaX -= .1;
					if (deltaX < 0) {
						deltaX = 0;
						ship.fuel--;
					}
				} else if (isMineable(right.tileType) && (Math.abs(deltaY) < .1 || (upright.tileType == 0 && deltaY > 0)
						|| (downright.tileType == 0 && deltaY < 0))) {
					if (isPassable(right.tileType)) {
						deltaX -= .1;
						if (deltaX < -0.5) {
							startx++;
							deltaX = 0.5f;
							ship.fuel--;
						}
					} else if (down.tileType != 0 && Math.abs(deltaX) < 0.01 && Math.abs(deltaY) < 0.01) {
						digTile = right;
						diggingDirection = 2;
						digging = dig(digTile, diggingDirection);
						ship.fuel--;
					}
				} else if (deltaX != 0) {
					deltaX -= .1;
					if (deltaX < 0) {
						deltaX = 0;
						ship.fuel--;
					}
				}
			} // Move right if player hit right
			if (p1.pressed(Button.D)) {
				lastDirection = 'd';
				if (isMineable(down.tileType) && starty < height - 9 && ((int) (deltaX * 10) == 0
						|| ((downleft.tileType == 0 || deltaX < 0) && (downright.tileType == 0 || deltaX > 0)))) {
					if (isPassable(down.tileType)) {
						deltaY -= .1;
						if (deltaY < -.5) {
							starty++;
							deltaY = 0;
							ship.fuel--;
						}
					} else {
						digTile = down;
						diggingDirection = 3;
						digging = dig(digTile, diggingDirection);
						ship.fuel--;
					}
				}
			}
			if (p1.pressed(Button.U)) {
				downSpeed = 0;
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
				if (deltaY < 0) {
					deltaY += upSpeed;
					if (deltaY > 1) {
						starty--;
						deltaY = 0;
						ship.fuel--;
					}

				}
				if (isPassable(up.tileType) && (Math.abs(deltaX) < .1
						|| ((upleft.tileType == 0 && deltaX > 0) || (upright.tileType == 0 && deltaX < 0)))) {
					if (starty > 1) {
						if (up.tileType == 0) {
							upSpeed += accel;
							deltaY += upSpeed;
							if (deltaY > 1) {
								starty--;
								deltaY = 0;
								ship.fuel--;
							}
						} else if (down.tileType == 0) {
							upSpeed += accel;
							deltaY += upSpeed;
							if (deltaY > 1) {
								starty--;
								deltaY = 0;
								ship.fuel--;
							}
						}
					}
				}
			} // Move up if player hit up
		} else {
			digging = dig(digTile, diggingDirection);
		}
	}

	public void createTiles() {
		try {
			map = new Scanner(new File("map.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
			digtime = tileData[tile.tileType].getTough() - ship.drill < 10 ? 10 : tileData[tile.tileType].getTough() - ship.drill;
			if (tile.tileType != 1) {
				if (ship.curInventory + tileData[tile.tileType].getStorageSpace() <= ship.maxInventory) {
					ship.inventory[tile.tileType]++;
					ship.curInventory += tileData[tile.tileType].getStorageSpace();
					System.out.println(
							"Current Inventory: " + ship.curInventory + ", Max Inventory: " + ship.maxInventory);
				} else {
					System.out.println("Ship's Inventory was too full to store ore");
				}
			}
			tile.tileType = 0;
			if (d == 3) { // down
				moveDeltaY = (float) (-1 / (float)digtime);
			} else if (d == 2) { // right
				moveDeltaX = (float) (-1 / (float) digtime);
			} else { // left
				moveDeltaX = - (float) (-1 / (float) digtime);
			}
		}
		diggingTime++;

		deltaX += moveDeltaX;
		deltaY += moveDeltaY;

		if (diggingTime == digtime-1) {
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
		OreData	air	= new OreData(0,0,0,50);
		OreData dirt = new OreData(0, 0, 1, 50);
		OreData ore1 = new OreData(5, 1, 2, 100);
		OreData ore2 = new OreData(10, 1, 3, 50);
		OreData ore3 = new OreData(20, 1, 4, 50);
		OreData ore4 = new OreData(40, 1, 5, 50);
		OreData ore5 = new OreData(80, 1, 6, 50);
		OreData ore6 = new OreData(160, 1, 8, 50);
		OreData ore7 = new OreData(320, 1, 9, 50);
		OreData ore8 = new OreData(640, 1, 10, 50);
		OreData ore9 = new OreData(1280, 1, 11, 50);
		OreData ore10 = new OreData(2560, 1, 12, 50);
		OreData ore11 = new OreData(5120, 1, 13, 50);
		OreData ore12 = new OreData(10240, 1, 14, 50);
		OreData ore13 = new OreData(20480, 1, 15, 50);
		OreData ore14 = new OreData(40960, 1, 16, 50);
		OreData ore15 = new OreData(81920, 1, 17, 50);
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

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	/*
	 * Banner being displayed on the Arcadia launcher
	 */
	@Override
	public Image banner() {
		// 512x128
		return banner;
	}

	public static void main(String[] args) {
		Arcadia.display(new Arcadia(new Game[] { new MyGame(), new IntroGame(), new BasicGame(), new Shooter() }));
	}
}