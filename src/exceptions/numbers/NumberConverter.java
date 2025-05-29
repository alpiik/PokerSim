package exceptions.numbers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class NumberConverter {
    private final Properties properties = new Properties();
    private final String language;

    public NumberConverter(String lang) {
        String filePath = "src/exceptions/numbers/numbers_" + lang + ".properties";

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);

            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.ISO_8859_1);
            properties.load(reader);

            if (lang.equals("es") || lang.equals("fr")) {
                for (String essential : new String[] {"0", "1", "2", "3"}) {
                    if (!properties.containsKey(essential)) {
                        throw new MissingTranslationException(lang);
                    }
                }
            }

        } catch (IOException e) {
            throw new MissingLanguageFileException(lang, e);
        } catch (IllegalArgumentException e) {
            throw new BrokenLanguageFileException(lang, e);
        } finally {
            close(fis);
        }

        language = lang;
    }

    public String numberInWords(Integer number) {
        return switch (language) {
            case "en" -> numberInWordsEN(number);
            case "et" -> segmentToWordsET(number);
            case "cu" -> numberInWordsCU(number);
            default -> "0";
        };
    }

    private static void close(FileInputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                System.err.println("Failed to close FileInputStream: " + e.getMessage());
            }
        }
    }

    public String numberInWordsEN(Integer number) {
        if (number == 0) {
            return properties.getProperty("0");
        }

        StringBuilder numberInWords = new StringBuilder();

        int hundreds = number / 100;
        if (hundreds != 0) {
            numberInWords.append(properties.getProperty(String.valueOf(hundreds)))
                    .append(" ")
                    .append(properties.getProperty("hundred"));
            number %= 100;
        }

        if (number > 0) {
            if (hundreds > 0) {
                numberInWords.append(" ");
            }

            if (number < 20) {
                numberInWords.append(handleTeens(number));
            } else {
                numberInWords.append(handleTens(number));
            }
        }

        return numberInWords.toString();
    }

    private String handleTeens(int number) {
        String teenWord;
        if (number == 10) {
            teenWord = properties.getProperty("10");
        } else if (number > 10) {
            int ones = number % 10;
            if (properties.containsKey(String.valueOf(number))) {
                teenWord = properties.getProperty(String.valueOf(number));
            } else {
                teenWord = properties.getProperty(String.valueOf(ones)) + properties.getProperty("teen");
            }
        } else {
            teenWord = properties.getProperty(String.valueOf(number));
        }
        return teenWord;
    }

    private String handleTens(int number) {
        int tens = number / 10 * 10;
        int ones = number % 10;
        StringBuilder result = new StringBuilder();

        if (tens > 0) {
            if (properties.containsKey(String.valueOf(tens))) {
                result.append(properties.getProperty(String.valueOf(tens)));
            } else {
                result.append(properties.getProperty(String.valueOf(tens / 10)));
                result.append(properties.getProperty("tens-suffix"));
            }
        }

        if (ones != 0) {
            result.append(properties.getProperty("tens-after-delimiter"))
                    .append(properties.getProperty(String.valueOf(ones)));
        }

        return result.toString();
    }

    private String convertTwoDigits(int number) {
        if (number == 10) {
            return properties.getProperty("10");
        } else if (number < 10) {
            return properties.getProperty(String.valueOf(number));
        } else if (number < 20) {
            return properties.getProperty(String.valueOf(number % 10)) + properties.getProperty("teen");
        } else {
            int tens = number / 10;
            int ones = number % 10;
            StringBuilder result = new StringBuilder();

            result.append(properties.getProperty(String.valueOf(tens)))
                    .append(properties.getProperty("tens-suffix"));

            if (ones > 0) {
                result.append(" ").append(properties.getProperty(String.valueOf(ones)));
            }

            return result.toString();
        }
    }
    private String convertHundreds(int number) {
        int hundreds = number / 100;
        int remainder = number % 100;

        StringBuilder result = new StringBuilder();

        if (hundreds > 0) {
            result.append(properties.getProperty(String.valueOf(hundreds)))
                    .append(properties.getProperty("hundred"));
        }

        if (remainder > 0) {
            result.append(" ").append(convertTwoDigits(remainder));
        }
        return result.toString();
    }
    public String segmentToWordsET(int number) {
        if (number == 0) {
            return properties.getProperty("0");
        }
        StringBuilder words = new StringBuilder();

        if (number >= 1_000_000_000) {
            int billions = number / 1_000_000_000;
            words.append(convertHundreds(billions)).append(" ").append(properties.getProperty("billion-singular"));
            number %= 1_000_000_000;
        }
        if (number >= 1_000_000) {
            int millions = number / 1_000_000;
            if (!words.isEmpty()) {
                words.append(" ");
            }
            words.append(convertHundreds(millions))
                    .append(" ")
                    .append(properties.getProperty(millions == 1 ? "million-singular" : "million-plural"));
            number %= 1_000_000;
        }
        if (number >= 1_000) {
            int thousands = number / 1_000;
            if (!words.isEmpty()) {
                words.append(" ");
            }
            words.append(convertHundreds(thousands))
                    .append(" ")
                    .append(properties.getProperty("thousand"));
            number %= 1_000;
        }
        if (number > 0) {
            if (!words.isEmpty()) {
                words.append(" ");
            }
            words.append(convertHundreds(number));
        }
        return words.toString().trim();
    }

    public String numberInWordsCU(Integer number) {
        int hundreds = number / 100 % 10;
        int tens = number / 10 % 10;
        int ones = number % 10;

        StringBuilder numberInWords = new StringBuilder();

        if (hundreds != 0) {
            numberInWords.append(properties.getProperty(String.valueOf(hundreds)))
                    .append(properties.getProperty("hundred-before-delimiter"))
                    .append(properties.getProperty("hundred"));
        }
        if (tens != 0 || ones != 0) {
            if (hundreds > 0) {
                numberInWords.append(properties.getProperty("hundred-after-delimiter"));
            }
            if (tens == 1) {
                if (ones == 0) {
                    numberInWords.append(properties.getProperty("10"));
                } else {
                    numberInWords.append(properties.getProperty(String.valueOf(ones)))
                            .append(properties.getProperty("teen"));
                }
            } else if (tens > 1) {
                numberInWords.append(properties.getProperty(String.valueOf(tens)))
                        .append(properties.getProperty("tens-suffix"));
                if (ones > 0) {
                    numberInWords.append(properties.getProperty("tens-after-delimiter"));
                }
            }
            if (tens != 1 && ones > 0) {
                numberInWords.append(properties.getProperty(String.valueOf(ones)));
            }
        }
        if (number == 0) {
            numberInWords.append(properties.getProperty("0"));
        }
        return numberInWords.toString();
    }
}
