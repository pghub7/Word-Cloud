public class Maths {
    public static double pi = 3.141;
    public static int engineers_pi = 3;

    static int fibbonacci(int x) {
        if (x == 0)
            return 0;
        if (x == 1)
            return 1;
        return fibbonacci(x-1) + fibbonacci(x-2);
    }

    static void useless() {
    }
}
