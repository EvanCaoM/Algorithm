package InsertionSort;

import java.util.Arrays;

public class InsertionSortData {
    public enum Type{
        Default,
        NearlyOrdered
    }
    private int[] numbers;
    public int orderedIndex = -1; //[0...orderedIndex)是有序的
    public int currentIndex = -1;

    public InsertionSortData(int N, int randomBound,Type dataType){
        this.numbers = new int[N];
        for (int i = 0; i < N; i++) {
            this.numbers[i] = (int)(Math.random() * randomBound) + 1;
        }
        if (dataType == Type.NearlyOrdered){
            Arrays.sort(numbers);
            int swapTime = (int)(0.02*N);
            for (int i = 0; i < swapTime; i++) {
                int a = (int)(Math.random()*N);
                int b = (int)(Math.random()*N);
                swap(a,b);
            }
        }
    }

    public InsertionSortData(int N, int randomBound){
        this(N, randomBound, Type.Default);
    }

    public int N(){
        return numbers.length;
    }

    public int get(int index){
        if (index < 0 || index >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access sort data!");
        return numbers[index];
    }

    public void swap(int i, int j){
        int t = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = t;
    }

    public static void main(String[] args) {
        int N = 30;
        int randomBound = 30;
        String strBeforeSort = "";
        InsertionSortData insertionSortData = new InsertionSortData(N, randomBound);
        for (int i = 0; i < N; i++) {
            strBeforeSort = strBeforeSort + ',' + insertionSortData.numbers[i];
        }
        System.out.println(strBeforeSort);
        int[] afterSort = new int[N];
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (insertionSortData.numbers[i] < insertionSortData.numbers[j]){
                    int a = insertionSortData.numbers[i];
                    for (int k = i; k > j; k--) {
                        insertionSortData.numbers[k] = insertionSortData.numbers[k - 1];
                    }
                    insertionSortData.numbers[j] = a;
                }
            }
        }
        String strAfterSort = "";
        for (int i = 0; i < N; i++) {
            strAfterSort = strAfterSort + ',' + insertionSortData.numbers[i];
        }
        System.out.println(strAfterSort);
    }

}
