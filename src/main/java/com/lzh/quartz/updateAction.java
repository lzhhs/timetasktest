package com.lzh.quartz;

import com.opensymphony.xwork2.ActionSupport;

public class updateAction extends ActionSupport {

    public String update(){
        ScheduledConfig.setCron("0/1 * * * * ?");
        return "success";
    }
    public String update1(){
        ScheduledConfig.setCron("0/2 * * * * ?");
        return "success";
    }
}
