package shop.web.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.net.Facility;
import shop.model.Role;
import shop.model.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Amysue on 2016/3/23.
 */
//@WebListener
public class sessionListen implements HttpSessionListener {
    private static Logger logger = LogManager.getLogger(sessionListen.class);
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        User u = new User();
        u.setId(-1);
        u.setRole(Role.ANON);
        se.getSession().setAttribute("lguser", u);
        logger.info("New Session Start " + se.getSession().getId());

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("Session " + se.getSession().getId() + " is Destroyed");
    }
}
