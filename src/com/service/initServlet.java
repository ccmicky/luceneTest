package com.service;

import javax.servlet.ServletException;

/**
 * Created by ccmicky on 15-11-20.
 */
public class initServlet extends javax.servlet.http.HttpServlet {
    @Override
    public void destroy()
    {
        super.destroy();
    }

    @Override
    public void init()throws ServletException {
        readData rd = new readData();
        LoadData ld = new LoadData();
        rd = ld.initDatafromFile();
        getServletContext().setAttribute("rd",rd);
        //System.out.print("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        super.init();

    }
}
