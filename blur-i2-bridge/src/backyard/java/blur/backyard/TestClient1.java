package blur.backyard;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestClient1 {
	public static void main(String[] args) {
		new TestClient1().run();
	}

	public void run() {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://localhost:9090/rest/bridge");
			InputStream is = this.getClass().getResourceAsStream("single-event.xml");
			StringEntity input = new StringEntity(IOUtils.toString(is, "UTF-8"));
			post.setEntity(input);
			HttpResponse response = client.execute(post);
			System.out.println(response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
