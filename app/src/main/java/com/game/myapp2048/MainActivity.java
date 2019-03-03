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
    MediaPlayer mysong;

    private GridView gdvGamePlay;
    private OSoAdapter OSoAdapter;
    private View.OnTouchListener listener;
    private float X,Y;
    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//При создании
        super.onCreate(savedInstanceState);

        mysong = MediaPlayer.create(MainActivity.this,R.raw.music);
        mysong.setLooping(true);

        setContentView(R.layout.activity_main);

        Datagame.getDatagame().getColorAndEmpyPlace(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        button1 =(Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mysong.isPlaying()){
                    mysong.stop();
                }else {
                    mysong.start();
                }

                return;
            }

        });




        anhXa();
        initialization();
        setData();
    }

    private void anhXa(){
        gdvGamePlay=(GridView)findViewById(R.id.qdvGamePlay);
    }


    private void initialization(){
        Datagame.getDatagame().Initialization();
        OSoAdapter= new OSoAdapter(MainActivity.this,0,Datagame.getDatagame().getArr());

        listener= new View.OnTouchListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        X=event.getX();
                        Y=event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(event.getX()-X)>Math.abs(event.getY()-Y)){
                            if (event.getX() > X){
                                Datagame.getDatagame().runRight();
                                OSoAdapter.notifyDataSetChanged();
                            }else {

                                Datagame.getDatagame().runLeft();
                                OSoAdapter.notifyDataSetChanged();
                            }
                        }else {
                            if (event.getY() > Y) {
                                Datagame.getDatagame().runDown();
                                OSoAdapter.notifyDataSetChanged();

                            } else {
                                Datagame.getDatagame().runUp();
                                OSoAdapter.notifyDataSetChanged();
                            }
                        }

                        break;
                }
                return true;
            }
        };
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setData(){
        gdvGamePlay.setAdapter(OSoAdapter);
        gdvGamePlay.setOnTouchListener(listener);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
