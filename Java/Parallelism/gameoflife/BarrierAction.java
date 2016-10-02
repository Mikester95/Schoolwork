package gameoflife;

class BarrierAction implements Runnable {
    private int n, m;
    private int matrix[][][];
    private IntegerWrapper current;

    public BarrierAction(int n, int m, int matrix[][][], IntegerWrapper current) {
        this.n = n;
        this.m = m;
        this.matrix = matrix;
        this.current = current;
    }

    private void reset() {
        current.set(1 - current.get());
    }

    public void run() {
        this.reset();
    }
}