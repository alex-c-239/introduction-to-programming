import java.util.Arrays;

public class IntList {
    private int[] arr;
    private int len;

    public IntList(int value) {
        arr = new int[]{ value };
        len = 1;
    }

    public IntList() {
        arr = new int[1];
        len = 0;
    }

    public void add(int value) {
        if (len == arr.length) {
            arr = Arrays.copyOf(arr, arr.length * 2);
        }
        arr[len] = value;
        len++;
    }

    public int get(int index) {
        if(index < len) {
            return arr[index];
        }
        return 0;
    }

    public int size() {
        return len;
    }
}