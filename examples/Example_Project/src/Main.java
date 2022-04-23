public class Main {
    public static void main(String[] args) {
        Hailstorm hailstorm = new Hailstorm(Maths.engineers_pi * 150);
        int a = Maths.fibbonacci(10);
        System.out.println("Fibbonacci value: " + a);
        hailstorm.collatz();
        int[] arr = new int[]{1,2,3,4,5};
        for (Integer i : arr) {
            System.out.println(Circle.area(i));
        }
    }
}
