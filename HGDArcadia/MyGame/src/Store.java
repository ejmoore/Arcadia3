import java.awt.Color;
import java.awt.Graphics2D;

public class Store implements Building{

	int x = -1;
	int y = -1;
	int activeButton = 0;
	
	public Store(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void drawBuilding(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,0,WIDTH,HEIGHT);
		g.setColor(Color.GRAY);
		g.fillRect(102, 125, 820, 125);
		g.setColor(Color.GRAY);
		g.fillRect(102, 375, 820, 125);
	}

	@Override
	public boolean isInside() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void buildingControls() {
		// TODO Auto-generated method stub
		
	}

}
