package com.lhm.ex_naverapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import parser.Parser;
import vo.BookVO;

public class NaverActivity extends AppCompatActivity {

    public static EditText search;
    ListView myListView;
    Button search_btn;
    Parser parser;
    ViewModelAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver);

        search = findViewById(R.id.search);
        myListView = findViewById(R.id.myListView);
        search_btn = findViewById(R.id.search_btn);
        parser = new Parser();

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              parser.connectNaver(); 이렇게 사용시 클릭하면 작업이 완료될때까지 다른작업을 전부 못한다.
                //백그라운드에서 로딩하며, 서버에 연결(Async클래스의 doInBackground())
                new NaverAsync().execute("홍","길","동");
            }
        });


    }//onCreate()

    // AsyncTask : 백그라운드에서의 서버통신을 위해 반드시 필요한 클래스
    // AsyncTast는 세개의 제너릭 타입을 가지고있다.
    //1) doInBackground의 파라미터 타입
    //2) onProgressUpdate가 오버라이딩 되어 있다면 메서드에서 사용할 타입
    //3) doInBackgroud의 반환형이자, 작업의 최종 결과를 차지하는 onPostExecute()의 파라미터 타입ㅣ=]
    class NaverAsync extends AsyncTask<String, Void, ArrayList<BookVO>> {
        @Override
        //doInBackground(String...strings) ...의 의미 : variable agrguments 배열의 개수를 따지지 않고 파라미터로 들어온는 
        //모든것들을 배열로 만들어 주는 긴으을 가능하게 한다. *개수의 제한이 없다.
        // Strings[0] -> 홍 
        // Strings[1] -> 길
        // Strings[2] -> 동
        // Strings[3] -> 전
        protected ArrayList<BookVO> doInBackground(String... strings) {
            //필수 메서드임
            //각종 반복이나 제어등의 백그라운드에서 필요한 처리 코드를 담당하는 메서드이다.

            return parser.connectNaver();
        }

        @Override
        protected void onPostExecute(ArrayList<BookVO> bookVOS) {
            //doInBackground에서 return된 최종 작업 결과를 bookVOS가 받게된다.

            //bookVOS를 ListView를 그리기 위해 존재하는 adapter클래스에게 넘겨줘야한다.(컨텍스트, 한 칸 디자인, 책 정보를 adapter로)
            adapter = new ViewModelAdapter(NaverActivity.this, R.layout.book_item, bookVOS);

            //준비된 어댑터를 ListView에 탑재
            myListView.setAdapter(adapter);

//            잘 넘어오는지 로그로 확인
//            for(int i = 0; i < bookVOS.size(); i++){
//                Log.i("MY",""+bookVOS.get(i).getB_title());
//            }

        }
    }

}