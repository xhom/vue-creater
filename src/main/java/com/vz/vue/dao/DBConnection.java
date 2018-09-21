package com.vz.vue.dao;
import com.vz.vue.common.JDBC;
import com.vz.vue.util.NameConvert;
import com.vz.vue.common.Result;
import com.vz.vue.model.Column;
import com.vz.vue.model.Table;

import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static Connection conn = null;
    private static Statement stmt = null;

    //初始化数据库连接
    public static Result init (JDBC jdbc){
        try{
            Class.forName(jdbc.getDriverName());
            System.out.println(jdbc.getUrl());
            conn = DriverManager.getConnection(jdbc.getUrl(),jdbc.getUser(),jdbc.getPassword());
            stmt = conn.createStatement();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            return new Result(false,"数据库驱动名错误");
        }catch (SQLException e){
            e.printStackTrace();
            return new Result(false,"数据库连接获取失败");
        }
        return new Result("数据库连接初始化成功");
    }

    //查询指定数据库下的所有表
    public static List<Table> findTable(String dbName){
        List<Table> tables = new ArrayList<Table>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TABLE_NAME FROM `COLUMNS` WHERE TABLE_SCHEMA='");
        sql.append(dbName);
        sql.append("' GROUP BY TABLE_NAME");
        ResultSet rs = null;
        try{
            rs = stmt.executeQuery(sql.toString());
            while (rs.next()){
                Table table = new Table();
                table.setDbName(dbName);
                table.setTableName(rs.getString("TABLE_NAME"));
                tables.add(table);
            }
        }catch (SQLException e){
            System.out.println("数据表列表查询出错");
            e.printStackTrace();
        }finally {
            if(rs != null){
                try{rs.close();}catch (SQLException e){}
            }
        }
        return tables;
    }

    //查询指定表下的所有字段信息
    public static Table getTable(Table table){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("COLUMN_NAME,"); //列名
        sql.append("COLUMN_COMMENT,"); //列注释
        sql.append("DATA_TYPE,"); //列数据类型
        sql.append("IFNULL(CHARACTER_MAXIMUM_LENGTH,(NUMERIC_PRECISION+NUMERIC_SCALE)) MAX_LENGTH,"); //字段最大长度
        sql.append("IS_NULLABLE,"); //是否必填
        sql.append("COLUMN_KEY,"); //是否主键
        sql.append("COLUMN_TYPE "); //列类型
        sql.append("FROM `COLUMNS` WHERE TABLE_SCHEMA='");
        sql.append(table.getDbName());
        sql.append("' AND TABLE_NAME='");
        sql.append(table.getTableName());
        sql.append("'");

        ResultSet rs = null;
        try{
            rs = stmt.executeQuery(sql.toString());
            while (rs.next()){
                Column column = new Column();
                column.setName(NameConvert.lineToHump(rs.getString("COLUMN_NAME")));
                column.setComment(rs.getString("COLUMN_COMMENT"));
                column.setDataType(rs.getString("DATA_TYPE"));
                column.setMaxLength(rs.getInt("MAX_LENGTH"));
                column.setIsNull("YES".equalsIgnoreCase(rs.getString("IS_NULLABLE")));
                column.setIsPriKey("PRI".equalsIgnoreCase(rs.getString("COLUMN_KEY")));
                column.setType(rs.getString("COLUMN_TYPE"));

                column.setIsRow(!column.getIsPriKey());
                column.setIsCond(!column.getIsPriKey());
                column.setIsField(!column.getIsPriKey());
                column.setIsRule(!column.getIsPriKey() && !column.getIsNull());

                table.appendToColumns(column);
            }
        }catch (SQLException e){
            System.out.println("查询出错");
            e.printStackTrace();
        }finally {
            if(rs != null){
                try{rs.close();}catch (SQLException e){}
            }
        }
        //在获取字段信息后把表名转为驼峰命名
        table.setTableName(NameConvert.lineToHump(table.getTableName()));
        return table;
    }
}
