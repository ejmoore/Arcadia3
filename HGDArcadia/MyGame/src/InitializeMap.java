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
		zone3.dirtSpawnRate = 100;
		zoneWrite(zone3);
		writer.close();
	}

	public void zoneWrite(Zone zone) {

		int n1 = zone.dirtSpawnRate;
		int n2 = n1 + zone.airSpawnRate;
		int n3 = n2 + zone.ore1SpawnRate;
		int n4 = n3 + zone.ore2SpawnRate;
		int n5 = n4 + zone.ore3SpawnRate;
		int n6 = n5 + zone.ore4SpawnRate;

		Random randomGenerator = new Random();
		for (int y = zone.getUpper(); y <= zone.getLower(); y++) {
			for (int i = 0; i < 7; i++) {
				writer.print("7 ");
			}
			for (int x = 0; x <= width; x++) {
				int tileNum = randomGenerator.nextInt(100);

				if (tileNum >= 0 && tileNum <= n1) {
					writer.print("1 ");
				} else if (tileNum > n1 && tileNum <= n2) {
					writer.print("0 ");
				} else if (tileNum > n2 && tileNum <= n3) {
					writer.print("2 ");
				} else if (tileNum > n3 && tileNum <= n4) {
					writer.print("3 ");
				} else if (tileNum > n4 && tileNum <= n5) {
					writer.print("4 ");
				} else if (tileNum > n5 && tileNum <= n6) {
					writer.print("5 ");
				} else if (tileNum > n6 && tileNum <= 100) {
					writer.print("6 ");
				}

			}
			for (int i = 0; i < 7; i++) {
				writer.print("7 ");
			}
			writer.println();
		}
	}

}
