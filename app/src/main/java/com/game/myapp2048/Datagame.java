package com.game.myapp2048;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

public class Datagame {
    private static Datagame datagame;
    private ArrayList<Integer> arr = new ArrayList<>();
    private int[] ColorArray; //Массив цветов
    private int[][] NumberArray = new int[4][4]; //Массив Номеров
    private int СurrentResult; //soDiem - Количество очков(балов) набраных
    private boolean BlockRandom = true; //Опять блокировка?
    private Random r = new Random();

    static {//Блок инициализации переменных при статическом вызове
        datagame = new Datagame();
    }



    private void InitializationLocalParametrs() {//Инициализация
        BlockRandom = true;//Блокировка
        СurrentResult = 0; //Номер точки
    }


    public static Datagame getDatagame() {
        return datagame;
    }

    public void getColorAndEmpyPlace(Context context) {//получить цвета и очистить поле. Метод инициализационный
        InitializationLocalParametrs();

        TypedArray ta = context.getResources().obtainTypedArray(R.array.mayNewItemIcon);//Получаем цвет политру
        ColorArray = new int[ta.length()];//Создаем массив для всех цветов
        for (int i = 0; i < ta.length(); i++) {//По порядку
            ColorArray[i] = ta.getColor(i, 0);//Выдаем цвет
        }
        ta.recycle();//Удаляем политру

        for (int i = 0; i < 4; i++) {//ДЛя всех элементов поля
            for (int j = 0; j < 4; j++) {
                NumberArray[i][j] = 0;//Присваиваем пустоту
            }
        }

    }// Метод підключення для плиток кольорового оформлення зі зміною кольору при досягнені павного прогресу



