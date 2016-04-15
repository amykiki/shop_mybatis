package shop.web.servlet;

import shop.dao.IProductDao;
import shop.model.CartProduct;
import shop.model.Product;
import shop.model.ShopCart;
import shop.util.ShopDi;
import shop.util.ShopException;
import shop.web.annotation.Auth;

import javax.lang.model.element.TypeElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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
        ShopCart sc = getShopCart(req);
        String errMsg = "";
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

    public String list(HttpServletRequest req, HttpServletResponse resp) {
        ShopCart sc = getShopCart(req);
        LinkedHashMap<Integer, CartProduct> cps = sc.getCartProducts();
        for (Map.Entry<Integer, CartProduct> item : cps.entrySet()) {
            int pid = item.getKey();
            try {
                Product p = pDao.load(pid);
                item.getValue().setProduct(p);
                item.getValue().setPrice(p.getPrice());
            } catch (ShopException e) {
                e.printStackTrace();
            }
        }
        req.setAttribute("cartProductMap", cps);

        return "/WEB-INF/shopcart/list.jsp";
    }

    public String update(HttpServletRequest req, HttpServletResponse resp) {
        int pid = getId(req, "pid");
        try {
            Product p = pDao.load(pid);
            ShopCart sc = getShopCart(req);
            LinkedHashMap<Integer, CartProduct> cps = sc.getCartProducts();
            CartProduct cp = cps.get(pid);
            cp.setProduct(p);
            req.setAttribute("cartProduct", cp);
            return "/WEB-INF/shopcart/updateInput.jsp";
        } catch (ShopException e) {
            e.printStackTrace();
            return getRedirectTo() + "/product.do?method=list";
        }

    }

    public String updateInput(HttpServletRequest req, HttpServletResponse resp) {
        int pid = getId(req, "pid");
        boolean update = true;
        String numStr = req.getParameter("num");
        int num = 0;
        try {
            num = Integer.parseInt(numStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            getErrMap().put("num", "输入数量格式不对");
            update = false;
        }
        if (num < 0) {
            getErrMap().put("num", "输入数量不能为负");
            update = false;
        }
        String type = req.getParameter("type");
        ShopCart sc = getShopCart(req);
        if (update) {
            try {
                sc.update(type, num, pid);
                return getRedirectTo() + "/shopcart.do?method=list";
            } catch (ShopException e) {
                getErrMap().put("num", e.getMessage());
            }
        }
        return "/WEB-INF/shopcart/updateInput.jsp";


    }

    public String delete(HttpServletRequest req, HttpServletResponse resp) {
        int pid = getId(req, "pid");
        ShopCart sc = getShopCart(req);
        sc.delete(pid);
        return getRedirectTo() + "/shopcart.do?method=list";
    }

    private ShopCart getShopCart(HttpServletRequest req) {
        ShopCart sc = (ShopCart) req.getSession().getAttribute("shopcart");
        if (sc == null) {
            sc = new ShopCart();
            req.getSession().setAttribute("shopcart", sc);
        }
        return sc;
    }
}
