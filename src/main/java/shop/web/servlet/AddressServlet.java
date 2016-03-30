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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Amysue on 2016/3/29.
 */
public class AddressServlet extends BaseServlet {

    private IAddressDao adao;
    private IUserDao udao;

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
        User lguser = (User)req.getSession().getAttribute("lguser");
        int id = lguser.getId();
        User u = udao.load(id);
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
        boolean             addFail = false;
//        if (errMap.isEmpty() && addr.getRecipient() != null) {
//
//        }
        return "WEB-INF/address/addInput.jsp";
    }

    private List<Integer> getSelected(HttpServletRequest req) {
        return super.getSelected(req, "addrids", -1);
    }

}