    public void content() {//После отработки жеста и перемещения во внутреннем массиве элементов, передаем их в ArrayList
        arr.clear();       //На котором завязан механизм визуализации
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arr.add(NumberArray[i][j]);
            }
        }
    }


    public void Initialization() {
        Random();
        arr.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arr.add(NumberArray[i][j]);
            }
        }
    }

    public int colorr(int number) {//Метод меняет фон плитки
        if (number == 0) {
            return Color.WHITE;
        } else {
            int a = (int) (Math.log(number) / Math.log(2));
            return ColorArray[a - 1];
        }
    }

    public ArrayList<Integer> getArr() {

        return arr;// повертає значення arr для getArr
    }

    private void Random() {
        if(!BlockRandom){
            return;
        }

        int Counter=0;//Счетчик
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (NumberArray[i][j] == 0) {
                    Counter++;//Считаем сколько свободных клеток без цифр
                }

            }
        }
        int number = 2;
        if (Counter == 1) {//Если мы можем создать только 1
            number = 1;//Генерим 1 рандомную точку
        } else if (Counter == 0) {//Есили поле не пустое
            number = 0;//Ничего не рандомим
        } else {
            number = 1 + r.nextInt(2);//Если нет, рандомим от 1 до 2
        }
        while (number != 0) {//Пока мы можем что-то сгенерить
            int x = r.nextInt(4), y = r.nextInt(4);//Генерим случайные кординаты
            if (NumberArray[x][y] == 0) {//Если эти кординаты пустые
                int k = r.nextInt(46) + 10;//Генерим вероятность сгенерировать четверку
                if (k % 10 == 0) {//Проверяем вероятность
                    NumberArray[x][y] = 4;//Добавляем 4 на поле
                    this.СurrentResult=this.СurrentResult+4;//Добавляем очки
                } else {
                    this.СurrentResult=this.СurrentResult+2;//Добавляем 2 очка
                    NumberArray[x][y] = 2;//Добавляем двойку на поле
                }
                number--;//Понижаем счетчик количества сколько нужно еще сгенерить чисел
            }
        }
        BlockRandom=false;
    }//Метод для рандомного появлення плиток номіналом 2 та 4


    public void runLeft() {
        //setMangLui();
        BlockRandom = false;//БЛОКИРУЕМ РАНДОМ ЕСЛИ МЫ НЕ НАЙДЕМ ВОЗМОЖНЫЙ ХОД
        for (int i = 0; i < 4; i++) {//Для каждой строки
            for (int j = 0; j < 4; j++) {//Для каждого элемента этой строки
                int CheckedValue = NumberArray[i][j];//Запоминаем текущий элемент
                if (CheckedValue == 0) {//Для элементов которые
                    continue;// не равны нулю
                } else {
                    int Current =j+1;//Указатель текущего элемента прохода
                    for (int k = Current; k < 4; k++) {
                        int NumberRight = NumberArray[i][k];//Запоминаем элемент справа
                        if (NumberRight == 0) {
                            continue;//Если элемент пустой - переходим на следущий
                        } else if (NumberRight == CheckedValue) {//Если элемент справа равен проверяемому элементу
                            NumberArray[i][j] = 2 * CheckedValue;//Устанавливаем значение элемента слева умноженым на 2
                            NumberArray[i][k] = 0;//Удаляем элемент который переместили
                            BlockRandom = true;//РАЗБЛАКИРОВКА РАНДОМА
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {//Для каждой строки
            for (int j = 0; j < 4; j++) {//Для каждого элемента этой строки
                int CheckedValue = NumberArray[i][j];//Запоминаем текущий элемент
                if (CheckedValue != 0) {//Для элементов которые
                    continue;//равны нулю
                } else {
                    for (int k = j + 1; k < 4; k++) {//Проходим по всем элементам этой строки
                        if (NumberArray[i][k] == 0) {//Если пустой
                            continue;//Пропускаем
                        } else {//Если с значением
                            NumberArray[i][j] = NumberArray[i][k];//Копируем элемент справа в левую ячейку
                            NumberArray[i][k] = 0;//Удаляем скопированый элемент
                            BlockRandom = true;//РАЗБЛОКИРУЕМ РАНДОМ
                            break;
                        }
                    }
                }
            }
        }
        Random();//Вызываем Рандом новых чисел
        content();//Отображаем на дисплее

    }//Метод руху в ліво

    public void runRight() {
        //setMangLui();
        BlockRandom = false;
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                int CheckedValue = NumberArray[i][j];
                if (CheckedValue == 0) {
                    continue;
                } else {
                    int Current = j - 1;
                    for (int k = Current; k >= 0; k--) {
                        int NumberLeft = NumberArray[i][k];
                        if (NumberLeft == 0) {
                            continue;
                        } else if (NumberLeft == CheckedValue) {
                            NumberArray[i][j] = 2 * CheckedValue;
                            NumberArray[i][k] = 0;
                            BlockRandom = true;
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
                int CheckedValue = NumberArray[i][j];
                if (CheckedValue != 0) {
                    continue;
                } else {
                    for (int k = j - 1; k >= 0; k--) {
                        if (NumberArray[i][k] == 0) {
                            continue;
                        } else {
                            NumberArray[i][j] = NumberArray[i][k];
                            NumberArray[i][k] = 0;
                            BlockRandom = true;
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
        //setMangLui();
        BlockRandom = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int CheckedValue = NumberArray[j][i];
                if (CheckedValue == 0) {

                    continue;
                } else {
                    int Current = j + 1;
                    for (int k = Current; k < 4; k++) {
                        int NumberDown = NumberArray[k][i];
                        if (NumberDown == 0) {
                            continue;
                        } else if (NumberDown == CheckedValue) {
                            NumberArray[j][i] = 2 * CheckedValue;
                            NumberArray[k][i] = 0;
                            BlockRandom = true;
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
                int CheckedValue = NumberArray[j][i];
                if (CheckedValue != 0) {
                    continue;
                } else {
                    for (int k = j + 1; k < 4; k++) {
                        if (NumberArray[k][i] == 0) {
                            continue;
                        } else {
                            NumberArray[j][i] = NumberArray[k][i];
                            NumberArray[k][i] = 0;
                            BlockRandom = true;
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
        //setMangLui();
        BlockRandom = false;
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                int CheckedValue = NumberArray[j][i];
                if (CheckedValue == 0) {

                    continue;
                } else {
                    int Current = j - 1;
                    for (int k = Current; k >= 0; k--) {
                        int NumberUp = NumberArray[k][i];
                        if (NumberUp == 0) {
                            continue;
                        } else if (NumberUp == CheckedValue) {
                            NumberArray[j][i] = 2 * CheckedValue;
                            NumberArray[k][i] = 0;
                            BlockRandom = true;
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
                int CheckedValue = NumberArray[j][i];
                if (CheckedValue != 0) {
                    continue;
                } else {
                    for (int k = j - 1; k >= 0; k--) {
                        if (NumberArray[k][i] == 0) {
                            continue;
                        } else {
                            NumberArray[j][i] = NumberArray[k][i];
                            NumberArray[k][i] = 0;
                            BlockRandom = true;
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