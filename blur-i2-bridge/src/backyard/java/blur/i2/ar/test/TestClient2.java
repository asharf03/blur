package blur.i2.ar.test;

import java.io.InputStream;

import javax.xml.transform.stream.StreamSource;

import com.i2group.apollo.externaldata.connector.IExternalDataItemEditor;
import com.i2group.apollo.externaldata.loader.AnalysisRepositoryLoader;
import com.i2group.apollo.externaldata.loader.IDataLoader;

import blur.i2.ar.ExampleItemEditor;

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
