package exceptions.numbers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class NumberConverter {

    private final Properties properties;

    public NumberConverter(String lang) {
        properties = new Properties();
        String fileName = "numbers_" + lang + ".properties";
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new RuntimeException("Language file not found: " + fileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load language file: " + fileName, e);
        }
    }
    public String numberInWords(Integer number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number must be non-negative.");
        }
        if (number < 20) {
            return getNumber(number);
        }
        if (number < 100) {
            return getTens(number);
        }
        if (number < 1000) {
            return getHundreds(number);
        }
        if (number < 1_000_000) {
            return getThousands(number);
        }
        if (number < 1_000_000_000) {
            return getMillions(number);
        }
        if (number < 1_000_000_000_000L) {
            return getBillions(number);
        }
        throw new IllegalArgumentException("Number is too large.");
    }
    private String getNumber(int number) {
        return properties.getProperty(String.valueOf(number));
    }
    private String getTens(int number) {
        if (number < 20) {
            return getNumber(number);
        }
        int tens = number / 10 * 10;
        int ones = number % 10;
        String tensWord = properties.getProperty(String.valueOf(tens));
        String delimiter = properties.getProperty("tens-after-delimiter", "");
        if (ones == 0) {
            return tensWord;
        } else {
            return tensWord + delimiter + getNumber(ones);
        }
    }
    private String getHundreds(int number) {
        int hundreds = number / 100;
        int remainder = number % 100;
        String hundredWord = properties.getProperty(String.valueOf(hundreds));
        String hundredSuffix = properties.getProperty("hundred");
        String beforeDelimiter = properties.getProperty("hundred-before-delimiter", "");
        String afterDelimiter = properties.getProperty("hundred-after-delimiter", "");
        if (remainder == 0) {
            return hundredWord + beforeDelimiter + hundredSuffix;
        } else {
            return hundredWord + beforeDelimiter + hundredSuffix + afterDelimiter + numberInWords(remainder);
        }
    }
    private String getThousands(int number) {
        int thousands = number / 1000;
        int remainder = number % 1000;
        String thousandWord = numberInWords(thousands);
        String thousandSuffix = properties.getProperty("thousand");
        String beforeDelimiter = properties.getProperty("thousand-before-delimiter", "");
        String afterDelimiter = properties.getProperty("thousand-after-delimiter", "");
        if (remainder == 0) {
            return thousandWord + beforeDelimiter + thousandSuffix;
        } else {
            return thousandWord + beforeDelimiter + thousandSuffix + afterDelimiter + numberInWords(remainder);
        }
    }
    private String getMillions(int number) {
        int millions = number / 1_000_000;
        int remainder = number % 1_000_000;
        String millionWord = numberInWords(millions);
        String millionSuffix = (millions == 1) ? properties.getProperty("million-singular") : properties.getProperty("million-plural");
        String beforeDelimiter = properties.getProperty("million-before-delimiter", "");
        String afterDelimiter = properties.getProperty("million-after-delimiter", "");
        if (remainder == 0) {
            return millionWord + beforeDelimiter + millionSuffix;
        } else {
            return millionWord + beforeDelimiter + millionSuffix + afterDelimiter + numberInWords(remainder);
        }
    }
    private String getBillions(int number) {
        int billions = number / 1_000_000_000;
        int remainder = number % 1_000_000_000;
        String billionWord = numberInWords(billions);
        String billionSuffix = (billions == 1) ? properties.getProperty("billion-singular") : properties.getProperty("billion-plural");
        String beforeDelimiter = properties.getProperty("billion-before-delimiter", "");
        String afterDelimiter = properties.getProperty("billion-after-delimiter", "");
        if (remainder == 0) {
            return billionWord + beforeDelimiter + billionSuffix;
        } else {
            return billionWord + beforeDelimiter + billionSuffix + afterDelimiter + numberInWords(remainder);
        }
    }
}