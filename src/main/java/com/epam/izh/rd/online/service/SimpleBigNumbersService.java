package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     *
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        BigDecimal bigDecimal = new BigDecimal(a);
        /* При делении на ноль => ArithmeticException */
        return bigDecimal.divide(new BigDecimal(b), range, RoundingMode.HALF_UP);
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        int i = 0;
        BigInteger j = BigInteger.valueOf(1);
        if (range == 0) {
            return BigInteger.valueOf(2);
        }
        while (i <= range) {
            j = j.add(BigInteger.valueOf(1));
            if (isPrimaryBigInt(j)) {
                i++;
            }
        }
        return j;
    }

    /* Метод который был написан в предыдущих заданиях адаптированный под BigInteger */
    private boolean isPrimaryBigInt(BigInteger number) {
        /* Чтобы хоть как-то ускорить алгоритм : */

        /* 1) Отсеиваем единицу */
        if (number.compareTo(BigInteger.valueOf(1)) == 0) return false;

        /* 2) Отсеиваем четные, которые > 2 */
        if ((number.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0) &&  //четные
                (number.compareTo(BigInteger.valueOf(2)) > 0)) {                         //и больше 2х
            return false;
        }

        /* 3) От 3 х до кв.корня из number проверяем все нечетн. числа */
        for (BigInteger i = BigInteger.valueOf(3);
                        i.compareTo( (BigInteger) sqrtBigInt(number))<=0;
                                 i = i.add(BigInteger.valueOf(2))) {

            if (number.mod(i).compareTo(BigInteger.valueOf(0)) == 0) {
                return false;
            }
        }
        return true;
    }


    public static BigInteger sqrtBigInt(BigInteger value) {
        BigInteger x = BigInteger.valueOf((long) Math.sqrt(value.longValue()));
        return x.add( BigInteger.valueOf((long) (value.subtract(x.multiply(x)).longValue() / (x.longValue() * 2.0))));
    }

}
