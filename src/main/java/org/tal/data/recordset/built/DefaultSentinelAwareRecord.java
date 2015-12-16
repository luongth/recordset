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

import org.tal.data.recordset.ExceptionAwareSentinelRecord;
import org.tal.data.recordset.RecordSchema;

/**
 * @author Thuan Luong
 */
public class DefaultSentinelAwareRecord extends BuiltRecord implements ExceptionAwareSentinelRecord {

    private final boolean sentinel;

    private final Throwable error;

    private DefaultSentinelAwareRecord(boolean sentinel, RecordSchema metaData, Throwable error) {
        super(metaData);
        this.sentinel = sentinel;
        this.error = error;
    }

    private DefaultSentinelAwareRecord(boolean sentinel, RecordSchema metaData) {
        this(sentinel, metaData, null);
    }

    private DefaultSentinelAwareRecord(RecordSchema metaData) {
        this(false, metaData);
    }

    @Override
    public boolean hasError() {
        return this.error != null;
    }

    @Override
    public Throwable getError() {
        return this.error;
    }

    @Override
    public boolean isSentinel() {
        return this.sentinel;
    }

    public static DefaultSentinelAwareRecord createSentinelRecord(RecordSchema metaData) {
        return new DefaultSentinelAwareRecord(true, metaData);
    }

    public static DefaultSentinelAwareRecord createSentinelRecord(RecordSchema metaData, Throwable error) {
        return new DefaultSentinelAwareRecord(true, metaData, error);
    }

    public static final class Builder {

        private final DefaultSentinelAwareRecord record;

        private final RecordSchema metaData;

        public Builder(RecordSchema metaData) {
            this.record = new DefaultSentinelAwareRecord(metaData);
            this.metaData = metaData;
        }

        public Builder setColumn(int index, Object value) {
            this.record.addColumnValue(index, value);
            return this;
        }

        public Builder setColumn(String name, Object value) {
            return setColumn(this.metaData.columnIndex(name), value);
        }

        public DefaultSentinelAwareRecord build() {
            return this.record;
        }
    }
}
