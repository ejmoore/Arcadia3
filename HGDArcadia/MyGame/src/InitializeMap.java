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
		Zone air = new Zone(0, 17);
		air.airSpawnRate = 100;
		for (int x = 0; x <= 17; x++) {
			for (int i = 0; i < 7; i++) {
				writer.print("07 ");
			}
			for (int i = 0; i <= width; i++) {
				if (x == 17 && i == 1) {
					writer.print("99 ");
				} else if(x == 17 && i == 6){
					writer.print("97 ");
				} else if(x == 17 && i == 20){
					writer.print("96 ");
				} else if(x == 17 && i == 14){
					writer.print("95 ");
				} else {
					writer.print("00 ");
				}
			}

			for (int i = 0; i < 7; i++) {
				writer.print("07 ");
			}
			writer.println();
		}

		// 8-10 now makes row 8 columns 0-2 of tiletype 98 a un-mineable dirt
		Zone crust = new Zone(18, 21);
		for (int x = 0; x <= 2; x++) {
			
			for (int i = 0; i < 7; i++) {
				writer.print("07 ");
			}
			for (int i = 0; i <= width; i++) {
				if (x == 0 && (i == 1 || i == 0 || i == 2 || i == 5 || i == 6|| i == 7 || i == 13 || i == 14 || i == 15 || i == 19 || i == 20 || i == 21)) {
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
		
		Zone zone1 = new Zone(22, 30);
		zone1.dirtSpawnRate = 80;
		zone1.airSpawnRate = 5;
		zone1.ore1SpawnRate = 15;
		zoneWrite(zone1);

		Zone zone2 = new Zone(31, 70);
		zone2.dirtSpawnRate = 75;
		zone2.airSpawnRate = 10;
		zone2.ore1SpawnRate = 10;
		zone2.ore2SpawnRate = 5;
		zoneWrite(zone2);

		Zone zone3 = new Zone(71, 110);
		zone3.dirtSpawnRate = 70;
		zone3.airSpawnRate = 10;
		zone3.ore1SpawnRate = 10;
		zone3.ore2SpawnRate = 5;
		zone3.ore3SpawnRate = 5;
		zoneWrite(zone3);

		Zone zone4 = new Zone(111, 150);
		zone4.dirtSpawnRate = 70;
		zone4.airSpawnRate = 10;
		zone4.ore1SpawnRate = 10;
		zone4.ore2SpawnRate = 5;
		zone4.ore3SpawnRate = 5;
		zoneWrite(zone4);

		Zone zone5 = new Zone(151, 190);
		zone5.dirtSpawnRate = 70;
		zone5.airSpawnRate = 10;
		zone5.ore1SpawnRate = 5;
		zone5.ore2SpawnRate = 10;
		zone5.ore3SpawnRate = 5;
		zoneWrite(zone5);

		Zone zone6 = new Zone(191, 230);
		zone6.dirtSpawnRate = 70;
		zone6.airSpawnRate = 10;
		zone6.ore2SpawnRate = 10;
		zone6.ore3SpawnRate = 5;
		zone6.ore4SpawnRate = 5;
		zoneWrite(zone6);

		Zone zone7 = new Zone(231, 270);
		zone7.dirtSpawnRate = 70;
		zone7.airSpawnRate = 10;
		zone7.ore2SpawnRate = 5;
		zone7.ore3SpawnRate = 10;
		zone7.ore4SpawnRate = 5;
		zoneWrite(zone7);

		Zone zone8 = new Zone(271, 310);
		zone8.dirtSpawnRate = 70;
		zone8.airSpawnRate = 10;
		zone8.ore3SpawnRate = 10;
		zone8.ore4SpawnRate = 5;
		zone8.ore5SpawnRate = 5;
		zoneWrite(zone8);

		Zone zone9 = new Zone(311, 350);
		zone9.dirtSpawnRate = 70;
		zone9.airSpawnRate = 10;
		zone9.ore3SpawnRate = 5;
		zone9.ore4SpawnRate = 10;
		zone9.ore5SpawnRate = 5;
		zoneWrite(zone9);

		Zone zone10 = new Zone(351, 390);
		zone10.dirtSpawnRate = 70;
		zone10.airSpawnRate = 10;
		zone10.ore4SpawnRate = 10;
		zone10.ore5SpawnRate = 5;
		zone10.ore6SpawnRate = 5;
		zoneWrite(zone10);

		Zone zone11 = new Zone(391, 430);
		zone11.dirtSpawnRate = 70;
		zone11.airSpawnRate = 10;
		zone11.ore4SpawnRate = 5;
		zone11.ore5SpawnRate = 10;
		zone11.ore7SpawnRate = 5;
		zoneWrite(zone11);
		
		Zone zone12 = new Zone(431, 470);
		zone12.dirtSpawnRate = 70;
		zone12.airSpawnRate = 10;
		zone12.ore5SpawnRate = 10;
		zone12.ore6SpawnRate = 5;
		zone12.ore7SpawnRate = 5;
		zoneWrite(zone12);
		
		Zone zone13 = new Zone(471, 510);
		zone13.dirtSpawnRate = 70;
		zone13.airSpawnRate = 10;
		zone13.ore5SpawnRate = 5;
		zone13.ore6SpawnRate = 10;
		zone13.ore7SpawnRate = 5;
		zoneWrite(zone13);
		
		Zone zone14 = new Zone(511, 550);
		zone14.dirtSpawnRate = 70;
		zone14.airSpawnRate = 10;
		zone14.ore6SpawnRate = 10;
		zone14.ore7SpawnRate = 5;
		zone14.ore8SpawnRate = 5;
		zoneWrite(zone14);
		
		Zone zone15 = new Zone(551, 590);
		zone15.dirtSpawnRate = 70;
		zone15.airSpawnRate = 10;
		zone15.ore6SpawnRate = 5;
		zone15.ore7SpawnRate = 10;
		zone15.ore9SpawnRate = 5;
		zoneWrite(zone15);
		
		Zone zone16 = new Zone(591, 630);
		zone16.dirtSpawnRate = 70;
		zone16.airSpawnRate = 10;
		zone16.ore7SpawnRate = 10;
		zone16.ore8SpawnRate = 5;
		zone16.ore9SpawnRate = 5;
		zoneWrite(zone16);
		
		Zone zone17 = new Zone(631, 670);
		zone17.dirtSpawnRate = 70;
		zone17.airSpawnRate = 10;
		zone17.ore7SpawnRate = 5;
		zone17.ore8SpawnRate = 10;
		zone17.ore9SpawnRate = 5;
		zoneWrite(zone17);
		
		Zone zone18 = new Zone(671, 710);
		zone18.dirtSpawnRate = 70;
		zone18.airSpawnRate = 10;
		zone18.ore8SpawnRate = 10;
		zone18.ore9SpawnRate = 5;
		zone18.ore10SpawnRate = 5;
		zoneWrite(zone18);
		
		Zone zone19 = new Zone(711, 750);
		zone19.dirtSpawnRate = 70;
		zone19.airSpawnRate = 10;
		zone19.ore8SpawnRate = 5;
		zone19.ore9SpawnRate = 10;
		zone19.ore10SpawnRate = 5;
		zoneWrite(zone19);
		
		Zone zone20 = new Zone(711, 750);
		zone20.dirtSpawnRate = 70;
		zone20.airSpawnRate = 10;
		zone20.ore9SpawnRate = 10;
		zone20.ore10SpawnRate = 5;
		zone20.ore11SpawnRate = 5;
		zoneWrite(zone20);
		
		Zone zone21 = new Zone(751, 790);
		zone21.dirtSpawnRate = 70;
		zone21.airSpawnRate = 10;
		zone21.ore9SpawnRate = 5;
		zone21.ore10SpawnRate = 10;
		zone21.ore11SpawnRate = 5;
		zoneWrite(zone21);
	
		Zone zone22 = new Zone(791, 830);
		zone22.dirtSpawnRate = 70;
		zone22.airSpawnRate = 10;
		zone22.ore9SpawnRate = 5;
		zone22.ore10SpawnRate = 10;
		zone22.ore11SpawnRate = 5;
		zoneWrite(zone22);
		
		Zone zone23 = new Zone(831, 870);
		zone23.dirtSpawnRate = 70;
		zone23.airSpawnRate = 10;
		zone23.ore10SpawnRate = 10;
		zone23.ore11SpawnRate = 5;
		zone23.ore12SpawnRate = 5;
		zoneWrite(zone23);
		
		Zone zone24 = new Zone(871, 910);
		zone24.dirtSpawnRate = 70;
		zone24.airSpawnRate = 10;
		zone24.ore10SpawnRate = 5;
		zone24.ore11SpawnRate = 10;
		zone24.ore12SpawnRate = 5;
		zoneWrite(zone24);
		
		Zone zone25 = new Zone(911, 950);
		zone25.dirtSpawnRate = 70;
		zone25.airSpawnRate = 10;
		zone25.ore11SpawnRate = 10;
		zone25.ore12SpawnRate = 5;
		zone25.ore13SpawnRate = 5;
		zoneWrite(zone25);
		
		Zone zone26 = new Zone(951, 990);
		zone26.dirtSpawnRate = 70;
		zone26.airSpawnRate = 10;
		zone26.ore11SpawnRate = 5;
		zone26.ore12SpawnRate = 10;
		zone26.ore13SpawnRate = 5;
		zoneWrite(zone26);

		Zone end = new Zone(991, 1011);
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
