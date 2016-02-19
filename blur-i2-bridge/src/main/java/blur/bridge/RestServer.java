package blur.bridge;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class RestServer {

	public static void main(String[] args) {
		URI baseUri = UriBuilder.fromUri("http://localhost/rest").port(9083).build();
		ResourceConfig config = new ResourceConfig(BlurBridgeResource.class);
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
		try {
			server.start();
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		server.shutdown();
	}
}