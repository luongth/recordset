
package org.tal.data.recordset;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Thuan Luong
 */
public interface Record extends Serializable {

    Optional<Boolean> readBoolean(int column);

    Optional<Number> readNumber(int column);

    Optional<String> readString(int column);

    Optional<LocalDateTime> readDateTime(int column);

    Optional<Object> readObject(int column);

    Optional<Boolean> readBoolean(String column);

    Optional<Number> readNumber(String column);

    Optional<String> readString(String column);

    Optional<LocalDateTime> readDateTime(String column);

    Optional<Object> readObject(String column);

    RecordSchema recordSchema();
}
