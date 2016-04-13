package shop.model;

import shop.util.ShopDi;
import shop.util.ShopException;

import java.util.LinkedHashMap;

/**
 * Created by Amysue on 2016/4/12.
 */
public class ShopCart {
    private LinkedHashMap<Integer, CartProduct> cartProducts;

    public ShopCart() {
        cartProducts = new LinkedHashMap<>();
    }

    public void add(Product product) throws ShopException{
        int id = product.getId();
        CartProduct cp = null;
        if (cartProducts.containsKey(id)) {
            cp = cartProducts.get(id);
        } else {
            cp = new CartProduct();
            cartProducts.put(id, cp);
        }
        int formerNum = cp.getPurchaseNum();
        if ((formerNum > product.getStock()) || (formerNum + 1) > product.getStock()) {
            throw new ShopException(product.getName() + "库存不足不能购买,库存." +
                                            product.getStock() + ",购买量" + (formerNum + 1));
        }
        cp.setPrice(product.getPrice());
        cp.setPurchaseNum(formerNum + 1);
        cp.setProduct(product);
    }

    public boolean isEmpty() {
        return cartProducts.isEmpty();
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "cartProducts=" + cartProducts +
                '}';
    }
}
