package com.zuhd.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by dell013 on 2017/12/8.
 */
public class MyCharacterEncodingRequest extends HttpServletRequestWrapper
{
    private HttpServletRequest m_servletRequest;

    public MyCharacterEncodingRequest(HttpServletRequest request)
    {
        super(request);
        m_servletRequest = request;
    }

    @Override
    public String getParameter(String name)
    {
        try
        {
            String value = m_servletRequest.getParameter(name);
            if (null == value)
            {
                return null;
            }

            if (m_servletRequest.getMethod().equalsIgnoreCase("get"))
            {
                value = new String(value.getBytes("ISO8859-1"), m_servletRequest.getCharacterEncoding());
                return value;
            }
            else
            {
                return value;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
