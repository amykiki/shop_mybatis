package shop.web.servlet;

import shop.dao.AddressDao;
import shop.dao.IAddressDao;
import shop.model.Address;
import shop.model.EqualID;
import shop.model.Role;
import shop.util.RequestUtil;
import shop.util.ShopDi;
import shop.util.ShopException;
import shop.web.annotation.Auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Amysue on 2016/3/29.
 */
public class AddressServlet extends BaseServlet {

    private IAddressDao adao;

    @ShopDi("addressDao")
    public void setAdao(IAddressDao adao) {
        this.adao = adao;
    }

    @Auth(value = Role.NORMAL, equalID = EqualID.EQUAL)
    public String delete(HttpServletRequest req, HttpServletResponse resp) {
        List<Integer> list = getSelected(req);
        try {
            adao.deleteLists(list);
        } catch (ShopException e) {
            req.setAttribute("errMsg", "删除地址失败");
        }
        return "/user.do?method=show";
    }

    private List<Integer> getSelected(HttpServletRequest req) {
        return super.getSelected(req, "addrids", -1);
    }

}
