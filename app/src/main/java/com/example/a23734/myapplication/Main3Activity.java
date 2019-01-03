package com.example.a23734.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

public class Main3Activity extends AppCompatActivity {

    public final static int PORT_first = 688;
    public final static int PORT_fifth=702;
    public final static int PORT_six=703;
    private Button my_button = null;
    public static int state=0;
    public static String [] matrix=new String[4];
    public static volatile int k=0;
    //public static String username="237340453";
    public static int weizhi;
    public static EditText flag;
    private ImageView imageView = null;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    public static String name_wanted;
    public static String hostname = "10.202.14.147";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        for (int i=0;i<4;i++){
            matrix[i]="";
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        flag=(EditText)findViewById(R.id.editText2);
        //my_button = (Button) findViewById(R.id.button);
        //Button btn_click=(Button)findViewById(R.id.button);
        //imageView = (ImageView) findViewById(R.id.imageView1);
        iv1=(ImageView)findViewById(R.id.imageView1);
        iv2=(ImageView)findViewById(R.id.imageView2);
        iv3=(ImageView)findViewById(R.id.imageView3);
        iv4=(ImageView)findViewById(R.id.imageView4);
        new Main3Activity.customer().start();
        /*
        for (int i=0;i<4&&i<1;i++){
            new Main3Activity.picture().start();
        }
        */
        findViewById(R.id.button0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                weizhi=0;
                state=1;
                System.out.println("ok");
                System.out.println("1");
                new Main3Activity.reserve().start();
                Intent intent = new Intent();
                intent.setClass(Main3Activity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                weizhi=1;
                state=1;
                System.out.println("ok");
                System.out.println("2");
                new Main3Activity.reserve().start();
                Intent intent = new Intent();
                intent.setClass(Main3Activity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                weizhi=2;
                state=1;
                System.out.println("ok");
                System.out.println("3");
                new Main3Activity.reserve().start();
                Intent intent = new Intent();
                intent.setClass(Main3Activity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                weizhi=3;
                state=1;
                System.out.println("ok");
                System.out.println("4");
                new Main3Activity.reserve().start();
                Intent intent = new Intent();
                intent.setClass(Main3Activity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                name_wanted=flag.getText().toString();
                Intent intent = new Intent();
                intent.setClass(Main3Activity.this, Main6Activity.class);
                startActivity(intent);
            }
        });
    }

    public class customer extends Thread {
        Socket socket = null;

        @Override
        public void run() {
            try {
                socket=new Socket(hostname,PORT_first);
                //socket.setSoTimeout(15000);
                OutputStream outputStream=socket.getOutputStream();
                Writer writer=new OutputStreamWriter(outputStream,"UTF-8");
                InputStream inputStream=socket.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String username=flag.getText().toString();
                String time=null;
                //out.write(username+'\0');
                //out.flush();
                writer.write(username+"\r\n");
                writer.flush();
                for (int i=0;i<4;i++) {
                    /*
                    for (int c = reader.read(); c != '\0'; c = reader.read()) {
                        time.append((char) c);
                    }
                    */
                    time=bufferedReader.readLine();
                    Message message = new Message();
                    final String qqq = time.toString();
                    final int finalI = i;
                    Main3Activity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            switch (finalI) {
                                case 0:
                                    downLoad("http://"+hostname+":8081/resturant/"+qqq+".jpg", "1f.png");//下载完成后就可以在手机目录里查看到了
                                    my_button = (Button) findViewById(R.id.button0);
                                    my_button.setText(qqq);
                                    matrix[0]=qqq;
                                    k=1;
                                    loadImage1();
                                    break;
                                case  1:
                                    downLoad("http://"+hostname+":8081/resturant/"+qqq+".jpg", "2s.png");//下载完成后就可以在手机目录里查看到
                                    my_button = (Button) findViewById(R.id.button1);
                                    my_button.setText(qqq);
                                    matrix[1]=qqq;
                                    k=2;
                                    loadImage2();
                                    break;
                                case  2:
                                    downLoad("http://"+hostname+":8081/resturant/"+qqq+".jpg", "3t.png");//下载完成后就可以在手机目录里查看到
                                    my_button = (Button) findViewById(R.id.button2);
                                    my_button.setText(qqq);
                                    matrix[2]=qqq;
                                    k=3;
                                    loadImage3();
                                    break;
                                default:
                                    downLoad("http://"+hostname+":8081/resturant/"+qqq+".jpg", "4f.png");//下载完成后就可以在手机目录里查看到
                                    my_button = (Button) findViewById(R.id.button3);
                                    my_button.setText(qqq);
                                    matrix[4]=qqq;
                                    k=4;
                                    loadImage4();
                                    break;
                            }
                        }
                    });
                    System.out.println(time);
                    //time.setLength(0);
                    time=null;
                }
                System.out.println("ok,prepare to accept pictures");
                //FileOutputStream fos = new FileOutputStream("server.bmp");
                /*
                byte[] buf = new byte[1024];
                int len = 0;
                FileOutputStream outputStream = openFileOutput("sever.bmp", Context.MODE_APPEND);
                loop1:      while ((len = in.read(buf)) != -1)
                {
                    //fos.write(buf,0,len);
                    outputStream.write(buf);
                    outputStream.flush();
                }
                outputStream.close();
                */
                System.out.println("picture has been conveyed");
                System.out.println("input");
                //Scanner sc = new Scanner(System.in);
                //out.write(sc.next());
                System.out.println(time);
                //out.flush();
                writer.flush();
                //fos.close();
                //in.close();
                //out.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
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

    public class reserve extends Thread {
        Socket socket = null;
        Toast toast1=Toast.makeText(getApplicationContext(), "排队成功", Toast.LENGTH_SHORT);
        Toast toast2=Toast.makeText(getApplicationContext(), "排队失败", Toast.LENGTH_SHORT);

        @Override
        public void run() {
            super.run();
            try {
                socket=new Socket(hostname,PORT_fifth);
                OutputStream outputStream=socket.getOutputStream();
                Writer writer=new OutputStreamWriter(outputStream,"UTF-8");
                InputStream inputStream=socket.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                //out.write(matrix[weizhi]+'\0');
                //out.flush();
                writer.write(matrix[weizhi]+"\r\n");
                writer.flush();
                String username=flag.getText().toString();
                //out.write(username+'\0');
                //out.flush();
                writer.write(username+"\r\n");
                writer.flush();
                if(1==bufferedReader.read()){
                    System.out.println("successful to add");
                    toast1.show();
                }else {
                    System.out.println("failed to add");
                    toast2.show();
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
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

    /*
    public class picture extends Thread{
        String hostname = "192.168.1.4";
        Socket socket = null;
        private Bitmap bmp = null;

        @Override
        public void run() {
            super.run();
            try {
                socket=new Socket(hostname,PORT_six);
                Writer out=new OutputStreamWriter(socket.getOutputStream());
                out.write(matrix[k]+'\0');
                out.flush();
                DataInputStream dataInput = new DataInputStream(socket.getInputStream());
                int size = dataInput.readInt();
                byte[] data = new byte[size];
                int len = 0;
                while (len < size) {
                    len += dataInput.read(data, len, size - len);
                }
                ByteArrayOutputStream outPut = new ByteArrayOutputStream();
                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, outPut);
                imageView.setImageBitmap(bmp);
                System.out.println("finish picture");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*


    class MyButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(Main3Activity.this, Main2Activity.class);
        }
    }

    /**
     * 从服务器下载文件
     * @param path 下载文件的地址
     * @param FileName 文件名字
     */
    public static void downLoad(final String path, final String FileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    //con.setReadTimeout(5000);
                    //con.setConnectTimeout(5000);
                    con.setRequestProperty("Charset", "UTF-8");
                    con.setRequestMethod("GET");
                    if (con.getResponseCode() == 200) {
                        InputStream is = con.getInputStream();//获取输入流
                        FileOutputStream fileOutputStream = null;//文件输出流
                        if (is != null) {
                            FileUtils fileUtils = new FileUtils();
                            fileOutputStream = new FileOutputStream(fileUtils.createFile(FileName));//指定文件保存路径，代码看下一步
                            byte[] buf = new byte[1024];
                            int ch;
                            while ((ch = is.read(buf)) != -1) {
                                fileOutputStream.write(buf, 0, ch);//将获取到的流写入文件中
                            }
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void loadImage1() {
        String path = Environment.getExternalStorageDirectory().toString() + "/shidoe";
        try {
            Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(new File(path, "1f.png")));
            iv1.setImageBitmap(bmp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void loadImage2() {
        String path = Environment.getExternalStorageDirectory().toString() + "/shidoe";
        try {
            Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(new File(path, "2s.png")));
            iv2.setImageBitmap(bmp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void loadImage3() {
        String path = Environment.getExternalStorageDirectory().toString() + "/shidoe";
        try {
            Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(new File(path, "3t.png")));
            iv3.setImageBitmap(bmp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void loadImage4() {
        String path = Environment.getExternalStorageDirectory().toString() + "/shidoe";
        try {
            Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(new File(path, "4f.png")));
            iv4.setImageBitmap(bmp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
