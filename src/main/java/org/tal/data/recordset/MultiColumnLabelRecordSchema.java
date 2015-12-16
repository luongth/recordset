package org.tal.data.recordset;

/**
 * @author Thuan Luong
 */
public interface MultiColumnLabelRecordSchema extends RecordSchema {

    String[] columnLabels(int columnIndex);
}
