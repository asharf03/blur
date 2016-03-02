package blur.simulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.csv.CSVRecord;

import blur.i2.ArCommand;
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
		// s.run();
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
		ArCommand arc = new ArCommand();
		long now = System.currentTimeMillis();

		Iterable<CSVRecord> csv = Utils.csvReader(SCENARIO);
		for (CSVRecord r : csv) {

			String timestampOrDelta = r.get(0);
			if (!(timestampOrDelta.startsWith("+") || timestampOrDelta.startsWith("-"))) {
				now = Utils.parseTimestamp(timestampOrDelta);
			} else {
				now = now + (Integer.valueOf(timestampOrDelta) * 1000);
			}

			System.out.println(Utils.formatTimestamp(now));
			
			String cmd = r.get(1);
			switch (cmd) {
			case "enter building":
				break;
			}
		}
	}
}
