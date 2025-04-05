package generics.converter;

public class CompositeConverter<A, B, C> implements Converter<A, C> {
    private final Converter<A, B> first;
    private final Converter<B, C> second;

    public CompositeConverter(Converter<A, B> first, Converter<B, C> second) {
        this.first = first;
        this.second = second;
    }
    @Override
    public C convert(A input) {
        return second.convert(first.convert(input));
    }
}