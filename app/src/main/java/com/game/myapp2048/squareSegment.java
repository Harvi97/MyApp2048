package com.game.myapp2048;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class squareSegment extends TextView {
    public squareSegment(Context context) {
        super(context);//Визов супер конструктора для context
    }

    public squareSegment(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);//Визов супер конструктора для context i attrs
    }

    public squareSegment(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr); //Визов супер конструктора для context, attrs i defStyleAttr
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int currentSize=getMeasuredWidth();
        setMeasuredDimension(currentSize,currentSize);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void formSquare(int number) {//Форма плиток
        if (number < 100) {//Якщо number менше 100
            setTextSize(40);//Розмір тексту 40
        } else if (number < 1000) {// Якщо number менше 1000
            setTextSize(35);//Розмір тексту 35
        } else {
            setTextSize(30);// Розмір тексту 30
        }
        if (number >= 8) {// Якщо number
            setTextColor(Color.WHITE);//Не відповідає умові то текс буде білий
        } else {
            setTextColor(Color.BLACK);//Відповідає умові то текст буде чорний
        }
        GradientDrawable drawable = (GradientDrawable) this.getBackground();
        drawable.setColor(Datagame.getDatagame().colorr(number));
        setBackground(drawable);
        if (number == 0) {//Якщо number рівний 0
            setText(" ");//Пуста плитка
        } else {//если
            setText("" + number);//До плитки додається номер
        }
    }
}