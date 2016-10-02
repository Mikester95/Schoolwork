package gameoflife;

import java.util.Random;
import java.util.concurrent.*;
import gameoflife.BarrierAction;
import gameoflife.Worker;
import gameoflife.ConfigurationGenerator;

public class Master {
    private int n, m, threadCount;
    private int matrix[][][];
    private IntegerWrapper current;
    private Worker[] workers;
    private CyclicBarrier barrier;
    
    public Master(int n, int m, int threadCount) {
        this.n = n;
        this.m = m;
        this.threadCount = threadCount; 
        this.matrix = new int[2][n][m];
        this.current = new IntegerWrapper();
        matrix[0] = ConfigurationGenerator.random(n, m);
        
        int cells = this.n * this.m;
        int cellsPerThread = cells / this.threadCount + (cells % this.threadCount == 0 ? 0 : 1);
        workers = new Worker[threadCount];
        this.barrier = new CyclicBarrier(this.threadCount + 1, new BarrierAction(n, m, matrix, current));
        for (int i = 0; i < threadCount; ++i) {
            workers[i] = new Worker(n, m, matrix, current, i * cellsPerThread, Math.min((i+1) * cellsPerThread, n * m), barrier);
        }
    }
    
    public void start() {
        for (int i = 0; i < threadCount; ++i) {
            new Thread(workers[i]).start();
        }
        
        while(true) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    System.out.format("%d ", matrix[current.get()][i][j]);
                }
                System.out.print("\n");
            }
            System.out.print("\n\n");
            try {
                Thread.sleep(1000);
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
