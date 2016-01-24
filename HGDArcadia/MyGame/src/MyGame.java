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
	Tile[][] tiles = new Tile[20][100];
	int startx = 10;
	int starty = 0;
	private final int width = 20;
	private final int height = 100;
	private final int tileSizeW = WIDTH / 8;
	private final int tileSizeH = HEIGHT / 8;

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
			for (int i = 0; i < width; i++) {
				tiles[i][j] = new Tile(map.nextInt(), i, j, tileSizeW, tileSizeH);
			}
		}
	}

	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		int k= 0;
		for (int i = startx; i <= startx + 8; i++) {
			int h = 0;
			for (int j = starty; j <= starty + 8; j++) {
				tiles[i][j].drawTile(g, k, h);
				h++;
			}
			k++;
		}
		
		g.setColor(Color.MAGENTA);
	//drawImage(g, ship, WIDTH / 2, HEIGHT / 2, 0);
		System.out.println(startx + " " + starty);
		g.fillOval(WIDTH / 2 - tileSizeW , HEIGHT / 2 - tileSizeH, tileSizeW, tileSizeH);
		
		if (p1.pressed(Button.L)) {
			if (tiles[startx + 2][starty + 3].tileType != 2)
				if (startx != 0)
					startx--;
		} // Move left if player hit left
		if (p1.pressed(Button.R)) {
			if(startx<11)
			if (tiles[startx + 4][starty + 3].tileType != 2)
				startx++;
		} // Move right if player hit right
		if (p1.pressed(Button.D)) {
			if (tiles[startx + 3][starty + 4].tileType != 2)
				starty++;
		} // Move down if player hit down
		if (p1.pressed(Button.U)) {
			if (tiles[startx + 3][starty + 2].tileType != 2)
				if (starty != 0)
					starty--;
		} // Move up if player hit up
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createMap() {

		PrintWriter writer = null;
		try {
			writer = new PrintWriter("map.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// draw top
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j <= width-1; j++) {
				writer.print("0 ");
			}
			writer.println();
		}
		
		// draw first row
		for (int j = 0; j <=width-1;j++){
			writer.print("1 ");
		}
		writer.println();
		
		// draw ground
		Random randomGenerator = new Random();
		for (int i = 5; i <= height-1; i++) {
			for (int j = 0; j <= width-1; j++) {
				int tileNum = randomGenerator.nextInt(100);
				if (tileNum >= 0 && tileNum < 80) {
					writer.print("1 ");
				}
				if (tileNum >= 80 && tileNum < 90) {
					writer.print("2 ");
				}
				if (tileNum >= 90 && tileNum <= 100) {
					writer.print("0 ");
				}
			}
			writer.println();
		}

		writer.close();
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