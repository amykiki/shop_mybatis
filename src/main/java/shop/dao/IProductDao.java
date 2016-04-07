package shop.dao;

import shop.enums.PStatus;
import shop.model.Pager;
import shop.model.Product;
import shop.util.ShopException;

import java.util.Map;

/**
 * Created by Amysue on 2016/4/7.
 */
public interface IProductDao {
    public int add(Product p) throws ShopException;

    public int update(Product product);
    public Product load(int id) throws ShopException;

    public int delete(int id) throws ShopException;
    public int setPrice(int id, double price);
    public int setStock(int id, int stock);
    public int setSales(int id, int sales);
    public int setStatus(int id, PStatus status);

    public int addStock(int id, int addStock);

    public int reduceStock(int id, int reduceStock) throws ShopException;

    public Pager<Product> find(Map<String, Object> params);
}
