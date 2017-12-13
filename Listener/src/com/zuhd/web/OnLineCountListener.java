package com.zuhd.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by dell013 on 2017/12/13.
 */
public class OnLineCountListener implements HttpSessionListener
{
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent)
    {
        ServletContext context = httpSessionEvent.getSession().getServletContext();
        Integer onLineCount = (Integer)context.getAttribute("OnLineCount");
        if (null == onLineCount)
        {
            context.setAttribute("OnLineCount", 1);
        }
        else
        {
            onLineCount++;
            context.setAttribute("OnLineCount", onLineCount);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent)
    {
        ServletContext context = httpSessionEvent.getSession().getServletContext();
        Integer onLineCount = (Integer)context.getAttribute("OnLineCount");
        if (onLineCount != null)
        {
            onLineCount--;
            context.setAttribute("OnLineCount", onLineCount);
        }
    }
}
