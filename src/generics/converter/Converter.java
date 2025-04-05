package generics.converter;

public interface Converter<T, U> {
    public U convert(T input);
}
