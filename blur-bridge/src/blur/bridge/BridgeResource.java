package blur.bridge;

import java.io.IOException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

@Path("/bridge/1.0")
public class BridgeResource {
	
	DocumentBuilderFactory f_factory = DocumentBuilderFactory.newInstance();
	
	@Context
	private UriInfo f_context;

	@POST
	public Response post(String content) {
		Document doc = null;
		
		try {
			DocumentBuilder builder = f_factory.newDocumentBuilder();
			doc = builder.parse(content);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		Node timestamp = doc.getElementsByTagName( "timestamp" ).item(0);
		timestamp.getNodeValue();
		
		
		return Response.ok().build();
	}
	
}

