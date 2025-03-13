package exceptions.numbers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadPropertiesFileExample {

    private static final Logger LOGGER = Logger.getLogger(ReadPropertiesFileExample.class.getName());

    public static void main(String[] args) {
        String filePath = "src/exceptions/numbers/numbers_en.properties";

        Properties properties = new Properties();
        FileInputStream is = null;

        try {
            is = new FileInputStream(filePath);
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.ISO_8859_1);
            properties.load(reader);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading properties file: " + e.getMessage(), e);
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
            LOGGER.log(Level.WARNING, "Failed to close FileInputStream: " + e.getMessage(), e);
        }
    }
}
