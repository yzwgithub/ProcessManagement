package com.andios.util;

/**
 * Created by ASUS on 2017/12/30.
 */

public class ProjectInfo {
    String p_id;
    String p_name;

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    @Override
    public String toString() {
        return "ProjectInfo{" +
                "p_id='" + p_id + '\'' +
                ", p_name='" + p_name + '\'' +
                '}';
    }
}
