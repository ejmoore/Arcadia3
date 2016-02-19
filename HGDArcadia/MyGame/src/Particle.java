import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;


public class Particle {
	int acceleration = 0;
	double velocity = 0;
	int x = 0;
	int y = 0;
	double angle;
	Random ran = new Random();
	long cooldown = 0;
	
	public Particle(int x, int y) {
		angle = ran.nextInt(50)/100.0f -.244;
		this.x = x;
		this.y = y;
		velocity = 20;
		acceleration = -1;
	}
	
	public static void drawParticles(ArrayList<Particle> p, Graphics2D g) {
		for (Particle par : p) {
			par.updateParticle();
			if (par.velocity > 0) par.drawParticle(g);
		}
	}
	
	public void updateParticle() {
		if (System.nanoTime() >= cooldown) {
			velocity += acceleration;
			cooldown = System.nanoTime() + 125000000;
			x += Math.cos(angle) * velocity;
			y += Math.sin(angle) * velocity;
		}
	}
	
	public void drawParticle(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, 10, 10);
	}
}
