package shop.dao;

import shop.enums.OStatus;
import shop.model.Order;
import shop.model.Pager;
import shop.model.Product;
import shop.mybatis.map.OrderMapper;
import shop.util.ShopDi;
import shop.util.ShopException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/4/15.
 */
public class OrderDao extends BaseDao<Order> implements IOrderDao {
    private ICartProductDao cpDao;


    public OrderDao() {
        super(OrderMapper.class);
        DaoFactory.setDao(this);
    }

    @ShopDi("cartProductDao")
    public void setCpDao(ICartProductDao cpDao) {
        this.cpDao = cpDao;
    }

    @Override
    public int updatePayDate(Order order) {
        return updateField("updatePayDate", Order.class, order);
    }

    @Override
    public int updateDeliverDate(Order order) {
        return updateField("updateDeliverDate", Order.class, order);
    }

    @Override
    public int updateConfirmDate(Order order) {
        return updateField("updateConfirmDate", Order.class, order);
    }

    @Override
    public int updateStatus(Order order) {
        return updateField("updateStatus", Order.class, order);
    }

    @Override
    public int updatePrice(Order order) {
        return updateField("updatePrice", Order.class, order);
    }

    @Override
    public int delete(int id) throws ShopException {
        OStatus status = loadStatus(id);
        if (status != null) {
            if (status == OStatus.FINISHED || status == OStatus.CANCELED) {
                cpDao.delete(id);
                return super.delete(id);
            }
        }
        return 0;
    }

    @Override
    public Order load(int id) throws ShopException {
        return super.load(id);
    }

    @Override
    public Pager<Order> find(Map<String, Object> params) {
        return super.find(params);
    }

    @Override
    public OStatus loadStatus(int id) {
        Object[]   params = new Object[]{id};
        Class<?>[] pClz   = new Class[]{int.class};
        try {
            return (OStatus) super.runSelectMethod("loadStatus", params, pClz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Load Order Status id = " + id + " fail" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Order> loadToPay() {
        Object[]   params = new Object[]{OStatus.ToPAID};
        Class<?>[] pClz   = new Class[]{OStatus.class};
        try {
            return (List<Order>) super.runSelectMethod("loadToPay", params, pClz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Load Order with to pay status fail" + e.getMessage());
        }
        return null;
    }

    @Override
    public int updateMultipleCancel(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        Object[]   params = new Object[]{OStatus.CANCELED, list};
        Class<?>[] pClz   = new Class[]{OStatus.class, List.class};
        try {
            return  (int)super.runMethod("updateMultipleStatus", params, pClz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("updateMultipleCancel" + e.getMessage());
        }
        return 0;
    }

    @Override
    public List<Order> loadListsStatus(Map<String, Object> map) {
        Object[]   params = new Object[]{map};
        Class<?>[] pClz   = new Class[]{Map.class};
        try {
            return (List<Order>) super.runSelectMethod("loadListsStatus", params, pClz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Load Order with to pay status fail" + e.getMessage());
        }
        return null;
    }

    private int updateField(String method, Class clz, Object p) {
        Object[]   params      = new Object[]{p};
        Class<?>[] pClz        = new Class[]{clz};
        int        affectedRow = 0;
        try {
            affectedRow = (int) super.runMethod(method, params, pClz);
        } catch (Exception e) {
            logger.debug(method + " fail");
            e.printStackTrace();
        }
        return affectedRow;
    }
}
