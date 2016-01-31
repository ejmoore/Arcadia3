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
	private final int tileSizeW = WIDTH / 8;
	private final int tileSizeH = HEIGHT / 8;
	Ship ship = new Ship(WIDTH, HEIGHT, tileSizeH, tileSizeW);

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
		if (System.currentTimeMillis() - startTime > 100) {
			if (tiles[startx + 4][starty + 5].tileType == 0) {
				starty++;
			}
			startTime = System.currentTimeMillis();
		}

		float k = -1 + deltaX;
		for (int i = startx; i <= startx + 10; i++) {
			float h = -1 + deltaY;
			for (int j = starty; j <= starty + 10; j++) {
				tiles[i][j].drawTile(g, k, h);
				h++;
			}
			k++;
		}

		ship.drawShip(g);

		Tile left = tiles[startx + 3][starty + 4];
		Tile right = tiles[startx + 5][starty + 4];
		Tile down = tiles[startx + 4][starty + 5];
		Tile up = tiles[startx + 4][starty + 3];
		if (p1.pressed(Button.L)) {
			if (left.tileType != 7) {
				if (left.tileType == 0) {
					// startx--;
					deltaX += .1;
					if (deltaX > 1) {
						startx--;
						deltaX = 0;
					}
				} else if (left.tileType != 2 && down.tileType != 0) {
					left.tileType = 0;
					startx--;
				}
			}
		} // Move left if player hit left
		if (p1.pressed(Button.R)) {
			if (right.tileType != 7) {
				if (right.tileType == 0) {
					//startx++;
					deltaX -= .1;
					if (deltaX < -1) {
						startx++;
						deltaX = 0;
					}
				} else if (right.tileType != 2 && down.tileType != 0) {
					right.tileType = 0;
					startx++;
				}
			}
		} // Move right if player hit right
		if (p1.pressed(Button.D)) {
			if (down.tileType != 7 && starty < height-9) {
				if (down.tileType == 0) {
					starty++;
				} else if (down.tileType != 2) {
					down.tileType = 0;
					starty++;
				}
			}
		}
		if (p1.pressed(Button.U)) {
			if (up.tileType != 7) {
				if (up.tileType == 0)
					if (starty != 0)
						starty--;
			}
		} // Move up if player hit up
		try {
			Thread.sleep(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createMap() {
		InitializeMap map1 = new InitializeMap(width, height);
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
