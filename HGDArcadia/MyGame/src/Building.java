import java.awt.Graphics2D;

import arcadia.Input;

public interface Building {
	
	static final int HEIGHT = 576;
	static final int WIDTH = 1024;
	
	public void buildingControls(Input p1, Input p2);
	
	public void drawBuilding(Graphics2D g);
	
	public boolean isInside();
	
	public void enter();
}
