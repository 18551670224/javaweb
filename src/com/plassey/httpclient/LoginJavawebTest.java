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
import org.apache.http.client.CookieStore;
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
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class LoginJavawebTest {
	
	private static CookieStore cookieStore = new BasicCookieStore();
	private static CloseableHttpClient client = HttpClients.createDefault();
	private static HttpGet get = new HttpGet("http://127.0.0.1:8080/javaweb/SessionServlet");
	
	public static void main(String[] args) throws Exception {
		try {
			HttpResponse response = client.execute(get);
			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
				System.out.println(header);
			}
			System.out.println("----------");
			//HttpEntity result = response.getEntity();
			//String content = EntityUtils.toString(result);
			
			//System.out.println(content);
			//printCookieStore(response);
			
			response = client.execute(get);
			headers = response.getAllHeaders();
			for (Header header : headers) {
				System.out.println(header);
			}

			//String setCookie = get.getFirstHeader("Set-Cookie").getValue();
			//System.out.println(setCookie);
			printCookieStore(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	  public static void setCookieStore(HttpResponse httpResponse) {
		    System.out.println("----setCookieStore");
		    cookieStore = new BasicCookieStore();
		    // JSESSIONID
		    String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
		    String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
		    System.out.println("JSESSIONID:" + JSESSIONID);
		    // 新建一个Cookie
		    BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
		    cookie.setVersion(0);
		    cookie.setDomain("127.0.0.1");
		    cookie.setPath("/operator/");
		    // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
		    // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
		    // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
		    // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
		    cookieStore.addCookie(cookie);
		  }
	  public static void printCookieStore(HttpResponse httpResponse) {
	    System.out.println("----printCookieStore");
	    cookieStore = new BasicCookieStore();
	    if (httpResponse.getFirstHeader("Set-Cookie") != null) {
	    	String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
	    	String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
	    	System.out.println("JSESSIONID:" + JSESSIONID);		    	
	    }
	    
	  }

}