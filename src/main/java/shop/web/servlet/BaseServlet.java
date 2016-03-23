package shop.web.servlet;

import shop.dao.DaoFactory;
import shop.model.Role;
import shop.model.User;
import shop.web.annotation.Auth;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Amysue on 2016/3/23.
 */
public class BaseServlet extends HttpServlet {
    private final String redirectTo = "redirect:";
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("lguser") == null) {
            User u = new User();
            u.setRole(Role.ANON);
            req.getSession().setAttribute("lguser", u);
        }
        DaoFactory.setDao(this);
        String methodName = req.getParameter("method");
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            if (method.isAnnotationPresent(Auth.class)) {
                int rc = checkAuth(req, method.getAnnotation(Auth.class));
                if (rc == 1) {
                    req.setAttribute("errMsg", "没有权限进行操作");
                    req.getRequestDispatcher("/WEB-INF/util/error.jsp").forward(req, resp);
                    return;
                }
                if (rc == 2) {
                    resp.sendRedirect("/WEB-INF/user/login.jsp");
                    return;
                }
            }
            String nextPage = (String) method.invoke(this, req, resp);
            if (nextPage.startsWith(redirectTo)) {
                resp.sendRedirect(nextPage.substring(redirectTo.length()));
                return;
            } else {
                req.getRequestDispatcher(nextPage).forward(req, resp);
            }

        } catch (NoSuchMethodException e) {
            req.setAttribute("errMsg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/util/error.jsp").forward(req, resp);
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * return 0: auth check pass
     * return 1: user do not have the auth to continue the next method, return to error.jsp
     * return 2: user not login, need return to login page
     */
    public String getRedirectTo() {
        return redirectTo;
    }

    private int checkAuth(HttpServletRequest req, Auth auth) {
        User u = (User)req.getSession().getAttribute("lguser");
        if (auth.value() == Role.ANON) {
            return 0;
        }
        if (u.getRole() == Role.ANON) {
            return 2;
        }
        if (auth.equalID()) {
            int id = -1;
            if (req.getParameter("userid") != null) {
                id = Integer.parseInt(req.getParameter("userid"));
                if (u.getId() != id) {
                    return 1;
                }
            }
        }
        if (u.getRole().getCode() > auth.value().getCode()) {
            return 1;
        }
        return 0;

    }
}
