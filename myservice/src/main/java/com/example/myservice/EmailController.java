package com.example.myservice;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;

import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

@RestController
public class EmailController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();


    @RequestMapping("/RestSend")
    public String RestSendEmail(@RequestParam(value = "url")String url,
                                @RequestParam(value = "payload")String payload){
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "LTAI4FwDkB41Y92QwX7rMqWy", "zXJpgPhq0IgxP1NkG5Y1pAmRRS3kgJ");

        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        //request.setVersion("2017-06-22");// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
        request.setAccountName("wwx@www.wwx1999.xyz");
        request.setFromAlias("WWX");
        request.setAddressType(1);
        request.setTagName("WWX");
        request.setReplyToAddress(true);
        request.setToAddress(url);
        //request.setToAddress("邮箱1,邮箱2");
        request.setSubject("");
        request.setHtmlBody(payload);
        try {
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            return "Y";
        }catch (Exception ex){
            return "N";
        }
    }

    @RequestMapping("/RestSendBatch")
    public String RestSendEmailBatch(@RequestBody Email mail){
        //System.out.println(mail.getUrls());
        String[] urls=mail.getUrls();
        String s="Y";
        for(String url:urls){
            System.out.println(url);
            String r=RestSendEmail(url,mail.getpayload());
            if(r.equals("N")) {
                s="N";
            }
        }
        return s;
        //return RestSendEmail(mail.getUrls(),mail.getpayload());
    }

    @RequestMapping("/TestEmail")
    public String TestEmail(@RequestParam(value = "email")String email){
        System.out.println(email);
        String pattern=".*@.*";
        boolean isMatch= Pattern.matches(pattern,email);
        email=email.replaceFirst("@","");
        boolean dMatch=Pattern.matches(pattern,email);
        if(isMatch&&!dMatch)
            return "Y";
        else
            return "N";


    }


//    @RequestMapping("/sendEmailBatch")
//    public String sendEmailBatch(@RequestBody Email mail){
//        //String[] urls=mail.getUrl();
//        //String payload=mail.getpayload();
//        try{
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom(from);
//            String[] urls = mail.getUrls().split(",");
//            message.setTo(urls);
//            message.setText(mail.getpayload());
//
//            System.out.println(mail.toString());
//            javaMailSender.send(message);
//            return "Y";
//        }catch (Exception e){
//            System.out.println("EEEEEE");
//            return "N";
//        }


//        try {
//            String[] urls = mail.getUrls().split("#");
//            for (int i = 0; i < urls.length - 1; i++) {
//                sendEmail(urls[i], mail.getpayload());
//            }
//            return "Y";
//        } catch (Exception e){
//            return "N";
//        }

}
