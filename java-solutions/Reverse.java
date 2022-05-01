import myscanner.MyScanner;

import java.util.Arrays;

public class Reverse {
    public static void main(String[] args) {
        MyScanner scanner = new MyScanner(System.in);
        int[][] matrix = new int[2][2];
        int lineInd = 0;

        while (scanner.hasNext()) {
            if (lineInd == matrix.length) {
                matrix = Arrays.copyOf(matrix, (int) (matrix.length * 1.5));
            }
            int numberInd = 0;
            matrix[lineInd] = new int[2];
            while (scanner.hasNextNumber()) {
                if (numberInd == matrix[lineInd].length) {
                    matrix[lineInd] = Arrays.copyOf(matrix[lineInd], (int) (matrix[lineInd].length * 1.5));
                }
                matrix[lineInd][numberInd] = scanner.nextInt();
                numberInd++;
            }
            matrix[lineInd] = Arrays.copyOf(matrix[lineInd], numberInd);
            scanner.toNextLine();
            lineInd++;
        }
        scanner.close();

        for (int i = lineInd - 1; i >= 0; --i) {
            for (int j = matrix[i].length - 1; j >= 0; --j) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}