
public abstract class Consumable {
	
	int number;
	
	public void setNumber( int n) {
		number = n;
	}
	
	public abstract void use(Ship s, Tile t);
		
	
}
