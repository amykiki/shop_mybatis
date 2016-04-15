package shop.dao;

import shop.model.CartProduct;
import shop.mybatis.map.CartProductMapper;

/**
 * Created by Amysue on 2016/4/15.
 */
public class CartProductDao extends BaseDao<CartProduct> implements ICartProductDao {
    public CartProductDao() {
        super(CartProductMapper.class);
    }

}
