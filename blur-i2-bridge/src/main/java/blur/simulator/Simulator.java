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

			String eventTimestamp = Utils.formatTimestamp(now);
			String eventType = r.get(1);
			String eventId = r.get(2);

			if (eventType.equals("enter building") || eventType.equals("exit building")) {
				String personId = r.get(3);
				String buildingId = r.get(4);

				//arc.createEvent(eventId, eventType, eventTimestamp, "", "");
				//arc.createInvolvedIn(arc.GENERATE_ID, "Involved", eventId, personId, eventTimestamp);
				//arc.createInvolvedIn(arc.GENERATE_ID, "Involved", eventId, buildingId, eventTimestamp);
				arc.createAccessTo(eventId, eventType, personId, buildingId, eventTimestamp, String.format("source: event {0}", eventId));
			
			} else if (eventType.equals("call")) {
				String callerId = r.get(3);
				String calleeId = r.get(4);
				String callDuration = r.get(5);

				arc.createCommunication(eventId, "Phone Call", callerId, calleeId, eventTimestamp, callDuration);
			}
		}
	}
}
