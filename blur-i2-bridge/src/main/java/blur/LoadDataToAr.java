package blur;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

import blur.i2.ar.ArCommand;

public class LoadDataToAr {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoadDataToAr ltar = new LoadDataToAr();
		ltar.db_people();
		ltar.db_structure();
		ltar.db_vehicles();
		ltar.ob_org();
		ltar.fake_events();
	}

	private void db_people() {
		try {
			ArCommand arc = new ArCommand();
			
			Reader r = fileToReader("data/db_people.data");
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(r);
			for(CSVRecord record: records) {
				String id = record.get(0);
				String firstName = record.get(1);
				
				arc.createPerson(id, firstName, "", "");
			}
			
			arc.execute();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	private void ob_org() {
		try {
			ArCommand arc = new ArCommand();
			Random random = new Random();
			
			Reader r = fileToReader("data/ob_org.data");
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(r);
			for(CSVRecord record: records) {
				String id = record.get(0);
				String type = record.get(1);
				
				arc.createOrganization(id, id, type);
			}
			
			r = fileToReader("data/ob_org_role.data");
			records = CSVFormat.DEFAULT.parse(r);
			for(CSVRecord record: records) {
				String id = record.get(0);
				String name = record.get(1);
				String role = record.get(2);
				String orgId = record.get(3);
				
				arc.createPerson(id, name, "", "" );
				String linkId = String.valueOf(random.nextLong());
				arc.createMemberOf(linkId, role, id, orgId);
			}
			
			arc.execute();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	private void db_structure() {
		try {
			ArCommand arc = new ArCommand();
			Random random = new Random();
			
			Reader r = fileToReader("data/db_structure.data");
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(r);
			for(CSVRecord record: records) {
				String id = record.get(0);
				String lat = record.get(1);
				String lon = record.get(2);
				String type = record.get(3);
				String owner = record.get(4);
				
				arc.createAddress(id, type, lat, lon );
				String linkId = String.valueOf(random.nextLong());
				arc.createAccessTo(linkId, "Owns", owner, id);
			}
			
			arc.execute();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	private void db_vehicles() {
		try {
			Map<String,String> vehicletypeList = new HashMap<String,String>();
			Reader r = fileToReader("data/db_vehicletype.data");
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(r);
			for(CSVRecord record: records) {
				String make = record.get(0);
				String model = record.get(1);
				String year = record.get(2);
				String clazz = record.get(3);
				String max = record.get(4);
				String type = record.get(5);
				
				vehicletypeList.put(make+model+year, type);
			}
			
			ArCommand arc = new ArCommand();
			Random random = new Random();
			
			r = fileToReader("data/db_vehicles.data");
			records = CSVFormat.DEFAULT.parse(r);
			for(CSVRecord record: records) {
				String id = record.get(0);
				String make = record.get(1);
				String model = record.get(2);
				String year = record.get(3);
				String owner = record.get(4);
				
				String type = vehicletypeList.get(make+model+year); 
				
				arc.createVehicle(id, make, model, year, type );
				
				String linkId = String.valueOf(random.nextLong());
				arc.createAccessTo(linkId, "Owns", owner, id);
			}
			
			arc.execute();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	private void fake_events() {
		ArCommand arc = new ArCommand();
		
		//arc.createEvent(id, type, timestamp, description, location);
	}
	
	private Reader fileToReader(String name) {
		try {
			String dir = this.getClass().getPackage().toString().replace('.', '/').substring(8);
			StringBuffer sb = new StringBuffer().append('/').append(dir).append('/').append(name);
			
			InputStream is = this.getClass().getResourceAsStream(sb.toString());
			return new StringReader( IOUtils.toString(is, "UTF-8") );
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
