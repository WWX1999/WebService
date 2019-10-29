package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Soap {
    static String IP="http://www.wwx1999.xyz:8080";
    static String ResponseBody="<SOAP-ENV:Envelope xmlns:SOAP-ENV" +
            "=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
            "<SOAP-ENV:Header/><SOAP-ENV:Body><ns2:EmailResponse " +
            "xmlns:ns2=\"http://segmentfault.com/schemas\"><ns2:status>" +
            "Y</ns2:status></ns2:EmailResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";




    public static boolean sendSoap(String email,String payload,String task) {
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
}
