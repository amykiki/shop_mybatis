package shop.mybatis.map;

import shop.model.CartProduct;

/**
 * Created by Amysue on 2016/4/15.
 */
public interface CartProductMapper {
    public int add(CartProduct cartProduct);

    public int delete(int oid);


}
