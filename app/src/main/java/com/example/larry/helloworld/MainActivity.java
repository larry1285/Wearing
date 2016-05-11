package com.example.bauxite.ai_showimgtest;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;

import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

import android.graphics.Bitmap;

public class MainActivity extends AppCompatActivity {

    private ImageView tImg;
    private Button bt;
    private String base = "here is your url(with folder name)";//e.g. http://people.cs.nctu.edu.tw/~yourname/your folder/
    private String imgName = "default";//you can assigned any String or null(the default won't be shown)
    private List<String> imgWorld = Arrays.asList("my_checkbox.png","bubble_d.bmp","bubble_s.bmp","tile1_d.bmp","tile2_d.bmp","chess1.png","chess2","park1.png","park2.png");
    //the above imgWorld's contents can be modified to your picture names
    private int change = -1;
    private int cLeng = 8;//the length of your imgWorld
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button)findViewById(R.id.bt);
        tImg = (ImageView)findViewById(R.id.tImg);

        bt.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        change = (change+1)%cLeng;
                        loadImg();
                    }
                }
        );
    }

    @Override
    protected  void onResume(){
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected  void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
    }
    private void loadImg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bmp = getBmp();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tImg.setImageBitmap(bmp);
                    }
                });
            }
        }).start();
    }
    private Bitmap getBmp(){
        HttpURLConnection connection = null;
        try{
            imgName = imgWorld.get(change);
            URL url = new URL(base+imgName);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream in = connection.getInputStream();
            Bitmap bmp = BitmapFactory.decodeStream(in);
            return bmp;
        }catch (java.net.MalformedURLException e){
            e.printStackTrace();
            System.out.println("url problem");
            return null;
        }catch (java.io.IOException e){
            e.printStackTrace();
            System.out.println("connection problem");
            return null;
        }finally{
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
