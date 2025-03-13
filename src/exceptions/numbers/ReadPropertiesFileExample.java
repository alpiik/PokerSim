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

    public static Properties loadProperties(String filePath) throws MissingLanguageFileException, BrokenLanguageFileException {
        Properties properties = new Properties();

        try (FileInputStream is = new FileInputStream(filePath);
             InputStreamReader reader = new InputStreamReader(is, StandardCharsets.ISO_8859_1)) {

            properties.load(reader);
            return properties;

        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Language file not found: " + filePath);
            throw new MissingLanguageFileException("Language file not found: " + filePath, e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading language file: " + filePath);
            throw new BrokenLanguageFileException("Error reading language file: " + filePath, e);
        }
    }
}
