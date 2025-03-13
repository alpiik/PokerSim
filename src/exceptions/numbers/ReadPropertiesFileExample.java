package exceptions.numbers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

        try {
            Properties properties = loadProperties(filePath);
            System.out.println(properties.containsKey(String.valueOf(1)));
            System.out.println(properties.getProperty(String.valueOf(1)));
        } catch (MissingLanguageFileException | BrokenLanguageFileException e) {
            LOGGER.log(Level.SEVERE, "Error loading properties file: " + e.getMessage(), e);
        }
    }

    public static Properties loadProperties(String filePath) throws MissingLanguageFileException, BrokenLanguageFileException {
        Properties properties = new Properties();

        try (FileInputStream is = new FileInputStream(filePath);
             InputStreamReader reader = new InputStreamReader(is, StandardCharsets.ISO_8859_1)) {

            properties.load(reader);
            return properties;

        } catch (FileNotFoundException e) {
            throw new MissingLanguageFileException("Language file not found: " + filePath, e);
        } catch (IOException e) {
            throw new BrokenLanguageFileException("Error reading language file: " + filePath, e);
        }
    }
}
