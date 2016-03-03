package blur.simulator.cmd;

import org.apache.commons.csv.CSVRecord;

import blur.i2.ArProxy;
import blur.simulator.Utils;

public class ScenarioCommand {
	private String f_scenario;
	private long f_accelerationRatio;

	public void setScenario(String scenario) {
		f_scenario = scenario;
	}

	public void setAccelerationRatio(long accelerationRatio) {
		f_accelerationRatio = accelerationRatio;
	}

	public void execute() {
		long now = 0;
		long before = 0;
		long sleep = 0;

		Iterable<CSVRecord> csv = Utils.csvReader(f_scenario);
		for (CSVRecord r : csv) {
			before = now;

			String timestampOrDelta = r.get(0);
			if (!(timestampOrDelta.startsWith("+") || timestampOrDelta.startsWith("-"))) {
				now = Utils.parseTimestamp(timestampOrDelta);
			} else {
				now = now + (Integer.valueOf(timestampOrDelta) * 1000);
			}

//			try {
//				sleep = now - before / 1000 / f_accelerationRatio;
//				Thread.sleep(sleep);
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new RuntimeException();
//			}

			ArProxy arp = new ArProxy();

			String eventTimestamp = Utils.formatTimestamp(now);
			String eventType = r.get(1);
			String eventId = r.get(2);

			if (eventType.equals("enter building") || eventType.equals("exit building")) {
				String personId = r.get(3);
				String buildingId = r.get(4);

				// arc.createEvent(eventId, eventType, eventTimestamp, "", "");
				// arc.createInvolvedIn(arc.GENERATE_ID, "Involved", eventId,
				// personId, eventTimestamp);
				// arc.createInvolvedIn(arc.GENERATE_ID, "Involved", eventId,
				// buildingId, eventTimestamp);
				arp.createAccessTo(eventId, eventType, personId, buildingId, eventTimestamp,
						String.format("source: event {0}", eventId));

			} else if (eventType.equals("call")) {
				String callerId = r.get(3);
				String calleeId = r.get(4);
				String callDuration = r.get(5);

				arp.createCommunication(eventId, "Phone Call", callerId, calleeId, eventTimestamp, callDuration);
			}

			arp.execute();
		}

	}

}
