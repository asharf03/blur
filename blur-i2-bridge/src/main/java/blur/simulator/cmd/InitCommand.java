package blur.simulator.cmd;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.csv.CSVRecord;

import blur.i2.ArProxy;
import blur.simulator.Utils;

public class InitCommand {
    public void execute() { 
		db_people();
		db_structure();
		db_vehicles();
		ob_org();
		simulator_data();
	}

	private void db_people() {
		ArProxy arp = new ArProxy();

		Iterable<CSVRecord> records = Utils.csvReader("/blur/resource/data/db_people.data");
		for (CSVRecord record : records) {
			String id = record.get(0);
			String firstName = record.get(1);

			arp.createPerson(id, firstName, "", "");
		}

		arp.execute();
	}

	private void ob_org() {
		ArProxy arp = new ArProxy();
		Random random = new Random();

		Iterable<CSVRecord> records = Utils.csvReader("/blur/resource/data/ob_org.data");
		for (CSVRecord record : records) {
			String id = record.get(0);
			String type = record.get(1);

			arp.createOrganization(id, id, type);
		}

		records = Utils.csvReader("/blur/resource/data/ob_org_role.data");
		for (CSVRecord record : records) {
			String id = record.get(0);
			String name = record.get(1);
			String role = record.get(2);
			String orgId = record.get(3);

			arp.createPerson(id, name, "", "");
			String linkId = String.valueOf(random.nextLong());
			arp.createMemberOf(linkId, role, id, orgId);
		}

		arp.execute();
	}

	private void db_structure() {
		ArProxy arp = new ArProxy();
		Random random = new Random();

		Iterable<CSVRecord> records = Utils.csvReader("/blur/resource/data/db_structure.data");
		for (CSVRecord record : records) {
			String id = record.get(0);
			String lat = record.get(1);
			String lon = record.get(2);
			String type = record.get(3);
			String owner = record.get(4);

			arp.createAddress(id, type, lat, lon);
			String linkId = String.valueOf(random.nextLong());
			// arp.createAccessTo(linkId, "Owns", owner, id);
		}

		arp.execute();
	}

	private void db_vehicles() {
		Map<String, String> vehicletypeList = new HashMap<String, String>();
		Iterable<CSVRecord> records = Utils.csvReader("/blur/resource/data/db_vehicletype.data");
		for (CSVRecord record : records) {
			String make = record.get(0);
			String model = record.get(1);
			String year = record.get(2);
			String clazz = record.get(3);
			String max = record.get(4);
			String type = record.get(5);

			vehicletypeList.put(make + model + year, type);
		}

		ArProxy arp = new ArProxy();
		Random random = new Random();

		records = Utils.csvReader("/blur/resource/data/db_vehicles.data");
		for (CSVRecord record : records) {
			String id = record.get(0);
			String make = record.get(1);
			String model = record.get(2);
			String year = record.get(3);
			String owner = record.get(4);

			String type = vehicletypeList.get(make + model + year);

			arp.createVehicle(id, make, model, year, type);

			String linkId = String.valueOf(random.nextLong());
			// arp.createAccessTo(linkId, "Owns", owner, id);
		}

		arp.execute();
	}

	private void simulator_data() {
		ArProxy arp = new ArProxy();

		arp.createPerson("Tony Soprano", "Tony", "Soprano", "1965");
		arp.createPerson("Richie Aprile", "Richie", "Aprile", "1955");

		arp.execute();
	}
}
