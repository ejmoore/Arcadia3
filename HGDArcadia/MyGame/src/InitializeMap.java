import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class InitializeMap {

	private final int width;
	private final int height;
	PrintWriter writer = null;

	public class Zone {
		private int upperHeight;
		private int lowerHeight;
		public int airSpawnRate = 0;
		public int dirtSpawnRate = 0;
		public int ore1SpawnRate = 0;
		public int ore2SpawnRate = 0;
		public int ore3SpawnRate = 0;
		public int ore4SpawnRate = 0;
		public int ore5SpawnRate = 0;
		public int ore6SpawnRate = 0;
		public int ore7SpawnRate = 0;
		public int ore8SpawnRate = 0;
		public int ore9SpawnRate = 0;
		public int ore10SpawnRate = 0;
		public int ore11SpawnRate = 0;
		public int ore12SpawnRate = 0;
		public int ore13SpawnRate = 0;
		public int ore14SpawnRate = 0;
		public int ore15SpawnRate = 0;
		public int wallSpawnRate = 0;

		public Zone(int UH, int LH) {
			upperHeight = UH;
			lowerHeight = LH;
		}

		public int getUpper() {
			return upperHeight;
		}

		public int getLower() {
			return lowerHeight;
		}
	}

	public InitializeMap(int w, int h) {
		width = w;
		height = h;

		try {
			writer = new PrintWriter("map.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Zone air = new Zone(0, 3);
		air.airSpawnRate = 100;
		zoneWrite(air);

		Zone crust = new Zone(4, 5);
		crust.dirtSpawnRate = 100;
		zoneWrite(crust);

		Zone zone1 = new Zone(6, 20);
		zone1.dirtSpawnRate = 80;
		zone1.airSpawnRate = 15;
		zone1.ore1SpawnRate = 5;
		zoneWrite(zone1);

		Zone zone2 = new Zone(21, 60);
		zone2.dirtSpawnRate = 75;
		zone2.airSpawnRate = 10;
		zone2.ore1SpawnRate = 10;
		zone2.ore2SpawnRate = 5;
		zoneWrite(zone2);

		Zone zone3 = new Zone(61, 100);
		zone3.dirtSpawnRate = 70;
		zone3.airSpawnRate = 10;
		zone3.ore1SpawnRate = 10;
		zone3.ore2SpawnRate = 5;
		zone3.ore3SpawnRate = 5;
		zoneWrite(zone3);
		
		
		Zone zone4 = new Zone(101, 140);
		zone4.dirtSpawnRate = 65;
		zone4.airSpawnRate = 10;
		zone4.ore1SpawnRate = 10;
		zone4.ore2SpawnRate = 5;

		Zone end = new Zone(900, 1000);
		end.wallSpawnRate = 100;
		writer.close();
	}

	public void zoneWrite(Zone zone) {

		int n1 = zone.airSpawnRate;
		int n2 = n1 + zone.dirtSpawnRate;
		int n3 = n2 + zone.ore1SpawnRate;
		int n4 = n3 + zone.ore2SpawnRate;
		int n5 = n4 + zone.ore3SpawnRate;
		int n6 = n5 + zone.ore4SpawnRate;
		int n7 = n6 + zone.ore5SpawnRate;
		int n8 = n7 + zone.ore6SpawnRate;
		int n9 = n8 + zone.ore7SpawnRate;
		int n10 = n9 + zone.ore8SpawnRate;
		int n11 = n10 + zone.ore9SpawnRate;
		int n12 = n11 + zone.ore10SpawnRate;
		int n13 = n12 + zone.ore11SpawnRate;
		int n14 = n13 + zone.ore12SpawnRate;
		int n15 = n14 + zone.ore13SpawnRate;
		int n16 = n15 + zone.ore14SpawnRate;
		int n17 = n16 + zone.ore15SpawnRate;

		Random randomGenerator = new Random();
		for (int y = zone.getUpper(); y <= zone.getLower(); y++) {
			for (int i = 0; i < 7; i++) {
				writer.print("07 ");
			}
			for (int x = 0; x <= width; x++) {
				int tileNum = randomGenerator.nextInt(100);

				if (tileNum >= 0 && tileNum <= n1) {
					writer.print("00 ");
				} else if (tileNum > n1 && tileNum <= n2) {
					writer.print("01 ");
				} else if (tileNum > n2 && tileNum <= n3) {
					writer.print("02 ");
				} else if (tileNum > n3 && tileNum <= n4) {
					writer.print("03 ");
				} else if (tileNum > n4 && tileNum <= n5) {
					writer.print("04 ");
				} else if (tileNum > n5 && tileNum <= n6) {
					writer.print("05 ");
				} else if (tileNum > n6 && tileNum <= n7) {
					writer.print("06 ");
				} else if (tileNum > n7 && tileNum <= n8) {
					writer.print("08 ");
				} else if (tileNum > n8 && tileNum <= n9) {
					writer.print("09 ");
				} else if (tileNum > n9 && tileNum <= n10) {
					writer.print("10 ");
				} else if (tileNum > n10 && tileNum <= n11) {
					writer.print("11 ");
				} else if (tileNum > n11 && tileNum <= n12) {
					writer.print("12 ");
				} else if (tileNum > n12 && tileNum <= n13) {
					writer.print("13 ");
				} else if (tileNum > n13 && tileNum <= n14) {
					writer.print("14 ");
				} else if (tileNum > n14 && tileNum <= n15) {
					writer.print("15 ");
				} else if (tileNum > n15 && tileNum <= n16) {
					writer.print("16 ");
				} else if (tileNum > n16 && tileNum <= n17) {
					writer.print("17 ");
				} else if (tileNum > n17 && tileNum <= 100) {
					writer.print("18 ");
				}

			}
			for (int i = 0; i < 7; i++) {
				writer.print("07 ");
			}
			writer.println();
		}
	}

}
