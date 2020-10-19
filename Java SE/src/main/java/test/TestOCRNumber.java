package test;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * @author wincher
 * @date 2019-10-17
 * <p> test <p>
 */
public class TestOCRNumber {
  public static void main(String[] args) {
    String ocr = "123125";
    if (Pattern.matches("[0-9]{1,25}", ocr)) {
      System.out.println("ok");
    }
    if (validate(ocr)) {
      System.out.println("ok");
    }
  }
  
  private static boolean validate(String ocr) {
    int [] ocrArray = new int[ocr.length()];
    int sum = 0;
    for (int i = 0; i < ocrArray.length; i++) {
      ocrArray[i] = Integer.parseInt(ocr.substring(i, i + 1));
    }
    for (int i = ocrArray.length - 2; i >= 0; i -= 2) {
      ocrArray[i] *= 2;
      if (ocrArray[i] > 9) {
        ocrArray[i] -= 9;
      }
    }
    for (int i = 0; i < ocrArray.length; i++) {
      sum += ocrArray[i];
    }
    System.out.println("sum:" + sum);
    return ((sum % 10) == 0) ? true : false;
  }
}
