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
    private int[][] NumberArray = new int[4][4]; //Массив Номерів
    private int СurrentResult; //soDiem - Кількість очків(балів) набраних
    private boolean BlockRandom = true; //Блокировка дєйствій
    private Random r = new Random();

    static {//Блок ініціалізація перемєнних при статическом визові
        datagame = new Datagame();
    }



    private void InitializationLocalParametrs() {//Ініціалізація
        BlockRandom = true;//Блокировка
        СurrentResult = 0; //Номер точки
    }


    public static Datagame getDatagame() {
        return datagame;
    }

    public void getColorAndEmpyPlace(Context context) {//получить кольри і очистить поле. Метод ініціалізований
        InitializationLocalParametrs();

        TypedArray ta = context.getResources().obtainTypedArray(R.array.mayNewItemIcon);//Получаем цвет палітру
        ColorArray = new int[ta.length()];//Создаєм массив для всіх цветов
        for (int i = 0; i < ta.length(); i++) {//По порядку
            ColorArray[i] = ta.getColor(i, 0);//Видаєм цвет
        }
        ta.recycle();//Удаляєм палітру

        for (int i = 0; i < 4; i++) {//Для всіх элементів поля
            for (int j = 0; j < 4; j++) {
                NumberArray[i][j] = 0;//Присваиваем пустоту
            }
        }

    }// Метод підключення для плиток кольорового оформлення зі зміною кольору при досягнені павного прогресу



    public void content() {//Після отработки жеста і переміщеня в внутрішньому массиві элементів, передаєм їх в ArrayList
        arr.clear();       //На якому завязан механізм візуалізації
        for (int i = 0; i < 4; i++) {//Для всіх элементів поля
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

    public int colorr(int number) {//Метод міняє фон плитки
        if (number == 0) {//Якщо number рівний 0
            return Color.WHITE;//Повертає білий колір
        } else {
            int a = (int) (Math.log(number) / Math.log(2));
            return ColorArray[a - 1];//Повертає ColorArray -1
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
    }//Метод для рандома


    public void runLeft() {
        BlockRandom = false;//БЛОКИРУЕМ РАНДОМ ЯКЩО МИ НЕ НАЙДЕМ ВОЗМОЖНиЙ ХОД
        for (int i = 0; i < 4; i++) {//Для кажної строки
            for (int j = 0; j < 4; j++) {//Для кожного елемента цієї строки
                int CheckedValue = NumberArray[i][j];//Запоминаем текущий елемент
                if (CheckedValue == 0) {//Для елементів які
                    continue;// не рівні нулю
                } else {
                    int Current =j+1;//Указатель текущего елемента прохода
                    for (int k = Current; k < 4; k++) {
                        int NumberRight = NumberArray[i][k];//Запоминаєм елемент справа
                        if (NumberRight == 0) {
                            continue;//Якщо елемент пустий - переходим на наступний
                        } else if (NumberRight == CheckedValue) {//Якщо елемент справа рівний провіряємому елементу
                            NumberArray[i][j] = 2 * CheckedValue;//Встановлюємо значення елемента з ліва умноженим на 2
                            NumberArray[i][k] = 0;//Удаляєм елемент який перемістили
                            BlockRandom = true;//Розблокіровка рандомв
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {//Для каноі строки
            for (int j = 0; j < 4; j++) {//Для кажного елемента цієї строки
                int CheckedValue = NumberArray[i][j];//Запоминаєм текущий елемент
                if (CheckedValue != 0) {//Для елементів які
                    continue;//рівні нулю
                } else {
                    for (int k = j + 1; k < 4; k++) {//Проходим по всім елементам цієї строки
                        if (NumberArray[i][k] == 0) {//Якщо пустой
                            continue;//Пропускаєм
                        } else {//Якщо з значеням
                            NumberArray[i][j] = NumberArray[i][k];//Копируєм елемент з права в ліву ячейку
                            NumberArray[i][k] = 0;//Удаляєм скопіруваний елемент
                            BlockRandom = true;//Розблоковуєм рандом
                            break;
                        }
                    }
                }
            }
        }
        Random();//Визиваєм Рандом нових чисел
        content();//Відображаєм на дисплеі

    }//Метод руху в ліво

    public void runRight() {
        BlockRandom = false;//Блокуємо рандом якщо ми не найдем можливий хід
        for (int i = 3; i >= 0; i--) {//Для кажної строки
            for (int j = 3; j >= 0; j--) {//Для кажного елемента цієї строки
                int CheckedValue = NumberArray[i][j];//Запоминаєм текущий элемент
                if (CheckedValue == 0) {//Для элементов которые
                    continue;// не равны нулю
                } else {
                    int Current = j - 1;//Указатель текущего элемента прохода
                    for (int k = Current; k >= 0; k--) {
                        int NumberLeft = NumberArray[i][k];//Запоминаем элемент слева
                        if (NumberLeft == 0) {
                            continue;//Если элемент пустой - переходим на следущий
                        } else if (NumberLeft == CheckedValue) {//Если элемент слева равен проверяемому элементу
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
            for (int j = 3; j >= 0; j--) {//Для каждого элемента этой строки
                int CheckedValue = NumberArray[i][j];//Запоминаем текущий элемент
                if (CheckedValue != 0) {//Для элементов которые
                    continue;//равны нулю
                } else {
                    for (int k = j - 1; k >= 0; k--) {//Проходим по всем элементам этой строки
                        if (NumberArray[i][k] == 0) {//Если пустой
                            continue;//Пропускаем
                        } else {//Если с значением
                            NumberArray[i][j] = NumberArray[i][k];//Копируем элемент слева в праву ячейку
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
    }//Метод руху в право

    public void runUp() {
        BlockRandom = false;//БЛОКИРУЕМ РАНДОМ ЕСЛИ МЫ НЕ НАЙДЕМ ВОЗМОЖНЫЙ ХОД
        for (int i = 0; i < 4; i++) {//Для каждой строки
            for (int j = 0; j < 4; j++) {//Для каждого элемента этой строки
                int CheckedValue = NumberArray[j][i];//Запоминаем текущий элемент
                if (CheckedValue == 0) {//Для элементов которые
                    continue;// не равны нулю
                } else {
                    int Current = j + 1;//Указатель текущего элемента прохода
                    for (int k = Current; k < 4; k++) {
                        int NumberDown = NumberArray[k][i];//Запоминаем элемент снизу
                        if (NumberDown == 0) {
                            continue;//Если элемент пустой - переходим на следущий
                        } else if (NumberDown == CheckedValue) {//Если элемент снизу равен проверяемому элементу
                            NumberArray[j][i] = 2 * CheckedValue;//Устанавливаем значение элемента снизу умноженым на 2
                            NumberArray[k][i] = 0;//Удаляем элемент который переместили
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
                int CheckedValue = NumberArray[j][i];//Запоминаем текущий элемент
                if (CheckedValue != 0) {//Для элементов которые
                    continue;//равны нулю
                } else {
                    for (int k = j + 1; k < 4; k++) {//Проходим по всем элементам этой строки
                        if (NumberArray[k][i] == 0) {//Если пустой
                            continue;//Пропускаем
                        } else {//Если с значением
                            NumberArray[j][i] = NumberArray[k][i];//Копируем элемент нижньой ечейки в верхню ячейку
                            NumberArray[k][i] = 0;//Удаляем скопированый элемент
                            BlockRandom = true;//РАЗБЛОКИРУЕМ РАНДОМ
                            break;
                        }
                    }
                }

            }
        }


        Random();//Вызываем Рандом новых чисел
        content();//Отображаем на дисплее
    }//Метод руху в гору

    public void runDown() {
        BlockRandom = false;//БЛОКИРУЕМ РАНДОМ ЕСЛИ МЫ НЕ НАЙДЕМ ВОЗМОЖНЫЙ ХОД
        for (int i = 3; i >= 0; i--) {//Для каждой строки
            for (int j = 3; j >= 0; j--) {//Для каждого элемента этой строки
                int CheckedValue = NumberArray[j][i];//Запоминаем текущий элемент
                if (CheckedValue == 0) {//Для элементов которые
                    continue;// не равны нулю
                } else {
                    int Current = j - 1;//Указатель текущего элемента прохода
                    for (int k = Current; k >= 0; k--) {
                        int NumberUp = NumberArray[k][i];//Запоминаем элемент сверху
                        if (NumberUp == 0) {
                            continue;//Если элемент пустой - переходим на следущий
                        } else if (NumberUp == CheckedValue) {//Если элемент сверху равен проверяемому элементу
                            NumberArray[j][i] = 2 * CheckedValue;//Устанавливаем значение элемента сверху умноженым на 2
                            NumberArray[k][i] = 0;//Удаляем элемент который переместили
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
            for (int j = 3; j >= 0; j--) {//Для каждого элемента этой строки
                int CheckedValue = NumberArray[j][i];//Запоминаем текущий элемент
                if (CheckedValue != 0) {//Для элементов которые
                    continue;//равны нулю
                } else {
                    for (int k = j - 1; k >= 0; k--) {//Проходим по всем элементам этой строки
                        if (NumberArray[k][i] == 0) {//Если пустой
                            continue;//Пропускаем
                        } else {//Если с значением
                            NumberArray[j][i] = NumberArray[k][i];//Копируем элемент верхньой ячейки в нижню ячейку
                            NumberArray[k][i] = 0;//Удаляем скопированый элемент
                            BlockRandom = true;//РАЗБЛОКИРУЕМ РАНДОМ
                            break;
                        }
                    }
                }

            }
        }

        Random();//Вызываем Рандом новых чисел
        content();//Отображаем на дисплее
    }//Метод руху в низ

}