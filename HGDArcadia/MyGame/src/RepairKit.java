
public class RepairKit extends Consumable {
	
	public RepairKit(int n){
		setNumber(n);
	}

	@Override
	public void use(Ship s, Tile t) {
		s.health = s.maxHealth;
		MyGame.ship.consumables[this.number] = null;
	}
}
