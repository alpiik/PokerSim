package exceptions.basic;

public class Code {

    public String readDataFrom(FakeFile file) {
        try {
            file.open();
            String data = file.read();
            return data;
        } catch (Exception e) {
            return "someDefaultValue";
        } finally {
            file.close();
        }
    }

    public static Integer minimumElement(int[] integers) {
        if (integers == null || integers.length == 0) {
            throw new IllegalArgumentException("Massiiv ei tohi olla null või tühi.");
        }

        int minimumElement = integers[0];

        for (int current : integers) {
            if (current < minimumElement) {
                minimumElement = current;
            }
        }

        return minimumElement;
    }

    public static boolean containsSingleLetters(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

}
