package blur.simulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
		s.doInit();
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

	private void doStarti2Server() {
		// XXX
		Runtime.getRuntime().exec("")
	}
	
	private void doReset() {

	}

	private void doInit() {
		ArCommand arc = new ArCommand();
		Random random = new Random();
		Iterable<CSVRecord> records = null;

		records = Utils.csvReader("data/db_people.data");
		for (CSVRecord record : records) {
			String id = record.get(0);
			String firstName = record.get(1);

			arc.createPerson(id, firstName, "", "");
		}

		arc.execute();

		arc = new ArCommand();
		records = Utils.csvReader("data/ob_org.data");
		for (CSVRecord record : records) {
			String id = record.get(0);
			String type = record.get(1);

			arc.createOrganization(id, id, type);
		}

		records = Utils.csvReader("data/ob_org_role.data");
		for (CSVRecord record : records) {
			String id = record.get(0);
			String name = record.get(1);
			String role = record.get(2);
			String orgId = record.get(3);

			arc.createPerson(id, name, "", "");
			String linkId = String.valueOf(random.nextLong());
			arc.createMemberOf(linkId, role, id, orgId);
		}

		arc.execute();

		arc = new ArCommand();
		records = Utils.csvReader("data/db_structure.data");
		for (CSVRecord record : records) {
			String id = record.get(0);
			String lat = record.get(1);
			String lon = record.get(2);
			String type = record.get(3);
			String owner = record.get(4);

			arc.createAddress(id, type, lat, lon);
			String linkId = String.valueOf(random.nextLong());
			// arc.createAccessTo(linkId, "Owns", owner, id);
		}

		arc.execute();

		records = Utils.csvReader("data/db_vehicletype.data");
		Map<String, String> vehicletypeList = new HashMap<String, String>();
		for (CSVRecord record : records) {
			String make = record.get(0);
			String model = record.get(1);
			String year = record.get(2);
			String clazz = record.get(3);
			String max = record.get(4);
			String type = record.get(5);

			vehicletypeList.put(make + model + year, type);
		}

		arc = new ArCommand();
		records = Utils.csvReader("data/db_vehicles.data");
		for (CSVRecord record : records) {
			String id = record.get(0);
			String make = record.get(1);
			String model = record.get(2);
			String year = record.get(3);
			String owner = record.get(4);

			String type = vehicletypeList.get(make + model + year);

			arc.createVehicle(id, make, model, year, type);

			String linkId = String.valueOf(random.nextLong());
			// arc.createAccessTo(linkId, "Owns", owner, id);
		}

		arc.execute();

		arc = new ArCommand();
		arc.createPerson("Tony Soprano", "Tony", "Soprano", "1965");
		arc.createPerson("Richie Aprile", "Richie", "Aprile", "1955");

		arc.execute();
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

				// arc.createEvent(eventId, eventType, eventTimestamp, "", "");
				// arc.createInvolvedIn(arc.GENERATE_ID, "Involved", eventId,
				// personId, eventTimestamp);
				// arc.createInvolvedIn(arc.GENERATE_ID, "Involved", eventId,
				// buildingId, eventTimestamp);
				arc.createAccessTo(eventId, eventType, personId, buildingId, eventTimestamp,
						String.format("source: event {0}", eventId));

			} else if (eventType.equals("call")) {
				String callerId = r.get(3);
				String calleeId = r.get(4);
				String callDuration = r.get(5);

				arc.createCommunication(eventId, "Phone Call", callerId, calleeId, eventTimestamp, callDuration);
			}
		}

		arc.execute();
	}
}
