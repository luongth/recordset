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

import org.junit.Test;
import org.tal.data.recordset.ColumnType;
import org.tal.data.recordset.RecordColumn;
import org.tal.data.recordset.RecordSchema;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * @author Thuan Luong
 */
public class StaticRecordSchemaTests {

    @Test
    public void multiple_column_schema() {
        RecordSchema schema = buildMultipleColumnSchema();

        assertThat(schema, is(notNullValue()));
        assertThat(schema.columnCount(), is(3));

        int count = 0;
        for (RecordColumn column : schema) {
            assertThat(column, is(notNullValue()));
            switch (count) {
                case 0: {
                    validateColumn(column, "account_code", "Account", ColumnType.STRING);
                    break;
                }
                case 1: {
                    validateColumn(column, "ccy", "Currency", ColumnType.STRING);
                    break;
                }
                case 2: {
                    validateColumn(column, "cash", "Cash", ColumnType.NUMBER);
                    break;
                }
            }
            count++;
        }
        assertThat(count, is(3));
    }

    @Test
    public void column_name_lookup() {
        RecordSchema schema = buildMultipleColumnSchema();

        assertThat(schema.columnName(0), is("account_code"));
        assertThat(schema.columnName(1), is("ccy"));
        assertThat(schema.columnName(2), is("cash"));

        try {
            schema.columnName(3);
            fail("Should have thrown IndexOutOfBoundsException");
        } catch (Exception e) {
            assertThat(e, is(instanceOf(IndexOutOfBoundsException.class)));
        }
    }

    @Test
    public void column_label_lookup() {
        RecordSchema schema = buildMultipleColumnSchema();

        assertThat(schema.columnLabel(0), is("Account"));
        assertThat(schema.columnLabel(1), is("Currency"));
        assertThat(schema.columnLabel(2), is("Cash"));

        try {
            schema.columnLabel(3);
            fail("Should have thrown IndexOutOfBoundsException");
        } catch (Exception e) {
            assertThat(e, is(instanceOf(IndexOutOfBoundsException.class)));
        }
    }

    @Test
    public void column_type_lookup() {
        RecordSchema schema = buildMultipleColumnSchema();

        assertThat(schema.columnType(0), is(ColumnType.STRING));
        assertThat(schema.columnType(1), is(ColumnType.STRING));
        assertThat(schema.columnType(2), is(ColumnType.NUMBER));

        try {
            schema.columnType(3);
            fail("Should have thrown IndexOutOfBoundsException");
        } catch (Exception e) {
            assertThat(e, is(instanceOf(IndexOutOfBoundsException.class)));
        }
    }

    @Test
    public void column_index_lookup() {
        RecordSchema schema = buildMultipleColumnSchema();

        assertThat(schema.columnIndex("account_code"), is(0));
        assertThat(schema.columnIndex("ccy"), is(1));
        assertThat(schema.columnIndex("cash"), is(2));

        try {
            schema.columnIndex("invalid");
            fail("Should have thrown IllegalArgumentException");
        } catch (Exception e) {
            assertThat(e, is(instanceOf(IllegalArgumentException.class)));
        }
    }

    @Test
    public void has_column_lookup() {
        RecordSchema schema = buildMultipleColumnSchema();

        assertThat(schema.hasColumn("account_code"), is(true));
        assertThat(schema.hasColumn("ccy"), is(true));
        assertThat(schema.hasColumn("cash"), is(true));
        assertThat(schema.hasColumn("invalid"), is(false));
        assertThat(schema.hasColumn("Account_Code"), is(false)); // names are case-sensitives
        assertThat(schema.hasColumn(null), is(false));
    }

    @Test
    public void single_column_schema() {
        StaticRecordSchema.Builder builder = new StaticRecordSchema.Builder();
        builder.addColumn("account_code", "Account", ColumnType.STRING);
        RecordSchema schema = builder.build();
        assertThat(schema.columnCount(), is(1));
        for (RecordColumn column : schema) {
            validateColumn(column, "account_code", "Account", ColumnType.STRING);
        }
    }

    @Test
    public void name_only_column_schema() {
        StaticRecordSchema.Builder builder = new StaticRecordSchema.Builder();
        builder.addColumn("account_code", ColumnType.STRING);
        RecordSchema schema = builder.build();
        assertThat(schema.columnCount(), is(1));
        assertThat(schema.columnName(0), is(schema.columnLabel(0)));
    }

    private static void validateColumn(RecordColumn column, String name, String label, ColumnType type) {
        assertThat(column.name(), is(name));
        assertThat(column.label(), is(label));
        assertThat(column.type(), is(type));
    }

    private static RecordSchema buildMultipleColumnSchema() {
        StaticRecordSchema.Builder builder = new StaticRecordSchema.Builder();
        builder.addColumn("account_code", "Account", ColumnType.STRING);
        builder.addColumn("ccy", "Currency", ColumnType.STRING);
        builder.addColumn("cash", "Cash", ColumnType.NUMBER);
        return builder.build();
    }
}
