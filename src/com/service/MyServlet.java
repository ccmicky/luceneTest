package com.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.sun.xml.internal.ws.util.Constants;
import org.apache.lucene.search.*;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by ccmicky on 15-8-22.
 */
public class MyServlet extends javax.servlet.http.HttpServlet {

    /*protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
//        PrintWriter pw  = new PrintWriter()
        PrintWriter out = response.getWriter();
        String keyword = request.getParameter("keyword");
        keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
        String str[] = request.getParameterValues("select");
        System.out.println("aaaaaaaaaaaaaa");
        System.out.println(str.getClass());
        //response.sendRedirect("/content.jsp");
        out.print("finish!");
        DataBaseSearch sr = new DataBaseSearch();
        try {
            //sentlist  = sr.search(keyword, first, max, sort);
            sr.search(keyword,str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //slist = sentlist.getsentlist();


    }


    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        this.doPost(request, response);
    }*/
    /*public readData rd ;
    public MyServlet(){
        System.out.println("调用了无参的构造函数");
        distance d = new distance();
        try {

            rd = d.init();
            //sentlist  = sr.search(keyword, first, max, sort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=GB2312"); //这条语句指明了向客户端发送的内容格式和采用的字符编码． 　　
        PrintWriter out = response.getWriter();
        //out.println(" 您好！");//利用PrintWriter对象的方法将数据发送给客户端 　　
        //out.close();
        int size;
        String strsize = "40";
        String wordsline = "酒店";
        try {
            strsize = request.getParameter("size");
            wordsline = request.getParameter("word");
            System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbb" + strsize);
        }catch (Exception e) {
            e.printStackTrace();
        }
        wordsline = new String(wordsline.getBytes("iso8859-1"),"utf-8");
        size = Integer.parseInt(strsize);
        readData rd = (readData)request.getServletContext().getAttribute("rd");
        if(rd!=null){
            System.out.print("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+rd.str[0]);
        }

        distance d = new distance();
        resultData res = new resultData();
        try {
            //sentlist  = sr.search(keyword, first, max, sort);
            res = d.getnearnestWords(wordsline.split(" "),size,rd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //out.println("\nWord Cosine distance\n------------------------------------------------------------------------\n");
        JSONArray array = new JSONArray();
        for (int i =0;i<res.str.length;i++) {

           //out.printf(res.str[i]+" "+res.f[i]+'\t');
            array.put(res.str[i]+" " +res.f[i]);
        }

        response.getWriter().write(array.toString());
        //PrintWriter writer = response.getWriter();
        //writer.write(new Gson().toJson(res, resultData.class));
       // out.print(new Gson().toJson(res, resultData.class));

        out.close();
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); //这条语句的作用是，当客户端发送POST请求时，调用doGet()方法进行处理 　　
        // }

    }

}
