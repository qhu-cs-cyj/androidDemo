package com.example.webview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.cyj52.androiddemo.R;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetActivity extends Activity {
    TextView gettv;
    String urlString;
    private Handler handler= new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    gettv.setText(msg.obj.toString());
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        gettv = (TextView)findViewById(R.id.gettv);
        urlString = "http://www.baidu.com";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.connect();
                    InputStream is = connection.getInputStream();
                    InputStreamReader isreader = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isreader);
                    String line = null;
                    StringBuffer sb = new StringBuffer();
                    while((line = reader.readLine()) != null){
                        sb.append(line).append("\n");
                    }

                    reader.close();
                    isreader.close();
                    is.close();
                    connection.disconnect();

                    Message m = new Message();
                    m.what = 1;
                    m.obj = sb;
                    handler.sendMessage(m);
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
