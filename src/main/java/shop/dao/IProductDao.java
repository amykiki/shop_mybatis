package shop.dao;

import shop.enums.PStatus;
import shop.model.Pager;
import shop.model.Product;
import shop.util.ShopException;

import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/4/7.
 */
public interface IProductDao {
    public int add(Product p) throws ShopException;

    public int update(Product product);
    public Product load(int id) throws ShopException;
    public List<Product> loadCategoryLists(int cid);

    public int delete(int id) throws ShopException;
    public int setPrice(Product product);
    public int setSales(Product product);
    public int setStock(Product product);
    public int setStatus(Product product);

    public int setName(Product product);
//    public int setInSale(Product product);
//    public int setOffSale(Product product);

    public int addStock(int id, int addStock);

    public int addSales(int id, int addSales);

    public int reduceStock(int id, int reduceStock) throws ShopException;

    public int reduceSales(int id, int reduceSales);
    public Pager<Product> find(Map<String, Object> params);
}
