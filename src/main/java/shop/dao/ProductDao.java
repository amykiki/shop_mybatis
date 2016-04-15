package shop.dao;

import shop.model.Pager;
import shop.model.Product;
import shop.mybatis.map.ProductMapper;
import shop.util.ShopException;

import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/4/7.
 */
public class ProductDao extends BaseDao<Product> implements IProductDao {
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
    public int setPrice(Product product) {
        return setMethod(product, "setPrice");
    }

    @Override
    public int setStock(Product product) {
        return setMethod(product, "setStock");
    }

    @Override
    public int setSales(Product product) {
        return setMethod(product, "setSales");
    }

    @Override
    public int setStatus(Product product) {
        return setMethod(product, "setStatus");
    }

    @Override
    public int setName(Product product) {
        return setMethod(product, "setName");
    }

    private int setMethod(Product p, String method) {
        Object[]   params      = new Object[]{p};
        Class<?>[] pClz        = new Class[]{Product.class};
        int        affectedRow = 0;
        try {
            affectedRow = (int) super.runMethod(method, params, pClz);
        } catch (Exception e) {
            logger.debug(method + " fail");
            e.printStackTrace();
        }
        return affectedRow;
    }

    @Override
    public int addStock(int id, int addStock) {
        try {
            Product p     = load(id);
            int     stock = p.getStock() + addStock;
            p.setStock(stock);
            return setStock(p);
        } catch (ShopException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int reduceStock(int id, int reduceStock) throws ShopException {
        Product p = load(id);
        if (p.getStock() < reduceStock) {
            throw new ShopException("减少的商品数量多于商品库存，请重新输入");
        }
        p.setStock(p.getStock() - reduceStock);
        return setStock(p);
    }

    @Override
    public int addSales(int id, int addSales) {
        try {
            Product p     = load(id);
            int     sales = p.getSales() + addSales;
            p.setSales(sales);
            return setSales(p);
        } catch (ShopException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int reduceSales(int id, int reduceSales) {
        try {
            Product p     = load(id);
            int     sales = p.getSales() - reduceSales;
            p.setSales(sales);
            return setSales(p);
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
