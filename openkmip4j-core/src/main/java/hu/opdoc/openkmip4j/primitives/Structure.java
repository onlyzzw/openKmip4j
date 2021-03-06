package hu.opdoc.openkmip4j.primitives;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by peter on 2017.06.11..
 */
public class Structure extends Primitive {

    public static final Type TYPE = Type.Structure;

    private final List<Primitive> value;

    public Structure() {
        this(null);
    }

    public Structure(final Tag tag) {
        this(tag, Collections.emptyList());
    }

    public Structure(final Tag tag, final List<Primitive> value) {
        super(tag, Type.Structure);

        this.value = new ArrayList<>(value);  // Copy the structure so that it shouldn't change any more
        calculateLength();
    }

    public List<Primitive> getValue() {
        return value;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    protected void calculateLength() {
        setLength(multipleOfEight(
                value.stream()
                        .map(primitive -> primitive.getLength() + 8) // Add 8 byte TTLV header length to the payload length
                        .reduce(0l, (x, y) -> x + y)        // Sum it all up
        ));
    }

    private static long multipleOfEight(final long originalLength) {
        long result = Math.floorDiv(originalLength, 8);
        if (result * 8 < originalLength) {
            result++;
        }
        return result;
    }
}
