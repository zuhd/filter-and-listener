package com.zuhd.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell013 on 2017/12/11.
 */
public class ResourceCacheFilter implements Filter
{
    private Map<String, byte[]> m_mapCache = new HashMap<String, byte[]>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String uri = request.getRequestURI();
        String strResourceHtml = null;
        byte resource[] = m_mapCache.get(uri);
        if (resource != null)
        {
            // if HIT
            strResourceHtml = new String(resource, response.getCharacterEncoding());
            System.out.println(strResourceHtml);
            response.getOutputStream().write(resource);
            return;
        }

        // NOT hit
        ResourceCacheResponse cacheResponse = new ResourceCacheResponse(response);
        filterChain.doFilter(request, cacheResponse);

        byte out[] = cacheResponse.getBuffer();
        m_mapCache.put(uri, out);
        response.getOutputStream().write(out);
    }

    @Override
    public void destroy()
    {

    }

    class ResourceCacheResponse extends HttpServletResponseWrapper
    {
        private ByteArrayOutputStream m_outputStream = new ByteArrayOutputStream();
        private HttpServletResponse m_response;
        private PrintWriter m_printWriter;

        public ResourceCacheResponse(HttpServletResponse response)
        {
            super(response);
            m_response = response;
        }

        @Override
        public PrintWriter getWriter() throws IOException
        {
            m_printWriter = new PrintWriter(new OutputStreamWriter(m_outputStream, m_response.getCharacterEncoding()));
            return m_printWriter;
        }

        public byte[] getBuffer()
        {
            if (m_printWriter != null)
            {
                m_printWriter.close();
            }
            return m_outputStream.toByteArray();
        }
    }

}

