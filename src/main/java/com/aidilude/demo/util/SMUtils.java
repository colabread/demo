package com.aidilude.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Slf4j
public class SMUtils {

    private static String encode = "GBK";
    private static String mobile = "13688405192";
//    private static String username = "jimeihd";   // 短信
    private static String username = "jimeiyy";   // 语音
//    private static String password_md5 = "A1C0E236B4D85A29E3B6370523F613F8";  // 短信
    private static String password_md5 = "6572bdaff799084b973320f43f09b363";  // 语音
//    private String apiKey = "2cba9167486a883aa2ea28002c22ddbb";   // 短信
    private static String apiKey = "4db2ec91834309cce68b312d9fd74d64";   // 语音

    public static String phoneYang = "18908079793";

    public static String phoneDu = "18408219153";

    public static String phoneDing = "18582851776";

    public static boolean sendMsg(String msg, String phone) {
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); //连接超时：30秒
        System.setProperty("sun.net.client.defaultReadTimeout", "30000");    //读取超时：30秒
        StringBuffer buffer = new StringBuffer();
        try {
            String contentUrlEncode = URLEncoder.encode(msg, encode);
            buffer.append("http://m.5c.com.cn/api/send/index.php?username=" + username + "&password_md5=" + password_md5 + "&mobile=" + phone + "&apikey=" + apiKey + "&content=" + contentUrlEncode + "&encode=" + encode);
            URL url = new URL(buffer.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String result = reader.readLine();
            return result.indexOf("success") >= 0;
        } catch (Exception e) {
            log.error("【短信发送异常】", e);
            return false;
        }
    }

}