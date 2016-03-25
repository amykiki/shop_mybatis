package shop.web.servlet;

import shop.dao.IUserDao;
import shop.model.Pager;
import shop.model.Role;
import shop.model.User;
import shop.util.ShopDi;
import shop.util.ShopException;
import shop.web.annotation.Auth;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amysue on 2016/3/23.
 */
public class UserServlet extends BaseServlet {
    private IUserDao udao;
    private int pageLimit;
    private int pageShow;

    @ShopDi("userDao")
    public void setUdao(IUserDao udao) {
        this.udao = udao;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("This is UserServlet init");
        String limit = config.getInitParameter("pageLimit");
        pageLimit = Integer.parseInt(limit);
        pageShow = Integer.parseInt(config.getInitParameter("pageShow"));
    }

    public String login(HttpServletRequest req, HttpServletResponse resp) {
        User curUser = (User) req.getSession().getAttribute("lguser");
        if (curUser.getRole() != Role.ANON) {
            return getRedirectTo() + "/user.do?method=list";
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
        return getRedirectTo() + "/user.do?method=list";

    }

    public String loginInput(HttpServletRequest req, HttpServletResponse resp) {
        User curUser = (User) req.getSession().getAttribute("lguser");
        if (curUser.getRole() != Role.ANON) {
            return getRedirectTo() + "/user.do?method=list";
        }
        return "WEB-INF/user/login.jsp";
    }

    @Auth(Role.NORMAL)
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        return getRedirectTo() + "/user.do?method=loginInput";
    }

    @Auth(Role.ADMIN)
    public String list(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageLimit", pageLimit);
        params.put("pageShow", pageShow);
        int toPage = 1;
        try {
            toPage = Integer.parseInt(req.getParameter("toPage"));
        } catch (NumberFormatException e) {
            toPage = 1;
        }
        params.put("toPage", toPage);

        try {
            Role role = Role.valueOf(req.getParameter("role"));
            params.put("role", role);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        if (req.getParameter("username") != null) {
            params.put("username", req.getParameter("username"));
        }

        if (req.getParameter("nickname") != null) {
            params.put("nickname", req.getParameter("nickname"));
        }
        req.setAttribute("cuser", params);
        Pager<User> pLists = udao.find(params);
        req.setAttribute("pLists", pLists);
        return "/WEB-INF/user/list.jsp";

    }
}
