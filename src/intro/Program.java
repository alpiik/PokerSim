package intro;

public class Program {

    public static void main(String[] args) {
        int integer = asInteger("11001101");
        System.out.println(integer); // 205

        String binary = asBinaryString(205);
        System.out.println(binary); // "11001101"
    }

    public static String asBinaryString(int input) {
        return Integer.toBinaryString(input);
    }

    public static int asInteger(String input) {
        int result = 0;
        int length = input.length();

        for (int i = 0; i < length; i++) {
            char bit = input.charAt(length - 1 - i);
            if (bit == '1') {
                result += pow(2, i);
            }
        }
        return result;
    }

    private static int pow(int base, int exponent) {
        int result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }
}
