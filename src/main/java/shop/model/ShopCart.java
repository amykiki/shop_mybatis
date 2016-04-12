package shop.model;

import java.util.LinkedHashMap;

/**
 * Created by Amysue on 2016/4/12.
 */
public class ShopCart {
    private LinkedHashMap<Integer, CartProduct> cartProducts;

    public ShopCart() {
        cartProducts = new LinkedHashMap<>();
    }

    public void add(Product product) {
        int id = product.getId();
        CartProduct cp = null;
        if (cartProducts.containsKey(id)) {
            cp = cartProducts.get(id);
        } else {
            cp = new CartProduct();
            cartProducts.put(id, cp);
        }
        cp.setPrice(product.getPrice());
        cp.setPurchaseNum(cp.getPurchaseNum() + 1);
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
