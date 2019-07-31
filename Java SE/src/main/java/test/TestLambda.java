package test;

import java.util.Arrays;

public class TestLambda {
    public static void main(String[] args) {
        int[] array = {1,2};
        Arrays.asList(array).stream().map(String::valueOf);

    }
}
