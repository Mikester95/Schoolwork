package gameoflife;

import java.util.Random;

public class ConfigurationGenerator {
    public static int[][] blinker() {
        int mat[][] = new int[3][3];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (i == 1) {
                    mat[i][j] = 1;
                } else {
                    mat[i][j] = 0;
                }
            }
        }
        return mat;
    }
    
    public static int[][] random(int n, int m) {
        int mat[][] = new int[n][m];
        Random gen = new Random();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                mat[i][j] = gen.nextInt(2);
            }
        }
        return mat;
    }
}
