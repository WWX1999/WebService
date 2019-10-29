package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Rest {
    static String IP="http://www.wwx1999.xyz:8080";
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
        return (Soap.doPOST(url,type,body));
    }

    public static boolean sendRest(String url,String payload){
        String geturl=IP+"/RestSend?url="+url+"&payload="+payload;
        return doGET(geturl);
    }

    public static boolean testEmail(String url){
        String geturl=IP+"/TestEmail?email="+url;
        return doGET(geturl);
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
                System.out.println(sb.toString());
                if(sb.toString().equals("Y"))
                    return true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
