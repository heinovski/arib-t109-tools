import java.util.Random;

/**
 * @author Julian Heinovski
 *
 */
public class AribTools {

	private static int _PeriodCount = 16;
	private static int _MaxPeriodLength = 6240;
	private static int _MaxLengthOfRvcPeriod = 3024;

	static void generateRvcPeriods(int baseStationCount) {

	}

	static void printHelp() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n"); // TODO add description
		sb.append("\n");
		sb.append("\tperiods: ");
		
		System.out.println(sb.toString());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			switch (args[0]) {
			case "periods":
				generateRvcPeriods(Integer.parseInt(args[1]));
				break;
			case "--h":
				printHelp();
				break;
			default:
				System.out.println("Use --h to display help.");
				break;
			}
		} else {
			System.out.println("Tools for ARIB T109 by Julian Heinovski (mail@julian-heinovski.de). 2015.");
			System.out.println();
			System.out.println("Use --h to display help.");
		}
	}
}
