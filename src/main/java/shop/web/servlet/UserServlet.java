package shop.web.servlet;

import shop.dao.IUserDao;
import shop.model.Role;
import shop.model.User;
import shop.util.ShopDi;
import shop.util.ShopException;
import shop.web.annotation.Auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amysue on 2016/3/23.
 */
public class UserServlet extends BaseServlet {
    private IUserDao udao;

    @ShopDi("userDao")
    public void setUdao(IUserDao udao) {
        this.udao = udao;
    }

    public String login(HttpServletRequest req, HttpServletResponse resp) {
        User curUser = (User) req.getSession().getAttribute("lguser");
        if (curUser.getRole() != Role.ANON) {
            return "/WEB-INF/user/list.jsp";
        }
        String              username = req.getParameter("username");
        String              password = req.getParameter("password");
        Map<String, String> errMap   = new HashMap<>();
        try {
            User u = udao.login(username, password);
            req.getSession().setAttribute("lguser", u);
        } catch (ShopException e) {
            if (e.getMessage().equals("密码不正确")) {
                errMap.put("password", e.getMessage());
                req.setAttribute("username", username);
            } else {
                errMap.put("username", e.getMessage());
            }
            req.setAttribute("errMap", errMap);
            return "/WEB-INF/user/login.jsp";
        }
        return "WEB-INF/user/list.jsp";

    }

    public String loginInput(HttpServletRequest req, HttpServletResponse resp) {
        User curUser = (User) req.getSession().getAttribute("lguser");
        if (curUser.getRole() != Role.ANON) {
            return "/WEB-INF/user/list.jsp";
        }
        return "WEB-INF/user/login.jsp";
    }

    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        return getRedirectTo() + "/user.do?method=loginInput";
    }


}
