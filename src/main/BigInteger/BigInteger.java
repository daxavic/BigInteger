import java.util.ArrayList;

/**
 * Вариант 2 -- беззнаковое большое целое число (**)
 *
 * Хранит целое неотрицательное число, без верхней границы (от нуля до бесконечности).
 *
 * Методы:
 * - сравнить на равенство/больше/меньше;
 * - сложить;
 * - вычесть;
 * - умножить;
 * - разделить;
 * - найти остаток от деления.
 *
 * Конструктор из строки, целого числа.
 * Не использовать BigInteger и т.п.
 */

public final class BigInteger {

    private ArrayList <Byte> byteEl = new ArrayList <Byte>();
    public int bigSize;

    //создаём конструктор из строки
     BigInteger(String val) {
        if ( !val.matches("[0-9]+") ) {
            throw new IllegalArgumentException("Число введено неверно");
        }
        String[] splitNumber = val.split("");
        for ( int i = 0; i < splitNumber.length; i++ ) {
            byteEl.add(0, new Byte(splitNumber[ i ]));
        }
        bigSize = byteEl.size();
    }


    // Константы:
    private final byte MIN_ELEMENT = 0; // минимальное число в ячейке массива
    private final byte ELEMENT_BASE = 10;

    // метод "Сложение"
    private ArrayList <Byte> plusHelp(ArrayList <Byte> first, ArrayList <Byte> second) {
        ArrayList<Byte> max;
        ArrayList<Byte> min ;
        if (first.size() > second.size()){max = new ArrayList <>(first);
                                          min = new ArrayList <>(second);}
         else {
             max = new ArrayList <>(second);
             min = new ArrayList <>(first);}
        int maxSize = max.size();
        int minSize = min.size();
        int count;
        int nextRank = 0; // переменная, в которой хранится число переносящееся в след. рязряд.
        ArrayList <Byte> result = max;
        for ( int i = 0; i < minSize; i++ ) {           // складываем пока длина чисел одинаковая
            count = (max.get(i) + min.get(i) + nextRank);
            nextRank = count/ELEMENT_BASE;
            Byte set = result.set(i , (byte) (count % ELEMENT_BASE));
        }
        for ( int i = minSize; i < maxSize; i++ ) {     // прибавляем излишек из предыдущего разряда
            if ( nextRank == 0 ) break;
            count = max.get(i) + 1;
            nextRank = count/ELEMENT_BASE;
            result.set(i, (byte)(count % ELEMENT_BASE ));
        }
        if ( nextRank != 0 ) result.add((byte) nextRank);

        return result;
    }

    public String plus(BigInteger this,BigInteger other) {
        ArrayList <Byte> result;
        result = plusHelp(this.byteEl, other.byteEl);
        if ( toString(result) == "" ) return "0";
        else
            return toString(result);
    }


    // Метод "Вычитание"
    private ArrayList <Byte> minusHelp(ArrayList <Byte> minuend, ArrayList <Byte> subtrahend) {
        int nextRank = 0; // переменная, в которой хранится число переносящееся в пред. рязряд.
        int count;
        ArrayList <Byte> result =new ArrayList <>(minuend);
        int minSize = minuend.size();
        int subSize = subtrahend.size();
        if ( minSize < subSize ) throw new IllegalArgumentException("Число не может быть отрицательным");
        for ( int i = 0; i < subSize; i++ ) {                        // вычитаем пока длина чисел одинаковая
            count = minuend.get(i) - subtrahend.get(i) - nextRank;
            if ( count < MIN_ELEMENT ) {
                count += ELEMENT_BASE;
                nextRank = 1;}
             else nextRank = 0;
             result.set(i, (byte)count);
        }
        for ( int i = subSize; i < minSize; i++ ) {                 // вычитаем заимствованную меньшими разрядами 1
            if ( nextRank == 0 ) {
                break;
            }
            count = minuend.get(i) - nextRank;
            if ( count >= MIN_ELEMENT ) {
                nextRank = 0;}
              else count += ELEMENT_BASE;
             result.set(i, (byte) count);
        }
        if ( nextRank == 1 ) throw new IllegalArgumentException("Число не может быть отрицательным");
        return result;
    }

