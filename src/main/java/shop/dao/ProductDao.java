package shop.dao;

import shop.enums.PStatus;
import shop.model.Category;
import shop.model.Pager;
import shop.model.Product;
import shop.mybatis.map.ProductMapper;
import shop.util.ShopException;

import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/4/7.
 */
public class ProductDao extends BaseDao<Product> implements IProductDao{
    public ProductDao() {
        super(ProductMapper.class);
    }

    @Override
    public int add(Product obj) throws ShopException {
        return super.add(obj);
    }

    @Override
    public int update(Product obj) {
        return super.update(obj);
    }

    @Override
    public Product load(int id) throws ShopException {
        return super.load(id);
    }

    @Override
    public int delete(int id) throws ShopException {
        // TODO: 2016/4/7 在添加订单后，删除商品之前必须检查该商品是否有订单，否则不能删除
        return super.delete(id);
    }

    @Override
    public int setPrice(int id, double price) {
        return setMethod(id, price, double.class, "setPrice");
    }

    @Override
    public int setStock(int id, int stock) {
        return setMethod(id, stock, int.class, "setStock");
    }

    @Override
    public int setSales(int id, int sales) {
        return setMethod(id, sales, int.class, "setSales");
    }

    @Override
    public int setStatus(int id, PStatus status) {
        return setMethod(id, status, PStatus.class, "setStatus");
    }
    @Override
    public int setInSale(int id) {
        PStatus ps = PStatus.InSale;
        return setMethod(id, ps, PStatus.class, "setStatus");
    }
    @Override
    public int setOffSale(int id) {
        PStatus ps = PStatus.OffSale;
        return setMethod(id, ps, PStatus.class, "setStatus");
    }

    private int setMethod(int id, Object o, Class clz, String method) {
        Object[]   params = new Object[]{id, o};
        Class<?>[] pClz   = new Class[]{int.class, clz};
        int affectedRow = 0;
        try {
            affectedRow = (int)super.runMethod(method, params, pClz);
        } catch (Exception e) {
            logger.debug(method + " fail");
            e.printStackTrace();
        }
        return affectedRow;
    }

    @Override
    public int addStock(int id, int addStock) {
        try {
            int oldStock = load(id).getStock();
            int stock = oldStock + addStock;
            return setStock(id, stock);
        } catch (ShopException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int reduceStock(int id, int reduceStock) throws ShopException{
        try {
            int oldStock = load(id).getStock();
            if (oldStock < reduceStock) {
                throw new ShopException("减少的商品数量多于商品库存，请重新输入");
            }
            int stock = oldStock - reduceStock;
            return setStock(id, stock);
        } catch (ShopException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Pager<Product> find(Map<String, Object> params) {
        return super.find(params);
    }

    @Override
    public List<Product> loadCategoryLists(int cid) {
        Object[]   params = new Object[]{cid};
        Class<?>[] pClz   = new Class[]{int.class};
        try {
            return (List<Product>) super.runSelectMethod("loadCategoryLists", params, pClz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Load Product Lists cid = " + cid + " fail" + e.getMessage());
        }
        return null;
    }
}
