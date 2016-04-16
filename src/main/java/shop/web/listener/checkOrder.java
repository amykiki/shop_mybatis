package shop.web.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shop.dao.DaoFactory;
import shop.dao.IOrderDao;
import shop.dao.IProductDao;
import shop.dao.ProductDao;
import shop.enums.OStatus;
import shop.model.CartProduct;
import shop.model.Order;
import shop.model.Product;
import shop.util.ShopDi;
import shop.util.ShopException;

import java.util.*;

/**
 * Created by Amysue on 2016/4/16.
 */
public class checkOrder implements Runnable {
    private IOrderDao oDao;
    private IProductDao pDao;
    Logger logger;

    public checkOrder() {
        logger = LogManager.getLogger(this.getClass());
        DaoFactory.setDao(this);
    }

    @ShopDi("orderDao")
    public void setoDao(IOrderDao oDao) {
        this.oDao = oDao;
    }

    @ShopDi("productDao")
    public void setpDao(IProductDao pDao) {
        this.pDao = pDao;
    }

    @Override
    public void run() {
        logger.debug("======Check Order Status================");
        Map<String, Object> params = new HashMap<>();
        params.put("ostatus", OStatus.ToPAID);
        List<Order> orders = oDao.loadListsStatus(params);
        Date        currentTime = new Date();
        List<Integer> ids = new ArrayList<>();
        Map<Integer, Integer> psales = new HashMap<>();
        if (orders != null) {
            for (Order o : orders) {
                long timeDiff = currentTime.getTime() - orders.get(0).getBuyDate().getTime();
                long minutes  = timeDiff / (1000 * 60);
                if (minutes > 5) {
                    logger.debug("Order id[" + o.getId() + "] buy date is " + o.getBuyDate());
                    logger.debug("buy time diff is above 5 MINUTE, the order has to be Cancled");
                    List<CartProduct> cps = o.getProducts();
                    for (CartProduct cp : cps) {
                        int pid = cp.getProduct().getId();
                        int salesNum = cp.getPurchaseNum();
                        if (psales.containsKey(pid)) {
                            int oldSales = psales.get(pid);
                            psales.put(pid, oldSales + salesNum);
                        } else {
                            psales.put(pid, salesNum);
                        }
                    }
                    ids.add(o.getId());

                }
            }
            if (ids.size() > 0) {
                oDao.updateMultipleCancel(ids);
            }
            for (int key : psales.keySet()) {
                int pid = key;
                int salesNum = psales.get(pid);
                try {
                    Product p = pDao.load(pid);
                    int stock = p.getStock() + salesNum;
                    int sales = p.getSales() - salesNum;
                    Product cp = new Product();

                    cp.setId(p.getId());
                    cp.setSales(sales);
                    cp.setStock(stock);
                    pDao.update(cp);
                } catch (ShopException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
