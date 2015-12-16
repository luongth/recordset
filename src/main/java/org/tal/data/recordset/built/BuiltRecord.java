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
import org.tal.data.recordset.RecordSchema;
import org.tal.data.recordset.support.AbstractRecord;

/**
 * @author Thuan Luong
 */
class BuiltRecord extends AbstractRecord {

    private final Object[] data;

    private final RecordSchema schema;

    public BuiltRecord(RecordSchema schema) {
        Preconditions.checkNotNull(schema);
        this.schema = schema;
        this.data = new Object[schema.columnCount()];
    }

    @Override
    public RecordSchema recordSchema() {
        return schema;
    }

    @Override
    protected Object readValue(int column) {
        Preconditions.checkElementIndex(column, data.length);
        return data[column];
    }

    @Override
    protected Object readValue(String column) {
        return readValue(schema.columnIndex(column));
    }

    protected void addColumnValue(int index, Object value) {
        Preconditions.checkElementIndex(index, data.length);
        data[index] = value;
    }
}
