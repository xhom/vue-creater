package com.vz.vue.common;

/**
 * JDBC连接管理类
 * @author visy.wang
 */
public enum JDBC {
    MYSQL(0,
        "MySQL",
        "com.mysql.jdbc.Driver",
        "jdbc:mysql://${ip}:${port}/${dbName}?useUnicode=true&characterEncoding=UTF8",
        "localhost",
        "3306",
        "root",
        "123456",
        "test",
        "information_schema"),
    ORACLE(1,
        "Oracle",
        "oracle.jdbc.driver.OracleDriver",
        "jdbc:oracle:thin:@//${ip}:${port}/orcl",
        "localhost",
        "1521",
        "scott",
        "tiger",
        "",
        "");
    private Integer type;
    private String desc;
    private String driverName;
    private String urlTemp;
    private String user;
    private String password;
    private String ip;
    private String port;
    private String dbName;
    private String baseDb;

    JDBC(Integer type,
         String desc,
         String driverName,
         String urlTemp,
         String ip,
         String port,
         String user,
         String password,
         String dbName,
         String baseDb){

        this.type = type;
        this.desc = desc;
        this.driverName = driverName;
        this.urlTemp = urlTemp;
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
        this.dbName = dbName;
        this.baseDb = baseDb;
    }

    public Integer getType() {
        return this.type;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getUrl(){
        return this.urlTemp.replace("${ip}",this.ip).
                replace("${port}",this.port).
                replace("${dbName}",this.baseDb);
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public JDBC init(String ip, String port,String user, String password){
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUrlTemp() {
        return urlTemp;
    }

    public void setUrlTemp(String urlTemp) {
        this.urlTemp = urlTemp;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBaseDb() {
        return baseDb;
    }

    public void setBaseDb(String baseDb) {
        this.baseDb = baseDb;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public static JDBC getByDesc(String desc){
        for(JDBC jdbc : JDBC.values()){
            if(jdbc.getDesc().equals(desc)){
                return jdbc;
            }
        }
        return null;
    }
}
