package com.zuhd.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell013 on 2017/12/11.
 */
public class KeywordsFilter implements Filter
{
    private FilterConfig m_filterConfig = null;
    private String m_defaultCharset = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        m_filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        String charset = m_filterConfig.getInitParameter("charset");
        if (null == charset)
        {
            charset = m_defaultCharset;
        }
        servletRequest.setCharacterEncoding(charset);
        servletResponse.setCharacterEncoding(charset);
        servletResponse.setContentType("text/html;charset=" + charset);
        KeywordsRequest keywordsRequest = new KeywordsRequest((HttpServletRequest)servletRequest);
        filterChain.doFilter(keywordsRequest,servletResponse);
    }

    @Override
    public void destroy()
    {

    }

    public List<String> getKeywords()
    {
        List<String> keyWords = new ArrayList<String>();
        String keyWordsPath = m_filterConfig.getInitParameter("keywords");
        if (null == keyWordsPath)
        {
            System.out.println("path error!");
            return keyWords;
        }

        InputStream input = m_filterConfig.getServletContext().getResourceAsStream(keyWordsPath);
        InputStreamReader isReader = null;
        try
        {
            isReader = new InputStreamReader(input, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        BufferedReader bufferReader = new BufferedReader(isReader);
        String line = null;
        try
        {
            while ((line = bufferReader.readLine()) != null)
            {
                keyWords.add(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return keyWords;

    }

    public class KeywordsRequest extends HttpServletRequestWrapper
    {
        private HttpServletRequest m_servletRequest;

        public KeywordsRequest(HttpServletRequest request)
        {
            super(request);
            m_servletRequest = request;
        }

        @Override
        public String getParameter(String name)
        {
            String value = m_servletRequest.getParameter(name);
            if (null == value)
            {
                return null;
            }

            List<String> keyWords = getKeywords();
            for (String word : keyWords)
            {
                if (value.contains(word))
                {
                    System.out.println(value + " contains key word = " + word);
                    value = value.replace(word, "***");
                }
            }

            return value;
        }
    }
}
