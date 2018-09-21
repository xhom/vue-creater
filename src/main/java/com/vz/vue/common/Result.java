package com.vz.vue.common;

/**
 * 返回处理结果
 * @author visy.wang
 */
public class Result {
    private boolean success = true;
    private String msg;

    public Result() {
    }

    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public Result(String msg) {
        this.msg = msg;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
