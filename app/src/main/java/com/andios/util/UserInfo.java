package com.andios.util;

/**
 * Created by YangZheWen on 2017/12/7.
 * 获取用户信息的工具类
 */

public class UserInfo {
    private String user_id;
    public UserInfo(){

    }
    public UserInfo(String user_id) {
        this.user_id = user_id;
    }

    private String dept_id;
    private String real_name;
    private String job;
    private String positio;
    private String pracreq;
    private String phone;
    private String idcard;
    private String dailysettle;
    private String borrow;

    public UserInfo(String user_id, String dept_id, String real_name, String job, String positio,
                    String pracreq, String phone, String idcard, String dailysettle, String borrow) {
        this.user_id = user_id;
        this.dept_id = dept_id;
        this.real_name = real_name;
        this.job = job;
        this.positio = positio;
        this.pracreq = pracreq;
        this.phone = phone;
        this.idcard = idcard;
        this.dailysettle = dailysettle;
        this.borrow = borrow;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPositio(String positio) {
        this.positio = positio;
    }

    public void setPracreq(String pracreq) {
        this.pracreq = pracreq;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public void setDailysettle(String dailysettle) {
        this.dailysettle = dailysettle;
    }

    public void setBorrow(String borrow) {
        this.borrow = borrow;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getDept_id() {
        return dept_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public String getJob() {
        return job;
    }

    public String getPositio() {
        return positio;
    }

    public String getPracreq() {
        return pracreq;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdcard() {
        return idcard;
    }

    public String getDailysettle() {
        return dailysettle;
    }

    public String getBorrow() {
        return borrow;
    }

}
