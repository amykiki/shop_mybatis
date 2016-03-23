package shop.web.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by Amysue on 2016/3/23.
 */
//@WebListener
public class AppListen implements ServletContextListener {
    private static Logger logger = LogManager.getLogger(AppListen.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("===============APP START===========");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("===============APP SHUT DOWN===========");
    }
}
