
public class Net extends Consumable {

	public Net(int n) {
		setNumber(n);
	}

	@Override
	public void use(Ship s, Tile t) {
		if (t.tileType < 50) {
			t.tileType = 25;
			MyGame.ship.consumables[this.number] = null;
		}
	}

}
