package blur.simulator;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.csv.CSVRecord;

import blur.i2.ArCommand;
import blur.utils.Utils;

public class LoadDataToAr {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoadDataToAr ltar = new LoadDataToAr();
		ltar.db_people();
		ltar.db_structure();
		ltar.db_vehicles();
		ltar.ob_org();
		ltar.simulator_data();
	}

	private void db_people() {
		ArCommand arc = new ArCommand();

		Iterable<CSVRecord> records = Utils.csvReader("data/db_people.data");
		for (CSVRecord record : records) {
			String id = record.get(0);
			String firstName = record.get(1);

			arc.createPerson(id, firstName, "", "");
		}

		arc.execute();
	}

	private void ob_org() {
		ArCommand arc = new ArCommand();
		Random random = new Random();

		Iterable<CSVRecord> records = Utils.csvReader("data/ob_org.data");
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
	}

	private void db_structure() {
		ArCommand arc = new ArCommand();
		Random random = new Random();

		Iterable<CSVRecord> records = Utils.csvReader("data/db_structure.data");
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
	}

	private void db_vehicles() {
		Map<String, String> vehicletypeList = new HashMap<String, String>();
		Iterable<CSVRecord> records = Utils.csvReader("data/db_vehicletype.data");
		for (CSVRecord record : records) {
			String make = record.get(0);
			String model = record.get(1);
			String year = record.get(2);
			String clazz = record.get(3);
			String max = record.get(4);
			String type = record.get(5);

			vehicletypeList.put(make + model + year, type);
		}

		ArCommand arc = new ArCommand();
		Random random = new Random();

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
	}

	private void simulator_data() {
		ArCommand arc = new ArCommand();

		arc.createPerson("Tony Soprano", "Tony", "Soprano", "1965");
		arc.createPerson("Richie Aprile", "Richie", "Aprile", "1955");

		arc.execute();
	}
}
