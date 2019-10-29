package com.example.myservice;

public class Email {
    private String[] urls;
    private String payload;

    public Email(String[] urls, String payload) {
        this.urls = urls;
        this.payload = payload;
    }

    public void setUrls(String[] urls1){
        urls=urls1;
    }

    public void setPayload(String payload1){
        payload=payload1;
    }

    public String[] getUrls(){
        return urls;
    }

    public String getpayload(){
        return payload;
    }



//    public String toString(){
//        StringBuffer sb=new StringBuffer();
//        for(String url:urls){
//            sb.append(url);
//            sb.append("\n");
//        }
//        sb.append(payload);
//        return sb.toString();
//    }
}
