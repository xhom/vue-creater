package com.vz.vue.model;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
    String[] head = {"描述","字段名","基础字段(rows)","可查询(conds)","可编辑(fields)","可校验(rules)"};
    Object[][] data = {};
    Class[] typeArray = { Object.class, Object.class, Boolean.class,Boolean.class, Boolean.class,Boolean.class };

    public MyTableModel(){}

    public MyTableModel(Object[][] data){
        super();
        this.data = data;
    }

    // 获得表格的列数
    public int getColumnCount() {
        return head.length;
    }

    // 获得表格的行数
    public int getRowCount() {
        return data.length;
    }

    // 获得表格的列名称
    @Override
    public String getColumnName(int column) {
        return head[column];
    }

    // 获得表格的单元格的数据
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    // 使表格具有可编辑性
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    // 替换单元格的值
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    // 实现了如果是boolean自动转成JCheckbox
    /*
     * 需要自己的celleditor这么麻烦吧。jtable自动支持Jcheckbox，
     * 只要覆盖tablemodel的getColumnClass返回一个boolean的class， jtable会自动画一个Jcheckbox给你，
     * 你的value是true还是false直接读table里那个cell的值就可以
     */
    public Class getColumnClass(int columnIndex) {
        return typeArray[columnIndex];// 返回每一列的数据类型
    }

}
