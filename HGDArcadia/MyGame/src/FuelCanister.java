
public class FuelCanister extends Consumable {

	public FuelCanister(int n){
		setNumber(n);
	}
	
	@Override
	public void use(Ship s, Tile t) {
		s.fuel = s.maxFuel;
		
	}

}
