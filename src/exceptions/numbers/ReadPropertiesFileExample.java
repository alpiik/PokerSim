package exceptions.numbers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ReadPropertiesFileExample {

    public static void main(String[] args) {
        String filePath = "src/exceptions/numbers/numbers_en.properties";

        Properties properties = new Properties();
        FileInputStream is = null;

        try {
            is = new FileInputStream(filePath);

            InputStreamReader reader = new InputStreamReader(
                    is, StandardCharsets.ISO_8859_1);

            properties.load(reader);
        } catch (Exception e) {
            // Log the exception or print a message
            System.err.println("An error occurred while reading the properties file: " + e.getMessage());
            // Optionally, rethrow the exception as a runtime exception
            throw new RuntimeException("Failed to read properties file", e);
        } finally {
            close(is);
        }

        System.out.println(properties.containsKey(String.valueOf(1)));
        System.out.println(properties.getProperty(String.valueOf(1)));

    }

    private static void close(FileInputStream is) {
        if (is == null) {
            return;
        }

        try {
            is.close();
        } catch (IOException e) {
            // Log the exception or print a message
            System.err.println("An error occurred while closing the stream: " + e.getMessage());
            // Alternatively, add a comment explaining why the exception is ignored
            // For example: "Ignoring IOException because the stream is already closed or no further action is needed."
        }
    }
}