package com.android.email;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ResponseCache;
import java.net.URL;

public class EmailClient {
    static String IP="http://106.53.31.52:8080";

    static String ResponseBody="<SOAP-ENV:Envelope xmlns:SOAP-ENV" +
            "=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
            "<SOAP-ENV:Header/><SOAP-ENV:Body><ns2:EmailResponse " +
            "xmlns:ns2=\"http://segmentfault.com/schemas\"><ns2:status>" +
            "Y</ns2:status></ns2:EmailResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";

    public static void main(String[] args) throws MalformedURLException {

        //Frame();
        //String[] urls={"20171003247@cug.edu.cn","2381679817@qq.com"};
        //System.out.println(urls.toString());
        //sendRestBatch(urls,"12345");
        //sendRest("2381679817@qq.com","123");
        //run("2381679817@qq.com","123456789");
    }


    public static boolean sendsoap(String email,String payload,String task) {
        String url = IP+"/emailservice/soap/sendEmail";
        String type = "text/xml";
        String body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sch=\"http://segmentfault.com/schemas\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <sch:EmailRequest>\n" +
                "         <sch:urls>"+email+"</sch:urls>\n" +
                "         <sch:payload>"+payload+"</sch:payload>\n" +
                "         <sch:task>"+task+"</sch:task>\n"+
                "      </sch:EmailRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        return doPOST(url, type, body);

    }

    public static boolean sendRest(String url,String payload){
        String geturl=IP+"/RestSend?url="+url+"&payload="+payload;
        return doGET(geturl);
    }

    public static boolean testEmail(String url){
        String geturl=IP+"/TestEmail?email="+url;
        return doGET(geturl);
    }

    public static boolean sendRestBatch(String[] urls,String payload){
        String url=IP+"/RestSendBatch";
        String type="application/json";
        StringBuilder sb=new StringBuilder();
        sb.append("[");
        for(String s:urls){
            sb.append("\""+s+"\",");
        }
        sb.delete(sb.length()-1,sb.length());
        sb.append("],");
        String body= "{\"urls\":"+sb.toString()+
                "\"payload\":\""+payload+"\"}";
        //System.out.println(body);
        return (doPOST(url,type,body));
    }

    public static boolean doPOST(String getUrl, String type, String body) {
        try {
            //String getUrl ="http://localhost:8080/test/ws/sendEmail";
            URL url = new URL(getUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            //connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", type + ";charset=utf-8");//设置参数类型是json格式
            connection.connect();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            writer.write(body);
            writer.flush();
            writer.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder sb = new StringBuilder(); // 用来存储响应数据
                // 循环读取流,若不到结尾处
                while ((line = bf.readLine()) != null) {
                    //sb.append(bf.readLine());
                    sb.append(line);
                }
                System.out.println(sb.toString());
                if(type.equals("text/xml")&&sb.toString().equals(ResponseBody))
                    return true;
                else if(type.equals("application/json")&&sb.toString().equals("Y")) {
                    return true;
                }
                else{
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }



    public static boolean doGET(String getUrl){
        try {
            //String getUrl ="http://localhost:8080/test/ws/sendEmail";
            URL url = new URL(getUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            //connection.setRequestProperty("Content-Type", type + ";charset=utf-8");//设置参数类型是json格式
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder sb = new StringBuilder(); // 用来存储响应数据
                // 循环读取流,若不到结尾处
                while ((line = bf.readLine()) != null) {
                    //sb.append(bf.readLine());
                    sb.append(line);
                }
                System.out.println(sb.toString()+"----------------------------------------------");
                if(sb.toString().equals("Y"))
                    return true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }





}