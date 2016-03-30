package shop.web.servlet;

import shop.dao.AddressDao;
import shop.dao.IAddressDao;
import shop.dao.IUserDao;
import shop.model.Address;
import shop.model.EqualID;
import shop.model.Role;
import shop.model.User;
import shop.util.RequestUtil;
import shop.util.ShopDi;
import shop.util.ShopException;
import shop.web.annotation.AddFiled;
import shop.web.annotation.Auth;
import shop.web.annotation.UpdateFiled;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Amysue on 2016/3/29.
 */
public class AddressServlet extends BaseServlet {

    private IAddressDao adao;
    private IUserDao    udao;

    @ShopDi("addressDao")
    public void setAdao(IAddressDao adao) {
        this.adao = adao;
    }

    @ShopDi("userDao")
    public void setUdao(IUserDao udao) {
        this.udao = udao;
    }

    @Auth(value = Role.NORMAL, equalID = EqualID.EQUAL)
    public String delete(HttpServletRequest req, HttpServletResponse resp) {
        User          lguser  = (User) req.getSession().getAttribute("lguser");
        int           id      = lguser.getId();
        User          u       = null;
        try {
            u = udao.load(id);
        } catch (ShopException e) {
            req.setAttribute("errMsg", "不存在的用户");
            return "/WEB-INF/util/error.jsp";
        }
        List<Integer> addrids = new ArrayList<>();
        for (Address a : u.getAddresses()) {
            addrids.add(a.getId());
        }
        addrids.retainAll(getSelected(req)); // this is must, only delete the lguser's address
        Integer[] datas = new Integer[addrids.size()];
        logger.debug(Arrays.toString(addrids.toArray(datas)));
        try {
            adao.deleteLists(addrids);
        } catch (ShopException e) {
            req.setAttribute("errMsg", "删除地址失败");
        }
        return "/user.do?method=show";
    }

    @Auth(value = Role.NORMAL)
    public String add(HttpServletRequest req, HttpServletResponse resp) {
        return "WEB-INF/address/addInput.jsp";
    }

    // TODO: 2016/3/29  添加地址
    @Auth(value = Role.NORMAL)
    public String addInput(HttpServletRequest req, HttpServletResponse resp) {
        Address             addr    = (Address) RequestUtil.setFileds(Address.class, req, AddFiled.class);
        Map<String, String> errMap  = (Map<String, String>) req.getAttribute("errMap");
        User                lguser  = lgUser(req);
        boolean             addFail = false;
        if (errMap.isEmpty()) {
            try {
                adao.add(addr, lguser.getId());
            } catch (ShopException e) {
                errMap.put("errMsg", "不存在的用户");
                addFail = true;
            }
        } else {
            logger.debug("对象转换失败");
            addFail = true;
        }
        if (addFail == true) {
            req.setAttribute("caddr", addr);
            return "WEB-INF/address/addInput.jsp";
        }
        return "/user.do?method=show&userid=" + lguser.getId();
    }

    @Auth(value = Role.NORMAL)
    public String update(HttpServletRequest req, HttpServletResponse resp) {
        Address caddr = loadSelfAddr(req);
        if (caddr == null) {
            return "/WEB-INF/util/error.jsp";
        }
        req.setAttribute("caddr", caddr);
        return "WEB-INF/address/updateInput.jsp";
    }

    @Auth(value = Role.NORMAL)
    public String updateInput(HttpServletRequest req, HttpServletResponse resp) {
        int addrId = getId(req, "addrid");
        User    lguser     = lgUser(req);
        Address oldaddr = loadSelfAddr(req);
        if (oldaddr == null) {
            return "/WEB-INF/util/error.jsp";
        }
        Address caddr = (Address) RequestUtil.setFileds(Address.class, req, UpdateFiled.class);
        caddr.setId(oldaddr.getId());
        Map<String, String> errMap = (Map<String, String>) req.getAttribute("errMap");
        if (errMap.isEmpty()) {
            adao.update(caddr);
            return "/user.do?method=show&userid=" + lguser.getId();
        } else {
            logger.debug("对象转换失败");
            req.setAttribute("caddr", caddr);
            req.setAttribute("oldaddr", oldaddr);
            return "WEB-INF/address/updateInput.jsp";
        }
    }

    private Address loadSelfAddr(HttpServletRequest req) {
        User    lguser     = lgUser(req);
        int     addrId     = getId(req, "addrid");
        Address addr = null;
        if (addrId == -1) {
            req.setAttribute("errMsg", "传入地址id不正确");
            logger.debug("传入地址id不正确");
            return null;
        }
        try {
            addr = adao.load(addrId);
        } catch (ShopException e) {
            req.setAttribute("errMsg", "地址id为" + addrId + "的地址不存在");
            logger.debug("地址id为" + addrId + "的地址不存在");
            return null;
        }
        if (addr.getUser().getId() != lguser.getId()) {
            req.setAttribute("errMsg", "地址id为" + addrId + "的地址不属于当前用户");
            logger.debug("地址id为" + addrId + "的地址不属于当前用户");
            return null;
        }
        return addr;
    }

    private List<Integer> getSelected(HttpServletRequest req) {
        return super.getSelected(req, "addrids", -1);
    }

    private User lgUser(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("lguser");
    }

}
