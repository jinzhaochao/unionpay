package com.unionpay.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLContext;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpClient对象(连接池) create by xiongym
 */
public class HttpClientUtils {

	private static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);

	private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
	static {
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(20);
	}

	/**
	 * 创建HttpClient对象(连接池)
	 * 
	 * @return httpClient对象
	 */
	public static CloseableHttpClient getHttpClient() {
		// 定义HttpClient对象
		CloseableHttpClient httpClient = null;
		try {
			LayeredConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(SSLContext.getDefault());
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("https", socketFactory).register("http", PlainConnectionSocketFactory.INSTANCE).build();
			// 创建连接池对象
			PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager(registry);
			// 设置最大连接数
			pool.setMaxTotal(200);
			// 设置每个路由基础的连接数
			pool.setDefaultMaxPerRoute(20);
			httpClient = HttpClients.custom()
					.setConnectionManager(pool)
					.setDefaultRequestConfig(getRequestConfig())
					.build();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return httpClient;
	}

	/**
	 * HTTP GET请求方法
	 * 
	 * @param url
	 *            请求路径
	 * @param param
	 *            请求参数
	 * @return JSON 响应结果
	 */
	public static String doGet(String url, Map<String, String> param) {
		log.info("调用url========"+url);
		String json = null;
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			// 创建uri
			URI uri = builder.build();
			// 创建HTTP GET请求
			HttpGet httpGet = new HttpGet(uri);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
					.setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
			httpGet.setConfig(requestConfig);
			// 执行请求
			response = getHttpClient().execute(httpGet);
			InputStream inputStream = null;
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				inputStream = response.getEntity().getContent();
			}
			if (inputStream != null) {
				json = IOUtils.toString(inputStream, "UTF-8");
				inputStream.close();
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return json;
	}

	/**
	 * HTTP GET请求方法
	 * 
	 * @param url
	 *            请求路径
	 * @return JSON 响应结果
	 */
	public static String doGetJson(String url) {
		log.info("调用url========"+url);
		String json = null;
		CloseableHttpResponse response = null;
		try {
			// 创建HTTP GET请求
			HttpGet httpGet = new HttpGet(url);
			// 执行请求
			response = getHttpClient().execute(httpGet);
			InputStream inputStream = null;
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				inputStream = response.getEntity().getContent();
			}
			if (inputStream != null) {
				json = IOUtils.toString(inputStream, "UTF-8");
				inputStream.close();
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return json;
	}

	/**
	 * HTTP POST请求方法
	 * 
	 * @param url
	 *            请求路径
	 * @param param
	 *            请求参数
	 * @return JSON 响应结果
	 */
	public static String doPost(String url, Map<String, String> param) {
		log.info("调用url=========="+url);
		String json = null;
		CloseableHttpResponse response = null;
		try {
			// 创建HTTP POST请求
			HttpPost httpPost = new HttpPost(url);

			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
				httpPost.setEntity(entity);
			}
			// 执行HTTP请求
			response = getHttpClient().execute(httpPost);
			InputStream inputStream = null;
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				inputStream = response.getEntity().getContent();
			}
			if (inputStream != null) {
				json = IOUtils.toString(inputStream, "UTF-8");
				inputStream.close();
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return json;
	}

	/**
	 * 通过Https往API post xml数据
	 *
	 * @param url
	 *            API地址
	 * @param postDataXML
	 *            要提交的XML数据对象
	 * @return API回包的实际数据
	 * @throws IOException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */

	public static String sendPost(String url, String postDataXML) throws IOException, KeyStoreException,
			UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {

		String result = null;

		HttpPost httpPost = new HttpPost(url);

		StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);

		// httpPost.setConfig(requestConfig);

		try {
			HttpResponse response = getHttpClient().execute(httpPost);

			HttpEntity entity = response.getEntity();

			result = EntityUtils.toString(entity, "UTF-8");

		} catch (ConnectionPoolTimeoutException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.abort();
		}

		return result;
	}

	/**
	 * HTTP GET请求方法
	 * 
	 * @param url
	 *            请求路径
	 * @return XML 响应结果
	 */
	public static String doGetRequestXML(String url) {
		String xml = null;
		CloseableHttpResponse response = null;
		try {
			// 创建HTTP GET请求
			HttpGet httpGet = new HttpGet(url);
			// 执行请求
			response = getHttpClient().execute(httpGet);
			InputStream inputStream = null;
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				inputStream = response.getEntity().getContent();
			}
			if (inputStream != null) {
				StringBuffer buffer = new StringBuffer();
				InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
				Reader reader = new BufferedReader(isr);
				int ch;
				while ((ch = reader.read()) > -1) {
					buffer.append((char) ch);
				}
				xml = buffer.toString().trim();
				buffer = null;
				inputStream.close();
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return xml;
	}

	/**
	 * HTTP GET请求方法
	 * 
	 * @param url
	 *            请求路径
	 * @return JSON 响应结果
	 */
	public static String doGetRequestJson(String url) {
		String json = null;
		CloseableHttpResponse response = null;
		try {
			// 创建HTTP GET请求
			HttpGet httpGet = new HttpGet(url);
			// 执行请求
			response = getHttpClient().execute(httpGet);
			InputStream inputStream = null;
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				inputStream = response.getEntity().getContent();
			}
			if (inputStream != null) {
				json = IOUtils.toString(inputStream, "UTF-8");
				inputStream.close();
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return json;
	}

	/**
	 * HTTP GET请求方法
	 * 
	 * @param url
	 *            请求路径
	 * @param param
	 *            请求参数
	 * @return JSON 响应结果
	 */
	public static String doGet(String url, String hearder, Map<String, String> param) {
		String json = null;
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			// 创建uri
			URI uri = builder.build();
			// 创建HTTP GET请求
			HttpGet httpGet = new HttpGet(uri);
			httpGet.setHeader("apikey", hearder);
			// 执行请求
			response = getHttpClient().execute(httpGet);
			InputStream inputStream = null;
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				inputStream = response.getEntity().getContent();
			}
			if (inputStream != null) {
				json = IOUtils.toString(inputStream, "UTF-8");
				inputStream.close();
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return json;
	}

	/**
	 * HTTP GET请求方法
	 * 
	 * @param url
	 *            请求路径
	 * @param headers
	 *            请求头
	 * @param params
	 *            请求参数
	 * @return
	 */
	public static String doGet(String url, Map<String, String> headers, Map<String, String> params) {
		String json = null;
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			if (params != null) {
				for (Map.Entry<String, String> e : params.entrySet()) {
					builder.addParameter(e.getKey(), e.getValue());
				}
			}
			// 创建uri
			URI uri = builder.build();
			// 创建HTTP GET请求
			HttpGet httpGet = new HttpGet(uri);
			if (headers != null) {
				for (Map.Entry<String, String> e : headers.entrySet()) {
					httpGet.addHeader(e.getKey(), e.getValue());
				}
			}
			// 执行请求
			response = getHttpClient().execute(httpGet);
			InputStream inputStream = null;
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				inputStream = response.getEntity().getContent();
			}
			if (inputStream != null) {
				json = IOUtils.toString(inputStream, "UTF-8");
				inputStream.close();
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return json;
	}

	/**
	 * HTTP POST请求方法
	 * 
	 * @param url
	 *            请求路径
	 * @param json
	 *            请求参数
	 * @return
	 */
	public static String doPostJson(String url, String json) {
		String result = null;
		CloseableHttpResponse response = null;
		try {
			// 创建HTTP POST请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			if (ToolUtil.isNotEmpty(json)) {
				StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
				httpPost.setEntity(entity);
			}
			// 执行HTTP请求
			response = getHttpClient().execute(httpPost);
			InputStream inputStream = null;
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				inputStream = response.getEntity().getContent();
			}
			if (inputStream != null) {
				result = IOUtils.toString(inputStream, "UTF-8");
				inputStream.close();
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * HTTP POST请求方法，设置终端请求头
	 * 
	 * @param url
	 *            请求路径
	 * @param json
	 *            请求参数
	 * @return
	 */
	public static String doPostJsonWeb(String url, String json, Map<String, String> headers) {
		String result = null;
		CloseableHttpResponse response = null;
		try {
			// 创建HTTP POST请求
			HttpPost httpPost = new HttpPost(url);
			if (headers != null) {
				for (Map.Entry<String, String> e : headers.entrySet()) {
					httpPost.addHeader(e.getKey(), e.getValue());
				}
			}
			// 创建请求内容
			if (ToolUtil.isNotEmpty(json)) {
				StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
				httpPost.setEntity(entity);
			}
			// 执行HTTP请求
			response = getHttpClient().execute(httpPost);
			InputStream inputStream = null;
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				inputStream = response.getEntity().getContent();
			}
			if (inputStream != null) {
				result = IOUtils.toString(inputStream, "UTF-8");
				inputStream.close();
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 通过get方式获取指定地址的内容
	 * 
	 * @param url
	 *            需要访问的地址如：http://www.baidu.com
	 * @param chartset
	 *            字符编码，将地址返回的内容进行字符编码，如果为空则默认为：UTF-8
	 * @return 地址对应的内容
	 */
	public static String get(String url, int socketTime, int connectTimeout, String chartset)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
		/*
		 * HttpHost proxy = new HttpHost("10.1.27.102", 8080, "http");
		 * RequestConfig requetConfig =
		 * RequestConfig.custom().setSocketTimeout(socketTime)
		 * .setConnectTimeout(connectTimeout).setProxy(proxy).build();
		 */
		RequestConfig requetConfig = RequestConfig.custom().setSocketTimeout(socketTime)
				.setConnectTimeout(connectTimeout).build();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requetConfig);
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpGet);
			HttpEntity entity1 = response1.getEntity();
			if (chartset == null || "".equals(chartset)) {
				chartset = "UTF-8";
			}
			String responseBody = EntityUtils.toString(entity1, chartset);
			EntityUtils.consume(entity1);
			StatusLine statusLine = response1.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != 200) {
				log.error("current request url error,satusCode:{},responseBody:{}" + "statusCode:" + statusCode
						+ "responseBody:" + responseBody);
				throw new IOException("request url statusCode is 500!");
			}
			return responseBody;
		} finally {
			if (response1 != null) {
				response1.close();
			}
		}
	}

	private static RequestConfig getRequestConfig() {
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000)
				.setSocketTimeout(30000).build();
		return requestConfig;
	}

	public static void main(String[] args) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("carnumber", "皖ADJ980");
		param.put("carcode", "228885");
		param.put("cardrivenumber", "211240");
		param.put("userpwd", "BB351287FC8847AC8E9C8C6C8C96F323");
		param.put("from", "proxy");
		param.put("userid", "dadidianshang2017");
		String json = HttpClientUtils.doGet("http://chaxun.cx580.com:9008/DYQueryindex2.aspx", param);
		System.out.println(json);
	}

	/**
	 * HTTP POST请求方法
	 *
	 * @param url
	 *            请求路径
	 * @param param
	 *            请求参数
	 * @return JSON 响应结果
	 */
	public static String doPostHeader(String url,String header, Map<String, String> param) {
		log.info("调用url=========="+url);
		String json = null;
		CloseableHttpResponse response = null;
		try {
			// 创建HTTP POST请求
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type",header);

			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
				httpPost.setEntity(entity);
			}
			// 执行HTTP请求
			response = getHttpClient().execute(httpPost);
			InputStream inputStream = null;
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				inputStream = response.getEntity().getContent();
			}
			if (inputStream != null) {
				json = IOUtils.toString(inputStream, "UTF-8");
				inputStream.close();
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return json;
	}
}
