package utility;

public class Pair<A, B> {
    private A a;
    private B b;
    
    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }
    
    public A first() {
        return a;
    }
    
    public B second() {
        return b;
    }
    
    public void setFirst(A a) {
        this.a = a;
    }
    
    public void setSecond(B b) {
        this.b = b;
    }
}
