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
	private final int tileSizeW = WIDTH / 9;
	private final int tileSizeH = HEIGHT / 8;
	Ship ship = new Ship(WIDTH, HEIGHT, tileSizeH, tileSizeW);
	int diggingTime = 0;
	boolean digging = false;
	Tile digTile = null;
	int diggingDirection = 0;

	public MyGame() {
		try {
			banner = ImageIO.read(MyGame.class.getResource("banner.png"));
		} catch (IOException e) {
			System.out.println("NO BANNER FOUND");
			e.printStackTrace();
		}
		createMap();
		Scanner map = null;
		try {
			map = new Scanner(new File("map.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width + 15; i++) {
				tiles[i][j] = new Tile(map.nextInt(), i, j, tileSizeW, tileSizeH);
			}
		}

	}

	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		Tile upleft = tiles[startx + 4][starty + 3];
		Tile downleft = tiles[startx + 4][starty + 5];
		Tile upright = tiles[startx + 6][starty + 3];
		Tile downright = tiles[startx + 6][starty + 5];
		Tile left = tiles[startx + 4][starty + 4];
		Tile right = tiles[startx + 6][starty + 4];
		Tile down = tiles[startx + 5][starty + 5];
		Tile up = tiles[startx + 5][starty + 3];

		if (!digging) {
			// if (System.currentTimeMillis() - startTime > 1) {
			if (tiles[startx + 5][starty + 5].tileType == 0 && ((int) (deltaX * 10) == 0
					|| ((downleft.tileType == 0 || deltaX < 0) && (downright.tileType == 0 || deltaX > 0)))) {
				deltaY -= .1;
				if (deltaY < -1) {
					starty++;
					deltaY = 0;
				}
			} else if (deltaY > .1) {
				deltaY -= .1;
			}

			if (p1.pressed(Button.L)) {
				if (deltaX < 0) {
					deltaX += .1;
					if (deltaX > 0) {
						deltaX = 0;
					}
				}
				if (left.tileType != 7 && ((int) (deltaY * 10) == 0 || (upleft.tileType == 0 && deltaY > 0)
						|| (upleft.tileType == 0 && deltaY < 0))) {
					if (left.tileType == 0) {
						deltaX += .1;
						if (deltaX > 0.5) {
							startx--;
							deltaX = -0.5f;
						}
					} else if (down.tileType != 0 && deltaX == 0) {
						left.tileType = 0;
						digTile = left;
						diggingDirection = 1;
						digging = dig(digTile, diggingDirection);
					}
				}

			} // Move left if player hit left
			if (p1.pressed(Button.R)) {

				if (deltaX > 0) {
					deltaX -= .1;
					if (deltaX < 0) {
						deltaX = 0;
					}
				}
				if (right.tileType != 7 && (int) (deltaY * 10) == 0 || (upright.tileType == 0 && deltaY > 0)
						|| (upright.tileType == 0 && deltaY < 0)) {
					if (right.tileType == 0) {
						deltaX -= .1;
						if (deltaX < -0.5) {
							startx++;
							deltaX = 0.5f;
						}
					} else if (down.tileType != 0 && deltaX == 0) {
						right.tileType = 0;
						digTile = right;
						diggingDirection = 2;
						digging = dig(digTile, diggingDirection);
					}
				} else if (deltaX != 0) {
					deltaX -= .1;
					if (deltaX < 0) {
						deltaX = 0;
					}
				}
			} // Move right if player hit right
			if (p1.pressed(Button.D)) {
				if (down.tileType != 7 && starty < height - 9 && ((int) (deltaX * 10) == 0
						|| ((downleft.tileType == 0 || deltaX < 0) && (downright.tileType == 0 || deltaX > 0)))) {
					if (down.tileType == 0) {
						deltaY -= .1;
						if (deltaY < -.5) {
							starty++;
							deltaY = 0;
						}
					} else {
						down.tileType = 0;
						digTile = down;
						diggingDirection = 3;
						digging = dig(digTile, diggingDirection);
					}
				}
			}
			if (p1.pressed(Button.U)) {
				if (up.tileType != 7 && ((int) (deltaX * 10) == 0
						|| ((upleft.tileType == 0 || deltaX < 0) && (upright.tileType == 0 || deltaX > 0)))) {
					if (starty > 1) {
						if (up.tileType == 0) {
							deltaY += .2;
							if (deltaY > 1) {
								starty--;
								deltaY = 0;
							}
						} else if (down.tileType == 0) {
							deltaY += .1;
							if (deltaY > 1) {
								starty--;
								deltaY = 0;
							}
						}
					}
				}
			} // Move up if player hit up
		}
		else {
			digging = dig(digTile, diggingDirection);
		}

		float k = -1 + deltaX;
		for (int i = startx; i <= startx + 10; i++) {
			float h = -1 + deltaY;
			for (int j = starty; j <= starty + 10; j++) {
				tiles[i][j].drawTile(g, (k), (h));
				h++;
			}
			k++;
		}

		ship.drawShip(g);

		// try {
		// Thread.sleep(60);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	public void createMap() {
		InitializeMap map1 = new InitializeMap(width, height);
	}

	float moveDeltaX = 0;
	float moveDeltaY = 0;

	public boolean dig(Tile tile, int d) {
		digging = true;
		if (diggingTime == 0) {
			if (d == 3) { //down
				moveDeltaY = -1 / 30.0f;
			}
			else if (d == 2) { //right
				moveDeltaX = -1 / 30.0f;
			}
			else { //left
				moveDeltaX = 1 / 30.0f;
			}
		}
		diggingTime++;

		deltaX += moveDeltaX;
		deltaY += moveDeltaY;


		if (diggingTime == 29) {
			moveDeltaX = 0;
			moveDeltaY = 0;
			if (d == 3) { //down
				starty++;
				deltaY = 0;
			}
			else if (d == 2) { //right
				startx++;
				deltaX = 0;
			}
			else { //left
				startx--;
				deltaX = 0;
			}
			
			diggingTime = 0;
			return false;
		}
		return true;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public Image banner() {
		// 512x128
		return banner;
	}

	public static void main(String[] args) {
		Arcadia.display(new Arcadia(new Game[] { new MyGame(), new IntroGame(), new BasicGame(), new Shooter() }));
	}
}
