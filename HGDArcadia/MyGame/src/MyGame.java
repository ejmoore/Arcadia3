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
	Tile[][] tiles = new Tile[55][1001];
	int startx = 10;
	int starty = 0;
	float deltaX = 0;
	float deltaY = 0;
	long startTime = System.currentTimeMillis();
	private final int width = 40;
	private final int height = 1000;
	private static final int tileSizeW = WIDTH / 9;
	private static final int tileSizeH = HEIGHT / 8;
	public static Ship ship = new Ship(WIDTH, HEIGHT, tileSizeH, tileSizeW);
	int diggingTime = 0;
	boolean digging = false;
	Tile digTile = null;
	int diggingDirection = 0;
	Building[] buildings = new Building[3];
	Scanner map = null;
	char lastDirection;
	ArrayList<Particle> particles = new ArrayList<Particle>();

	int[] notMineable = { 7, 98 };
	int[] passables = { 0, 97, 99 };

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

		buildings[0] = new Store();
		buildings[1] = new SaveLocation(tiles);
	}

	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		g.setColor(Color.WHITE); // Set the background color and draw it
		g.fillRect(0, 0, WIDTH, HEIGHT);

		if (loadingGame)
			createTiles();

		if (buildings[0].isInside()) {
			buildings[0].buildingControls(p1, p2);
			buildings[0].drawBuilding(g);
		} else if (buildings[1].isInside()) {
			buildings[1].buildingControls(p1, p2);
			buildings[1].drawBuilding(g);
		} else {
			if (ship.fuel != 0)
				checkMovement(p1, p2, s); // Executes all code involving
											// movement anddigging
			drawTiles(g); // Draws all the tiles
			ship.drawShip(lastDirection, g); // Draws the ship
			ship.drawInterface(g); // Draws the interface
		}

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
					// saveTheGame();
					buildings[0].enter();
				}
			}

			if (!p1.pressed(Button.U)
					&& isPassable(down.tileType)
					&& (Math.abs(deltaX) < .1 || ((isPassable(downleft.tileType) && deltaX >= .2) || (isPassable(downright.tileType) && deltaX <= -.2)))) {
				lastDirection = 'u';
				deltaY -= .1;
				if (deltaY < -1) {
					starty++;
					deltaY = 0;
				}
			} else if (deltaY > 0) {
				deltaY -= .1;
				if (deltaY < 0) {
					deltaY = 0;
				}
			} else if (player.tileType == 97) {
				if (p1.pressed(Button.D)) {
					buildings[1].enter();
				}
			}

			if (p1.pressed(Button.L)) {
				lastDirection = 'l';
				if (deltaX < 0) {
					deltaX += .1;
					if (deltaX > 0) {
						deltaX = 0;
						ship.fuel--;
					}
				} else if (isMineable(left.tileType)
						&& (Math.abs(deltaY) < .1
								|| (upleft.tileType == 0 && deltaY > 0) || (downleft.tileType == 0 && deltaY < 0))) {
					if (isPassable(left.tileType)) {
						deltaX += .1;
						if (deltaX > 0.5) {
							startx--;
							deltaX = -0.5f;
							ship.fuel--;
						}
					} else if (down.tileType != 0 && Math.abs(deltaX) < 0.01
							&& Math.abs(deltaY) < 0.01) {
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
				} else if (isMineable(right.tileType)
						&& (Math.abs(deltaY) < .1
								|| (upright.tileType == 0 && deltaY > 0) || (downright.tileType == 0 && deltaY < 0))) {
					if (isPassable(right.tileType)) {
						deltaX -= .1;
						if (deltaX < -0.5) {
							startx++;
							deltaX = 0.5f;
							ship.fuel--;
						}
					} else if (down.tileType != 0 && Math.abs(deltaX) < 0.01
							&& Math.abs(deltaY) < 0.01) {
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
				if (isMineable(down.tileType)
						&& starty < height - 9
						&& ((int) (deltaX * 10) == 0 || ((downleft.tileType == 0 || deltaX < 0) && (downright.tileType == 0 || deltaX > 0)))) {
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
				lastDirection = 'u';
				if (particles.size() >= 106) {
					for (int i = particles.size()-5; i < particles.size(); i++) {
						particles.remove(i);
					}
				}
				for (int i = 0; i < 5; i++) {
					particles.add(0, new Particle(550, 260));
					particles.add(0, new Particle(455, 260));
				}
				if (deltaY < 0) {
					deltaY += .1;
					if (deltaY > 1) {
						starty--;
						deltaY = 0;
						ship.fuel--;
					}

				}
				if (isPassable(up.tileType)
						&& (Math.abs(deltaX) < .1 || ((upleft.tileType == 0 && deltaX > 0) || (upright.tileType == 0 && deltaX < 0)))) {
					if (starty > 1) {
						if (up.tileType == 0) {
							deltaY += .2;
							if (deltaY > 1) {
								starty--;
								deltaY = 0;
								ship.fuel--;
							}
						} else if (down.tileType == 0) {
							deltaY += .1;
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
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width + 15; i++) {
				tiles[i][j] = new Tile(map.nextInt(), i, j, tileSizeW,
						tileSizeH);
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
			ship.inventory[tile.tileType]++;
			tile.tileType = 0;
			if (d == 3) { // down
				moveDeltaY = -1 / 30.0f;
			} else if (d == 2) { // right
				moveDeltaX = -1 / 30.0f;
			} else { // left
				moveDeltaX = 1 / 30.0f;
			}
		}
		diggingTime++;

		deltaX += moveDeltaX;
		deltaY += moveDeltaY;

		if (diggingTime == 29) {
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
		for (int i = 0; i < notMineable.length; i++) {
			if (notMineable[i] == tile) {
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
		Arcadia.display(new Arcadia(new Game[] { new MyGame(), new IntroGame(),
				new BasicGame(), new Shooter() }));
	}
}