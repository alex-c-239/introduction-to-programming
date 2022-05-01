import myscanner.MyScanner;

import java.util.Arrays;

public class ReverseMin2 {
    public static void main(String[] args) {
        MyScanner scanner = new MyScanner(System.in);
        int[][] matrix = new int[1][1];
        int lineInd = 0;
        int maxLineLen = 0;

        while (scanner.hasNext()) {
            if (lineInd == matrix.length) {
                matrix = Arrays.copyOf(matrix, matrix.length * 2);
            }
            int numberInd = 0;
            matrix[lineInd] = new int[1];
            while (scanner.hasNextNumber()) {
                if (numberInd == matrix[lineInd].length) {
                    matrix[lineInd] = Arrays.copyOf(matrix[lineInd], matrix[lineInd].length * 2);
                }
                matrix[lineInd][numberInd] = scanner.nextInt();
                numberInd++;
            }
            matrix[lineInd] = Arrays.copyOf(matrix[lineInd], numberInd);
            maxLineLen = Math.max(maxLineLen, numberInd);
            scanner.toNextLine();
            lineInd++;
        }
        scanner.close();

        int[] prevLine = new int[maxLineLen];
        for (int i = 0; i < maxLineLen; ++i) {
            prevLine[i] = Integer.MAX_VALUE;
        }
        int prevAns;
        for (int i = 0; i < lineInd; ++i) {
            prevAns = Integer.MAX_VALUE;
            for (int j = 0; j < matrix[i].length; ++j) {
                int ans = Math.min(prevAns, Math.min(prevLine[j], matrix[i][j]));
                prevAns = ans;
                prevLine[j] = ans;
                System.out.print(ans + " ");
            }
            System.out.println();
        }
    }
}