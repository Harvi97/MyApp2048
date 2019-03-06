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
        super(context);
    }

    public squareSegment(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public squareSegment(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int currentSize=getMeasuredWidth();
        setMeasuredDimension(currentSize,currentSize);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void formSquare(int number) {
        if (number < 100) {
            setTextSize(40);
        } else if (number < 1000) {
            setTextSize(35);
        } else {
            setTextSize(30);
        }
        if (number >= 8) {
            setTextColor(Color.WHITE);
        } else {
            setTextColor(Color.BLACK);
        }
        GradientDrawable drawable = (GradientDrawable) this.getBackground();
        drawable.setColor(Datagame.getDatagame().colorr(number));
        setBackground(drawable);


        if (number == 0) {
            setText(" ");
        } else {
            setText("" + number);
        }
    }
}