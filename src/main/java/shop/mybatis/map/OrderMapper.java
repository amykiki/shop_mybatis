package shop.mybatis.map;

import shop.enums.OStatus;
import shop.model.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/4/15.
 */
public interface OrderMapper {
    public int add(Order order);

    public int updatePrice(Order order);
    public int updatePayDate(Order order);
    public int updateDeliverDate(Order order);
    public int updateConfirmDate(Order order);
    public int updateStatus(Order order);


    public int delete(int id);

    public Order load(int id);
    public List<Order> find(Map<String, Object> map);
    public int findCount(Map<String, Object> map);

    public OStatus loadStatus(int id);
}
