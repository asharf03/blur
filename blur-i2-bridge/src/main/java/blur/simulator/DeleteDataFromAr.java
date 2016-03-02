package blur.simulator;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

import blur.i2.ArCommand;

public class DeleteDataFromAr {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DeleteDataFromAr ddfar = new DeleteDataFromAr();
		ddfar.db_people();
	}

	private void db_people() {
		ArCommand arc = new ArCommand();
		
		try {
			Reader r = fileToReader("data/db_people.data");
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(r);
			for(CSVRecord record: records) {
				String id = record.get(0);
				String firstName = record.get(1);
				
				arc.purgeItem(id);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		arc.execute();
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
