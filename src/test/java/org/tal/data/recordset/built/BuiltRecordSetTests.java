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

import org.junit.Test;
import org.tal.data.recordset.ColumnType;
import org.tal.data.recordset.Record;
import org.tal.data.recordset.RecordSchema;
import org.tal.data.recordset.RecordSet;
import org.tal.data.recordset.support.StaticRecordSchema;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * @author Thuan Luong
 */
public class BuiltRecordSetTests {

    @Test
    public void iterate_through_recordset() {
        final LocalDateTime now = LocalDateTime.now();
        final RecordSchema schema = buildSchema();
        final RecordSet<Record> recordSet = buildRecordSet(schema, now);

        assertThat(recordSet.recordSchema(), is(schema));

        int count = 0;
        for (Record record : recordSet) {
            assertThat(record, is(notNullValue()));
            assertThat(record, is(recordSet.current()));
            switch (count) {
                case 0: {
                    validateRecord(record, "Bob", 41, true, now);
                    break;
                }
                case 1: {
                    validateRecord(record, "Tom", 30, false, now);
                    break;
                }
            }
            count++;
        }
        assertThat(count, is(2));
    }

    @Test
    public void index_based_lookup() {
        final LocalDateTime now = LocalDateTime.now();
        final RecordSchema schema = buildSchema();
        final RecordSet<Record> recordSet = buildRecordSet(schema, now);

        final Record record = recordSet.next();
        assertThat(record.readString(0).get(), is("Bob"));
        assertThat(record.readNumber(1).map(Number::intValue).get(), is(41));
        assertThat(record.readBoolean(2).get(), is(true));
        assertThat(record.readDateTime(3).get(), is(now));
    }

    @Test
    public void read_as_objects() {
        final LocalDateTime now = LocalDateTime.now();
        final RecordSchema schema = buildSchema();
        final RecordSet<Record> recordSet = buildRecordSet(schema, now);

        final Record record = recordSet.next();
        Object value = record.readObject("name").get();
        assertThat(value, is(instanceOf(String.class)));

        value = record.readObject(1).get();
        assertThat(value, is(instanceOf(Integer.class)));
    }

    @Test
    public void attempt_invalid_read() {
        final RecordSchema schema = new StaticRecordSchema.Builder()
                .addColumn("name", ColumnType.STRING)
                .build();
        final BuiltRecordSet recordSet = new BuiltRecordSet(schema);
        recordSet.newRecord().setColumn("name", "Bob");

        final Record record = recordSet.next();

        try {
            record.readNumber(0);
            fail("Should have thrown IllegalArgumentException");
        } catch (Exception e) {
            assertThat(e, is(instanceOf(IllegalArgumentException.class)));
        }
    }

    private static RecordSchema buildSchema() {
        return new StaticRecordSchema.Builder()
                .addColumn("name", "Name", ColumnType.STRING)
                .addColumn("age", "Age", ColumnType.NUMBER)
                .addColumn("active", "Active", ColumnType.BOOLEAN)
                .addColumn("joined", "Joined", ColumnType.DATETIME)
                .build();
    }

    private static RecordSet<Record> buildRecordSet(RecordSchema schema, LocalDateTime now) {
        final BuiltRecordSet recordSet = new BuiltRecordSet(schema);
        recordSet.newRecord().setColumn("name", "Bob").setColumn("age", 41).setColumn("active", true).setColumn("joined", now);
        recordSet.newRecord().setColumn("name", "Tom").setColumn("age", 30).setColumn("active", false).setColumn("joined", now);
        return recordSet;
    }

    private static void validateRecord(Record record, String name, int age, boolean active, LocalDateTime date) {
        assertThat(record.readString("name").get(), is(name));
        assertThat(record.readNumber("age").map(Number::intValue).get(), is(age));
        assertThat(record.readBoolean("active").get(), is(active));
        assertThat(record.readDateTime("joined").get(), is(date));
    }
}
