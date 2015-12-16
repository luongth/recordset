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

package org.tal.data.recordset;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Thuan Luong
 */
public class ColumnTypeTests {

    @Test
    public void verify_only_four_column_types_supported() {
        assertThat(ColumnType.values().length, is(4));
    }

    @Test
    public void verify_number_column_type_has_code_of_1() {
        assertThat(ColumnType.NUMBER.value(), is(1));
    }

    @Test
    public void verify_string_column_type_has_code_of_2() {
        assertThat(ColumnType.STRING.value(), is(2));
    }

    @Test
    public void verify_boolean_column_type_has_code_of_3() {
        assertThat(ColumnType.BOOLEAN.value(), is(3));
    }

    @Test
    public void verify_datetime_column_type_has_code_of_4() {
        assertThat(ColumnType.DATETIME.value(), is(4));
    }

    @Test
    public void lookup_for_supported_column_type() {
        ColumnType type = ColumnType.fromValue(1);
        assertThat(type, is(notNullValue()));
        assertThat(type, is(ColumnType.NUMBER));
    }
}
