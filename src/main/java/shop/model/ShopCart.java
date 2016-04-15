package shop.model;

import shop.dao.DaoFactory;
import shop.dao.IProductDao;
import shop.util.ShopDi;
import shop.util.ShopException;

import java.util.LinkedHashMap;

/**
 * Created by Amysue on 2016/4/12.
 */
public class ShopCart {
    private LinkedHashMap<Integer, CartProduct> cartProducts;

    private IProductDao pDao;

    public ShopCart() {
        cartProducts = new LinkedHashMap<>();
        DaoFactory.setDao(this);
    }

    @ShopDi("productDao")
    public void setpDao(IProductDao pDao) {
        this.pDao = pDao;
    }

    public void add(Product product) throws ShopException {
        int         id = product.getId();
        CartProduct cp = null;
        if (cartProducts.containsKey(id)) {
            cp = cartProducts.get(id);
        } else {
            cp = new CartProduct();
        }
        int formerNum = cp.getPurchaseNum();
        if ((formerNum > product.getStock()) || (formerNum + 1) > product.getStock()) {
            throw new ShopException(product.getName() + "库存不足不能购买,库存." +
                                            product.getStock() + ",购买量" + (formerNum + 1));
        }
        cp.setPrice(product.getPrice());
        cp.setPurchaseNum(formerNum + 1);
        cp.setProduct(product);
        cartProducts.put(id, cp);
    }

    public void update(String type, int num, int pid) throws ShopException {
        CartProduct cp = cartProducts.get(pid);
        Product p = null;
        try {
            p = pDao.load(pid);
        } catch (ShopException e) {
            e.printStackTrace();
        }
        if (cp != null) {
            cp.setProduct(p);
            int totalNum = 0;
            if (type.equals("increase")) {
                totalNum = cp.getPurchaseNum() + num;
            } else if (type.equals("reduce")) {
                totalNum = cp.getPurchaseNum() - num;
            } else {
                totalNum = num;
            }
            if (totalNum >= 1 && totalNum <= p.getStock()) {
                cp.setPurchaseNum(totalNum);
            } else {
                throw new ShopException("商品库存" + p.getStock() + ", 商品原购买数量为" + cp.getPurchaseNum() + ", 商品修改数量为" + totalNum + ", 超过了购买范围");
            }

        }

    }

    public void delete(int pid) {
        CartProduct cp = cartProducts.get(pid);
        if (cp != null) {
            cartProducts.remove(pid);
        }
    }

    public LinkedHashMap<Integer, CartProduct> getCartProducts() {
        return cartProducts;
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
