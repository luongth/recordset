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

import org.tal.core.utils.ReverseLookupEnumSupport;

/**
 * @author Thuan Luong
 */
public enum ColumnType implements ReverseLookupEnumSupport<Integer, ColumnType> {

    NUMBER (1),
    STRING (2),
    BOOLEAN (3),
    DATETIME (4);

    private final int value;

    private ColumnType(int value) {
        this.value = value;
    }

    public static ColumnType fromValue(int value) {
        return NUMBER.lookup(value).orElseThrow(() -> new IllegalArgumentException("No ColumnType defined for value: " + value));
    }

    @Override
    public Integer value() {
        return value;
    }
}
