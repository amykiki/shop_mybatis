package shop.web.servlet;

import shop.dao.ICartProductDao;
import shop.dao.IOrderDao;
import shop.dao.IProductDao;
import shop.dao.IUserDao;
import shop.enums.OStatus;
import shop.enums.Role;
import shop.model.*;
import shop.util.ShopDi;
import shop.util.ShopException;
import shop.web.annotation.Auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.function.DoubleToIntFunction;

/**
 * Created by Amysue on 2016/4/14.
 */
public class OrderServlet extends BaseServlet {
    private IProductDao     pDao;
    private IUserDao        uDao;
    private IOrderDao       oDao;
    private ICartProductDao CPDao;

    @ShopDi("productDao")
    public void setpDao(IProductDao pDao) {
        this.pDao = pDao;
    }

    @ShopDi("userDao")
    public void setuDao(IUserDao uDao) {
        this.uDao = uDao;
    }

    @ShopDi("orderDao")
    public void setoDao(IOrderDao oDao) {
        this.oDao = oDao;
    }

    @ShopDi("cartProductDao")
    public void setCPDao(ICartProductDao CPDao) {
        this.CPDao = CPDao;
    }

    @Auth(Role.NORMAL)
    public String add(HttpServletRequest req, HttpServletResponse resp) {
        ShopCart                            sc     = getShopCart(req);
        String[]                            pidstr = req.getParameterValues("pids");
        int[]                               pids   = new int[pidstr.length];
        LinkedHashMap<Integer, CartProduct> cps    = sc.getCartProducts();
        LinkedHashMap<Integer, CartProduct> cpMap  = new LinkedHashMap<>();
        for (int i = 0; i < pidstr.length; i++) {
            pids[i] = Integer.parseInt(pidstr[i]);
        }
        for (int pid : pids) {
            try {
                Product     p  = pDao.load(pid);
                CartProduct cp = cps.get(pid);
                cp.setPrice(p.getPrice());
                cp.setProduct(p);
                cps.remove(pid);
                cpMap.put(pid, cp);
            } catch (ShopException e) {
                e.printStackTrace();
            }
        }
        req.getSession().setAttribute("orderProductMap", cpMap);
//        req.setAttribute("orderProductMap", cpMap);
        return "/WEB-INF/order/addInput.jsp";
    }

    @Auth(Role.NORMAL)
    public String addInput(HttpServletRequest req, HttpServletResponse resp) {
        LinkedHashMap<Integer, CartProduct> cpMap = (LinkedHashMap<Integer, CartProduct>) req.getSession().getAttribute("orderProductMap");
        if (cpMap == null || cpMap.isEmpty()) {
            return handleException("没有购买产品", req);
        }
        int aid = 0;
        try {
            aid = Integer.parseInt(req.getParameter("aid"));
        } catch (NumberFormatException e) {
            return handleException("输入地址id格式不正确", req);
        }
        User lguser = (User) req.getSession().getAttribute("lguser");
        User cuser  = null;
        try {
            cuser = uDao.load(lguser.getId());
        } catch (ShopException e) {
            return handleException("用户没有登录", req);
        }
        boolean find = false;
        Address caddr = null;
        for (Address a : cuser.getAddresses()) {
            if (aid == a.getId()) {
                find = true;
                caddr = a;
                break;
            }
        }
        if (!find) {
            return handleException("输入地址id不属于当前用户", req);
        }
        Order   o     = new Order();
        o.setBuyDate(new Date());
        o.setStatus(OStatus.ToPAID);
        o.setUser(cuser);
        o.setAddress(caddr);
        int oid = 0;
        try {
            oid = oDao.add(o);
        } catch (ShopException e) {
            e.printStackTrace();
            return handleException("添加订单失败" + e.getMessage(), req);
        }
        double totalPrice = 0;
        for (int key : cpMap.keySet()) {
            CartProduct cp        = cpMap.get(key);
            double      unitPrice = cp.getPrice();
            int         num       = cp.getPurchaseNum();
            cp.setOid(oid);
            totalPrice += num * unitPrice;
            try {
                CPDao.add(cp);
                Product p = cp.getProduct();
                pDao.reduceStock(p.getId(), cp.getPurchaseNum());
                pDao.addSales(p.getId(), cp.getPurchaseNum());
            } catch (ShopException e) {
                e.printStackTrace();
            }
        }
        o.setTotalPrice(totalPrice);
        oDao.updatePrice(o);

        req.getSession().removeAttribute("orderProductMap");
        return getRedirectTo() + "/product.do?method=list";

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
