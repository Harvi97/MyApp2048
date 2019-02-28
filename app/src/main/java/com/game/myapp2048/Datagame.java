package com.game.myapp2048;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

public class Datagame {
    private static Datagame datagame;
    private ArrayList<Integer> arr = new ArrayList<>();
    private int[] mangMau;
    private int[][] arrSo = new int[4][4];
    private int[][] mangLui = new int[4][4];
    private boolean khoau = true;
    private int so0 = 14, soDiem = 400;
    private boolean khoa = true;
    private Random r = new Random();
    private int so=2;

    static {
        datagame = new Datagame();
    }



    private void khoiTao() {
        khoa = true;
        soDiem = 0;
        so0 = 14;
    }

    private void setMangLui() {
        if (!khoau) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mangLui[i][j] = arrSo[i][j];
            }
        }
    }


    public static Datagame getDatagame() {
        return datagame;
    }

    public void getMau(Context context) {
        khoiTao();
        TypedArray ta = context.getResources().obtainTypedArray(R.array.mayNewItemIcon);
        mangMau = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            mangMau[i] = ta.getColor(i, 0);
        }
        ta.recycle();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arrSo[i][j] = 0;
            }
        }

    }// Метод підключення для плиток кольорового оформлення зі зміною кольору при досягнені павного прогресу



    public void content() {
        arr.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arr.add(arrSo[i][j]);
            }
        }
    }


    public void intt() {
        Random();
        arr.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arr.add(arrSo[i][j]);
            }
        }
    }

    public int colorr(int so) {
        if (so == 0) {
            return Color.WHITE;
        } else {
            int a = (int) (Math.log(so) / Math.log(2));
            return mangMau[a - 1];
        }
    }//Метод зміни кольору цифри на плиткі після двох кроків

    public ArrayList<Integer> getArr() {

        return arr;// повертає значення arr для getArr
    }

    private void Random() {
        if(!khoa){
            return;
        }

        so0=0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (arrSo[i][j] == 0) {
                    so0++;
                }

            }
        }
        int so = 2;
        if (so0 == 1) {
            so = 1;
        } else if (so0 == 0) {
            so = 0;
        } else {
            so = 1 + r.nextInt(2);
        }
        while (so != 0) {
            int x = r.nextInt(4), y = r.nextInt(4);
            if (arrSo[x][y] == 0) {
                int k = r.nextInt(46) + 10;
                if (k % 10 == 0) {
                    arrSo[x][y] = 4;
                    this.soDiem=this.soDiem+4;
                } else {
                    this.soDiem=this.soDiem+2;
                    arrSo[x][y] = 2;
                }
                so--;
            }
        }
        khoa=false;
    }//Метод для рандомного появлення плиток номіналом 2 та 4


    public void runLeft() {
        so0 = 0;
        setMangLui();
        khoau = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int h = arrSo[i][j];
                if (h == 0) {
                    continue;
                } else {
                    int st =j+1;
                    for (int k = st; k < 4; k++) {
                        int sox = arrSo[i][k];
                        if (sox == 0) {
                            continue;
                        } else if (sox == h) {
                            arrSo[i][j] = 2 * h;
                            arrSo[i][k] = 0;
                            khoau = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int h = arrSo[i][j];
                if (h != 0) {
                    continue;
                } else {
                    for (int k = j + 1; k < 4; k++) {
                        if (arrSo[i][k] == 0) {
                            continue;
                        } else {
                            arrSo[i][j] = arrSo[i][k];
                            arrSo[i][k] = 0;
                            khoa = true;
                            break;
                        }
                    }
                }
            }
        }
        Random();
        content();

    }//Метод руху в ліво

    public void runRight() {
        so0 = 0;
        setMangLui();
        khoau = false;
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                int h = arrSo[i][j];
                if (h == 0) {
                    continue;
                } else {
                    int st = j - 1;
                    for (int k = st; k >= 0; k--) {
                        int g = arrSo[i][k];
                        if (g == 0) {
                            continue;
                        } else if (g == h) {
                            arrSo[i][j] = 2 * h;
                            arrSo[i][k] = 0;
                            khoau = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 3; j >= 0; j--) {
                int h = arrSo[i][j];
                if (h != 0) {
                    continue;
                } else {
                    for (int k = j - 1; k >= 0; k--) {
                        if (arrSo[i][k] == 0) {
                            continue;
                        } else {
                            arrSo[i][j] = arrSo[i][k];
                            arrSo[i][k] = 0;
                            khoa = true;
                            break;
                        }
                    }
                }

            }
        }


        Random();
        content();
    }//Метод руху в право

    public void runUp() {
        so0 = 0;
        setMangLui();
        khoau = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int h = arrSo[j][i];
                if (h == 0) {

                    continue;
                } else {
                    int st = j + 1;
                    for (int k = st; k < 4; k++) {
                        int g = arrSo[k][i];
                        if (g == 0) {
                            continue;
                        } else if (g == h) {
                            arrSo[j][i] = 2 * h;
                            arrSo[k][i] = 0;
                            khoau = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int h = arrSo[j][i];
                if (h != 0) {
                    continue;
                } else {
                    for (int k = j + 1; k < 4; k++) {
                        if (arrSo[k][i] == 0) {
                            continue;
                        } else {
                            arrSo[j][i] = arrSo[k][i];
                            arrSo[k][i] = 0;
                            khoa = true;
                            break;
                        }
                    }
                }

            }
        }


        Random();
        content();
    }//Метод руху в гору

    public void runDown() {
        so0 = 0;
        setMangLui();
        khoau = false;
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                int h = arrSo[j][i];
                if (h == 0) {

                    continue;
                } else {
                    int st = j - 1;
                    for (int k = st; k >= 0; k--) {
                        int g = arrSo[k][i];
                        if (g == 0) {
                            continue;
                        } else if (g == h) {
                            arrSo[j][i] = 2 * h;
                            arrSo[k][i] = 0;
                            khoau = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 3; j >= 0; j--) {
                int h = arrSo[j][i];
                if (h != 0) {
                    continue;
                } else {
                    for (int k = j - 1; k >= 0; k--) {
                        if (arrSo[k][i] == 0) {
                            continue;
                        } else {
                            arrSo[j][i] = arrSo[k][i];
                            arrSo[k][i] = 0;
                            khoa = true;
                            break;
                        }
                    }
                }

            }
        }

        Random();
        content();
    }//Метод руху в низ

}