package com.lhm.ex_naverapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.net.URL;

public class SubActivity extends AppCompatActivity {

    ImageView img;
    TextView txt_title, txt_author, txt_price;
    Button btn_ok;
    String s_img;   //경로를 넘겨줘야하므로 멤버변수로 바꿈
    Bitmap bm;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        //ViewModelAdapter에서 넘어온 정보를 현재 액티비티에서 받아온다.

        i = getIntent();
        String s_title = i.getStringExtra("title");
        String s_author = i.getStringExtra("author");
        String s_price = i.getStringExtra("price");
        s_img = i.getStringExtra("img");



        img = findViewById(R.id.img);
        txt_title = findViewById(R.id.txt_title);
        txt_author = findViewById(R.id.txt_author);
        txt_price = findViewById(R.id.txt_price);
        btn_ok = findViewById(R.id.btn_ok);

        txt_title.setText(s_title);
        txt_author.setText(s_author);
        txt_price.setText(s_price+"원");



        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        new ImgAsync().execute(s_img);
    }   //onCreate();

    class ImgAsync extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL img_url = new URL(strings[0]);

                BufferedInputStream bis = new BufferedInputStream(img_url.openStream());

                bm = BitmapFactory.decodeStream(bis);
                bis.close();

            }catch (Exception e){

            }

            if (bm != null){
                return bm;
            }
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.rabbit);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            img.setImageBitmap(bitmap);
        }
    }





}