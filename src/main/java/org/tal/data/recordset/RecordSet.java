package org.tal.data.recordset;

/**
 * @author Thuan Luong
 */
public interface RecordSet<T extends Record> extends Iterable<T> {

    /**
     * Attempt to move to the next row
     *
     * @return <code>true</code> if the move was successful, otherwise
     *         <code>false</code> if there is no data left to read
     */
    boolean hasNext();

    T next();

    T current();

    RecordSchema recordSchema();
}
