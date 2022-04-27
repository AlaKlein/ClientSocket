package com.example.clientsocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;

public class MainActivity extends AppCompatActivity {

    private EditText ip = null;
    private EditText port = null;
    private Button send = null;
    private EditText message = null;
    private Button connect = null;
    private EditText recieved = null;

    DataInputStream in = null;
    DataOutputStream out = null;


    private void Connect() {
        try {
            Socket socket = new Socket(ip.getText().toString(), Integer.valueOf(port.getText().toString()));
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread() {
                @Override
                public void run() {
                    try {
                        while(1 > 0) {
                            recieved.setText(in.readUTF());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void Send() {
        try {
            out.writeUTF(message.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ip = (EditText) findViewById(R.id.edtx_ip);
        port = (EditText) findViewById(R.id.edtx_port);
        send = (Button) findViewById(R.id.bt_send);
        message = (EditText) findViewById(R.id.edtx_message);
        connect = (Button) findViewById(R.id.bt_connect);
        recieved = (EditText) findViewById(R.id.edtx_recieved);


        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connect();
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Send();
            }
        });

    }
}
