package test;

import org.junit.Test;
import shop.dao.IOrderDao;
import shop.model.Order;
import shop.util.ShopDi;

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
}