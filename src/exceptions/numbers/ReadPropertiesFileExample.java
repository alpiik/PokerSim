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
        Properties properties = loadProperties(filePath);

        if (properties != null) {
            System.out.println(properties.containsKey("1"));
            System.out.println(properties.getProperty("1"));
        } else {
            System.out.println("Failed to load properties file.");
        }
    }

    public static Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        FileInputStream is = null;

        try {
            is = new FileInputStream(filePath);
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.ISO_8859_1);
            properties.load(reader);
            return properties;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading properties file: " + filePath, e);
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Failed to close file input stream: " + filePath, e);
                }
            }
        }
    }
}
