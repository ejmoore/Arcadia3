import java.awt.Graphics2D;

import arcadia.Input;

public interface Building {
	
	static final int HEIGHT = 1024;
	static final int WIDTH = 576;
	
	public void buildingControls(Input p1, Input p2);
	
	public void drawBuilding(Graphics2D g);
	
	public boolean isInside();
	
	public void enter();
}
