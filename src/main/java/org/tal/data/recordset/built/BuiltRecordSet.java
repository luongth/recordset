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

package org.tal.data.recordset.built;

import com.google.common.base.Preconditions;
import org.tal.data.recordset.Record;
import org.tal.data.recordset.RecordSchema;
import org.tal.data.recordset.support.AbstractRecordSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thuan Luong
 */
public class BuiltRecordSet extends AbstractRecordSet<Record> {

    private final RecordSchema schema;

    private final List<Record> records = new ArrayList<>();

    private Record current;

    private int count = -1;

    public BuiltRecordSet(RecordSchema schema) {
        Preconditions.checkNotNull(schema);
        this.schema = schema;
    }

    public RecordBuilder newRecord() {
        final BuiltRecord record = new BuiltRecord(schema);
        records.add(record);
        return new Builder(record);
    }

    @Override
    public boolean hasNext() {
        return count < records.size() - 1;
    }

    @Override
    public Record next() {
        count++;
        current = records.get(count);
        return current;
    }

    @Override
    public Record current() {
        return current;
    }

    @Override
    public RecordSchema recordSchema() {
        return this.schema;
    }

    private static class Builder implements RecordBuilder {

        private final BuiltRecord record;

        public Builder(BuiltRecord record) {
            this.record = record;
        }

        @Override
        public RecordBuilder setColumn(int column, Object value) {
            record.addColumnValue(column, value);
            return this;
        }

        @Override
        public RecordBuilder setColumn(String column, Object value) {
            return setColumn(record.recordSchema().columnIndex(column), value);
        }
    }
}
