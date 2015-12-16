
/**
 * Tools for ARIB T109 by Julian Heinovski (mail@julian-heinovski.de). 2015.
 * \n\nThese tools provide some functionality which will come in handy while working with the ARIB T109 communication standard for intelligent transport systems.\n
 */
import java.util.Random;

/**
 * @author Julian Heinovski
 *
 */
public class AribTools {

	private static int _PeriodCount = 16;
	private static int _MaxPeriodLength = 6240;
	private static int _MaxLengthOfRvcPeriod = 3024;

	private static StringBuilder sb = new StringBuilder();
	private static Random r = new Random();

	/**
	 * Generates Roadside-to-Vehicle period information details related to the
	 * count of base stations in the system.
	 * 
	 * @param baseStationCount
	 */
	private static void generateRvcPeriods(int baseStationCount) {
		System.out.println("\nCalculating Roadside-to-Vehicle period information details for " + baseStationCount
				+ " base station(s) in the system.");

		int[] rvcPeriodDurations = new int[_PeriodCount];
		int[][] transmissionPeriodBegins = new int[baseStationCount][_PeriodCount];
		int[][] transmissionPeriodDurations = new int[baseStationCount][_PeriodCount];

		sb.append("\nResults (in us):\n\n");
		sb.append("Roadside-to-Vehicle period durations:\n\n\"");

		for (int i = 0; i < _PeriodCount; i++) {
			int currentPeriodDuration = r.nextInt(_MaxLengthOfRvcPeriod + 1);
			rvcPeriodDurations[i] = currentPeriodDuration;
			sb.append(currentPeriodDuration);
			if (i + 1 != _PeriodCount) {
				sb.append(",");
			}

			for (int j = 0; j < baseStationCount; j++) {
				// calculate transmission period begins
				// the transmission period begin is relative to the
				// Roadside-to-Vehicle period begin. Hence, it's value has to be
				// between 0 (inclusive) and the Roadside-to-Vehicle period
				// duration (inclusive).

				// calculate transmission period durations
			}
		}
		sb.append("\"\n");

		sb.append("\nTransmission period begins:\n\n");
		for (int i = 0; i < baseStationCount; i++) {
			sb.append("Base Station " + (i + 1) + ":\n\"");
			for (int j = 0; j < _PeriodCount; j++) {
				sb.append(transmissionPeriodBegins[i][j]);
				if (j + 1 != _PeriodCount) {
					sb.append(",");
				}
			}
			sb.append("\"\n");
		}

		sb.append("\nTransmission period durations:\n\n");
		for (int i = 0; i < baseStationCount; i++) {
			sb.append("Base Station " + (i + 1) + ":\n\"");
			for (int j = 0; j < _PeriodCount; j++) {
				sb.append(transmissionPeriodDurations[i][j]);
				if (j + 1 != _PeriodCount) {
					sb.append(",");
				}
			}
			sb.append("\"\n");
		}

		System.out.print(sb.toString());
	}

	/**
	 * Prints information about the tools, e.g. known constants,
	 */
	private static void printInfo() {
		sb.append("\nConstants:\n\n");
		sb.append("Period count: " + _PeriodCount + "\n");
		sb.append("Maximum length of a period: " + _MaxPeriodLength + "us\n");
		sb.append("Maximum length of a Roadside-to-Vehicle period: " + _MaxLengthOfRvcPeriod + "us");
		System.out.print(sb.toString());
	}

	private static void printHelp() {
		sb.append(
				"\nTools for ARIB T109 by Julian Heinovski (mail@julian-heinovski.de). 2015.\n\nThese tools provide some functionality which will come in handy while working with the ARIB T109 communication standard for intelligent transport systems.\n");
		sb.append("\n\n");
		sb.append("Usage java -jar arib-t109-tools.jar [--help] <command> [args]\n");
		sb.append("\nThe commands are:\n");
		sb.append("\tinfo: Displays information about the tools, e.g. known constants.");
		sb.append(
				"\n\n\tperiods <baseStationCount>: Generates Roadside-to-Vehicle period information details related to the count of base stations in the system.");

		System.out.print(sb.toString());
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
			case "info":
				printInfo();
				break;
			case "--help":
				printHelp();
				break;
			case "--h":
				printHelp();
			default:
				System.out.println("\nUnknown option: " + args[0] + "\nUse --help to display help.");
				break;
			}
		} else {
			System.out.println("\nTools for ARIB T109 by Julian Heinovski (mail@julian-heinovski.de). 2015.");
			System.out.println();
			System.out.println("Use --help to display help.");
		}
	}
}
