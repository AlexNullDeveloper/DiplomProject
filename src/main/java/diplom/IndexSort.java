package diplom;

import org.apache.log4j.Logger;

public class IndexSort {

    public static void main(String[] args) {

        int[] array1 = {50, 10, 40, 20, 30};
        int max = getMax(array1);

        sortWithoutIf(array1, max);

        for (Integer itm : array1) {
            System.out.print(itm + " ");
        }
    }

    private static void sortWithoutIf(int[] array, int max) {
        int[] arrayWeNeed = new int[max + 1];
        for (int i = 0; i < array.length; i++) {
            arrayWeNeed[array[i]] = 1;
        }
        int k = 0;
        for (int j = 0; j < arrayWeNeed.length; j++) {
            array[k] = j;
            k = k + arrayWeNeed[j];
        }
    }

    private static int getMax(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (max < array[i])
                max = array[i];
        }
        return max;
    }
}


