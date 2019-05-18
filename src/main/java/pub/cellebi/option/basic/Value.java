package pub.cellebi.option.basic;

public interface Value<T> {

    String string();

    void parse(String value);

    T getValue();

    default <E> E getTypeValue(Class<E> type) {
        if (type.isInstance(this)) {
            return type.cast(this);
        }
        return null;
    }
}
