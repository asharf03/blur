package blur.bridge;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    public static final String BASE_URI = "http://localhost:9090/rest/";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("blur.bridge");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
       
        System.out.println("Server started, hit enter to stop it...");
        System.in.read();
        
        server.shutdown();
        System.out.println("Done!");
    }
}

