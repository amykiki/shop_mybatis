package shop.web.servlet;

import shop.dao.ICategoryDao;
import shop.dao.IProductDao;
import shop.enums.PStatus;
import shop.enums.Role;
import shop.model.Category;
import shop.model.Pager;
import shop.model.Product;
import shop.util.ShopDi;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/4/9.
 */
public class ProductServlet extends BaseServlet {
    private IProductDao pDao;
    private ICategoryDao cDao;
    private String pagePath = "/WEB-INF/product/";
    private int      pageLimit;
    private int      pageShow;

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
                cidsMap.put(cid , 1);
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
}
