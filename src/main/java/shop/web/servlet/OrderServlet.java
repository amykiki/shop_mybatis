package shop.web.servlet;

import com.sun.org.apache.xpath.internal.operations.Or;
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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.DoubleToIntFunction;

/**
 * Created by Amysue on 2016/4/14.
 */
public class OrderServlet extends BaseServlet {
    private IProductDao     pDao;
    private IUserDao        uDao;
    private IOrderDao       oDao;
    private ICartProductDao CPDao;
    private int pageLimit;
    private int pageShow;

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
    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.debug("This is OrderServlet init");
        String limit = config.getInitParameter("pageLimit");
        pageLimit = Integer.parseInt(limit);
        pageShow = Integer.parseInt(config.getInitParameter("pageShow"));
    }

    @Auth(Role.NORMAL)
    public String add(HttpServletRequest req, HttpServletResponse resp) {
        LinkedHashMap<Integer, CartProduct> cpMap  = (LinkedHashMap<Integer, CartProduct>) req.getSession().getAttribute("orderProductMap");
        if (cpMap == null) {
            cpMap = new LinkedHashMap<>();
        }

        String[]                            pidstr = req.getParameterValues("pids");
        if (pidstr != null) {
            ShopCart                            sc     = getShopCart(req);
            LinkedHashMap<Integer, CartProduct> cps    = sc.getCartProducts();
            int[]                               pids   = new int[pidstr.length];
            for (int i = 0; i < pidstr.length; i++) {
                pids[i] = Integer.parseInt(pidstr[i]);
            }
            for (int pid : pids) {
                try {
                    Product     p  = pDao.load(pid);
                    CartProduct cp = cps.get(pid);
                    cp.setPrice(p.getPrice());
                    cp.setProduct(p);
//                    cps.remove(pid);
                    cpMap.put(pid, cp);
                } catch (ShopException e) {
                    e.printStackTrace();
                }
            }
        }

        req.getSession().setAttribute("orderProductMap", cpMap);
        try {
            req.setAttribute("cuser", uDao.load(getLgUser(req).getId()));
        } catch (ShopException e) {
            e.printStackTrace();
        }
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
            getErrMap().put("errMsg", "输入地址id格式不正确");
            return add(req, resp);
//            return handleException("输入地址id格式不正确", req);
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
            getErrMap().put("errMsg", "输入地址id不属于当前用户");
            return add(req, resp);
//            return handleException("输入地址id不属于当前用户", req);
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
            getErrMap().put("errMsg", "添加订单失败");
            return add(req, resp);
//            return handleException("添加订单失败" + e.getMessage(), req);
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

        deleteShopCart(req);
        req.getSession().removeAttribute("orderProductMap");
        return getRedirectTo() + "/product.do?method=list";

    }

    @Auth(Role.NORMAL)
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
        if (req.getParameter("name") != null && !req.getParameter("name").equals("")) {
            params.put("name", req.getParameter("name"));
        }
        int oid = getId(req, "oid");
        if (oid > 0) {
            params.put("oid", oid);
        }
        String type = req.getParameter("type");
        boolean manageOrders = false;

        if (type != null && type.equals("all") && getLgUser(req).getRole() == Role.ADMIN) {
            int uid = getId(req, "uid");
            if (uid > 0) {
                params.put("uid", uid);
            }
            manageOrders = true;
        } else {
            params.put("uid", getLgUser(req).getId());
        }

        try {
            OStatus ostatus = OStatus.valueOf(req.getParameter("ostatus"));
            params.put("ostatus", ostatus);
        } catch (NullPointerException | IllegalArgumentException e) {
//            e.printStackTrace();
        }

        Pager<Order> pLists = oDao.find(params);
        req.setAttribute("pLists", pLists);
        if (manageOrders) {
            return "/WEB-INF/order/list_admin.jsp";
        } else {
            return "/WEB-INF/order/list.jsp";
        }
    }

    @Auth(Role.NORMAL)
    public String payOrder(HttpServletRequest req, HttpServletResponse resp) {
        int orderid = getId(req, "orderid");
        try {
            Order co = oDao.load(orderid);
            if (co.getUser().getId() == getLgUser(req).getId() && co.getStatus() == OStatus.ToPAID) {
                co.setPayDate(new Date());
                co.setStatus(OStatus.ToDELIVERED);
                oDao.update(co);
            }
        } catch (ShopException e) {
            e.printStackTrace();
        }
        return list(req, resp);

    }
    @Auth(Role.NORMAL)
    public String confirmOrder(HttpServletRequest req, HttpServletResponse resp) {
        int orderid = getId(req, "orderid");
        try {
            Order co = oDao.load(orderid);
            if (co.getUser().getId() == getLgUser(req).getId() && co.getStatus() == OStatus.ToCONFIRMED) {
                co.setConfirmDate(new Date());
                co.setStatus(OStatus.FINISHED);
                oDao.update(co);
            }
        } catch (ShopException e) {
            e.printStackTrace();
        }
        return list(req, resp);

    }

    @Auth(Role.NORMAL)
    public String deleteOrder(HttpServletRequest req, HttpServletResponse resp) {
        int orderid = getId(req, "orderid");
        try {
            Order co = oDao.load(orderid);
            if (co.getUser().getId() == getLgUser(req).getId()) {
                if (co.getStatus() == OStatus.FINISHED || co.getStatus() == OStatus.CANCELED) {
                    oDao.delete(co.getId());
                }
            }
        } catch (ShopException e) {
            e.printStackTrace();
        }
        return list(req, resp);

    }

    @Auth(Role.ADMIN)
    public String deliverOrder(HttpServletRequest req, HttpServletResponse resp) {
        int orderid = getId(req, "orderid");
        try {
            Order co = oDao.load(orderid);
            if (co.getStatus() == OStatus.ToDELIVERED) {
                co.setDeliverDate(new Date());
                co.setStatus(OStatus.ToCONFIRMED);
                oDao.update(co);
            }
        } catch (ShopException e) {
            e.printStackTrace();
        }
        return list(req, resp);

    }

    private ShopCart getShopCart(HttpServletRequest req) {
        ShopCart sc = (ShopCart) req.getSession().getAttribute("shopcart");
        if (sc == null) {
            sc = new ShopCart();
            req.getSession().setAttribute("shopcart", sc);
        }
        return sc;
    }

    private void deleteShopCart(HttpServletRequest req) {
        ShopCart sc = (ShopCart) req.getSession().getAttribute("shopcart");
        LinkedHashMap<Integer, CartProduct> cpMap  = (LinkedHashMap<Integer, CartProduct>) req.getSession().getAttribute("orderProductMap");

        if (cpMap != null) {
            LinkedHashMap<Integer, CartProduct> cps    = sc.getCartProducts();
            for (int key : cpMap.keySet()) {
                cps.remove(key);
            }
        }

    }
}
