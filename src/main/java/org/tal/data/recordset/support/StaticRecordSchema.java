/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tal.data.recordset.support;

import com.google.common.base.Preconditions;
import org.tal.data.recordset.ColumnType;
import org.tal.data.recordset.RecordColumn;
import org.tal.data.recordset.RecordSchema;

import java.io.Serializable;
import java.util.*;

/**
 * @author Thuan Luong
 */
public class StaticRecordSchema implements RecordSchema {

    private final List<RecordColumn> columns = new ArrayList<>();

    private final Map<String, Integer> columnIndexByName = new HashMap<>();

    private StaticRecordSchema() {
    }

    @Override
    public int columnCount() {
        return columns.size();
    }

    @Override
    public String columnName(int columnIndex) {
        Preconditions.checkElementIndex(columnIndex, columns.size());
        return columns.get(columnIndex).name();
    }

    @Override
    public String columnLabel(int columnIndex) {
        Preconditions.checkElementIndex(columnIndex, columns.size());
        return columns.get(columnIndex).label();
    }

    @Override
    public ColumnType columnType(int columnIndex) {
        Preconditions.checkElementIndex(columnIndex, columns.size());
        return columns.get(columnIndex).type();
    }

    @Override
    public int columnIndex(String columnName) {
        Integer index = columnIndexByName.get(columnName);
        return Optional.ofNullable(index)
                .orElseThrow(() -> new IllegalArgumentException(
                        "The column ['" + columnName + "'] does not exist in this schema."));
    }

    @Override
    public boolean hasColumn(String columnName) {
        return columnIndexByName.get(columnName) != null;
    }

    @Override
    public Iterator<RecordColumn> iterator() {
        return new Iterator<RecordColumn>() {

            private final Iterator<RecordColumn> source = columns.iterator();

            @Override
            public boolean hasNext() {
                return source.hasNext();
            }

            @Override
            public RecordColumn next() {
                return source.next();
            }
        };
    }

    public static final class Builder implements Serializable {

        private final StaticRecordSchema schema;

        public Builder() {
            this.schema = new StaticRecordSchema();
        }

        public Builder addColumn(String name, ColumnType type) {
            return addColumn(name, name, type);
        }

        public Builder addColumn(String name, String label, ColumnType type) {
            Preconditions.checkNotNull(name);
            Preconditions.checkNotNull(label);
            Preconditions.checkNotNull(type);
            if (schema.columns.stream().noneMatch(e->(e.name().equals(name) || e.label().equals(label)))) {
                schema.columns.add(new RecordColumn(name, label, type));
                schema.columnIndexByName.put(name, schema.columns.size() - 1);
            }
            return this;
        }

        public RecordSchema build() {
            return schema;
        }
    }

}
