package shop.dao;

import shop.model.CartProduct;
import shop.util.ShopException;

/**
 * Created by Amysue on 2016/4/15.
 */
public interface ICartProductDao {
    public int add(CartProduct cp)throws ShopException;
    public int delete(int oid)throws ShopException;
}
