package blur.simulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import blur.utils.Utils;

/**
 * Hello world!
 *
 */
public class Simulator {
	private static String SCENARIO = "scenario/scenario1.scn";
	private static double ACCELERATION = 100.0;

	public static void main(String[] args) {
		Simulator s = new Simulator();
		//s.run();
		s.doStart();
	}

	public void run() {
		String cmd = "";
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		while (!cmd.equals("quit")) {
			System.out.println("Blur Simulator: reset, init, start, quit");
			try {
				cmd = stdin.readLine();
				switch (cmd) {
				case "reset":
					doReset();
					break;

				case "init":
					doInit();
					break;

				case "start":
					doStart();
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		
		System.out.println("Done!");
	}

	private void doReset() {

	}

	private void doInit() {

	}

	private void doStart() {
		try {
			StringReader scenario = new StringReader(Utils.fileReader(SCENARIO));
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withCommentMarker('#').withIgnoreEmptyLines().withIgnoreSurroundingSpaces().parse(scenario);
			for (CSVRecord r : records) {
				System.out.println(r.toString());
				String time = r.get(0);
				String cmd = r.get(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
