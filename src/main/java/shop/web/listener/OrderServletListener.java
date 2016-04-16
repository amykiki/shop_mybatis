package shop.web.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Amysue on 2016/4/16.
 */
public class OrderServletListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;
    private Logger logger;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger = LogManager.getLogger(this.getClass());
        logger.debug("===============OrderServletListerner starting====================");
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new checkOrder(), 0, 5, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("===============OrderServletListerner Destroy====================");
        scheduler.shutdown();

    }
}
