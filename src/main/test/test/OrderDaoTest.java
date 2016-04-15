package test;

import org.junit.Test;
import shop.dao.IOrderDao;
import shop.model.Order;
import shop.model.Pager;
import shop.util.ShopDi;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Amysue on 2016/4/15.
 */
public class OrderDaoTest extends BaseTest{
    private IOrderDao oDao;

    @ShopDi("orderDao")
    public void setoDao(IOrderDao oDao) {
        this.oDao = oDao;
    }

    @Test
    public void testLoad() throws Exception {
        Order o = oDao.load(3);
        System.out.println("pass");
    }

    @Test
    public void testFind() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("pageLimit", 20);
        params.put("toPage", 1);
        params.put("pageShow", 10);
        Pager<Order> pager = oDao.find(params);
        List<Order> os = pager.gettLists();
        System.out.println("pass");
    }
}