package shop.dao;

import com.sun.org.apache.xpath.internal.operations.Or;
import shop.enums.OStatus;
import shop.model.Order;
import shop.model.Pager;
import shop.util.ShopException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/4/15.
 */
public interface IOrderDao {
    public int add(Order o) throws ShopException;
    public Order load(int id) throws ShopException;
    public OStatus loadStatus(int id);
    public Pager<Order> find(Map<String, Object> params);
    public int delete(int i) throws ShopException;

    public List<Order> loadToPay();
    public List<Order> loadListsStatus(Map<String, Object> map);

    public int updatePrice(Order order);
    public int updatePayDate(Order order);
    public int updateDeliverDate(Order order);

    public int updateMultipleCancel(List<Integer> list);

    public int update(Order order);

    public int updateConfirmDate(Order order);

    public int updateStatus(Order order);
}
