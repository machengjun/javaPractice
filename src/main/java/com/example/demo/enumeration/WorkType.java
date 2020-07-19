package com.example.demo.enumeration;

/**
 *
 * @author 马成军
 **/
public enum WorkType {

    /**
     *  老师
     */
    TEACHER("teacher"),

    /**
     *
     */
    DOCTOR("doctor");


    /**
     * desc
     */
    private String desc;

    WorkType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

}