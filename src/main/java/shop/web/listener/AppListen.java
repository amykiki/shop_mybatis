package shop.web.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Amysue on 2016/3/23.
 */
//@WebListener
public class AppListen implements ServletContextListener {
    private static Logger logger = LogManager.getLogger(AppListen.class);
    private ServletContext context = null;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("===============APP START===========");
        context = sce.getServletContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("===============APP SHUT DOWN===========");
    }
}
