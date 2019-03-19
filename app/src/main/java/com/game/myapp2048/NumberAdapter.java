package com.game.myapp2048;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class NumberAdapter extends ArrayAdapter<Integer> {
    private Context ct;
    private ArrayList<Integer> arr;

    public NumberAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Integer> objects) {
        super(context, resource, objects);//Визов супер конструктора для context, resource i object
        this.ct=context;//Визов конструктора для context
        this.arr= new ArrayList<>(objects);//Визов конструктора для arr і створення масиву обєктів
    }

    @Override
    public void notifyDataSetChanged() {
        arr=Datagame.getDatagame().getArr();
        super.notifyDataSetChanged();//Визов конструктора
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {//Якщо convertView рівний null
            LayoutInflater inflater= (LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item_square,null);
        }
        if (arr.size()>0){//Якщо arrSize більший 0
            squareSegment o= (squareSegment) convertView.findViewById(R.id.squaresegment);
            o.formSquare(arr.get(position));
        }
        return convertView;//Повертаєм значення convertView
    }
}
