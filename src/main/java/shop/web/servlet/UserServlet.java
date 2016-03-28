package shop.web.servlet;

import shop.dao.IUserDao;
import shop.model.Pager;
import shop.model.Role;
import shop.model.User;
import shop.util.RequestUtil;
import shop.util.ShopDi;
import shop.util.ShopException;
import shop.web.annotation.Auth;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/3/23.
 */
public class UserServlet extends BaseServlet {
    private IUserDao udao;
    private int      pageLimit;
    private int      pageShow;

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

    @Auth(value = Role.ANON)
    public String loginInput(HttpServletRequest req, HttpServletResponse resp) {
        User curUser = (User) req.getSession().getAttribute("lguser");
        if (curUser.getRole() != Role.ANON) {
            return getRedirectTo() + "/user.do?method=list";
        }
        return "WEB-INF/user/login.jsp";
    }

    @Auth(value = Role.ANON)
    public String addInput(HttpServletRequest req, HttpServletResponse resp) {
        User                u       = (User) RequestUtil.setFileds(User.class, req);
        Map<String, String> errMap  = (Map<String, String>) req.getAttribute("errMap");
        boolean             addFail = false;
        if (errMap.isEmpty() && u.getUsername() != null) {
            try {
                udao.add(u);
            } catch (ShopException e) {
                errMap.put("username", e.getMessage());
                addFail = true;
            }
        } else {
            logger.debug("对象转换失败");
            addFail = true;
        }
        if (addFail == true) {
            req.setAttribute("cuser", u);
            return "/WEB-INF/user/addInput.jsp";
        }
        return "WEB-INF/user/login.jsp";
    }

    @Auth(value = Role.ANON)
    public String add(HttpServletRequest req, HttpServletResponse resp) {
        return "/WEB-INF/user/addInput.jsp";
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

    @Auth(value = Role.ADMIN)
    public String delete(HttpServletRequest req, HttpServletResponse resp) {
        List<Integer> list = getSelected(req);
        try {
            udao.deleteLists(list);
        } catch (ShopException e) {
            req.setAttribute("errMsg", "删除失败,需要先删除用户的所有地址");
        }
        return "/user.do?method=list";
    }

    @Auth(value = Role.NORMAL)
    public String updateUser(HttpServletRequest req, HttpServletResponse resp) {
        User cu = checkSelf(req, false);
        if (cu == null) {
            req.setAttribute("errMsg", "没有权限进行操作");
            return "/WEB-INF/util/error.jsp";
        }
        req.setAttribute("cuser", cu);
        return "/WEB-INF/user/updateInput.jsp";
    }

    @Auth(value = Role.NORMAL)
    public String updateUserInput(HttpServletRequest req, HttpServletResponse resp) {
        User cu = checkSelf(req, false);
        if (cu == null) {
            req.setAttribute("errMsg", "没有权限进行修改");
            return "/WEB-INF/util/error.jsp";
        }
        User u  = (User) RequestUtil.setFileds(User.class, req);
        u.setId(cu.getId());
        u.setUsername(cu.getUsername());
        Map<String, String> errMap = (Map<String, String>) req.getAttribute("errMap");
        if (errMap.isEmpty() && u.getNickname() != null) {
            udao.update(u);
        } else {
            logger.debug("对象转换失败");
        }
        req.setAttribute("cuser", u);
        req.setAttribute("olduser", cu);
        return "/WEB-INF/user/updateInput.jsp";
    }


    @Auth(value = Role.ADMIN)
    public String changeAuth(HttpServletRequest req, HttpServletResponse resp) {
        List<Integer> list    = getSelected(req);
        String        rolestr = req.getParameter("roles");

        try {
            Role role = Role.valueOf(rolestr);
            udao.updateAuth(role, list);
        } catch (Exception e) {
            req.setAttribute("errMsg", "修改用户权限失败");
        }
        return "/user.do?method=list";
    }

    private List<Integer> getSelected(HttpServletRequest req) {
        String[]      idstrs = req.getParameterValues("userids");
        List<Integer> list   = new ArrayList<>();
        User          u      = (User) req.getSession().getAttribute("lguser");
        if (idstrs != null) {
            for (String idstr : idstrs) {
                try {
                    int id = Integer.parseInt(idstr);
                    if (id != u.getId()) {
                        list.add(id);
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
        return list;
    }

    @Auth(value = Role.NORMAL)
    public String show(HttpServletRequest req, HttpServletResponse resp) {
        User cu = checkSelf(req, true);
        if (cu != null) {
            req.setAttribute("cuser", cu);
            return "/WEB-INF/user/show.jsp";
        } else {
            req.setAttribute("errMsg", "用户id不正确");
            return "/WEB-INF/util/error.jsp";
        }
    }

    private User checkSelf(HttpServletRequest req, boolean addr) {
        int  id = getId(req);
        User u  = (User) req.getSession().getAttribute("lguser");
        if (id == u.getId() || u.getRole() == Role.ADMIN) {
            User cu = udao.load(id, addr);
            if (cu != null) {
                return cu;
            }
        }
        return null;

    }

    private int getId(HttpServletRequest req) {
        try {
            int id = Integer.parseInt(req.getParameter("userid"));
            return id;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
