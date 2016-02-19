package blur.bridge;

import java.io.IOException;
import java.io.StringReader;
import java.util.Random;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import i2.ar.ArCommand;

@Path("/bridge/1.0")
public class BridgeResource {

	DocumentBuilderFactory f_factory = DocumentBuilderFactory.newInstance();

	@Context
	private UriInfo f_context;

	@POST
	public Response post(String content) {
		Document doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			doc = factory.newDocumentBuilder().parse(new InputSource(new StringReader(content)));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}

		String timestamp  = getTextForTag(doc, "defns:timestamp");
		String message    = getTextForTag(doc, "defns:message").replaceAll("<.*>", "");
		String personId   = getTextForTag(doc, "defns:person");
		String orgId      = getTextForTag(doc, "defns:organization");
		String buildingId = getTextForTag(doc, "defns:building");

		System.out.println(content);
		//System.out.println(timestamp);
		//System.out.println(message);
		//System.out.println(personId);
		//System.out.println(orgId);
		//System.out.println(buildingId);

		ArCommand arc = new ArCommand();
		Random random = new Random();
		String eventId = String.valueOf(random.nextLong());
		arc.createEvent(eventId, "Alert", timestamp, message, "");
		arc.createInvolvedIn(String.valueOf(random.nextLong()), "Involved", eventId, personId);
		arc.createInvolvedIn(String.valueOf(random.nextLong()), "Involved", eventId, buildingId);
		arc.createInvolvedIn(String.valueOf(random.nextLong()), "Involved", eventId, orgId);
		arc.execute();

		return Response.ok().build();
	}

	private String getTextForTag(Document doc, String tag) {
		try {
			return doc.getElementsByTagName(tag).item(0).getTextContent();
		} catch (Exception e) {
			return "";
		}
	}
}