    public String minus(BigInteger other) {
        ArrayList <Byte> result = minusHelp(this.byteEl, other.byteEl);
        if ( toString(result).isEmpty()) {
            return "0";
        } else
            return toString(result);
    }


    // метод: "Умножение" (умножение столбиком)
    // multiplyHelp умножает число на одну цифру
    private ArrayList <Byte> multiplyHelp(ArrayList <Byte> multiplier1, byte digit, int n) {
        ArrayList<Byte> result = new ArrayList<Byte>();
        int nextRank = 0;  // переменная, в которой хранится число переносящееся в пред. рязряд.
        int countor;
        int m = 0;
        for ( int i = 0; i < n ; i++ ) {
            result.add(0, (byte) 0);
            m++;
        }
        for ( int i = n; i < multiplier1.size(); i++ ) {
            countor = digit * multiplier1.get(i) + nextRank;
            nextRank = countor / ELEMENT_BASE;
            countor %= ELEMENT_BASE;
            result.add((byte) countor );}
        if ( nextRank != 0 ) {
            result.add((byte) nextRank);
        }
        return result;
    }

    public String multiply(BigInteger other) {
        ArrayList <Byte> multiplier = this.byteEl;       /* промежуточное число, на которое
                                                        умножается цифра из второго множителя*/
        int n = 0;                                      // показывет сколько нулей долбавлено в множитель
        ArrayList <Byte> result = new ArrayList <Byte>();
        for ( int i = 0; i < other.bigSize; i++ ) {
           if ( other.byteEl.get(i) == 0 ) {            // пропускаем умножение, если множитель равен 0
                multiplier.add(0, (byte) 0);
                n++;
                continue;
            }
            if ( other.byteEl.get(i) == 1 ) {
                result = plusHelp(result, multiplier);
            } else {
                result = plusHelp(result, multiplyHelp(multiplier, other.byteEl.get(i), n));
            }
            multiplier.add(0, (byte) 0);
            n++;
        }
        if ( toString(result).isEmpty() ) return "0";
        else
            return toString(result);
    }


    // метод "Деление"/ Остаток (деление столбиком)
    public String div (BigInteger other) {
        ArrayList <Byte> controller = new ArrayList <>(this.byteEl);
        ArrayList<Byte> maxDivinder ;
        ArrayList<Byte> divinder = new ArrayList <>(other.byteEl) ;    // делитель
        ArrayList <Byte> dividend = new ArrayList <Byte>();      // число, из которого вычитаем
        int n;                                              // сколько раз вычитаем число
        ArrayList <Byte> result = new ArrayList <>();
        ArrayList <Byte> zero = new ArrayList <Byte>();
        zero.add((byte) 0);
        ArrayList <Byte> first = new ArrayList <Byte>();
        first.add((byte) 1);
        if ( other.byteEl == zero ) throw new IllegalArgumentException("Нельзя делить на ноль");
        if ( other.byteEl == first ) return toString(this.byteEl);
        int equal = equalityHelp(this.byteEl, other.byteEl); // проверяет на равенство делитель и делимое
        if ( equal == 2 ) return "0";
        else if ( equal == 0 ) return "1";
        for (int i = controller.size() - 1; i >= 0; i--) {
            n = 0;
            maxDivinder = new ArrayList <>(divinder);
            dividend.add(0, controller.get(i));
            if (equalityHelp(dividend,divinder) == 2) {result.add(0, (byte) n);
                continue;}
            n++;
            if (zerosDeath(dividend).isEmpty()) dividend = new ArrayList <>(zero);
            while ( equalityHelp(dividend,maxDivinder) != 2) {
                n++;
                maxDivinder = plusHelp(maxDivinder,divinder);
            }
            n--;
            maxDivinder = zerosDeath(minusHelp(maxDivinder,divinder));

            dividend = zerosDeath(minusHelp(dividend, maxDivinder));
            result.add(0,(byte) n);
        }
        result = zerosDeath(result);
        if ( toString(result).isEmpty() ) return "0";
        else
            return toString(result);
    }

