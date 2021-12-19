/*
 * Copyright (C) 2004-2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rapiddweller.common;

import com.rapiddweller.common.math.IntDoublePair;
import com.rapiddweller.common.math.Segmented;

import java.util.List;

/**
 * Provides mathematical utility methods.
 * @author Volker Bergmann
 * @since 0.4.0
 */
public class MathUtil {

  public static Segmented segment(int n, int period) {
    return new Segmented(n / period, n % period);
  }

  public static Double nanToNull(double value) {
    return (Double.isNaN(value) ? null : value);
  }

  /** Returns the number of digits needed for displaying the postfix values of a number, but at most 7.
   *  @param number the number to examine
   *  @return the number of digits needed for displaying the postfix values of a number, but at most 7 */
  public static int fractionDigits(double number) {
    double x = fraction(number);
    int n = 0;
    while (x >= 0.0000001 && n < 7) {
      n++;
      x = fraction(x * 10);
    }
    return n;
  }

  public static boolean isIntegralValue(double number) {
    return (Math.IEEEremainder(Math.abs(number), 1) == 0);
  }

  private static double fraction(double number) {
    double value = Math.IEEEremainder(Math.abs(number), 1);
    if (value < 0) {
      value += 1;
    }
    return value;
  }

  public static int prefixDigitCount(double number) {
    return nonNegativeDigitCount((long) Math.abs(number));
  }

  public static int digitCount(long number) {
    return nonNegativeDigitCount(Math.abs(number));
  }

  private static int nonNegativeDigitCount(long number) {
    if (number <= 1) {
      return 1;
    }
    return 1 + (int) Math.log10(number);
  }

  public static int digitalRoot(int i) {
    Assert.notNegative(i, "number");
    int result = i;
    do {
      result = digitSum(result);
    } while (result >= 10);
    return result;
  }

  public static int digitSum(int i) {
    Assert.notNegative(i, "number");
    int tmp = i;
    int result = 0;
    while (tmp > 0) {
      result += tmp % 10;
      tmp /= 10;
    }
    return result;
  }

  public static int weightedSumOfSumOfDigits(String number, int startIndex, int... weights) {
    int sum = 0;
    for (int i = 0; i < weights.length; i++) {
      sum += MathUtil.digitSum(weights[i] * (number.charAt(startIndex + i) - '0'));
    }
    return sum;
  }

  public static int weightedSumOfDigits(CharSequence number, int startIndex, int... weights) {
    int sum = 0;
    for (int i = 0; i < weights.length; i++) {
      sum += weights[i] * (number.charAt(startIndex + i) - '0');
    }
    return sum;
  }

  public static boolean rangeIncludes(long x, long min, long max) {
    return (min <= x && x <= max);
  }

  public static boolean rangeIncludes(double x, double min, double max) {
    return (min <= x && x <= max);
  }

  public static boolean between(long x, long min, long max) {
    return (min < x && x < max);
  }

  public static boolean between(double x, double min, double max) {
    return (min < x && x < max);
  }

  public static Integer sum(int[] addends) {
    int result = 0;
    for (double addend : addends) {
      result += addend;
    }
    return result;
  }

  public static <T> long sum(List<T> addends, Accessor<T, Long> accessor) {
    long result = 0;
    for (T addend : addends) {
      result += accessor.getValue(addend);
    }
    return result;
  }

  public static Double sum(double[] addends) {
    double result = 0;
    for (double addend : addends) {
      result += addend;
    }
    return result;
  }

  public static int max(int... args) {
    int result = args[0];
    for (int i = 1; i < args.length; i++) {
      if (args[i] > result) {
        result = args[i];
      }
    }
    return result;
  }

  public static double max(double... args) {
    double result = args[0];
    for (int i = 1; i < args.length; i++) {
      if (args[i] > result) {
        result = args[i];
      }
    }
    return result;
  }

  public static double min(double... args) {
    double result = args[0];
    for (int i = 1; i < args.length; i++) {
      if (args[i] < result) {
        result = args[i];
      }
    }
    return result;
  }

  public static Double nullableProduct(Double... factors) {
    if (factors.length == 0 || factors[0] == null) {
      return null;
    }
    Double result = factors[0];
    for (int i = 1; i < factors.length; i++) {
      Double factor = factors[i];
      if (factor == null) {
        return null;
      }
      result *= factor;
    }
    return result;
  }

  public static Double nullableDivision(Double dividend, Double... divisors) {
    if (dividend == null || divisors.length == 0 || divisors[0] == null) {
      return null;
    }
    Double result = dividend;
    for (Double divisor : divisors) {
      if (divisor == null) {
        return null;
      }
      result /= divisor;
    }
    return result;
  }

  public static Double nullableSum(Double... summands) {
    if (summands.length == 0 || summands[0] == null) {
      return null;
    }
    Double result = summands[0];
    for (int i = 1; i < summands.length; i++) {
      Double factor = summands[i];
      if (factor == null) {
        return null;
      }
      result += factor;
    }
    return result;
  }

  public static Double nullableSubtraction(Double minuend, Double... subtrahends) {
    if (minuend == null || subtrahends.length == 0 || subtrahends[0] == null) {
      return null;
    }
    Double result = minuend;
    for (Double divisor : subtrahends) {
      if (divisor == null) {
        return null;
      }
      result -= divisor;
    }
    return result;
  }

  public static long factorial(int n) {
    long result = n;
    for (int i = n - 1; i > 0; i--) {
      result *= i;
    }
    return result;
  }

  public static double degToRad(double degree) {
    return degree * Math.PI / 180;
  }

  public static double radToDeg(double rad) {
    return rad / Math.PI * 180;
  }

  public static double square(double x) {
    return x * x;
  }

  public static double average(double[] values) {
    double result = 0;
    for (double value : values) {
      result += value / values.length;
    }
    return result;
  }

  public static double variance(double[] values) {
    double avg = average(values);
    double weight = 1. / values.length;
    double result = 0;
    for (double value : values) {
      result += weight * square(value - avg);
    }
    return result;
  }

  public static double standardDeviation(double[] values) {
    return Math.sqrt(variance(values));
  }

  public static double correctedStandardDeviation(double[] values) {
    double avg = average(values);
    double weight = 1. / (values.length - 1);
    double result = 0;
    for (double value : values) {
      result += weight * square(value - avg);
    }
    return Math.sqrt(result);
  }

  public static IntDoublePair minValue(List<IntDoublePair> values) {
    IntDoublePair min = values.get(0);
    for (int i = values.size() - 1; i >= 0; i--) {
      IntDoublePair tmp = values.get(i);
      if (tmp.d < min.d) {
        min = tmp;
      }
    }
    return min;
  }

  public static IntDoublePair maxValue(List<IntDoublePair> values) {
    IntDoublePair max = values.get(0);
    for (int i = values.size() - 1; i >= 0; i--) {
      IntDoublePair tmp = values.get(i);
      if (tmp.d > max.d) {
        max = tmp;
      }
    }
    return max;
  }

}
