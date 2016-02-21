
public class OreData {
	private int value;
	private int storageSpace;
	private int tileType;
	
	public OreData (int v, int ss, int tt){
		value = v;
		storageSpace = ss;
		tileType = tt;
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
}
