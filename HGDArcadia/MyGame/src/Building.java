import java.awt.Graphics2D;

public interface Building {
	
	static final int HEIGHT = 1024;
	static final int WIDTH = 576;
	
	public void buildingControls();
	
	public void drawBuilding(Graphics2D g);
	
	public boolean isInside();
}
