package types;

public class Overload {

    public static void main(String[] args) {
        long result1 = add(10L, 20L);
        System.out.println(result1);

        int result2 = add(10, 20);
        System.out.println(result2);

        long result3 = add("30", "40");
        System.out.println(result3);
    }

    public static long add(long x, long y) {
        System.out.println("Adding longs");
        return x + y;
    }

    public static int add(int x, int y) {
        System.out.println("Adding integers");
        return x + y;
    }

    public static long add(String x, String y) {
        System.out.println("Adding numbers from strings");
        return Long.parseLong(x) + Long.parseLong(y);
    }

}