    // метод "остаток"/"" (остаток деление столбиком)
    public String mod(BigInteger other) {

        ArrayList <Byte> controller = new ArrayList <>(this.byteEl);
        ArrayList<Byte> maxDivinder ;
        ArrayList<Byte> divinder = new ArrayList <>(other.byteEl) ;    // делитель
        ArrayList <Byte> dividend = new ArrayList <Byte>();      // число, из которого вычитаем
        int n;                                              // сколько раз вычитаем число
        ArrayList <Byte> result = new ArrayList <>();
        ArrayList <Byte> zero = new ArrayList <Byte>();
        zero.add((byte) 0);
        //ArrayList <Byte> first = new ArrayList <Byte>();
        //first.add((byte) 1);
        if ( other.byteEl == zero ) throw new IllegalArgumentException("Нельзя делить на ноль");
       // if ( other.byteEl == first ) return toString(this.byteEl);
        int equal = equalityHelp(this.byteEl, other.byteEl); // проверяет на равенство делитель и делимое
        if ( equal == 0 ) return "0";
        for (int i = controller.size() - 1; i >= 0; i--) {
            n = 0;
            maxDivinder = new ArrayList <>(divinder);
            dividend.add(0, controller.get(i));
            if (equalityHelp(dividend,divinder) == 2) {result.add(0, (byte) n);
                continue;}
            n++;
            if (zerosDeath(dividend).isEmpty()) dividend = new ArrayList <>(zero);
            while ( equalityHelp(dividend,maxDivinder) != 2) {
                n++;
                maxDivinder = plusHelp(maxDivinder,divinder);
            }
            n--;
            maxDivinder = zerosDeath(minusHelp(maxDivinder,divinder));

            dividend = zerosDeath(minusHelp(dividend, maxDivinder));
            result.add(0,(byte) n);
        }
       dividend = zerosDeath(dividend);
        if ( toString(dividend).isEmpty() ) return "0";
        else
            return toString(dividend);
    }


    /**
     * метод на равенство
     * выводит:
     * - 1, если первое чило больше (>),
     * - 2, если второе число больше (<),
     * - 0, если числа равны (=).
     */
    private int equalityHelp(ArrayList <Byte> first, ArrayList <Byte> second) {
        int size1 = first.size();
        int size2 = second.size();
        if ( size1 > size2 ) {
            return 1;
        }
        if ( size1 < size2 ) {
            return 2;
        }
        for ( int i = size1 - 1; i >= 0; i-- ) {
            if ( first.get(i) > second.get(i) ) {
                return 1;
            }
            if ( first.get(i) < second.get(i) ) {
                return 2;
            }
        }
        return 0;
    }

    public int equality(BigInteger other) {
        ArrayList <Byte> val1 = this.byteEl;
        ArrayList <Byte> val2 = other.byteEl;
        return equalityHelp(val1, val2);
    }

    // метод, убирающий лишние нули
    private ArrayList <Byte> zerosDeath(ArrayList <Byte> val) {
        int k = 0;  // переменная, показывающая нули, идущие перед числом
        int i;
        for ( i = val.size() - 1; k == 0 && i>=0; i-- ) {
            if ( val.get(i) == k ) val.remove(i);
            else k = 1;
        }
        return val;
    }

    // превращение массива в строку
    public String toString(ArrayList <Byte> val) {
        String result = new String();
        zerosDeath(val);
        int size = val.size();
        int intg;
        for ( int i = size - 1; i >= 0; i-- ) {
            intg = val.get(i);
            result += intg;
        }
        return result;
    }
}


