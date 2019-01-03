package com.example.a23734.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import static com.example.a23734.myapplication.Main3Activity.downLoad;

public class Main6Activity extends AppCompatActivity {
    private String hostname = Main3Activity.hostname;
    public final static int PORT_seventh = 704;
    private TextView txt = Main3Activity.flag;
    private ImageView imageView = null;
    private TextView phone;
    private TextView adress;
    private TextView name;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        name = findViewById(R.id.textView2);
        adress = findViewById(R.id.textView4);
        imageView = findViewById(R.id.imageView5);
        phone = findViewById(R.id.textView3);
        new search().start();
    }

    public class search extends Thread {
        Socket socket = null;
        private String time;

        @Override
        public void run() {
            super.run();
            try {
                socket = new Socket(hostname, PORT_seventh);
                OutputStream outputStream=socket.getOutputStream();
                Writer writer=new OutputStreamWriter(outputStream,"UTF-8");
                InputStream inputStream=socket.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                //out.write(String.valueOf(txt)+'\0');
                /*
                out.write("2340715\0");
                out.flush();
                time.setLength(0);
                */
                writer.write("2340715\r\n");
                writer.flush();
                time=null;
                if (1==bufferedReader.read()) {
                    /*
                    for (int c = reader.read(); c != '\0'; c = reader.read()) {
                        time.append((char) c);
                    }
                    */
                    time=bufferedReader.readLine();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            phone.setText(time);
                        }
                    });
                    time=null;
                    /*;
                    for (int c = reader.read(); c != '\0'; c = reader.read()) {
                        time.append((char) c);
                    }
                    */
                    time=bufferedReader.readLine();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adress.setText(time);
                        }
                    });
                    time=null;
                    //final String androidNO=time.toString();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //phone.setText(phoneNO.toString());
                            //adress.setText(androidNO.toString());
                            name.setText(txt.getText().toString());
                            //loadImage();
                        }
                    });
                    //final String phoneNO=time.toString();
                    //downLoad("http://"+hostname+":8080/resturant/"+txt.getText()+".jpg", "che.png");
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            //从网络上获取图片
                            final Bitmap bitmap=getPicture("http://10.202.14.147:8081/resturant/2340715.jpg");

                            try {
                                Thread.sleep(2000);//线程休眠两秒钟
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            //发送一个Runnable对象
                            imageView.post(new Runnable(){


                                @Override
                                public void run() {
                                    imageView.setImageBitmap(bitmap);//在ImageView中显示从网络上获取到的图片
                                }

                            });

                        }
                    }).start();//开启线程
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            phone.setText("无");
                            adress.setText("无");
                            name.setText("无");
                        }
                    });
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private void loadImage() {
        Toast toast1 = Toast.makeText(getApplicationContext(), "加载成功", Toast.LENGTH_SHORT);
        Toast toast2 = Toast.makeText(getApplicationContext(), "加载失败", Toast.LENGTH_SHORT);
        String path = Environment.getExternalStorageDirectory().toString() + "/shidoe";
        try {
            Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(new File(path, "che.png")));
            imageView.setImageBitmap(bmp);
            toast1.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            toast2.show();
        }
    }

    public Bitmap getPicture(String path) {
        Bitmap bm = null;
        URL url;
        try {
            url = new URL(path);//创建URL对象
            URLConnection conn = url.openConnection();//获取URL对象对应的连接
            conn.connect();//打开连接
            InputStream is = conn.getInputStream();//获取输入流对象
            bm = BitmapFactory.decodeStream(is);//根据输入流对象创建Bitmap对象
        } catch (MalformedURLException e1) {
            e1.printStackTrace();//输出异常信息
        } catch (IOException e) {
            e.printStackTrace();//输出异常信息
        }
        return bm;
    }
}
