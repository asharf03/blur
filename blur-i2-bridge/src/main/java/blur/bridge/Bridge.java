package blur.bridge;

import java.io.IOException;
import java.io.StringReader;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import blur.i2.ar.ArCommand;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("bridge")
public class Bridge {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it!";
	}

	@POST
	public Response bridge(String content) {
		Document doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			doc = factory.newDocumentBuilder().parse(new InputSource(new StringReader(content)));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		String timestamp = getTextForTag(doc, "defns:timestamp");
		String message = getTextForTag(doc, "defns:message").replaceAll("<.*>", "");
		String personId = getTextForTag(doc, "defns:person");
		String orgId = getTextForTag(doc, "defns:organization");
		String buildingId = getTextForTag(doc, "defns:building");

		System.out.println(timestamp);
		System.out.println(message);
		System.out.println(personId);
		System.out.println(orgId);
		System.out.println(buildingId);

		ArCommand arc = new ArCommand();
		Random random = new Random();
		String eventId = String.valueOf(random.nextLong());
		arc.createEvent(eventId, "Blur Alert", timestamp, message, "");
		arc.createInvolvedIn(null, "Involved", eventId, personId);
		arc.createInvolvedIn(null, "Involved", eventId, buildingId);
		arc.createInvolvedIn(null, "Involved", eventId, orgId);
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
