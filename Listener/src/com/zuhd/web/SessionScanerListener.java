package com.zuhd.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.*;

/**
 * Created by dell013 on 2017/12/13.
 */
public class SessionScanerListener implements HttpSessionListener, ServletContextListener
{
    private List<HttpSession> m_listSession = new LinkedList<HttpSession>();
    private Object m_lock = new Object();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent)
    {
        System.out.println("session has been created");
        HttpSession session = httpSessionEvent.getSession();

        synchronized (m_lock)
        {
            m_listSession.add(session);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent)
    {
        System.out.println("session has been destroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent)
    {
        System.out.println("context initialized");
        Timer timer = new Timer();
        timer.schedule(new MyTask(m_listSession, m_lock), 0, 10 * 1000);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent)
    {
        System.out.println("context destroyed");
    }

    class MyTask extends TimerTask
    {
        private List<HttpSession> m_listSession;
        private Object m_lock;

        public MyTask(List<HttpSession> listSession, Object lock)
        {
            m_listSession = listSession;
            m_lock = lock;
        }


        @Override
        public void run()
        {
            synchronized (m_lock)
            {
                ListIterator<HttpSession> it = m_listSession.listIterator();
                long nowTime = System.currentTimeMillis();

                while (it.hasNext())
                {
                    HttpSession session = it.next();
                    if (nowTime - session.getLastAccessedTime() > 1000 * 30)
                    {
                        session.invalidate();
                        it.remove();
                    }
                }
            }

        }
    }
}
