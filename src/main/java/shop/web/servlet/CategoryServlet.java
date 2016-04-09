package shop.web.servlet;

import jdk.nashorn.internal.ir.ReturnNode;
import shop.dao.ICategoryDao;
import shop.enums.Role;
import shop.model.Category;
import shop.util.RequestUtil;
import shop.util.ShopDi;
import shop.util.ShopException;
import shop.web.annotation.AddFiled;
import shop.web.annotation.Auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/4/8.
 */
public class CategoryServlet extends BaseServlet {
    private ICategoryDao cDao;
    private String pagePath = "/WEB-INF/category/";

    @ShopDi("categoryDao")
    public void setcDao(ICategoryDao cDao) {
        this.cDao = cDao;
    }

    // 默认全部用户都可以访问，所有没有Auth Annotation
    public String list(HttpServletRequest req, HttpServletResponse resp) {
        List<Category> cLists = cDao.loadLists();
        req.setAttribute("cLists", cLists);
        return pagePath + "list.jsp";
    }

    @Auth(Role.ADMIN)
    public String add(HttpServletRequest req, HttpServletResponse resp) {
        return pagePath + "addInput.jsp";
    }

    @Auth(Role.ADMIN)
    public String update(HttpServletRequest req, HttpServletResponse resp) {
        return pagePath + "updateInput.jsp";
    }

    @Auth(Role.ADMIN)
    public String delete(HttpServletRequest req, HttpServletResponse resp) {
        return pagePath + "deleteInput.jsp";
    }

    @Auth(Role.ADMIN)
    public String addInput(HttpServletRequest req, HttpServletResponse resp) {
        String   type        = req.getParameter("type");
        Category newCategory = (Category) RequestUtil.setFileds(Category.class, req, AddFiled.class, "add");
        String   oldName     = req.getParameter("oldname");
        Category oldCategory = null;
        try {
            if (oldName.equals("所有")) {
                oldCategory = new Category("所有");
            } else {
                oldCategory = cDao.loadByName(oldName);
            }
        } catch (Exception e) {
            getErrMap().put("oldname", oldName + " 分类不存在");
        }
        String   oldName2     = "";
        Category oldCategory2 = null;
        if (type.equals("parent")) {
            oldName2 = req.getParameter("oldname2");
            try {
                oldCategory2 = cDao.loadByName(oldName2);
            } catch (Exception e) {
                getErrMap().put("oldname2", oldName2 + " 分类不存在");
            }
        }
        if (errMapEmpty()) {
            try {
                cDao.addNode(type, newCategory, oldCategory, oldCategory2);
                return getRedirectTo() + "category.do?method=list";
            } catch (ShopException e) {
                getErrMap().put("name", e.getMessage());
            }
        }
        return pagePath + "addInput.jsp";
    }

    @Auth(Role.ADMIN)
    public String deleteInput(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Category c = cDao.loadByName(req.getParameter("name"));
            cDao.delete(c.getId());
            return getRedirectTo() + "category.do?method=list";
        } catch (ShopException e) {
            getErrMap().put("name", e.getMessage());
        }
        return pagePath + "deleteInput.jsp";
    }

    @Auth(Role.ADMIN)
    public String updateInput(HttpServletRequest req, HttpServletResponse resp) {
        Category newCategory = (Category) RequestUtil.setFileds(Category.class, req, AddFiled.class, "add");
        String   oldName     = req.getParameter("oldname");
        Category oldCategory = null;
        try {
            oldCategory = cDao.loadByName(oldName);
        } catch (Exception e) {
            getErrMap().put("oldname", oldName + " 分类不存在");
        }
        Category c = null;
        try {
            c = cDao.loadByName(newCategory.getName());
        } catch (ShopException e) {
        }
        if (c != null) {
            getErrMap().put("name", newCategory.getName() + "分类已经存在");
        }

        if (errMapEmpty()) {
            newCategory.setId(oldCategory.getId());
            cDao.update(newCategory);
            return getRedirectTo() + "category.do?method=list";
        }
        return pagePath + "updateInput.jsp";
    }

}
