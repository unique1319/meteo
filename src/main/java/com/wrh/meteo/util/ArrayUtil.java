package com.wrh.meteo.util;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/22 14:26
 * @describe
 */
public class ArrayUtil {

    private ArrayUtil() {
    }

    public static <T> boolean isEmpty(@Nullable T[] array) {
        return (array == null) || (array.length == 0);
    }

    public static <T> boolean isNotEmpty(@Nullable T[] array) {
        return !isEmpty(array);
    }

    public static <T> List<T> arrayToList(@Nullable T[] array) {
        if (array == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] listToArray(@Nullable List<T> list, Class<T> c) {
        if (list == null) {
            return null;
        }
        T[] ts = (T[]) Array.newInstance(c, list.size());
        for (int i = 0; i < list.size(); i++) {
            ts[i] = list.get(i);
        }
        return ts;
    }

    public static float[][][] doubleArrayToFloatArray(@NonNull double[][][] array) {
        int lenX = array.length;
        int lenY = array[0].length;
        int lenK = array[0][0].length;
        float[][][] f = new float[lenX][lenY][lenK];
        for (int i = 0; i < lenX; i++) {
            for (int j = 0; j < lenY; j++) {
                for (int k = 0; k < lenK; k++) {
                    f[i][j][k] = Float.parseFloat(String.valueOf(array[i][j][k]));
                }
            }
        }
        return f;
    }

    public static float[][] doubleArrayToFloatArray(@NonNull double[][] array) {
        int lenX = array.length;
        int lenY = array[0].length;
        float[][] f = new float[lenX][lenY];
        for (int i = 0; i < lenX; i++) {
            for (int j = 0; j < lenY; j++) {
                f[i][j] = Float.parseFloat(String.valueOf(array[i][j]));
            }
        }
        return f;
    }

    public static float[] doubleArrayToFloatArray(@NonNull double[] array) {
        float[] f = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            f[i] = Float.parseFloat(String.valueOf(array[i]));
        }
        return f;
    }

    @FunctionalInterface
    interface ArrayHandle {
        float handle(float f);

        static float handle_m_to_km(float f) {
            return NumberUtil.round(BigDecimal.valueOf(f / 1000f), 1).floatValue();
        }

        static float handle_km_to_m(float f) {
            return NumberUtil.round(BigDecimal.valueOf(f * 1000f), 1).floatValue();
        }

        static float handle_kelvin_to_centigrade(float f) {
            return NumberUtil.round(BigDecimal.valueOf(f - 273.15f), 1).floatValue();
        }

        static float handle_centigrade_to_kelvin(float f) {
            return NumberUtil.round(BigDecimal.valueOf(f + 273.15f), 1).floatValue();
        }

        static float handle_decimal_to_percentage(float f) {
            return NumberUtil.round(BigDecimal.valueOf(f * 100f), 1).floatValue();
        }

        static float handle_percentage_to_decimal(float f) {
            return NumberUtil.round(BigDecimal.valueOf(f / 100f), 1).floatValue();
        }
    }

    public static void arrayHandle(float[][] floats, ArrayHandle arrayHandle) {
        for (int i = 0; i < floats.length; i++) {
            for (int j = 0; j < floats[0].length; j++) {
                floats[i][j] = arrayHandle.handle(floats[i][j]);
            }
        }
    }

    public static void arrayHandle(double[][] doubles, ArrayHandle arrayHandle) {
        for (int i = 0; i < doubles.length; i++) {
            for (int j = 0; j < doubles[0].length; j++) {
                doubles[i][j] = arrayHandle.handle((float) doubles[i][j]);
            }
        }
    }
}
