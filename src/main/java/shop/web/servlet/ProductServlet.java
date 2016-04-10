package shop.web.servlet;

import org.apache.commons.fileupload.FileItem;
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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                File f = new File(getUploadPath(req, "imgdir", p.getImg()));
                f.delete();
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
}
