package com.vz.vue.model;

import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String dbName; //所属数据库名称
    private String tableName; //表名称
    private List<Column> columns; //字段集合

    public Table(){}
    public Table(String dbName,String tableName){
        this.dbName = dbName;
        this.tableName = tableName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public void appendToColumns(Column column){
        if(this.columns == null)
            this.columns = new ArrayList<Column>();
        this.columns.add(column);
    }
}
