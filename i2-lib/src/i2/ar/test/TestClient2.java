package i2.ar.test;

import java.io.InputStream;
import java.util.Date;

import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;

import com.example.ExampleItemEditor;
import com.i2group.apollo.externaldata.connector.IExternalDataItemEditor;
import com.i2group.apollo.externaldata.loader.AnalysisRepositoryLoader;
import com.i2group.apollo.externaldata.loader.IDataLoader;

public class TestClient2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestClient2 tc = new TestClient2();
		tc.run();
	}

	public void run() {
		System.setProperty("ApolloServerSettingsResource", "blur/bridge/test/loader.properties");
		IDataLoader dl = new AnalysisRepositoryLoader();
		IExternalDataItemEditor itemEditor = new ExampleItemEditor();

		InputStream is = this.getClass().getResourceAsStream("create-event.xml");
		dl.createItems(new StreamSource(is), itemEditor);
	}
}
