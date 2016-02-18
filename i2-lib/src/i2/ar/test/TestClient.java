package i2.ar.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class TestClient {
	public static void main(String[] args) {
		new TestClient().run();
	}

	public void run() {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://localhost:9081/blur-bridge/rest/bridge/1.0");
			InputStream is = this.getClass().getResourceAsStream("Input.xml");
			StringEntity input = new StringEntity(IOUtils.toString(is, "UTF-8"));
			post.setEntity(input);
			HttpResponse response = client.execute(post);
			System.out.println(response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
