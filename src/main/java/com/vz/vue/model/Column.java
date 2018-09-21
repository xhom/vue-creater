package com.vz.vue.model;

public class Column {
    private String name; //字段名
    /**
     * 关于字段注释的约定：
     * 1.如果注释不是一个正确格式的json对象，则不提取任何信息，整个注释会被当作字段的描述
     * 2.如果注释内容为json对象并符合以下约定则会提取其中的信息，并在页面生成时应用：
     * {
     *     "desc": "姓名", //字段描述
     *     "input": "select", //控件类型，选填，默认input, 可选值见：INPUT_TYPE
     *     "type": ["required","email"] //校验类型，选填，默认required, 可选值见：VALIDATE_TYPE
     * }
     */
    private String comment; //列注释
    private String dataType; //列数据类型
    private Integer maxLength; //字段最大长度
    private Boolean isNull; //是否必填
    private Boolean isPriKey; //是否主键
    private String type; //列类型

    private Boolean isField = true; //是否基础字段
    private Boolean isCond = true; //是否条件字段
    private Boolean isRule = true; //是否校验字段

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Boolean getIsNull() {
        return isNull;
    }

    public void setIsNull(Boolean aNull) {
        isNull = aNull;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsPriKey() {
        return isPriKey;
    }

    public void setIsPriKey(Boolean priKey) {
        isPriKey = priKey;
    }

    public Boolean getIsField() {
        return isField;
    }

    public void setIsField(Boolean field) {
        isField = field;
    }

    public Boolean getIsCond() {
        return isCond;
    }

    public void setIsCond(Boolean cond) {
        isCond = cond;
    }

    public Boolean getIsRule() {
        return isRule;
    }

    public void setIsRule(Boolean rule) {
        isRule = rule;
    }
}
