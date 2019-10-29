package com.android.email;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class SendbatchActivity extends AppCompatActivity implements View.OnClickListener {

    List<String> urllist=new ArrayList<>();
    EditText addurl_edit;
    EditText message;
    TextView urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendbatch_layout);
        Button add=findViewById(R.id.addemail);
        Button remove=findViewById(R.id.remove);
        Button send=findViewById(R.id.send_batch_msg);
        addurl_edit=findViewById(R.id.in_emails);
        message=findViewById(R.id.batch_msg);
        urls=findViewById(R.id.batch_urls);
        add.setOnClickListener(this);
        remove.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addemail:
                String url=addurl_edit.getText().toString();
                urllist.add(url);
                urls.append(url+"\n");
                break;
            case R.id.remove:
                urllist.remove(urllist.size()-1);
                urls.setText("邮箱：\n");
                for(String s:urllist){
                    urls.append(s+"\n");
                }
                break;
            case R.id.send_batch_msg:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String payload=message.getText().toString();
                        StringBuilder sb=new StringBuilder();
                        for(String s:urllist){
                            sb.append(s);
                            sb.append(",");
                        }
                        sb.delete(sb.length()-1,sb.length());//消除逗号

                        String[] emails=sb.toString().split(",");
                        for(String s:emails){
                            System.out.println(s+"--------------------------");
                        }
                        Message message1 = new Message();
                        if(EmailClient.sendRestBatch(emails,payload)){
                            message1.what=0;
                            handler.sendMessage(message1);
                        }

                        else {
                            message1.what=1;
                            handler.sendMessage(message1);
                        }
                    }
                }).start();
                break;
        }
    }

    Handler handler =new Handler(){
        public void handleMessage(Message message)
        {
            if(message.what==0)
                Toast.makeText(SendbatchActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(SendbatchActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
        }
    };

}
