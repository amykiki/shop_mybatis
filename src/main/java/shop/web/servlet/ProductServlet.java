package shop.web.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import shop.dao.ICategoryDao;
import shop.dao.IProductDao;
import shop.enums.PStatus;
import shop.enums.Role;
import shop.model.Category;
import shop.model.Pager;
import shop.model.Product;
import shop.util.RequestUtil;
import shop.util.ShopDi;
import shop.util.ShopException;
import shop.web.annotation.AddFiled;
import shop.web.annotation.Auth;
import shop.web.annotation.UpdateFiled;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

/**
 * Created by Amysue on 2016/4/9.
 */
public class ProductServlet extends BaseServlet {
    private IProductDao  pDao;
    private ICategoryDao cDao;
    private String pagePath = "/WEB-INF/product/";
    private int pageLimit;
    private int pageShow;

    @ShopDi("productDao")
    public void setpDao(IProductDao pDao) {
        this.pDao = pDao;
    }

    @ShopDi("categoryDao")
    public void setcDao(ICategoryDao cDao) {
        this.cDao = cDao;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.debug("This is ProductServlet init");
        String limit = config.getInitParameter("pageLimit");
        pageLimit = Integer.parseInt(limit);
        pageShow = Integer.parseInt(config.getInitParameter("pageShow"));
    }

    public String list(HttpServletRequest req, HttpServletResponse resp) {
        List<Category> cLists = cDao.loadLists();
        req.setAttribute("cLists", cLists);
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

        List<Integer> cids = getSelected(req, "cids", -1);
        if (cids.size() > 0) {
            params.put("cids", cids);
            req.setAttribute("selectcids", cids);
            Map<Integer, Integer> cidsMap = new HashMap<>();
            for (int cid : cids) {
                cidsMap.put(cid, 1);
            }
            for (Category c : cLists) {
                if (cidsMap.containsKey(c.getId())) {
                    c.setChecked(1);
                }
            }

        }
        if (req.getParameter("name") != null && !req.getParameter("name").equals("")) {
            params.put("name", req.getParameter("name"));
        }
        if (getLgUser(req).getRole() == Role.ADMIN) {
            PStatus ps;
            try {
                ps = PStatus.valueOf(req.getParameter("status"));
                if (ps != PStatus.All) {
                    params.put("status", ps);
                }
            } catch (IllegalArgumentException | NullPointerException e) {
//                e.printStackTrace();
            }
        } else {
            params.put("status", PStatus.InSale);
        }
        try {
            int price1 = Integer.parseInt(req.getParameter("price1"));
            int price2 = Integer.parseInt(req.getParameter("price2"));
            if (price2 < price1) {
                int i = price2;
                price2 = price1;
                price1 = i;
            }
            params.put("price1", price1);
            params.put("price2", price2);
        } catch (NumberFormatException e) {
//            e.printStackTrace();
        }
        Pager<Product> pLists = pDao.find(params);
        req.setAttribute("pLists", pLists);

        return pagePath + "list.jsp";
    }

    @Auth(Role.ADMIN)
    public String add(HttpServletRequest req, HttpServletResponse resp) {
        List<Category> cLists = cDao.loadLists();
        req.setAttribute("cLists", cLists);
        return pagePath + "addInput.jsp";
    }

