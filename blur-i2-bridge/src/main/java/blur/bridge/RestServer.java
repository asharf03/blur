package blur.bridge;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.server.ResourceConfig;

public class RestServer {

	public static void main(String[] args) {
		URI baseUri = UriBuilder.fromUri("http://localhost/").port(9082).build();
		ResourceConfig config = new ResourceConfig(BlurBridgeResource.class);
		//HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config);
	}
}