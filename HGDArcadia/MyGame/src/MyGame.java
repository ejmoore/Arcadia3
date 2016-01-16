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

	int playerX = 10;
	float playerY = 10;
	float velocity = 0;
	float gravity = 0.5f;
	boolean canJump = false;
	Image banner;
	
	public MyGame() {
		try {
			banner = ImageIO.read(MyGame.class.getResource("banner.png"));
		} catch (IOException e) {
			System.out.println("NO BANNER FOUND");
			e.printStackTrace();
		}
	}
	
	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		g.setColor(Color.MAGENTA);
		g.fillRect(0,0,WIDTH,HEIGHT);
		g.setColor(Color.BLACK);
		g.fillOval((int)playerX,(int)playerY,100,100);
		
		velocity += gravity;
		
		playerY += velocity;
		
		if (playerY > HEIGHT-100) {
			velocity = 0;
			playerY = HEIGHT-100;
			canJump = true;
		}
		
		if (canJump && p1.pressed(Button.U)) {
			canJump = false;
			velocity = -15;
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
