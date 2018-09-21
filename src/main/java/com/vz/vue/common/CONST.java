package com.vz.vue.common;

public class CONST {
    //模板中可用的标记（全局标记）
    public static final String ENTITY_NAME = "${ENTITY_NAME}"; //实体名
    public static final String ID_NAME = "${ID_NAME}"; //主键名称
    public static final String LIST_API = "${LIST_API}"; //分页查询接口名
    public static final String ADD_API = "${ADD_API}"; //新增记录接口名
    public static final String UPDATE_API = "${UPDATE_API}"; //修改记录接口名
    public static final String DELETE_API = "${DELETE_API}"; //删除记录接口名
    public static final String FORM_NAME = "${FORM_NAME}"; //实体对应表单名
    public static final String FORM_NAME_PLUS = "${FORM_NAME_PLUS}"; //实体对应表单名（首字母大写）

    //循环标记（成对使用）可以指定type,可选值有（fields,rules,conds,rows，默认fields，注意：其他值会导致整个for标记不被翻译）
    //type使用示例： ${for_begin type=rules} 注意：type=rules,等号两端不能有空格
    public static final String FOR_BEGIN = "${for_begin}";
    public static final String FOR_END = "${for_end}";
    //在循环标记中可以使用的内部标记，后续扩展
    public static final String FIELD_DESC = "${FIELD_DESC}"; //字段描述
    public static final String FIELD_NAME = "${FIELD_NAME}"; //字段名（变量名）
    public static final String FIELD_LENGTH = "${FIELD_LENGTH}"; //字段最大长度


    //正则
    public static final String FOR_BEGIN_REGX = "\\$\\{for_begin(\\s+type=(rules|fields|conds|rows))?\\s*\\}";
    public static final String FOR_TYPE_REGX = "type=\\w+";
    public static final String FOR_TYPE_PREFIX = "type=";
    public static final String FOR_END_REGX = "\\$\\{for_end\\}";

    //文件路径
    public static final String TEMP_PATH ="src\\main\\resources\\input.txt";
    public static final String OUTPUT_PATH = "D:\\vueCreater\\output\\";
    public static final String FILE_TYPE = ".vue";

}
