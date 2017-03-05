package com.blogspot.android_er.androidchatclient;

// https://examples.javacodegeeks.com/android/core/socket-core/android-socket-example/
// http://stackoverflow.com/questions/20131316/android-tcp-ip-socket-wont-send-data-to-computer/20131985#20131985
// http://www.androidhive.info/2014/10/android-building-group-chat-app-using-sockets-part-1/
// http://android-er.blogspot.it/2014/02/android-sercerclient-example-client.html
// http://www.vogella.com/tutorials/AndroidBackgroundProcessing/article.html#providing-feedback-to-a-long-running-operation
// https://aws.amazon.com/it/blogs/mobile/making-asynchronous-calls-with-handler/
// http://android-er.blogspot.it/2014/08/bi-directional-communication-between.html

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextUserName, editTextAddress;
    Button buttonConnect;
    TextView editTextPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUserName = (EditText) findViewById(R.id.username);
        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        buttonConnect = (Button) findViewById(R.id.connect);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
    }


    View.OnClickListener buttonConnectOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String textUserName = editTextUserName.getText().toString();
            if (textUserName.equals("")) {
                Toast.makeText(MainActivity.this, "Enter User Name",
                        Toast.LENGTH_LONG).show();
                return;
            }

            String textAddress = editTextAddress.getText().toString();
            if (textAddress.equals("")) {
                Toast.makeText(MainActivity.this, "Enter Addresse",
                        Toast.LENGTH_LONG).show();
                return;
            }

            String textPort = editTextPort.getText().toString();
            if (textPort.equals("")) {
                Toast.makeText(MainActivity.this, "Enter port",
                        Toast.LENGTH_LONG).show();
                return;
            }
            Intent chatActivity = new Intent(getApplicationContext(), ChatActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("name", textUserName);
            bundle.putString("address", textAddress);
            bundle.putString("port", textPort);

            chatActivity.putExtras(bundle);

            startActivity(chatActivity);
        }
    };


}
