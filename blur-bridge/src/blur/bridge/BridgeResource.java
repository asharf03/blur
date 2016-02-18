package blur.bridge;

import java.io.InputStream;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.transform.stream.StreamSource;

import com.example.ExampleItemEditor;
import com.i2group.apollo.externaldata.connector.IExternalDataItemEditor;
import com.i2group.apollo.externaldata.loader.AnalysisRepositoryLoader;
import com.i2group.apollo.externaldata.loader.IDataLoader;

@Path("/bridge/1.0")
public class BridgeResource {
	@Context
	private UriInfo f_context;

	@POST
	public Response post(String content) {
		System.out.println( "Post: " + content );
		
		IDataLoader dl = new AnalysisRepositoryLoader();
		IExternalDataItemEditor itemEditor = new ExampleItemEditor();

		InputStream is = this.getClass().getResourceAsStream("Input.xml");
		dl.createItems(new StreamSource(is), itemEditor);
		
		return Response.ok().build();
	}
	
}

