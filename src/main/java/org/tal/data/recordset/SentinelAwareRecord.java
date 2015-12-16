package org.tal.data.recordset;

/**
 * @author Thuan Luong
 */
public interface SentinelAwareRecord extends Record {

    boolean isSentinel();
}
