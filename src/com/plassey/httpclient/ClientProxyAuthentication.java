package com.plassey.httpclient;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * A simple example that uses HttpClient to execute an HTTP request
 * over a secure connection tunneled through an authenticating proxy.
 */
public class ClientProxyAuthentication {
    
    public static void main(String[] args) throws Exception {
    	//set proxy
    	HttpHost proxy = new HttpHost("proxy.huawei.com", 8080);
    	RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
    	//set proxy auth
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(AuthScope.ANY), new UsernamePasswordCredentials("l00211859", "goodday6*"));
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
        try {
            HttpGet httpget = new HttpGet("http://www.baidu.com");
            httpget.setConfig(config);
            System.out.println("Executing request " + httpget.getRequestLine() + " via " + proxy);

            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}