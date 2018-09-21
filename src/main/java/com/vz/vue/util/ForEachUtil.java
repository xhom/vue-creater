package com.vz.vue.util;

import com.vz.vue.common.CONST;
import com.vz.vue.model.Column;

import javax.swing.text.MutableAttributeSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangxh
 * @company vanke.com
 * @date 2018/9/20 11:48
 */
public class ForEachUtil {

    public static String process(String text, List<Column> columns){
        Pattern pattern = Pattern.compile(CONST.FOR_BEGIN_REGX+"(.|\\n)*?"+CONST.FOR_END_REGX);
        Pattern forBeginRegx = Pattern.compile(CONST.FOR_BEGIN_REGX);
        Pattern typeRegx = Pattern.compile(CONST.FOR_TYPE_REGX);

        Matcher matcher = pattern.matcher(text);

        int i = 0;
        while(matcher.find()){
            String for_text = matcher.group(i);


            String forBegin = "";
            Matcher beginMatcher = forBeginRegx.matcher(for_text);
            if(beginMatcher.find()){
                forBegin = beginMatcher.group(0);
            }
            //提取for的type
            String type = "";
            Matcher typeMatcher = typeRegx.matcher(forBegin);
            if(typeMatcher.find()){
                type = typeMatcher.group(0).replace(CONST.FOR_TYPE_PREFIX,"");
            }

            String item_text = for_text.replace(forBegin,"").replace(CONST.FOR_END,"");

            StringBuilder new_for_text = new StringBuilder();
            for(Column column : columns){
                String new_item_text = new String(item_text);
                String desc = column.getComment();
                String name = column.getName();
                Integer maxLength = column.getMaxLength();

                boolean isReplace = false;

                if("fields".equals(type) ){
                    isReplace = column.getIsField();
                }else if("conds".equals(type)){
                    isReplace = column.getIsCond();
                }else if("rules".equals(type) && column.getIsCond()){
                    isReplace = column.getIsRule();
                }else if("formitems".equals(type) && column.getIsField()){
                    isReplace = column.getIsField();//TODO
                }else{//默认fields
                    isReplace = column.getIsField();
                }

                if(isReplace){
                    new_item_text = new_item_text.replace(CONST.FIELD_DESC,desc).replace(CONST.FIELD_NAME,name).replace(CONST.FIELD_LENGTH,maxLength+"");
                    new_for_text.append(new_item_text);
                }
            }

            text = text.replace(for_text,new_for_text.toString());
        }

        return text;
    }
}
