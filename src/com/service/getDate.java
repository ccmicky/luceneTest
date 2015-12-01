package com.service;

import java.util.Date;
import java.text.*;

/**
 * Created by ccmicky on 15-8-31.
 */
public class getDate {
    public String GetNowDate(){
        String temp_str="";
        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        temp_str=sdf.format(dt);
        return temp_str;
    }
}
