package org.tal.data.recordset.support;

import org.tal.data.recordset.Record;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Thuan Luong
 */
public abstract class AbstractRecord implements Record {

    @Override
    public Optional<Boolean> readBoolean(int column) {
        return Optional.ofNullable(readValue(column, Boolean.class));
    }

    @Override
    public Optional<Number> readNumber(int column) {
        return Optional.ofNullable(readValue(column, Number.class));
    }

    @Override
    public Optional<String> readString(int column) {
        return Optional.ofNullable(readValue(column, String.class));
    }

    @Override
    public Optional<LocalDateTime> readDateTime(int column) {
        return Optional.ofNullable(readValue(column, LocalDateTime.class));
    }

    @Override
    public Optional<Object> readObject(int column) {
        return Optional.ofNullable(readValue(column, Object.class));
    }

    @Override
    public Optional<Boolean> readBoolean(String column) {
        return Optional.ofNullable(readValue(column, Boolean.class));
    }

    @Override
    public Optional<Number> readNumber(String column) {
        return Optional.ofNullable(readValue(column, Number.class));
    }

    @Override
    public Optional<String> readString(String column) {
        return Optional.ofNullable(readValue(column, String.class));
    }

    @Override
    public Optional<LocalDateTime> readDateTime(String column) {
        return Optional.ofNullable(readValue(column, LocalDateTime.class));
    }

    @Override
    public Optional<Object> readObject(String column) {
        return Optional.ofNullable(readValue(column, Object.class));
    }

    private void checkValueType(Object value, Class type) {
        if (value != null && !type.isInstance(value)) {
            throw new IllegalArgumentException(String.format("Value '%s' is of type '%s' and not of required type '%s'", value, value.getClass(), type));
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T readValue(int column, Class<T> type) {
        Object value = readValue(column);
        checkValueType(value, type);
        return (T) value;
    }

    @SuppressWarnings("unchecked")
    private <T> T readValue(String column, Class<T> type) {
        Object value = readValue(column);
        checkValueType(value, type);
        return (T) value;
    }

    protected abstract Object readValue(int column);

    protected abstract Object readValue(String column);

}
