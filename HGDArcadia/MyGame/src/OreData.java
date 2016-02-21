
public class OreData {
	private int value;
	private int storageSpace;
	private int tileType;
	public int danktoughness;
	
	
	public OreData (int v, int ss, int tt, int tough){
		value = v;
		storageSpace = ss;
		tileType = tt;
		danktoughness = tough;
	}
	public int getValue(){
		return value;
	}
	public int getStorageSpace(){
		return storageSpace;
	}
	public int getTileType(){
		return tileType;
	}
	public  int getTough(){
		return danktoughness;
	}
}
