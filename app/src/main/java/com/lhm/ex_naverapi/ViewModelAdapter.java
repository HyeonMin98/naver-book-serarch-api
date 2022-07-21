package com.lhm.ex_naverapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import vo.BookVO;

public class ViewModelAdapter extends ArrayAdapter<BookVO> {

    Context context;
    int resource;
    BookVO vo;
    ArrayList<BookVO> list;

    //list.size = 100개
    public ViewModelAdapter(Context context, int resource, ArrayList<BookVO> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;

    }


    //myListView.setAdapter(adapter)하는 순간 호출되는 메서드 (getView());
    //생성자의 파라미터를 받은 사이즈만큼 getView()메서드가 반복 호출 0~99까지 반복해서 총 100개 출력
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // xml -> view로 전환하는 작업

        //xml파일을 view형태로 만들 준비
        LayoutInflater linf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //conerView -> book_item.xml 짝궁이 없는 xml파일을 view 형태로 변경
        convertView = linf.inflate(resource, null);




        return convertView;
    }
}
