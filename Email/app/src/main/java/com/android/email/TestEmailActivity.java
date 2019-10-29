package com.android.email;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TestEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testemaillayout);
        final EditText emailedit=findViewById(R.id.testemail);
        Button test=findViewById(R.id.testbutton);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String email=emailedit.getText().toString();
                        Message message=new Message();
                        if(EmailClient.testEmail(email))
                            message.what=0;
                        else
                            message.what=1;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    Handler handler =new Handler(){
        public void handleMessage(Message message)
        {
            if(message.what==0)
                Toast.makeText(TestEmailActivity.this, "邮箱格式正确", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(TestEmailActivity.this, "无效的邮箱", Toast.LENGTH_SHORT).show();
        }
    };

}
