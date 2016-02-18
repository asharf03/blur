package i2.ar.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import i2.ar.ArCommand;

public class TestClient4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestClient4().run();
	}

	public void run() {
		Document doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			InputStream is = this.getClass().getResourceAsStream("single-event.xml");
			doc = factory.newDocumentBuilder().parse(is);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		String timestamp  = getTextForTag(doc, "defns:timestamp");
		String message    = getTextForTag(doc, "defns:message");
		String personId   = getTextForTag(doc, "defns:person");
		String orgId      = getTextForTag(doc, "defns:organization");
		String buildingId = getTextForTag(doc, "defns:building");
		
		System.out.println( timestamp );
		System.out.println( message );
		System.out.println( personId );
		System.out.println( orgId );
		System.out.println( buildingId );
		
		ArCommand arc = new ArCommand();
		Random random = new Random();
		String eventId = String.valueOf(random.nextLong());
		arc.createEvent(eventId, "ODM", timestamp, message, "");
		arc.createInvolvedIn(String.valueOf(random.nextLong()), "Involved", personId, eventId);
	}
	
	private String getTextForTag(Document doc, String tag) {
		try { 
			return doc.getElementsByTagName(tag).item(0).getTextContent();
		} catch(Exception e) {
			return "";
		}
	}
}
