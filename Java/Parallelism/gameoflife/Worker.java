package gameoflife;

import java.util.concurrent.*;

public class Worker implements Runnable{
    private int n, m;
    private int[][][] matrix;
    private IntegerWrapper current;
    private int minCell, maxCell;
    private CyclicBarrier barrier;
    
    public Worker(int n, int m, int[][][] matrix, IntegerWrapper current, int minCell, int maxCell, CyclicBarrier barrier) {
        this.n = n;
        this.m = m;
        this.matrix = matrix;
        this.current = current;
        this.minCell = minCell;
        this.maxCell = maxCell;
        this.barrier = barrier;
    }
    
    int getI(int cell) {
        return cell / m;
    }
    
    int getJ(int cell) {
        return cell % m;
    }
    
    boolean outOfBounds(int i, int j) {
        return (i < 0 || i >= n || j < 0 || j >= m);
    }
    
    public void compute(int i, int j) {
        int directions[] = new int[3];
        directions[0] = -1;
        directions[1] = 0;
        directions[2] = 1;
        int count = 0;
        for (int d1 = 0; d1 < 3; ++d1) {
            for (int d2 = 0; d2 < 3; ++d2) {
                if ((directions[d1] == 0 && directions[d2] == 0) || outOfBounds(i + directions[d1], j + directions[d2]))
                    continue;
                if (matrix[current.get()][i + directions[d1]][j + directions[d2]] == 1) {
                   ++count; 
                }
            }
        }
        
        if (matrix[current.get()][i][j] == 1) {
            if (count < 2) {
                matrix[1 - current.get()][i][j] = 0;
            } else if (count < 4) {
                matrix[1 - current.get()][i][j] = 1;
            } else {
                matrix[1 - current.get()][i][j] = 0;
            }
        } else {
            if (count == 3) {
                matrix[1 - current.get()][i][j] = 1;
            } else {
                matrix[1 - current.get()][i][j] = 0;
            }
        }
    }
    
    public void run(){
        while(true) {
            for (int cell = minCell; cell < maxCell; ++cell) {
                compute(getI(cell), getJ(cell));
            }
            
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