    @Auth(Role.ADMIN)
    public String addInput(HttpServletRequest req, HttpServletResponse resp) {
        Product  p   = (Product) RequestUtil.setFileds(Product.class, req, AddFiled.class, "add");
        int      cid = 0;
        Category c   = null;
        try {
            cid = Integer.parseInt(req.getParameter("cid"));
            c = cDao.load(cid);
        } catch (NumberFormatException e) {
            logger.debug("必须输入分类");
            getErrMap().put("cid", "必须输入分类");
        } catch (ShopException e) {
            logger.debug(cid + "分类不存在");
            getErrMap().put("cid", cid + "分类不存在");
        }
        FileItem item = (FileItem) req.getAttribute("fileItem-img");
        logger.debug("item is memory-------" + item.isInMemory());
        if (errMapEmpty() && p != null) {
            p.setCategory(c);
            p.setSales(0);
            p.setStatus(PStatus.InSale);
            try {
                uploadImage(item, p.getImg(), "img", req);
                pDao.add(p);
                return getRedirectTo() + "/product.do?method=list";
            } catch (ShopException e) {
                logger.debug(e.getMessage());
                getErrMap().put("img", e.getMessage());
            } finally {
                item.delete();
                req.removeAttribute("fileItem-img");
            }
        } else {
            item.delete();
            req.removeAttribute("fileItem-img");
        }
        return add(req, resp);
    }
    @Auth(Role.ADMIN)
    public String updateInput(HttpServletRequest req, HttpServletResponse resp) {
        int id = getId(req, "pid");
        if (id <= 0) {
            getErrMap().put("errMsg", "商品id不存在");
            return getRedirectTo() + "/product.do?method=list&toPage=" + req.getParameter("toPage");
        }
        Product op = null;
        try {
            op = pDao.load(id);
        } catch (ShopException e) {
            getErrMap().put("errMsg", "商品id不存在");
            return getRedirectTo() + "/product.do?method=list&toPage=" + req.getParameter("toPage");
        }

        Product cp = (Product) RequestUtil.setFileds(Product.class, req, UpdateFiled.class, "update");
        if (!getErrMap().containsKey("img") && cp.getImg() != null) {
            logger.debug("上传新产品图片");
            FileItem item = (FileItem) req.getAttribute("fileItem-img");
            logger.debug("item is memory-------" + item.isInMemory());
            try {
                uploadImage(item, cp.getImg(), "img", req);
                File oldPic = new File(getUploadPath(req, "imgdir", op.getImg()));
                oldPic.delete();
                logger.debug("删除" + op.getImg());
                logger.debug("替换原有的图片路径");
                op.setImg(cp.getImg());
                pDao.update(op);
            } catch (ShopException e) {
                logger.debug(e.getMessage());
                getErrMap().put("img", e.getMessage());
            } finally {
                item.delete();
                req.removeAttribute("fileItem-img");
            }
        } else {
            cp.setImg(op.getImg());
            if (getErrMap().get("img").startsWith("没有上传的文件")) {
                getErrMap().remove("img");
            }
        }

        if (getErrMap().containsKey("price")) {
            cp.setPrice(op.getPrice());
        }
        if (getErrMap().containsKey("stock")) {
            cp.setStock(op.getStock());
        }
        logger.debug("============"+ cp.getStatus().toString());
        int cid = getId(req, "cid");
        if (cid == op.getCategory().getId()) {
            cp.setCategory(op.getCategory());
        } else {
            try {
                Category c = cDao.load(cid);
                cp.setCategory(c);
            } catch (ShopException e) {
                getErrMap().put("cid", "请选择商品类别");
            }
        }

        if (errMapEmpty()) {
            cp.setId(op.getId());
            pDao.update(cp);
            return getRedirectTo() + "/product.do?method=list&toPage=" + req.getParameter("toPage");
        }

        req.setAttribute("cp", cp);
        List<Category> cLists = cDao.loadLists();
        req.setAttribute("cLists", cLists);
        return pagePath + "updateInput.jsp";
    }
    @Auth(Role.ADMIN)
    public String update(HttpServletRequest req, HttpServletResponse resp) {
        int id = getId(req, "pid");
        if (id <= 0) {
            getErrMap().put("errMsg", "商品id不存在");
            return getRedirectTo() + "/product.do?method=list&toPage=" + req.getParameter("toPage");
        }
        Product cp = null;
        try {
            cp = pDao.load(id);
        } catch (ShopException e) {
            getErrMap().put("errMsg", "商品id不存在");
            return getRedirectTo() + "/product.do?method=list&toPage=" + req.getParameter("toPage");
        }
        req.setAttribute("cp", cp);
        List<Category> cLists = cDao.loadLists();
        req.setAttribute("cLists", cLists);
        return pagePath + "updateInput.jsp";
    }
    @Auth(Role.ADMIN)
    public String updateName(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("method", "updateNameInput");
        req.setAttribute("field", "name");
        return pagePath + "updateFieldInput.jsp";
    }

    @Auth(Role.ADMIN)
    public String updateNameInput(HttpServletRequest req, HttpServletResponse resp) {
        int     id = getId(req, "pid");
        Product p  = (Product) RequestUtil.setFileds(Product.class, req, UpdateFiled.class, "update");
        if (errMapEmpty() && p != null && id > 0) {
            p.setId(id);
            pDao.setName(p);
            return getRedirectTo() + "/product.do?method=list&toPage=" + req.getParameter("toPage");
        }
        return updateName(req, resp);
    }

    @Auth(Role.ADMIN)
    public String updateStock(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("method", "updateStockInput");
        req.setAttribute("field", "stock");
        return pagePath + "updateFieldInput.jsp";
    }

    @Auth(Role.ADMIN)
    public String updateStockInput(HttpServletRequest req, HttpServletResponse resp) {
        int     id     = getId(req, "pid");
        int     stock  = 0;
        boolean update = false;
        try {
            stock = Integer.parseInt(req.getParameter("stock"));
        } catch (NumberFormatException e) {
            getErrMap().put("stock", "库存必须为正整数");
        }
        if (stock < 0) {
            getErrMap().put("stock", "库存必须为正整数");
        }
        if (errMapEmpty() && id > 0) {
            String type = req.getParameter("type");
            if (type.equals("add")) {
                pDao.addStock(id, stock);
                update = true;
            } else if (type.equals("reduce")) {
                try {
                    pDao.reduceStock(id, stock);
                    update = true;
                } catch (ShopException e) {
                    e.printStackTrace();
                    getErrMap().put("stock", e.getMessage());
                }
            }
        }
        if (update) {
            return getRedirectTo() + "/product.do?method=list&toPage=" + req.getParameter("toPage");
        } else {
            return updateStock(req, resp);
        }
    }

    @Auth(Role.ADMIN)
    public String updateStatus(HttpServletRequest req, HttpServletResponse resp) {
        int     id     = getId(req, "pid");
        PStatus status = null;
        try {
            status = PStatus.valueOf(req.getParameter("type"));
            if (id > 0) {
                Product p = new Product();
                p.setId(id);
                p.setStatus(status);
                pDao.setStatus(p);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return getRedirectTo() + "/product.do?method=list&toPage=" + req.getParameter("toPage");
    }

}


