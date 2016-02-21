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

		// 0-7 now spawns printing one building one away from the boarder on the
		// left above the ground
		Zone air = new Zone(0, 7);
		air.airSpawnRate = 100;
		for (int x = 0; x <= 7; x++) {
			for (int i = 0; i < 7; i++) {
				writer.print("07 ");
			}
			for (int i = 0; i <= width; i++) {
				if (x == 7 && i == 1) {
					writer.print("99 ");
				} else if(x ==7 && i == 6){
					writer.print("97 ");
				}else {
					writer.print("00 ");
				}
			}

			for (int i = 0; i < 7; i++) {
				writer.print("07 ");
			}
			writer.println();
		}

		// 8-10 now makes row 8 columns 0-2 of tiletype 98 a un-mineable dirt
		Zone crust = new Zone(8, 10);
		for (int x = 0; x <= 2; x++) {
			
			for (int i = 0; i < 7; i++) {
				writer.print("07 ");
			}
			for (int i = 0; i <= width; i++) {
				if (x == 0 && (i == 1 || i == 0 || i == 2 || i == 5 || i == 6|| i == 7)) {
					writer.print("98 ");
				} else {
					writer.print("01 ");
				}
			}

			for (int i = 0; i < 7; i++) {
				writer.print("07 ");
			}
			writer.println();
		}

		Zone zone1 = new Zone(11, 20);
		zone1.dirtSpawnRate = 80;
		zone1.airSpawnRate = 5;
		zone1.ore1SpawnRate = 15;
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
		zone4.ore2SpawnRate = 10;
		zone4.ore3SpawnRate = 5;
		zoneWrite(zone4);

		Zone zone5 = new Zone(141, 180);
		zone5.dirtSpawnRate = 60;
		zone5.airSpawnRate = 10;
		zone5.ore1SpawnRate = 10;
		zone5.ore2SpawnRate = 10;
		zone5.ore3SpawnRate = 5;
		zone5.ore4SpawnRate = 5;
		zoneWrite(zone5);

		Zone zone6 = new Zone(181, 220);
		zone6.dirtSpawnRate = 55;
		zone6.airSpawnRate = 10;
		zone6.ore1SpawnRate = 10;
		zone6.ore2SpawnRate = 10;
		zone6.ore3SpawnRate = 5;
		zone6.ore4SpawnRate = 5;
		zone6.ore5SpawnRate = 5;
		zoneWrite(zone6);

		Zone zone7 = new Zone(221, 260);
		zone7.dirtSpawnRate = 50;
		zone7.airSpawnRate = 10;
		zone7.ore1SpawnRate = 10;
		zone7.ore2SpawnRate = 10;
		zone7.ore3SpawnRate = 5;
		zone7.ore4SpawnRate = 5;
		zone7.ore5SpawnRate = 5;
		zone7.ore6SpawnRate = 5;
		zoneWrite(zone7);

		Zone zone8 = new Zone(261, 300);
		zone8.dirtSpawnRate = 50;
		zone8.airSpawnRate = 10;
		zone8.ore1SpawnRate = 5;
		zone8.ore2SpawnRate = 10;
		zone8.ore3SpawnRate = 10;
		zone8.ore4SpawnRate = 5;
		zone8.ore5SpawnRate = 5;
		zone8.ore6SpawnRate = 5;
		zoneWrite(zone8);

		Zone zone9 = new Zone(301, 340);
		zone9.dirtSpawnRate = 50;
		zone9.airSpawnRate = 10;
		zone9.ore2SpawnRate = 10;
		zone9.ore3SpawnRate = 15;
		zone9.ore4SpawnRate = 5;
		zone9.ore5SpawnRate = 5;
		zone9.ore6SpawnRate = 5;
		zoneWrite(zone9);

		Zone zone10 = new Zone(341, 380);
		zone10.dirtSpawnRate = 50;
		zone10.airSpawnRate = 10;
		zone10.ore2SpawnRate = 5;
		zone10.ore3SpawnRate = 15;
		zone10.ore4SpawnRate = 10;
		zone10.ore5SpawnRate = 5;
		zone10.ore6SpawnRate = 5;
		zoneWrite(zone10);

		Zone zone11 = new Zone(381, 420);
		zone11.dirtSpawnRate = 50;
		zone11.airSpawnRate = 10;
		zone11.ore3SpawnRate = 10;
		zone11.ore4SpawnRate = 10;
		zone11.ore5SpawnRate = 10;
		zone11.ore6SpawnRate = 5;
		zone11.ore7SpawnRate = 5;
		zoneWrite(zone11);

		Zone end = new Zone(421, 1000);
		end.wallSpawnRate = 100;
		zoneWrite(end);
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
		int n18 = n17 + zone.wallSpawnRate;

		Random randomGenerator = new Random();
		for (int y = zone.getUpper(); y <= zone.getLower(); y++) {
			for (int i = 0; i < 7; i++) {
				writer.print("07 ");
			}
			for (int x = 0; x <= width; x++) {
				int tileNum = randomGenerator.nextInt(99) + 1;
				if (tileNum >= 0 && tileNum <= n1) {
					//Air
					writer.print("00 ");
				} else if (tileNum > n1 && tileNum <= n2) {
					//Dirt
					writer.print("01 ");
				} else if (tileNum > n2 && tileNum <= n3) {
					//ore1
					writer.print("02 ");
				} else if (tileNum > n3 && tileNum <= n4) {
					//ore2
					writer.print("03 ");
				} else if (tileNum > n4 && tileNum <= n5) {
					//ore3
					writer.print("04 ");
				} else if (tileNum > n5 && tileNum <= n6) {
					//ore4
					writer.print("05 ");
				} else if (tileNum > n6 && tileNum <= n7) {
					//ore5
					writer.print("06 ");
				} else if (tileNum > n7 && tileNum <= n8) {
					//ore6
					writer.print("08 ");
				} else if (tileNum > n8 && tileNum <= n9) {
					//ore7
					writer.print("09 ");
				} else if (tileNum > n9 && tileNum <= n10) {
					//ore8
					writer.print("10 ");
				} else if (tileNum > n10 && tileNum <= n11) {
					//ore9
					writer.print("11 ");
				} else if (tileNum > n11 && tileNum <= n12) {
					//ore10
					writer.print("12 ");
				} else if (tileNum > n12 && tileNum <= n13) {
					//ore11
					writer.print("13 ");
				} else if (tileNum > n13 && tileNum <= n14) {
					//ore12
					writer.print("14 ");
				} else if (tileNum > n14 && tileNum <= n15) {
					//ore13
					writer.print("15 ");
				} else if (tileNum > n15 && tileNum <= n16) {
					//ore14
					writer.print("16 ");
				} else if (tileNum > n16 && tileNum <= n17) {
					//ore15
					writer.print("17 ");
				} else if (tileNum > n17 && tileNum <= n18) {
					//wall
					writer.print("07 ");
				}
			}
			for (int i = 0; i < 7; i++) {
				writer.print("07 ");
			}
			writer.println();
		}
	}

}
