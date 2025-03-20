package exceptions.numbers;

public class MissingLanguageFileException extends RuntimeException {

    public MissingLanguageFileException(String key, Throwable cause) {
        super(String.format("Language file for %s is missing ", key), cause);
    }
}
