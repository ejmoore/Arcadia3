
public class Net extends Consumable{

	public Net(int n){
		setNumber(n);
	}
	
	@Override
	public void use(Ship s, Tile t) {
		t.tileType = 25;
	}

}
