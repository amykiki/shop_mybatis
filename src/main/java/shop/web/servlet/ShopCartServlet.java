package shop.web.servlet;

import shop.dao.IProductDao;
import shop.model.Product;
import shop.model.ShopCart;
import shop.util.ShopDi;
import shop.util.ShopException;
import shop.web.annotation.Auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
        String errMsg = "";
        if (sc == null) {
            sc = new ShopCart();
            req.getSession().setAttribute("shopcart", sc);
        }
        int id = getId(req, "pid");
        Product p = null;
        try {
            p = pDao.load(id);
        } catch (ShopException e) {
            logger.debug("不存在的商品id" + id);
            return handleException("不存在的商品id" + id, req);
        }
        try {
            sc.add(p);
        } catch (ShopException e) {
            try {
                errMsg = "&errMsg=" + URLEncoder.encode(e.getMessage(), "UTF-8") ;
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
        String method = req.getParameter("remethod");
        String toPage = req.getParameter("toPage");
        return getRedirectTo() + "/product.do?method=" + method + "&pid=" + id + "&toPage=" + toPage + errMsg;
    }
}
