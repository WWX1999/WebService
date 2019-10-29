package com.android.email;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendEmailLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_email_layout);
        final EditText email=findViewById(R.id.email);
        final EditText message=findViewById(R.id.msg);
        Button Send=findViewById(R.id.sendmsg);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url=email.getText().toString();
                        String payload=message.getText().toString();
                        Message message1 = new Message();
                        if(EmailClient.sendRest(url,payload)) {
                            message1.what=0;
                            handler.sendMessage(message1);
                        }
                        else{
                            message1.what=1;
                            handler.sendMessage(message1);
                        }
                    }
                }).start();
            }
        });



    }
     Handler  handler =new Handler(){
        public void handleMessage(Message message)
        {
            if(message.what==0)
                Toast.makeText(SendEmailLayoutActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(SendEmailLayoutActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
        }
    };
}
