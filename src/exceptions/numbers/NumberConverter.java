package exceptions.numbers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class NumberConverter {

    private final Properties properties;

    public NumberConverter(String language) {

        this.properties = new Properties();

        String filePath = String.format("src/exceptions/numbers/numbers_%s.properties", language);
        FileInputStream is = null;
        try {
            is = new FileInputStream(filePath);
            InputStreamReader reader = new InputStreamReader(
                    is, StandardCharsets.ISO_8859_1);
            properties.load(reader);
            validateEssentialTranslations(language);
        } catch (IOException e) {
            throw new MissingLanguageFileException(language, e);
        } catch (IllegalArgumentException e) {
            throw new BrokenLanguageFileException(language, e);
        } finally {
            close(is);
        }
    }
    private void validateEssentialTranslations(String language) {
        // Check for essential translations (0-9, "teen", "tens-suffix", etc.)
        String[] essentialKeys = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "teen", "tens-suffix", "hundred", "hundred-before-delimiter", "hundred-after-delimiter", "tens-after-delimiter"};

        for (String key : essentialKeys) {
            if (!properties.containsKey(key)) {
                throw new MissingTranslationException(language);
            }
        }
    }
    private static void close(FileInputStream is) {
        if (is == null) {
            return;
        }
        try {
            is.close();
        } catch (IOException ignored) {
        }
    }
    public String numberInWords(Integer number) {
        if (properties.containsKey(String.valueOf(number))) {
            return properties.getProperty(String.valueOf(number));

        } else if (number >= 10 && number < 20) {
            return isTeens(number);

        } else if (number >= 20 && number < 100) {
            return isTens(number);

        } else if (number >= 100 && number < 200) {
            return isHundreds(number);
        }
        return "";
    }
    private String isTeens(int number) {
        int ones = number % 10;
        return properties.getProperty(String.valueOf(ones)) + properties.getProperty(String.valueOf("teen"));
    }
    private String isTens(int number) {
        String result = "";
        int ones = number % 10;
        int tens = number - ones;
        if (properties.containsKey(String.valueOf(tens))) {
            result += properties.getProperty(String.valueOf(tens));
        } else {
            int ten = tens / 10;
            result += properties.getProperty(String.valueOf(ten))
                    + properties.getProperty(String.valueOf("tens-suffix"));
        }
        if (ones == 0) {
            return result;
        }
        result += properties.getProperty(String.valueOf("tens-after-delimiter"))
                + properties.getProperty(String.valueOf(ones));
        return result;
    }
    private String isHundreds(int number) {
        int hundreds = number / 100;
        int remainder = number - (hundreds * 100);
        if (remainder == 0) {
            return properties.getProperty(String.valueOf(hundreds))
                    + properties.getProperty(String.valueOf("hundred-before-delimiter"))
                    + properties.getProperty(String.valueOf("hundred"));
        } else if (remainder > 0) {
            return properties.getProperty(String.valueOf(hundreds))
                    + properties.getProperty(String.valueOf("hundred-before-delimiter"))
                    + properties.getProperty(String.valueOf("hundred"))
                    + properties.getProperty(String.valueOf("hundred-after-delimiter"))
                    + numberInWords(remainder);
        }
        return "";
    }
}