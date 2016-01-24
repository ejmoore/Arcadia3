import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import arcadia.Arcadia;
import arcadia.Button;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;
import basicGame.BasicGame;
import intro.IntroGame;
import shooter.Shooter;

public class MyGame extends Game{

	Image banner;
	Tile[] tiles = new Tile[32];
	
	int tileSizeW = WIDTH / 8;
	int tileSizeH = HEIGHT / 8;
	
	public MyGame() {
		try {
			banner = ImageIO.read(MyGame.class.getResource("banner.png"));
		} catch (IOException e) {
			System.out.println("NO BANNER FOUND");
			e.printStackTrace();
		}
		
		int k = 0;
		for (int i = 0; i <= 7; i++) {
			for (int j = 4; j <= 7; j++) {
				tiles[k] = new Tile(0,j,i,tileSizeW,tileSizeH);
				k++;
			}
		}
	}
	
	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		g.setColor(Color.WHITE);
		g.fillRect(0,0,WIDTH,HEIGHT);
		
		g.setColor(Color.MAGENTA);
		g.fillOval(WIDTH/2 - tileSizeW/2, HEIGHT/2 - tileSizeH, tileSizeW, tileSizeH);
		
		for (int i = 0; i < tiles.length; i++) {
			System.out.println(i);
			tiles[i].drawTile(g);
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image banner() {
		//512x128
		return banner;
	}

	public static void main(String[] args) {
		Arcadia.display(new Arcadia(new Game[] {new MyGame(), new IntroGame(), new BasicGame(), new Shooter()}));
	}
}
