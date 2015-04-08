package collector.util;


import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * 用于发送RESTful请求的客户端
 * 
 * @author Wang Chao
 *
 * @date 2015-1-14 下午1:11:57
 *
 */
public class RestfulClient {

	/**
	 * 用于发送RESTful Get请求
	 * @param url
	 * @return json字符串
	 */
	public static String sendGetRequest(String url) {
		String json = null;
		try {

			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("content-type", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			json = EntityUtils.toString(response.getEntity(), "UTF-8");

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return json;
	}
}
