package gameoflife;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String args[]) {
        System.out.println("Input dimensions of the desired matrix");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        Master master = new Master(n, m, 10);
        master.start();
    }
}
