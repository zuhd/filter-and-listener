package com.zuhd.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by dell013 on 2017/12/8.
 */
public class CharacterEncodingServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String username = req.getParameter("username");
        String method = req.getMethod();
        PrintWriter out = resp.getWriter();
        out.write("请求的方式：" + method);
        out.write("<br/>");
        out.write("接受到的参数：" + username);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doGet(req, resp);
    }
}
