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
        if ( !val.matches("[0-9]+") )
            throw new IllegalArgumentException("Число введено неверно");
        String[] splitNumber = val.split("");
        for ( int i = 0; i < splitNumber.length; i++ ) {
            byteEl.add(0, new Byte(splitNumber[ i ]));
        }
        bigSize = byteEl.size();
    }


    // Константы:
    private final int MAX_ELEMENT = 9; // максимальное число в ячейке массива
    private final byte MIN_ELEMENT =(byte)0; // минимальное число в ячейке массива
    private final byte ELEMENT_BASE = 10;
    //private final Byte[] ZERO = {0};
    //private final Byte[] ONE = {1};

    // метод "Сложение"
    private ArrayList <Byte> plusHelp(ArrayList <Byte> max, ArrayList <Byte> min) {
        int maxSize = max.size();
        int minSize = min.size();
        int count;
        int nextRank = 0; // переменная, в которой хранится число переносящееся в след. рязряд.
        ArrayList <Byte> result = max;
        for ( int i = 0; i < minSize; i++ ) {
            count = (max.get(i) + min.get(i) + nextRank);
            nextRank = count/ELEMENT_BASE;
            result.set(i, (byte)(count % ELEMENT_BASE ));
        }
        for ( int i = minSize; i < maxSize; i++ ) {
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
        if ( equality(other) == 1 )
            result = plusHelp(this.byteEl, other.byteEl);
        else
            result = plusHelp(other.byteEl, this.byteEl);
        if ( toString(result) == "" ) return "0";
        else
            return toString(result);
    }


    // Метод "Вычитание"
    private ArrayList <Byte> minusHelp(ArrayList <Byte> minuend, ArrayList <Byte> subtrahend) {
        int lastRank = 0; // переменная, в которой хранится число переносящееся в пред. рязряд.
        int count;
        ArrayList <Byte> result = minuend;
        int minSize = minuend.size();
        int subSize = subtrahend.size();
        if ( minSize < subSize ) throw new IllegalArgumentException("Число не может быть отрицательным");
        for ( int i = 0; i < subSize; i++ ) {
            count = minuend.get(i) - subtrahend.get(i) - lastRank;
            if ( count < MIN_ELEMENT ) {
                result.set(i, (byte) (count + ELEMENT_BASE));
                lastRank = 1;
            } else {
                result.set(i, (byte) count);
                lastRank = 0;
            }
        }
        for ( int i = subSize; i < minSize; i++ ) {
            if ( lastRank == 0 ) {
                break;
            }
            count = minuend.get(i) - lastRank;
            lastRank = 0;
            if ( count < MIN_ELEMENT ) {
                lastRank = 1;
                result.set(i, (byte) (count + ELEMENT_BASE));
            }
        }
        if ( lastRank == 1 ) throw new IllegalArgumentException("Число не может быть отрицательным");
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
    private ArrayList <Byte> multiplyHelp(ArrayList <Byte> multiplier1, byte digit, int n) {
        ArrayList <Byte> result = null;
        int nextRank = 0;
        int countor;
        for ( int i = 0; i < multiplier1.size(); i++ ) {
            countor = digit * multiplier1.get(i) + nextRank;
            nextRank = 0;
            if ( countor > MAX_ELEMENT ) {
                nextRank = countor / 10;
            }
            result.add((byte) (countor % 10));
        }
        if ( nextRank != 0 ) {
            result.add((byte) nextRank);
        }
        return result;
    }

    public String multiply(BigInteger other) {
        ArrayList <Byte> multiplier = this.byteEl;
        int n = 0; // показывет сколько нулей долбавлено в множитель
        ArrayList <Byte> result = null;
        for ( int i = 0; i < other.bigSize; i++ ) {
            if ( other.byteEl.get(i) == 0 ) {
                multiplier.add(0, (byte) 0);
                n++;
                continue;
            }
            if ( other.byteEl.get(i) == 1 ) {
                result = plusHelp(result, this.byteEl);
            } else {
                result = plusHelp(result, multiplyHelp(multiplier, other.byteEl.get(i), n));
                n++;
                multiplier.add(0, (byte) 0);
            }
        }
        return toString(result);
    }


    // метод "Деление" (деление столбиком)
    public String div(BigInteger other) {
        ArrayList <Byte> controller = this.byteEl;
        ArrayList <Byte> result = new ArrayList <>(0);
        ArrayList <Byte> zero = new ArrayList <Byte>();
        zero.add((byte) 0);
        ArrayList <Byte> first = new ArrayList <Byte>();
        first.add((byte) 1);
        int n; // сколько раз вычитаем число
        int i = this.bigSize; // показывает индекс ячейки делимого
        ArrayList <Byte> dividend = new ArrayList <Byte>();// число, из которого вычитаем
        if ( other.byteEl == zero ) throw new IllegalArgumentException("Нельзя делить на ноль");
        if ( other.byteEl == first ) return toString(this.byteEl);
        int equal = equalityHelp(this.byteEl, other.byteEl);
        if ( equal == 2 ) {
            return "0";
        } else {
            if ( equal == 0 ) {
                return "1";
            }
        }
        while ( equalityHelp(controller, other.byteEl) != 2 ) {
            while ( equalityHelp(dividend, other.byteEl) == 2 ) {
                i--;
                dividend.add(0, this.byteEl.get(i));
                controller.remove(i);
            }
            n = 0;
            while ( equalityHelp(dividend, other.byteEl) == 1 ) {
                dividend = minusHelp(dividend, other.byteEl);
                n++;
            }
            result.add(0, (byte) n);
        }
        System.out.println(result);
        return toString(result);
    }

    // метод "остаток" (деление столбиком)
    public String mod(BigInteger other) {
        ArrayList <Byte> controller = this.byteEl;
        ArrayList <Byte> result = null;
        result.add((byte) 0);
        ArrayList <Byte> zero = new ArrayList <Byte>();
        zero.add((byte) 0);
        ArrayList <Byte> first = new ArrayList <Byte>();
        first.add((byte) 0);
        int n; // сколько раз вычитаем число
        int i = this.bigSize; // показывает индекс ячейки делимого
        ArrayList <Byte> dividend = new ArrayList <Byte>();// число, из которого вычитаем
        if ( other.byteEl == zero ) throw new IllegalArgumentException("Нельзя делить на ноль");
        if ( other.byteEl == first ) return toString(this.byteEl);
        int equal = equalityHelp(this.byteEl, other.byteEl);
        if ( equal == 2 ) {
            return "0";
        } else {
            if ( equal == 0 ) {
                return "1";
            }
        }
        while ( equalityHelp(controller, other.byteEl) != 2 ) {
            while ( equalityHelp(dividend, other.byteEl) == 2 ) {
                i--;
                dividend.add(0, this.byteEl.get(i));
                controller.remove(i);
            }
            n = 0;
            while ( equalityHelp(dividend, other.byteEl) == 1 ) {
                dividend = minusHelp(dividend, other.byteEl);
                n++;
            }
            result.add(0, (byte) n);
        }
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
    private void zerosDeath(ArrayList <Byte> val) {
        int k = 0;  // переменная, показывающая нули, идущие перед числом
        int i;
        for ( i = val.size() - 1; k == 0 && i>=0; i-- ) {
            if ( val.get(i) == k ) val.remove(i);
            else k = 1;
        }
    }

    // превращение массив в строку
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


