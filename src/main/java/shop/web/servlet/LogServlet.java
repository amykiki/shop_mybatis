package shop.web.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Amysue on 2016/3/21.
 */
@WebServlet(name="logServlet", urlPatterns = {"/logTest"})
public class LogServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("method", req.getParameter("method"));
        req.setAttribute("log", "debug");
        Logger logger = LogManager.getLogger();
        logger.info("log4j test");
        logger.error("error");
        logger.debug("debug");
        logger.info(req.getSession(false).getId());
        logger.info(req.getSession(false).getId());
        logger.info(req.getSession(false).getId());
//        logger.info(req.getSession(false).getAttribute("lguser"));
//        req.getSession().invalidate();
        req.getRequestDispatcher("01.jsp").forward(req, resp);
    }
}
