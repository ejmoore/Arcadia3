import java.awt.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

import arcadia.*;
import basicGame.BasicGame;
import intro.IntroGame;
import shooter.Shooter;

public class MyGame extends Game {

	private final int width = 8;
	private final int height = 100;
	
	Image banner;
	Tile[][] tiles = new Tile[width][height];
	
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
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j] = new Tile(map.nextInt(), i, j, tileSizeW, tileSizeH);
			}
		}
	}

	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				tiles[i][j].drawTile(g);
			}
		}

		
		g.setColor(Color.MAGENTA);
		g.fillOval(WIDTH / 2 - tileSizeW / 2, HEIGHT / 2 - tileSizeH, tileSizeW, tileSizeH);
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
