package MergeSort;

import java.util.Arrays;

public class MergeSortData {
    private int[] numbers;

    public MergeSortData(int N, int randomBound) {
        numbers = new int[N];
        for (int i = 0; i < N; i++) {
            numbers[i] =  (int)(Math.random() * randomBound) + 1;
        }
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

    private static void merge(Comparable[] arr, int l, int mid, int r) {

        Comparable[] aux = Arrays.copyOfRange(arr, l, r+1);

        // 初始化，i指向左半部分的起始索引位置l；j指向右半部分起始索引位置mid+1
        int i = l, j = mid+1;
        for( int k = l ; k <= r; k ++ ){

            if( i > mid ){  // 如果左半部分元素已经全部处理完毕
                arr[k] = aux[j-l]; j ++;
            }
            else if( j > r ){   // 如果右半部分元素已经全部处理完毕
                arr[k] = aux[i-l]; i ++;
            }
            else if( aux[i-l].compareTo(aux[j-l]) < 0 ){  // 左半部分所指元素 < 右半部分所指元素
                arr[k] = aux[i-l]; i ++;
            }
            else{  // 左半部分所指元素 >= 右半部分所指元素
                arr[k] = aux[j-l]; j ++;
            }
        }
    }

    //递归使用归并排序，对arr[l...r]的范围进行排序
    private static void sort(Comparable[] arr, int l, int r, int depth){
        System.out.print(repeatCharacters('-', depth*2));
        System.out.println("Deal with [ " + l + "," + r + "]");
        if (l >= r)
            return;
        int mid = (l + r) / 2;
        sort(arr, l, mid, depth + 1);
        sort(arr, mid + 1, r, depth + 1);
        merge(arr, l, mid, r);
    }

    public static void sort(Comparable[] arr){

        int n = arr.length;
        sort(arr, 0, n-1, 0);
    }

    private static String repeatCharacters(char character, int length){
        StringBuilder s = new StringBuilder(length);
        for(int i = 0 ; i < length ; i ++)
            s.append(character);
        return s.toString();
    }

    // 测试MergeSort
    public static void main(String[] args) {

        // Merge Sort是我们学习的第一个O(nlogn)复杂度的算法
        // 可以在1秒之内轻松处理100万数量级的数据
        // 注意：不要轻易尝试使用SelectionSort, InsertionSort或者BubbleSort处理100万级的数据
        // 否则，你就见识了O(n^2)的算法和O(nlogn)算法的本质差异：）
//        int N = 1000000;
//        Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 100000);
//        SortTestHelper.testSort("bobo.algo.MergeSort", arr);

        Integer[] arr = new Integer[8];
        for(int i = 0 ; i < 8 ; i ++)
            arr[i] = new Integer(8-i);
        MergeSortData.sort(arr);

        return;
    }
}
