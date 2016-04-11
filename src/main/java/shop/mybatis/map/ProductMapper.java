package shop.mybatis.map;

import org.apache.ibatis.annotations.Param;
import shop.enums.PStatus;
import shop.model.Product;

import java.util.List;
import java.util.Map;

/**
 * Created by Amysue on 2016/4/7.
 */
public interface ProductMapper {
    public int add(Product product);

    public int update(Product product);

    public Product load(int id);

    public List<Product> loadCategoryLists(int cid);

    public int delete(int id);

    /*public int setPrice(@Param("id") int id, @Param("price") double price);
    public int setStock(@Param("id") int id, @Param("stock") int stock);
    public int setSales(@Param("id") int id, @Param("sales") int sales);
    public int setStatus(@Param("id") int id, @Param("status")PStatus status);
    public int setName(@Param("id") int id, @Param("name")String name);*/

    public int setPrice(Product produc);
    public int setStock(Product product);
    public int setSales(Product product);
    public int setStatus(Product product);
    public int setName(Product product);


    public List<Product> find(Map<String, Object> map);
    public int findCount(Map<String, Object> map);
}
