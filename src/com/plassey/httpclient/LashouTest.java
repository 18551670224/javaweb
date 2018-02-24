package com.plassey.httpclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class LashouTest {

	// ��ȡһ��HTMLҳ������ݣ�һ���򵥵�getӦ��
	public void grabPageHTML() throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		//HttpClient httpclient = getProxyHttpClient();
		HttpGet httpget = new HttpGet("http://www.baidu.com/");
		//HttpGet httpget = new HttpGet("http://www.baidu.com/");
		//HttpGet httpget = new HttpGet("http://10.223.135.60:7300/_plugin/head/");
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String html = EntityUtils.toString(entity, "GBK");

		// releaseConnection��ͬ��reset������������request״̬λ��Ϊ�´�ʹ������׼����
		// ��ʵ������һ��HttpGet��ȡ���ҳ����������Ч����������Ժ��Դ˷�����
		httpget.releaseConnection();

		System.out.println(html);
	}

	// ����һ���ļ������أ���ʾ����Ϊһ����֤��ͼƬ��
	public void downloadFile() throws Exception {
		String url = "http://www.lashou.com/account/captcha";
		String destfilename = "D:\\yz.png";
    	//set proxy
    	HttpHost proxy = new HttpHost("proxy.huawei.com", 8080);
    	RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
    	//set proxy auth
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(AuthScope.ANY), new UsernamePasswordCredentials("l00211859", "goodday6*"));
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

		HttpGet httpget = new HttpGet(url);
		httpget.setConfig(config);
		File file = new File(destfilename);
		if (file.exists()) {
			file.delete();
		}

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		try {
			FileOutputStream fout = new FileOutputStream(file);
			int l = -1;
			byte[] tmp = new byte[2048];
			while ((l = in.read(tmp)) != -1) {
				fout.write(tmp);
			}
			fout.close();
		} finally {
			// ����InputStream����HttpEntityʱ���м�Ҫ�رյͲ�����
			in.close();
		}

		httpget.releaseConnection();
	}

	// Post������ģ����ύ������¼����վ��
	// �������������������grabPageHTML/downloadFile��ͬʱ������Post�Ĵ��롣
	public void login2Lashou() throws Exception {
		// ��һ������������֤�뵽����
		String url = "http://www.lashou.com/account/captcha";
		String destfilename = "D:\\aaa.png";
    	//set proxy
    	HttpHost proxy = new HttpHost("proxy.huawei.com", 8080);
    	RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
    	//set proxy auth
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(AuthScope.ANY), new UsernamePasswordCredentials("l00211859", "goodday6*"));
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

		HttpGet httpget = new HttpGet(url);
		httpget.setConfig(config);
		File file = new File(destfilename);
		if (file.exists()) {
			file.delete();
		}

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		try {
			FileOutputStream fout = new FileOutputStream(file);
			int l = -1;
			byte[] tmp = new byte[2048];
			while ((l = in.read(tmp)) != -1) {
				fout.write(tmp);
			}
			fout.close();
		} finally {
			in.close();
		}
		httpget.releaseConnection();

		// �ڶ�������Post���������ɲ������Ե�¼����Ҫ�ֹ�����������֤������ʾ����ĸ������
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("������������������֤������ʾ������...");
		String yan = br.readLine();

		HttpPost httppost = new HttpPost("http://www.lashou.com/account/login/");
		httppost.setConfig(config);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user_id", "plassey"));
		params.add(new BasicNameValuePair("pwd", "42126088"));
		params.add(new BasicNameValuePair("yan", yan));
		params.add(new BasicNameValuePair("save_user", "on"));
		params.add(new BasicNameValuePair("save_pwd", "on"));
		params.add(new BasicNameValuePair("sub", "��¼"));
		httppost.setEntity(new UrlEncodedFormEntity(params));

		response = httpclient.execute(httppost);
		entity = response.getEntity();
		// �����������Jsoup֮��Ĺ��߶Է��ؽ�����з��������жϵ�¼�Ƿ�ɹ�
		String postResult = EntityUtils.toString(entity, "GBK");
		//System.out.println(postResult);
		// ��������ֻ�Ǽ򵥵Ĵ�ӡ����ǰCookieֵ���жϵ�¼�Ƿ�ɹ���
		//List<Cookie> cookies = ((AbstractHttpClient) httpclient).getCookieStore().getCookies();
		//for (Cookie cookie : cookies)
		//	System.out.println(cookie);
		httppost.releaseConnection();

		// ���������򿪻�Աҳ�����жϵ�¼�ɹ���δ��¼�û��Ǵ򲻿���Աҳ��ģ�
		String memberpage = "http://www.lashou.com/account/orders/";
		httpget = new HttpGet(memberpage);
		httpget.setConfig(config);
		response = httpclient.execute(httpget); // ������ͬһ��HttpClient��
		entity = response.getEntity();
		String html = EntityUtils.toString(entity, "GBK");
		httpget.releaseConnection();

		System.out.println(html);
	}

	// ���ô��������
	public void testProxy() throws Exception {
		HttpHost proxy = new HttpHost("127.0.0.1", 8888);

		// ��ʽһ
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

		// ��ʽ��
		HttpParams params = new BasicHttpParams();
		params.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		HttpClient httpclient1 = new DefaultHttpClient(params);
	}

	// ���ֳ���HTTPͷ������
	public void testBasicHeader() throws Exception {
		HttpParams params = new BasicHttpParams();
		Collection<BasicHeader> collection = new ArrayList<BasicHeader>();
		collection.add(new BasicHeader("Accept", "text/html, application/xhtml+xml, */*"));
		collection.add(new BasicHeader("Referer", "http://www.sina.com/"));
		collection.add(new BasicHeader("Accept-Language", "zh-CN"));
		collection.add(new BasicHeader("User-Agent",
				"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)"));
		collection.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
		params.setParameter(ClientPNames.DEFAULT_HEADERS, collection);

		HttpClient httpclient = new DefaultHttpClient(params);

		// ����������
	}

	// ���̱߳���µ��̳߳����ã��������Ҫ��¼����һ��HttpClient����ץȡ���ҳ���������ر����ã�
	public void testConnectionManager() throws Exception {
		// ���ӳ�����
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		cm.setMaxTotal(200); // ���ӳ�������������
		cm.setDefaultMaxPerRoute(20); // ÿ��·�ɵ�Ĭ�����������
		HttpHost localhost = new HttpHost("locahost", 80); // �������ĳ�ض���վָ�����������
		cm.setMaxPerRoute(new HttpRoute(localhost), 30);

		// ��������
		HttpParams params = new BasicHttpParams();
		params.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		HttpClient httpclient = new DefaultHttpClient(cm, params);

		// ����������
	}

	// ����HTTP�����Ķ���HttpContext��
	public void testContext() throws Exception {
		// ����һ��ҳ�棬Ȼ������������Ķ���
		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpContext localContext = new BasicHttpContext();
		HttpGet httpget = new HttpGet("http://www.baidu.com/");

		HttpResponse response = httpclient.execute(httpget, localContext);

		// the actual connection to the target server.
		HttpConnection conn = (HttpConnection) localContext.getAttribute(ExecutionContext.HTTP_CONNECTION);
		System.out.println("Socket timeout: " + conn.getSocketTimeout());

		// the connection target
		HttpHost target = (HttpHost) localContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
		System.out.println("Final target: " + target);

		// the connection proxy, if used
		HttpHost proxy = (HttpHost) localContext.getAttribute(ExecutionContext.HTTP_PROXY_HOST);
		if (proxy != null)
			System.out.println("Proxy host/port: " + proxy.getHostName() + "/" + proxy.getPort());

		// the actual HTTP request
		HttpRequest request = (HttpRequest) localContext.getAttribute(ExecutionContext.HTTP_REQUEST);
		System.out.println("HTTP version: " + request.getProtocolVersion());
		Header[] headers = request.getAllHeaders();
		System.out.println("HTTP Headers: ");
		for (Header header : headers) {
			System.out.println("\t" + header.getName() + ": " + header.getValue());
		}
		System.out.println("HTTP URI: " + request.getRequestLine().getUri());

		// the actual HTTP response
		response = (HttpResponse) localContext.getAttribute(ExecutionContext.HTTP_RESPONSE);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			System.out.println("Content Encoding��" + entity.getContentEncoding());
			System.out.println("Content Type��" + entity.getContentType());
		}

		// the flag indicating whether the actual request has been fully transmitted to
		// the connection target.
		System.out.println("Sent flag: " + localContext.getAttribute(ExecutionContext.HTTP_REQ_SENT));

		// ���û���õ�����entity�е����ݣ���ôҪ�������ѵ����Ա�֤�ײ����Դ�����ͷš�
		entity = response.getEntity();
		EntityUtils.consume(entity);
	}

	// ���ô���

	public static HttpClient getProxyHttpClient() {

	     DefaultHttpClient httpClient = new DefaultHttpClient();
	     String proxyHost = "proxy.huawei.com";
	     int proxyPort = 8080;
	     //String userName = "china\\l00211859";
	     String userName = "l00211859";
	     String password = "goodday6*";
	     httpClient.getCredentialsProvider().setCredentials(
	       new AuthScope(proxyHost, proxyPort),
	       new UsernamePasswordCredentials(userName, password));
	     HttpHost proxy = new HttpHost(proxyHost,proxyPort);
	     httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
	     return httpClient;
	    }

	public static void main(String[] args) throws Exception {
		LashouTest ins = new LashouTest();

		//ins.grabPageHTML();
		//ins.downloadFile();
		ins.login2Lashou();
		// ins.testContext();
	}
}