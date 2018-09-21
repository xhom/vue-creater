package com.vz.vue.common;

/**
 * @author wangxh
 * @company vanke.com
 * @date 2018/9/17 16:04
 */
//校验类型配置
//对应 字段注释的{"type"："required"} //默认 required
public class VALIDATE_TYPE {
    public static final String REQUIRED = "required";
    public static final String EMAIL = "email";
    public static final String POST_CODE = "postcode";
    public static final String PHONE = "phone";
    public static final String MOBILE = "mobile";
    public static final String FAX = "fax";
    public static final String NUMBER = "number";
    public static final String INT = "int"; //正整数(含0)
    public static final String INT_PLUS = "intplus";//正整数
    public static final String FLOAT = "float";//浮点数
    public static final String FLOAT2 = "float2";//最多两位小数
    public static final String FLOAT3 = "float3";//最对三位小数
}
