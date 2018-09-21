package com.vz.vue.service;

import com.vz.vue.common.CONST;
import com.vz.vue.util.FileUtil;
import com.vz.vue.common.JDBC;
import com.vz.vue.dao.DBConnection;
import com.vz.vue.model.Column;
import com.vz.vue.model.Table;
import com.vz.vue.util.ForEachUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Creater {

    public static String inputPath = null;

    //一键创建指定数据库下所有表对应的vue
    public static void createAll(String dbName,JDBC jdbc){
        String baseTemplate = FileUtil.readFile(inputPath); //读取模版文件

        DBConnection.init(jdbc);

        for(Table t : DBConnection.findTable(dbName)){
            t = DBConnection.getTable(t); //获取表的字段信息

            Map<String,String> $map = get$map(t); //获取模版需替换标记的映射关系

            String template = new String(baseTemplate); //复制模板

            template = ForEachUtil.process(template,t.getColumns());//解释for标记

            for(Map.Entry<String,String> entry : $map.entrySet()){ //解释全局标记
                template = template.replace(entry.getKey(),entry.getValue());
            }

            FileUtil.writeFile(CONST.OUTPUT_PATH + t.getTableName() + CONST.FILE_TYPE,template); //输出文件

            System.out.println(t.getTableName() + CONST.FILE_TYPE+" 创建完成");
        }
    }

    //根据配置创建一个单页面
    public static void createSingle(Table t){
        String template = FileUtil.readFile(inputPath); //读取模版文件

        Map<String,String> $map = get$map(t); //获取模版需替换标记的映射关系

        template = ForEachUtil.process(template,t.getColumns());//解释for标记

        for(Map.Entry<String,String> entry : $map.entrySet()){ //解释全局标记
            template = template.replace(entry.getKey(),entry.getValue());
        }

        FileUtil.writeFile(CONST.OUTPUT_PATH + t.getTableName() + CONST.FILE_TYPE, template); //输出文件

        System.out.println(t.getTableName() + CONST.FILE_TYPE+ " 创建完成");
    }

    //获取指定数据库下的所有表
    public static List<Table> findTable(String dbName,JDBC jdbc){
        DBConnection.init(jdbc);
        return DBConnection.findTable(dbName);
    }

    //获取指定表的所有字段
    public static Table getTable(Table t, JDBC jdbc){
        DBConnection.init(jdbc);
        return DBConnection.getTable(t);
    }

    //获取模版文件中需要替换的字段
    public static Map<String,String> get$map(Table t){
        String tName = t.getTableName();
        String TName = tName.substring(0,1).toUpperCase()+tName.substring(1);
        List<Column> columns = t.getColumns();

        String idName = null;
        if(columns!=null && !columns.isEmpty()){
            for(Column column : columns){
                if(idName==null && column.getIsPriKey()){
                    idName = column.getName(); //获取主键的名称
                    break;
                }
            }
        }

        Map<String,String> $map = new HashMap<String,String>();

        $map.put(CONST.ENTITY_NAME,tName);
        $map.put(CONST.ID_NAME,idName);
        $map.put(CONST.FORM_NAME,tName+"Form");
        $map.put(CONST.FORM_NAME_PLUS,TName+"Form");
        $map.put(CONST.LIST_API,"list"+TName);
        $map.put(CONST.ADD_API,"add"+TName);
        $map.put(CONST.UPDATE_API,"update"+TName);
        $map.put(CONST.DELETE_API,"delete"+TName);

        return $map;
    }

}
