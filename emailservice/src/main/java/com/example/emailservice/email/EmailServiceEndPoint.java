package com.example.emailservice.email;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.example.emailservice.email.EmailRequest;
import com.example.emailservice.email.EmailResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.regex.Pattern;

@Endpoint
public class EmailServiceEndPoint {
    @PayloadRoot(namespace = "http://segmentfault.com/schemas", localPart = "EmailRequest")
    @ResponsePayload
    public EmailResponse sendemail(@RequestPayload EmailRequest Request){
        String urls=Request.getUrls();
        String task=Request.getTask();
        String payload=Request.getPayload();
        boolean sendsuccess;
        if(task.equals("send"))
            sendsuccess=send(urls,payload);
        else
            sendsuccess=test(urls);
        EmailResponse response=new EmailResponse();
        if(sendsuccess){
            System.out.println(" true....");
            response.setStatus("Y");
        }
        else{
            System.out.println("false.....");
            response.setStatus("N");
            //response.setRemark("False");
        }
        return response;
    }

    public boolean test(String email){
        System.out.println(email);
        String pattern=".*@.*";
        boolean isMatch= Pattern.matches(pattern,email);
        email=email.replaceFirst("@","");
        boolean dMatch=Pattern.matches(pattern,email);
        if(isMatch&&!dMatch)
            return true;
        else
            return false;

    }

    public boolean send(String urls,String payload){
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
        request.setToAddress(urls);
        //request.setToAddress("邮箱1,邮箱2");
        request.setSubject("");
        request.setHtmlBody(payload);
        try {
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
