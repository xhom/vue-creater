package com.vz.vue.ui;


import com.vz.vue.common.CONST;
import com.vz.vue.common.JDBC;
import com.vz.vue.model.Table;
import com.vz.vue.service.Creater;
import com.vz.vue.util.FileUtil;
import com.vz.vue.util.ForEachUtil;

import java.util.Map;

/**
 * @author wangxh
 * @company vanke.com
 * @date 2018/9/20 9:41
 */
public class Test {
    public static void main(String[] args) {
        String baseTemplate = FileUtil.readFile(CONST.TEMP_PATH); //读取模版文件
        for(Table t: Creater.findTable("cloud_chain",JDBC.MYSQL)){
            t.setTableName("md_sku");
            t = Creater.getTable(t,JDBC.MYSQL);

            String template = new String(baseTemplate); //复制模板

            template = ForEachUtil.process(template,t.getColumns());

            Map<String,String> $map = Creater.get$map(t); //获取模版需替换标记的映射关系


            for(Map.Entry<String,String> entry : $map.entrySet()){ //替换文件中的标记
                template = template.replace(entry.getKey(),entry.getValue());
            }

            System.out.println(template);

            break;
        }
    }
}
