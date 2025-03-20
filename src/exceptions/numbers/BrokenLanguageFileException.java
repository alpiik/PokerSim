package exceptions.numbers;

public class BrokenLanguageFileException extends RuntimeException {
    public BrokenLanguageFileException(String lang, Throwable cause) {
        super(String.format("Language file for %s is broken ", lang), cause);
    }
}
