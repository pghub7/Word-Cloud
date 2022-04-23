public class Hailstorm {
    private int x;
    private int y;
    private int z;

    public Hailstorm(int x) {
        this.x = x;
    }

    void collatz() {
        if (x == 1) {
            System.out.println("The value of 1 has been reached.");
            System.out.println("The value was even " + y + " times, and was odd " + z + " times");
        }
        else if (x % 2 == 0) {
            x = x / 2;
            y++;
            collatz();
        }
        else {
            x = 3*x + 1;
            z++;
            collatz();
        }
    }

    void useless() {}
}
