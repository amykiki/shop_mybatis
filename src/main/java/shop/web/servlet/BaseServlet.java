package shop.web.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shop.dao.DaoFactory;
import shop.dao.IUserDao;
import shop.enums.EqualID;
import shop.enums.Role;
import shop.model.User;
import shop.util.ShopException;
import shop.web.annotation.Auth;
import shop.web.filter.MultiPartWrapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/3/23.
 */
public class BaseServlet extends HttpServlet {
    private final String              redirectTo = "redirect:";
    private       Map<String, String> errMap     = new HashMap<>();
    protected Logger logger;

    public BaseServlet() {
        logger = LogManager.getLogger(this);
        logger.debug(this.getClass().getName() + " is starting.....==============");
        DaoFactory.setDao(this);
        logger.debug(this.getClass().getName() + " complete dao injectiong=========");
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("lguser") == null) {
            User u = new User();
            u.setRole(Role.ANON);
            req.getSession().setAttribute("lguser", u);
        }
        errMap.clear();
        req.setAttribute("errMap", errMap);
        if (ServletFileUpload.isMultipartContent(req)) {
            try {
                req = new MultiPartWrapper(req);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
        String methodName = req.getParameter("method");
        logger.debug(req.getRequestURL().append('?').append(getQuery(req)));
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            if (method.isAnnotationPresent(Auth.class)) {
                int rc = checkAuth(req, method.getAnnotation(Auth.class));
                if (rc == 1) {
                    if (req.getAttribute("errMsg") == null) {
                        req.setAttribute("errMsg", "没有权限进行操作");
                    }
                    req.getRequestDispatcher("/WEB-INF/util/error.jsp").forward(req, resp);
                    return;
                }
                if (rc == 2) {
                    resp.sendRedirect("/user.do?method=loginInput");
                    return;
                }
                if (rc == 3) {
                    resp.sendRedirect("/user.do?method=list");
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
     * return 3: user has logined, do not access these pages;
     */
    public String getRedirectTo() {
        return redirectTo;
    }

    private int checkAuth(HttpServletRequest req, Auth auth) {
        User u = (User) req.getSession().getAttribute("lguser");

        if (auth.value() == Role.ANON) {
            if (u.getRole() == Role.ANON) {
                return 0;
            } else {
                return 3;
            }
        }

        if (auth.value() == Role.NORMAL) {
            if (u.getRole() == Role.ANON) {
                return 2;
            }
        } else if (auth.value() == Role.ADMIN) {
            if (u.getRole() != Role.ADMIN) {
                return 1;
            }
        }

        int id = -1;
        if (auth.equalID() != EqualID.ALL) {
            if (req.getParameter("userid") != null) {
                try {
                    id = Integer.parseInt(req.getParameter("userid"));
                } catch (NumberFormatException e) {
                    req.setAttribute("errMsg", "id格式不正确");
                    return 1;
                }
            }
            if (auth.equalID() == EqualID.EQUAL) {
                if (u.getId() != id) {
                    return 1;
                }
            } else if (auth.equalID() == EqualID.NOEQUAL) {
                if (u.getId() == id) {
                    return 1;
                }
            }
        }
        return 0;

    }

    protected List<Integer> getSelected(HttpServletRequest req, String idName, int excludeId) {
        String[]      idstrs = req.getParameterValues(idName);
        List<Integer> list   = new ArrayList<>();
        if (idstrs != null) {
            for (String idstr : idstrs) {
                try {
                    int id = Integer.parseInt(idstr);
                    if (id != excludeId) {
                        list.add(id);
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
        return list;
    }

    protected int getUserId(HttpServletRequest req) {
        try {
            int id = Integer.parseInt(req.getParameter("userid"));
            return id;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    protected User getLgUser(HttpServletRequest req) {
        User u = (User) req.getSession().getAttribute("lguser");
        return u;
    }

    protected User checkSelf(HttpServletRequest req, boolean addr, IUserDao udao) {
        int  id = getUserId(req);
        User u  = (User) req.getSession().getAttribute("lguser");
        if (id == u.getId() || u.getRole() == Role.ADMIN) {
            User cu = null;
            try {
                cu = udao.load(id, addr);
                return cu;
            } catch (ShopException e) {
                logger.debug("用户id为" + id + "的用户不存在");
            }
        }
        return null;

    }

    protected int getId(HttpServletRequest req, String idName) {
        try {
            int id = Integer.parseInt(req.getParameter(idName));
            return id;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String getQuery(HttpServletRequest req) {
        String                query  = "";
        Map<String, String[]> params = req.getParameterMap();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            for (String v : values) {
                query += key + "=" + v + "&";
            }
        }
        query = query.substring(0, query.length() - 1);
        return query;
    }

    protected Map<String, String> getErrMap() {
        return errMap;
    }

    protected boolean errMapEmpty() {
        return errMap.isEmpty();
    }

    protected String uploadImage(FileItem item, String fileName, String fieldName, HttpServletRequest req) throws ShopException {
        String filePath = getUploadPath(req, "imgdir", fileName);
//        String filePath = fileName;
        File   storeFile = new File(filePath);
        try {
            item.write(storeFile);
        } catch (Exception e) {
            e.printStackTrace();
            errMap.put(fieldName, e.getMessage());
            logger.debug(fieldName, e.getMessage());
            storeFile.delete();
            throw new ShopException("上传" + fileName + "失败");
        }
        return filePath;
    }

    protected String getUploadPath(HttpServletRequest req, String pathName, String fileName) {
        ServletContext context    = req.getServletContext();
        String         imgDir     = context.getInitParameter(pathName);
        String         uploadPath = context.getRealPath("") + File.separator + imgDir;
        uploadPath = uploadPath.replace("target\\mybatis-shop-web", "src\\main\\webapp");
        logger.debug("uploadPath=" + uploadPath);
        String filePath = fileName.substring(fileName.lastIndexOf("/") + 1);
        filePath  = uploadPath + File.separator + filePath;
        logger.debug("filePath=" + filePath);
        return filePath;
    }

    protected String handleException(String errMsg, HttpServletRequest req) {
        req.setAttribute("errMsg", errMsg);
        return "/WEB-INF/util/error.jsp";
    }
}
