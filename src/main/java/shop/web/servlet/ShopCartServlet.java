package shop.web.servlet;

import shop.dao.IProductDao;
import shop.model.Product;
import shop.model.ShopCart;
import shop.util.ShopDi;
import shop.util.ShopException;
import shop.web.annotation.Auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Amysue on 2016/4/12.
 */
public class ShopCartServlet extends BaseServlet{
    private IProductDao pDao;

    @ShopDi("productDao")
    public void setpDao(IProductDao pDao) {
        this.pDao = pDao;
    }

    public String addToCart(HttpServletRequest req, HttpServletResponse resp) {
        ShopCart sc = (ShopCart) req.getSession().getAttribute("shopcart");
        if (sc == null) {
            sc = new ShopCart();
            req.getSession().setAttribute("shopcart", sc);
        }
        int id = getId(req, "pid");
        try {
            Product p = pDao.load(id);
            sc.add(p);
            String method = req.getParameter("remethod");
            String toPage = req.getParameter("toPage");
            return getRedirectTo() + "/product.do?method=" + method + "&pid=" + id + "&toPage=" + toPage;
        } catch (ShopException e) {
            logger.debug("不存在的商品id" + id);
            return handleException("不存在的商品id" + id, req);
        }
    }
}
