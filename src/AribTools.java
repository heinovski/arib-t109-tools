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
	private static int _MinTransmissionLength = 10500;

	private static StringBuilder sb = new StringBuilder();
	private static Random r = new Random();

	/**
	 * Generates Roadside-to-Vehicle period information details related to the
	 * count of base stations in the system.
	 * 
	 * @param baseStationCount
	 */
	private static void generateRvcPeriods(int baseStationCount,
			boolean minLength, boolean random, boolean fairness) {
		sb.setLength(0);

		sb.append("\nCalculating Roadside-to-Vehicle period information details for "
				+ baseStationCount
				+ " base station(s) in the system using"
				+ (random ? " random" : "")
				+ (fairness ? " fairness" : "")
				+ ".");
		System.out.println(sb.toString());

		int[] rvcPeriodDurations = new int[_PeriodCount];
		int[][] transmissionPeriodBegins = new int[baseStationCount][_PeriodCount];
		int[][] transmissionPeriodDurations = new int[baseStationCount][_PeriodCount];
		int[] leftTransmissionTimes = new int[_PeriodCount];

		boolean valid = true;
		do {
			if (!valid)
				System.out.println("Starting next iteration...");
			
			sb.setLength(0);
			sb.append("\nResults (in us):\n\n");
			sb.append("Roadside-to-Vehicle period durations:\n\t\"");
			
			for (int i = 0; i < _PeriodCount; i++) {
				int currentPeriodDuration = r
						.nextInt(_MaxLengthOfRvcPeriod + 1);
				rvcPeriodDurations[i] = currentPeriodDuration;
				sb.append(currentPeriodDuration);
				if (i + 1 != _PeriodCount) {
					sb.append(",");
				}

				// the transmission period begin is relative to the
				// Roadside-to-Vehicle period begin. Hence, it's value has to be
				// between 0 (inclusive) and the Roadside-to-Vehicle period
				// duration (inclusive).
				int nextTransmissionPeriodBegin = 0;
				int currentTransmissionPeriodDuration = 0;
				int fairTimeSlice = currentPeriodDuration / baseStationCount;
				int temp = currentPeriodDuration;

				for (int j = 0; j < baseStationCount; j++) {
					transmissionPeriodBegins[j][i] = nextTransmissionPeriodBegin;

					if (random) {
						if (fairness) {
							currentTransmissionPeriodDuration = r
									.nextInt(temp + 1);
						} else {
							currentTransmissionPeriodDuration = r
									.nextInt(fairTimeSlice + 1);
						}
					} else {
						currentTransmissionPeriodDuration = fairTimeSlice;
					}
					temp -= currentTransmissionPeriodDuration;

					transmissionPeriodDurations[j][i] = currentTransmissionPeriodDuration;

					nextTransmissionPeriodBegin += currentTransmissionPeriodDuration;
				}
				leftTransmissionTimes[i] = temp;
			}

			int completeTransmissionLength;
			check: for (int i = 0; i < baseStationCount; i++) {
				completeTransmissionLength = 0;
				for (int j = 0; j < _PeriodCount; j++) {
					completeTransmissionLength += transmissionPeriodDurations[i][j];
				}
				if (minLength) {
					if (completeTransmissionLength < _MinTransmissionLength) {
						valid = false;
						System.out.println(completeTransmissionLength);
						break check;
					}
				}
			}
		} while(!valid);
		sb.append("\"\n");

		sb.append("\nTransmission period begins:\n\n");
		for (int i = 0; i < baseStationCount; i++) {
			sb.append("Base Station " + (i + 1) + ":\n\t\"");
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
			sb.append("Base Station " + (i + 1) + ":\n\t\"");
			for (int j = 0; j < _PeriodCount; j++) {
				sb.append(transmissionPeriodDurations[i][j]);
				if (j + 1 != _PeriodCount) {
					sb.append(",");
				}
			}
			sb.append("\"\n");
		}
		
		sb.append("\nComplete transmission period durations:\n\n");
		int duration;
		for (int i = 0; i < baseStationCount; i++) {
			sb.append("Base Station " + (i + 1) + ":\n");
			duration = 0;
			for (int j = 0; j < _PeriodCount; j++) {
				duration += transmissionPeriodDurations[i][j];
			}
			sb.append("\t");
			sb.append(duration);
			sb.append("\n");
		}
		
		sb.append("\nLeft transmission times in each period:\n\t\"");
		for (int i = 0; i < _PeriodCount; i++) {
			sb.append(leftTransmissionTimes[i]);
			if (i + 1 != _PeriodCount) {
				sb.append(",");
			}
		}
		sb.append("\"\n");

		System.out.print(sb.toString());
	}

	/**
	 * Prints information about the tools, e.g. known constants,
	 */
	private static void printInfo() {
		sb.setLength(0);

		sb.append("\nConstants:\n\n");
		sb.append("Period count: " + _PeriodCount + "\n");
		sb.append("Maximum length of a period: " + _MaxPeriodLength + "us\n");
		sb.append("Maximum length of a Roadside-to-Vehicle period: "
				+ _MaxLengthOfRvcPeriod + "us\n");
		sb.append("Minimum length of transmission period: "
				+ _MinTransmissionLength + "us\n");

		System.out.print(sb.toString());
	}

	private static void printHelpPeriods() {
		sb.setLength(0);

		sb.append("\nperiods [<baseStationCount>] [<minTransmissionLength> <random> <fairness>]:\n");
		sb.append("\tGenerates Roadside-to-Vehicle period information details related to the count of base stations in the system, random and fairness.\n");
		sb.append("\nNote: If you want to use optional flags, you have to obey the order and also use all flags before.\n\n");
		sb.append("minTransmissionLength = <false = default>:\n");
		sb.append("\tDetermines whether the calculation should consider a minimum transmission length for each base station.\n");
		sb.append("random = <true|false = default>:\n");
		sb.append("\tDetermines whether a base stations uses the complete time slices or uses just a randomly chosen slice.\n");
		sb.append("fairness = <true = default|false>:\n");
		sb.append("\tDetermines whether a base stations uses at most a fair slice of the period time or ...\n");

		System.out.print(sb.toString());
	}

	private static void printHelp() {
		sb.setLength(0);

		sb.append("\nTools for ARIB T109 by Julian Heinovski (mail@julian-heinovski.de). 2015.\n\nThese tools provide some functionality which will come in handy while working with the ARIB T109 communication standard for intelligent transport systems.\n");
		sb.append("\nUsage java (-jar arib-t109-tools.jar) [--help] <command> [args]\n");
		sb.append("\nThe commands are:\n");
		sb.append("\tinfo: Displays information about the tools, e.g. known constants.\n");
		sb.append("\tperiods: Generates Roadside-to-Vehicle period information details.\n");
		sb.append("\thelp <command>: Display details for the given command.\n");

		System.out.print(sb.toString());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		sb.setLength(0);

		if (args.length > 0) {
			switch (args[0]) {
			case "periods":
				int baseStationCount = 0;
				boolean minLength = false;
				boolean random = false;
				boolean fairness = true;
				if (args.length > 1) {
					try {
						baseStationCount = Integer.parseInt(args[1]);
					} catch (NumberFormatException nfe) {
						sb.append("\nInvalid option " + args[1]
								+ ".\nUse --help to display help.");
						System.out.print(sb.toString());
					}
					if (args.length > 2) {
						minLength = Boolean.parseBoolean(args[2]);
						if (args.length > 3) {
							random = Boolean.parseBoolean(args[3]);
							if (args.length > 4) {
								fairness = Boolean.parseBoolean(args[4]);
							}
						}
					}
				} else {
					baseStationCount = r.nextInt();
				}
				generateRvcPeriods(baseStationCount, minLength, random,
						fairness);
				break;
			case "info":
				printInfo();
				break;
			case "help":
				if (args.length > 1) {
					switch (args[1]) {
					case "periods":
						printHelpPeriods();
						break;
					default:
						printHelp();
						break;
					}
				} else {
					printHelp();
				}
				break;
			case "--help":
				printHelp();
				break;
			case "-h":
				printHelp();
				break;
			default:
				sb.append("\nUnknown option: " + args[0]
						+ "\nUse --help to display help.");
				System.out.print(sb.toString());
				break;
			}
		} else {
			sb.append("\nTools for ARIB T109 by Julian Heinovski (mail@julian-heinovski.de). 2015.\n");
			sb.append("Use --help to display help.\n");
			System.out.print(sb.toString());
		}
	}
}
