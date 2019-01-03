package com.example.a23734.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.Scanner;

import javax.sql.StatementEvent;

public class Main4Activity extends AppCompatActivity {

    private String hostname=Main3Activity.hostname;
    public final static int PORT_forth=701;
    public final static int PORT_third=700;
    public final static int PORT_tenth=707;
    public static EditText identification;
    public static EditText Phone;
    public static EditText Adress;
    private TextView textView;
    Handler h = null;
    int cnt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        identification=(EditText)findViewById(R.id.editText);
        Phone=(EditText) findViewById(R.id.editText3);
        Adress=(EditText)findViewById(R.id.editText4);
        textView=(TextView)findViewById(R.id.textView);
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new dizhi().start();
                new denglu().start();
                Intent intent = new Intent();
                intent.setClass(Main4Activity.this, Main5Activity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new xiayiwei().start();
            }
        });
        h=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                textView.setText(String.valueOf(cnt));
            }
        };
    }
    public class denglu extends Thread {
        //String hostname="127.0.0.1";
        Socket socket=null;
        Toast toast=Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT);

        @Override
        public void run() {
            super.run();
            try {
                socket=new Socket(hostname,PORT_forth);
                OutputStream outputStream=socket.getOutputStream();
                Writer writer=new OutputStreamWriter(outputStream,"UTF-8");
                InputStream inputStream=socket.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String name = identification.getText().toString();
                String phone = Phone.getText().toString();
                String adress = Adress.getText().toString();
                /*
                out.write(name + '\0');
                out.flush();
                out.write(phone + '\0');
                out.flush();
                */
                writer.write(name +"\r\n");
                writer.flush();
                writer.write(phone + "\r\n");
                writer.flush();
                writer.write(Adress.getText().toString()+"\r\n");
                writer.flush();
                //给服务端发送消息
                toast.show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
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
    public class xiayiwei extends Thread {
        Socket socket=null;
        Toast toast=Toast.makeText(getApplicationContext(), "下一个确认了", Toast.LENGTH_SHORT);
        private String name;

        @Override
        public void run() {
            super.run();
            try {
                socket=new Socket(hostname,PORT_third);
                OutputStream outputStream=socket.getOutputStream();
                Writer writer=new OutputStreamWriter(outputStream,"UTF-8");
                InputStream inputStream=socket.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                System.out.println("input");
                String resturant_name=identification.getText().toString();
                /*
                out.write(resturant_name+'\0');
                out.flush();
                out.write(1);
                out.flush();
                */
                writer.write(resturant_name+"\r\n");
                writer.flush();
                writer.write("1\r\n");
                writer.flush();
                /*
                for (int c=reader.read();c!='\0';c=reader.read()){
                    name.append((char)c);
                }
                */
                name=bufferedReader.readLine();
                System.out.println(name);
                //name.setLength(0);
                name=null;
                //cnt=reader.read();
                //这里可能有错误
                cnt= bufferedReader.read();
                toast.show();
                System.out.println(cnt);
                h.sendEmptyMessage(cnt);
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
    public class shengyu extends Thread {
        Socket socket=null;

        @Override
        public void run() {
            super.run();
            try {
                socket=new Socket(hostname,PORT_third);
                OutputStream outputStream=socket.getOutputStream();
                Writer writer=new OutputStreamWriter(outputStream,"UTF-8");
                InputStream inputStream=socket.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                /*
                for (int c=reader.read();c!='\0';c=reader.read()){
                    name.append((char)c);
                }

                out.write(identification.getText().toString()+'\0');
                out.flush();
                int c=reader.read();
                */
                writer.write(identification.getText().toString()+"\r\n");
                writer.flush();
                int c= Integer.parseInt(bufferedReader.readLine());
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
    public class dizhi extends Thread {
        Socket socket=null;

        @Override
        public void run() {
            super.run();
            try {
                socket=new Socket(hostname,PORT_tenth);
                //给服务端发送消息
                OutputStream outputStream=socket.getOutputStream();
                Writer writer=new OutputStreamWriter(outputStream,"UTF-8");
                writer.write(Adress.getText().toString()+"\r\n");
                writer.flush();
                System.out.println("dizhi ok");
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
