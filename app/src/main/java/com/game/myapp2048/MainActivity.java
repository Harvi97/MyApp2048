package com.game.myapp2048;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mysong;//Створення зміної mysogn
    private GridView gdvGamePlay;
    private NumberAdapter numberAdapter;
    private View.OnTouchListener listener;
    private float X, Y;
    private Button button1;//\
    private Button button2;//- Обявление 3 кнопок
    private Button button3;///

    @Override
    protected void onCreate(Bundle savedInstanceState) {//При создании
        super.onCreate(savedInstanceState);

        mysong = MediaPlayer.create(MainActivity.this, R.raw.music);//Подключение музики по id
        mysong.setLooping(true);//Разрешить повтор

        setContentView(R.layout.activity_main);//Подключение по id activity_main

        Datagame.getDatagame().getColorAndEmpyPlace(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        button1 = (Button) findViewById(R.id.button1);//Підключення 1 кнопки по id
        button2 = (Button) findViewById(R.id.button2);//Підключення 2 кнопки по id
        button3 = (Button) findViewById(R.id.button3);//Підключення 3 кнопки по id


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Метод кліку кнопки 1
                finish();//текущий Activity буде остановлен
                startActivity(getIntent());//Запуск нового Activity
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Метод кліку кнопки 2
                finish();
                ;//текущий Activity буде остановлен
                System.exit(0);//Закриває програму
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Метод кліку кнопки 3
                if (mysong.isPlaying()) {//Якщо грає музика
                    mysong.stop();//Остановить
                } else {
                    mysong.start();//Запустить
                }
            }

        });


        gdvGamePlay = (GridView) findViewById(R.id.qdvGamePlay);//Подключенния по id игрового поля
        initialization();
        setData();
    }


    private void initialization() {
        Datagame.getDatagame().Initialization();
        numberAdapter = new NumberAdapter(MainActivity.this, 0, Datagame.getDatagame().getArr());

        listener = new View.OnTouchListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {//Переключатєль собитій
                    case MotionEvent.ACTION_DOWN://Собитіє монітора ActionDown
                    X = event.getX();//Ддя Х отримать собитіє Х
                    Y = event.getY();//Для Y отримать собитіє Y
                     break;
                    case MotionEvent.ACTION_UP://Собитіє монітора ACTION_UP
                        if (Math.abs(event.getX() - X) > Math.abs(event.getY() - Y)) {//Якщо
                            if (event.getX() > X) {//Якщо собитіє Х менше Х
                                Datagame.getDatagame().runRight();// Рух вправо
                                numberAdapter.notifyDataSetChanged();//зберігає і обновляє набір даний який змінився
                            } else {
                                Datagame.getDatagame().runLeft();//рух в ліво
                                numberAdapter.notifyDataSetChanged();//зберігає і обновляє набір даний який змінився
                            }
                        } else {
                            if (event.getY() > Y) {
                                Datagame.getDatagame().runDown();//рух в низ
                                numberAdapter.notifyDataSetChanged();//зберігає і обновляє набір даний який змінився

                            } else {
                                Datagame.getDatagame().runUp();//рух в гору
                                numberAdapter.notifyDataSetChanged();//зберігає і обновляє набір даний який змінився
                            }
                        }

                        break;//пауза
                }
                return true;//дозволити возврат
            }
        };
    }



    @SuppressLint("ClickableViewAccessibility")
    private void setData(){
        gdvGamePlay.setAdapter(numberAdapter);
        gdvGamePlay.setOnTouchListener(listener);
    }

    @Override
    protected void onStart() {
        super.onStart();//Переключатєль OnStart
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();//Переключатєль OnDestroy
        finish();//Закінчить роботу
    }
}
