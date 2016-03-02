package blur.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

public class Utils {
	
	public static String fileReader(String filename) {
		try {
			StringBuffer sb = new StringBuffer("/blur/resource/").append(filename);

			InputStream is = Utils.class.getResourceAsStream(sb.toString());
			return IOUtils.toString(is, "UTF-8");
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public static Iterable<CSVRecord> csvReader(String filename) {
		StringReader csv = new StringReader(Utils.fileReader(filename));
		try {
			Iterable<CSVRecord> iterator = CSVFormat.DEFAULT.withCommentMarker('#').withIgnoreEmptyLines().withIgnoreSurroundingSpaces().parse(csv);
			return iterator;
		
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
