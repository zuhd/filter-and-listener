package com.zuhd.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by dell013 on 2017/12/8.
 */
public class CharacterEncodingFilter implements Filter
{
    private FilterConfig m_filterConfig = null;
    private String m_strDefaultCharset = "UTF-8";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        String charSet = m_filterConfig.getInitParameter("charset");
        if (null == charSet)
        {
            charSet = m_strDefaultCharset;
        }

        servletRequest.setCharacterEncoding(charSet);
        servletResponse.setCharacterEncoding(charSet);
        servletResponse.setContentType("text/html;charset=" + charSet);
        MyCharacterEncodingRequest requestWrapper = new MyCharacterEncodingRequest((HttpServletRequest) servletRequest);
        filterChain.doFilter(requestWrapper, servletResponse);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        m_filterConfig = filterConfig;
    }

    @Override
    public void destroy()
    {

    }
}

