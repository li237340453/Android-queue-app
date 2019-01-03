package com.example.a23734.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main2Activity extends AppCompatActivity {
    public static int state=0;
    private TextView txt=Main3Activity.flag;
    public final static int PORT_eighth=705;
    private String hostname=Main3Activity.hostname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        new chaxun().start();
        findViewById(R.id.button2).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                state=1;
                System.out.println("ok");
                System.out.println("ack");
            }
        });
        findViewById(R.id.button8).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //state=1;
                System.out.println("out");
                new out().start();
            }
        });
    }
    public class chaxun extends Thread {
        Socket socket = null;
        public final static int PORT_second=689;
        Toast toast=Toast.makeText(getApplicationContext(), "到你了", Toast.LENGTH_SHORT);
        private String line;

        @Override
        public void run() {
            try {
                socket=new Socket(hostname,PORT_second);
                OutputStream outputStream=socket.getOutputStream();
                Writer writer=new OutputStreamWriter(outputStream,"UTF-8");
                //StringBuilder time=new StringBuilder();
                InputStream inputStream=socket.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                System.out.println("start querying");
                //out.write(txt.getText().toString()+'\0');
                //out.flush();
                writer.write(txt.getText().toString()+"\r\n");
                writer.flush();
                    if ((line=bufferedReader.readLine()).equals("1")){
                        System.out.println("receive qingqiu");
                        toast.show();
                        while (true) {
                            sleep(10);
                            if (state == 1) {
                                writer.write(1);
                                writer.flush();
                                System.out.println("kengding");
                                break;
                            }
                        }
                    }else {
                        sleep(1000);
                    }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(socket!=null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                state=0;
            }
        }
    }

    public class out extends Thread {
        Socket socket = null;
        Toast toast1=Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT);
        Toast toast2=Toast.makeText(getApplicationContext(), "删除失败", Toast.LENGTH_SHORT);
        private String line;

        @Override
        public void run() {
            super.run();
            try {
                socket=new Socket(hostname,PORT_eighth);
                OutputStream outputStream=socket.getOutputStream();
                Writer writer=new OutputStreamWriter(outputStream,"UTF-8");
                InputStream inputStream=socket.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                //输入店家名
                //out.write("2340715"+'\0');
                //out.flush();
                writer.write("2340715\r\n");
                writer.flush();
                //输入客户名
                //out.write("237340453"+'\0');
                //out.flush();
                writer.write("237340453\r\n");
                writer.flush();
                if (1==bufferedReader.read())
                    toast1.show();
                else
                    toast2.show();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(socket!=null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        }
    }
