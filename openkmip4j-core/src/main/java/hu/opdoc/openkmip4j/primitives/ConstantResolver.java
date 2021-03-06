package hu.opdoc.openkmip4j.primitives;

import java.lang.*;
import java.util.function.Function;

/**
 * Created by peter on 2017.06.11..
 */
public class ConstantResolver<T> {

    private final Function<String, T> stringResolver;
    private final Function<Long, T> numberResolver;

    public ConstantResolver(final Function<String, T> stringResolver, final Function<Long, T> numberResolver) {
        this.stringResolver = stringResolver;
        this.numberResolver = numberResolver;
    }

    public T resolve(final String stringValue) {
        if (stringValue.startsWith("0x")) {
            return resolve(Long.parseUnsignedLong(stringValue.substring(2), 16));
        } else {
            return stringResolver.apply(stringValue);
        }
    }

    public T resolve(final Long numberValue) {
        return numberValue == null ? null : numberResolver.apply(numberValue);
    }
}
