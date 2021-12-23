import java.util.Arrays;

public class emailTest {

    public static void main(String[] args) {
        int[][] arr = {{12, -13, 88}, {22, 51, -12, -51}, {56, -1}};
        int[] temp = new int[3 + 4 + 2];
        int index = 0;
        for (int[] a : arr) {
            for (int j : temp) {
                System.out.print(a[j] + "\t");
                temp[index] = a[j];
                index++;
            }
            System.out.println();
        }
        System.out.println("一维数组:" + Arrays.toString(temp));
    }
}