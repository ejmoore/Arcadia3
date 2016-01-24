import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class initializeMap {

	private final int width;
	private final int height;
	PrintWriter writer = null;
	
	public class Zone{
		private int upperHeight;
		private int lowerHeight;
		public int airSpawnRate = 0;
		public int dirtSpawnRate = 0;
		public int ore1SpawnRate = 0;
		public int ore2SpawnRate = 0;
		public int ore3SpawnRate = 0;
		public int ore4SpawnRate = 0;
		
		
		public Zone(int UH,int LH){
			upperHeight = UH;
			lowerHeight = LH;
		}
		public int getUpper(){
			return upperHeight;
		}
		public int getLower(){
			return lowerHeight;
		}
	}
	
	public initializeMap(int w, int h){
		width = w;
		height = h;
		
		try {
			writer = new PrintWriter("map.txt", "UTF-8");
		} catch (FileNotFoundException |UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Zone air = new Zone(0,3);
		air.airSpawnRate = 100;
		
		Zone crust = new Zone (4,5);
		crust.dirtSpawnRate = 100;
		
		Zone zone1 = new Zone(6,20);
		zone1.dirtSpawnRate = 80;
		zone1.airSpawnRate = 15;
		zone1.ore1SpawnRate = 5;
		
		Zone zone2 = new Zone(21,60);
		zone2.dirtSpawnRate = 75;
		zone2.airSpawnRate = 10;
		zone2.ore1SpawnRate = 10;
		zone2.ore2SpawnRate = 5;
		
		
	}
	
	public void zoneWrite(Zone zone){
		Random randomGenerator = new Random();
		for (int y = zone.getUpper(); y < zone.getLower(); y++){
			
			for ( int x = 0; x < width; x++){
				int tileNum = randomGenerator.nextInt(100);
				
				if (tileNum >= 0 && tileNum < zone.dirtSpawnRate){
					writer.print("1 ");
				} if else( tileNum > zone.dirtSpawnRate && tileNum < zone.dirtSpawnRate + zone.airSpawnRate);
				
				
			}
			writer.println();
		}
		
	}
	
}
