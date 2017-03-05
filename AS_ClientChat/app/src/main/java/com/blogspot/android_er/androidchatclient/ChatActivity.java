package com.blogspot.android_er.androidchatclient;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    public static TextView chatMsg;
    Button buttonDisconnect;
    ChatClientThread chatClientThread;
    Handler handlerMsg;
    EditText editTextSay;
    Button buttonSend;
    String name;
    String address;
    int port;
    String msgChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        msgChat = "";
        buttonDisconnect = (Button) findViewById(R.id.disconnect);
        chatMsg = (TextView) findViewById(R.id.chatmsg);
        // create a handler to update the UI
        handlerMsg = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                myHandlerMessage(msg);
            }

        };

        editTextSay = (EditText)findViewById(R.id.say);
        buttonSend = (Button)findViewById(R.id.send);

        buttonDisconnect.setOnClickListener(buttonDisconnectOnClickListener);
        buttonSend.setOnClickListener(buttonSendOnClickListener);

        chatClientThread = null;
        String msgLog = "";
        chatMsg.setText(msgLog);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            name    = bundle.getString("name");
            address = bundle.getString("address");
            String str = bundle.getString("port");
            port  = Integer.parseInt(str);
            chatClientThread = new ChatClientThread(name, address, port);
            chatClientThread.setHandler(handlerMsg);
            chatClientThread.start();
        }
    }

    private void myHandlerMessage(Message msg) {
        Bundle bundle = msg.getData();

        msgChat = msgChat + "\n" + bundle.getString("msg");
        chatMsg.setText(msgChat);
    }

    // http://stackoverflow.com/questions/3105673/how-to-kill-an-application-with-all-its-activities
    View.OnClickListener buttonDisconnectOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (chatClientThread != null) {
                chatClientThread.exit();
            }
            finish();
        }

    };


    View.OnClickListener buttonSendOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String str = editTextSay.getText().toString();
            if ( !str.equals("") && (chatClientThread != null) ) {
                chatClientThread.sendMsg(str);
                editTextSay.setText("");
                msgChat = msgChat + "\n" + str;
                chatMsg.setText(msgChat);
            }
        }

    };

}
