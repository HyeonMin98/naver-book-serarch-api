package com.lhm.ex_naverapi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

import vo.BookVO;

public class ViewModelAdapter extends ArrayAdapter<BookVO> {

    Context context;
    int resource;
    BookVO vo;
    ArrayList<BookVO> list;

    //list.size = 100개
    public ViewModelAdapter(Context context, int resource, ArrayList<BookVO> list, ListView myListView) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
        myListView.setOnItemClickListener(click);
    } //생성자

    //리스트뷰의 클릭을 감지하는 감지자 생성
    AdapterView.OnItemClickListener click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String title = list.get(i).getB_title();
            String author = list.get(i).getB_author();
            String price = list.get(i).getB_price();
            String img = list.get(i).getB_img();

            //화면 전환을 위한 Intent 준비
            Intent intent = new Intent(context,SubActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("author", author);
            intent.putExtra("price", price);
            //putExtra에 이미지뷰를 담을수 없어 경로만 보낸다.
            intent.putExtra("img", img);

            context.startActivity(intent);
        }
    };


    //리스트 뷰의 클릭을 감지하는 감지자 생성


    //myListView.setAdapter(adapter)하는 순간 호출되는 메서드 (getView());
    //생성자의 파라미터를 받은 사이즈만큼 getView()메서드가 반복 호출 0~99까지 반복해서 총 100개 출력
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //xml파일을 view형태로 만들 준비
        LayoutInflater linf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //conerView -> book_item.xml 짝궁이 없는 xml파일을 view 형태로 변경
        convertView = linf.inflate(resource, null);

        vo = list.get(position); //포지션이 0~99번까지 알아서 반복하기 때문에 반복문을 쓰지 않아도 100개에 대한 내용을 담을수있다.

        //값을 지정해주기 위해서 findViewBtId를 해야하는데 액티비티가 아니므로 View를 이용하여 검색한다.
        TextView title = convertView.findViewById(R.id.book_title);
        TextView author = convertView.findViewById(R.id.book_author);
        TextView price = convertView.findViewById(R.id.book_price);
        ImageView img = convertView.findViewById(R.id.book_img);

        title.setText(vo.getB_title());
        author.setText(vo.getB_author());
        price.setText(vo.getB_price() + "원");
        new ImgAsync(img,vo).execute();//doInBackground()호출

        return convertView;
    }//getView()

    //이미지를 가져올 Async 클래스

    class ImgAsync extends AsyncTask<Void, Void, Bitmap>{
        Bitmap bm;
        ImageView img;
        BookVO vo;

        public ImgAsync(ImageView img, BookVO vo) {
            this.img = img;
            this.vo = vo;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try{
                //vo가 가지고 있는 vo.getB_img()를 통해 이미지 경로를 들어간다.
                URL img_url = new URL(vo.getB_img());

                //BufferedInputStream을 통해 이미지 로드 : 전용공간을 만들어 데이터를빠르게 가져온다.
                //buffered : InputStream을 도와 더 빨리 데이터를 입출력 하기 위한 스트림

                BufferedInputStream bis = new BufferedInputStream(img_url.openStream());

                //bis가 읽어온 데이터를 이미지로 변환하기 위해 bitmap형태로 변경
                bm = BitmapFactory.decodeStream(bis);
                bis.close();

            }catch (Exception e){}

            if(bm == null){
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.cat);
                return bitmap;
            }

            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //비트맵 객체를 이미지뷰로 전환
            img.setImageBitmap(bitmap);
        }
    }
}
