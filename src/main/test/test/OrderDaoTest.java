package test;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.Test;
import shop.dao.IOrderDao;
import shop.enums.OStatus;
import shop.model.Order;
import shop.model.Pager;
import shop.util.ShopDi;

import java.util.*;

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
//        params.put("id", 1);
        params.put("name", "还幼");
        Pager<Order> pager = oDao.find(params);
        List<Order> os = pager.gettLists();
        System.out.println("pass");
    }

    @Test
    public void testLoadToPay() throws Exception {
        List<Order> orders = oDao.loadToPay();
        Date currentTime = new Date();
        long timeDiff = currentTime.getTime() - orders.get(0).getBuyDate().getTime();
        long minutes = timeDiff / (1000 * 60);
        System.out.println("timeDiff = " + timeDiff);
        System.out.println("minutes = " + minutes);
        System.out.println(orders.get(0).getBuyDate());
        System.out.println("pass");

    }

    @Test
    public void testSetCpDao() throws Exception {

    }

    @Test
    public void testUpdatePayDate() throws Exception {

    }

    @Test
    public void testUpdateDeliverDate() throws Exception {

    }

    @Test
    public void testUpdateConfirmDate() throws Exception {

    }

    @Test
    public void testUpdateStatus() throws Exception {

    }

    @Test
    public void testUpdatePrice() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testLoadStatus() throws Exception {

    }

    @Test
    public void testUpdateMultipleCancel() throws Exception {
        Integer[] data = new Integer[]{5};
        List<Integer> list = Arrays.<Integer>asList(data);
        oDao.updateMultipleCancel(list);
    }

    @Test
    public void testLoadListsStatus() throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("ostatus", OStatus.CANCELED);
        List<Order> orders = oDao.loadListsStatus(params);
        System.out.println(orders);
    }
}