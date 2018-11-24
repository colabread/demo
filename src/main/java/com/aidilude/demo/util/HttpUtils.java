package com.aidilude.demo.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpUtils {

    /**
     * http get请求
     * @param url
     * @param header
     * @return
     * @throws Exception
     */
    public static String doGet(String url, Map<String, String> header) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(url);
        return getAction(url, header, httpClient, httpGet, response);
    }

    /**
     * http post请求
     * @param url
     * @param header
     * @param body
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<String, String> header, String body) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(url);
        return postAction(url, header, body, httpClient, httpPost, response);
    }

    /**
     * http put请求
     * @param url
     * @param header
     * @param body
     * @return
     * @throws Exception
     */
    public static String doPut(String url, Map<String, String> header, String body) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPut httpPut = new HttpPut(url);
        return putAction(url, header, body, httpClient, httpPut, response);
    }

    /**
     * https get请求
     * @param url
     * @param header
     * @return
     * @throws Exception
     */
    public static String doHttpsGet(String url, Map<String, String> header) throws Exception{
        CloseableHttpClient httpClient = createSSLClientDefault();
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(url);
        return getAction(url, header, httpClient, httpGet, response);
    }

    /**
     * https post请求
     * @param url
     * @param header
     * @param body
     * @return
     * @throws Exception
     */
    public static String doHttpsPost(String url, Map<String, String> header, String body) throws Exception {
        CloseableHttpClient httpClient = createSSLClientDefault();
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(url);
        return postAction(url, header, body, httpClient, httpPost, response);
    }

    /**
     * get请求动作
     * @param url
     * @param header
     * @param httpClient
     * @param httpGet
     * @param response
     * @return
     * @throws Exception
     */
    public static String getAction(String url,
                                   Map<String, String> header,
                                   CloseableHttpClient httpClient,
                                   HttpGet httpGet,
                                   CloseableHttpResponse response) throws Exception{
        try {
            if(header != null && header.size() != 0) {
                for (String key : header.keySet()) {
                    String value = header.get(key);
                    httpGet.setHeader(key, value);
                }
            }
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }else{
                throw new Exception();
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
    }

    /**
     * post请求动作
     * @param url
     * @param header
     * @param body
     * @param httpClient
     * @param httpPost
     * @param response
     * @return
     * @throws Exception
     */
    public static String postAction(String url,
                                    Map<String, String> header,
                                    String body,
                                    CloseableHttpClient httpClient,
                                    HttpPost httpPost,
                                    CloseableHttpResponse response) throws Exception {
        try {
            if(header != null && header.size() != 0) {
                for (String key : header.keySet()) {
                    String value = header.get(key);
                    httpPost.setHeader(key, value);
                }
            }
            httpPost.setEntity(new StringEntity(body, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }else{
                throw new Exception();
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
    }

    /**
     * put请求动作
     * @param url
     * @param header
     * @param body
     * @param httpClient
     * @param httpPut
     * @param response
     * @return
     * @throws Exception
     */
    public static String putAction(String url,
                                    Map<String, String> header,
                                    String body,
                                    CloseableHttpClient httpClient,
                                    HttpPut httpPut,
                                    CloseableHttpResponse response) throws Exception {
        try {
            httpPut.addHeader("Content-Type", "application/json;charset=UTF-8");
            httpPut.addHeader("Accept", "application/json");
            httpPut.setEntity(new StringEntity(body, Charset.forName("UTF-8")));
            if(header != null && header.size() != 0) {
                for (String key : header.keySet()) {
                    String value = header.get(key);
                    httpPut.setHeader(key, value);
                }
            }
            response = httpClient.execute(httpPut);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }else{
                throw new Exception();
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
    }

    /**
     * 创建https请求
     * @return
     * @throws Exception
     */
    public static CloseableHttpClient createSSLClientDefault() throws Exception {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 信任所有
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    public static void main(String[] args) throws Exception {
        String url = "http://192.168.1.23:4096/api/chains/ubiquity/betdice/getAccount?currency=ubiquity.USO";
        String result = doGet(url, null);
        System.out.println(JSON.toJSONString(result));
    }

}
