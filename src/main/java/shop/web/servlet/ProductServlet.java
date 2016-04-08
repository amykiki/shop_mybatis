package shop.web.servlet;

import shop.dao.ICategoryDao;
import shop.dao.IProductDao;
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

        Pager<Product> pLists = pDao.find(params);
        req.setAttribute("pLists", pLists);
        List<Category> cLists = cDao.loadLists();
        req.setAttribute("cLists", cLists);
        return pagePath + "list.jsp";
    }
}
