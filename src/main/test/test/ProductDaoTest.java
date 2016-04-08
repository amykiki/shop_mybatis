package test;

import org.junit.Test;
import shop.dao.IProductDao;
import shop.dao.ProductDao;
import shop.enums.PStatus;
import shop.model.Category;
import shop.model.Pager;
import shop.model.Product;
import shop.util.ShopDi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Amysue on 2016/4/7.
 */
public class ProductDaoTest extends BaseTest {
    private IProductDao pDao;

    @ShopDi("productDao")
    public void setpDao(IProductDao pDao) {
        this.pDao = pDao;
    }

    @Test
    public void testAdd() throws Exception {
        Product p = new Product();
        p.setName("兰蔻「小黑瓶」系列大眼精华");
        p.setCategory(new Category(24, "眼霜"));
        p.setIntro("兰蔻眼部精华肌底液，多维旋转按摩亮珠，一触冰凉舒缓，轻抹一圈，灵动按摩眼周。见证双眸更大、更清澈、更年轻。 更大：提拉上眼脸，改善眼部浮肿，淡化眼袋。");
        p.setImg("/img/lancome_eye.jpg");
        p.setPrice(680);
        p.setStock(100);

        p.setName("苏菲娜（Sofina）保湿泡泡洁面乳");
        p.setCategory(new Category(19));
        p.setIntro("具有美容液成分的弹力泡泡，使肌肤持久滋润，柔嫩紧致。 *美容液成分：生姜精华、氨基酸保湿成分（甜菜碱）、甘油 *附送起泡网。");
        p.setImg("/img/sofina_clean.jpg");
        p.setPrice(153);
        p.setStock(200);

        p.setStatus(PStatus.InSale);
//        p.setSales(1);
        int id = pDao.add(p);
        System.out.println("id = " + id);
    }

    @Test
    public void testUpdate() throws Exception {
//        Product p = pDao.load(1);
//        p.setName("兰蔻[小黑瓶]大眼精华");
//        p.setPrice(668);
//        p.setStock(150);
//        p.setCategory(new Category(6));
        Product p = new Product();
        p.setId(1);
        p.setPrice(668);
        p.setStock(120);
        p.setSales(1);
        p.setCategory(new Category(24));
        pDao.update(p);
    }

    @Test
    public void testLoad() throws Exception {
        Product p = pDao.load(2);
        System.out.println(p);
    }

    @Test
    public void testDelete() throws Exception {
        int row = pDao.delete(2);
        System.out.println("row = " + row);
    }

    @Test
    public void testSetPrice() throws Exception {
        int id    = 1;
        int price = 666;
        int row   = pDao.setPrice(id, price);
        System.out.println("row = " + row);
        System.out.println(pDao.load(id));
    }

    @Test
    public void testSetStock() throws Exception {
        int id    = 3;
        int stock = 400;
        int row   = pDao.setStock(id, stock);
        System.out.println("row = " + row);
        System.out.println(pDao.load(id));
    }

    @Test
    public void testSetSales() throws Exception {
        int id    = 1;
        int sales = 8;
        int row   = pDao.setSales(id, sales);
        System.out.println("row = " + row);
        System.out.println(pDao.load(id));
    }

    @Test
    public void testSetStatus() throws Exception {
        int     id     = 3;
        PStatus status = PStatus.OffSale;
        int     row    = pDao.setStatus(id, status);
        System.out.println("row = " + row);
        System.out.println(pDao.load(id));
    }

    @Test
    public void testAddStock() throws Exception {
        int id  = 3;
        int row = pDao.reduceStock(id, 500);
        System.out.println("row = " + row);
        System.out.println(pDao.load(id));
    }

    @Test
    public void testReduceStock() throws Exception {
        int id  = 4;
        int row = pDao.reduceStock(id, 500);
        System.out.println("row = " + row);
        System.out.println(pDao.load(id));


    }

    @Test
    public void testFind() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("pageLimit", 20);
        params.put("toPage", 8);
        params.put("pageShow", 10);
//        params.put("name", "");
//        params.put("status", PStatus.OffSale);
        params.put("price1", 0);
        params.put("price2", 800);
        params.put("cid", 19);
        params.put("sort", "price");
        params.put("order", "desc");
        Pager<Product> pager = pDao.find(params);
        List<Product>  ps    = pager.gettLists();
        if (ps != null) {
            for (Product p : ps) {
                System.out.println(p);
            }
        }
    }
}