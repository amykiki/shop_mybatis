package test;

import org.junit.Test;
import shop.dao.DaoFactory;
import shop.dao.IAddressDao;
import shop.model.Address;
import shop.util.ShopDi;

import java.util.List;

/**
 * Created by Amysue on 2016/3/18.
 */
public class AddressDaoTest {
    IAddressDao adao;

    public AddressDaoTest() {
        System.out.println("=========Address Dao Test Begin===========");
        DaoFactory.setDao(this);
    }

    @ShopDi("addressDao")
    public void setAdao(IAddressDao adao) {
        this.adao = adao;
        System.out.println("=========Address Dao Test END===========");
    }


    @Test
    public void testLoad() throws Exception {
        Address address = adao.load(5);
        System.out.println(address + " " + address.getUser().toString());
//        System.out.println(address);
    }

    @Test
    public void testAdd() throws Exception {
        Address address = new Address();
        address.setRecipient("管理员");
        address.setAddressInfo("上海市北江燕路338弄17号");
        address.setPhone("13585603589");
        address.setZip("dd4526");

        address.setRecipient("小小周");
        address.setAddressInfo("上海市杨浦区邯郸路220号");
        address.setPhone("021-556678778");
        address.setZip("4526");

        int id = adao.add(address, 19);
        System.out.println("Generated ID = " + id);
    }

    @Test
    public void testDelete() throws Exception {
        int affectedRows = adao.delete(7);
        System.out.println("affectedRows = " + affectedRows);
    }

    @Test
    public void testUpdate() throws Exception {
        Address address = new Address();
        address.setId(4);
        address.setRecipient("白雪公主");
        address.setAddressInfo("上海市北江燕路338弄588号");
        address.setPhone("013585603589");
        address.setZip("4526");
        int affectedRows = adao.update(address);
        System.out.println("affectedRows = " + affectedRows);

    }

    @Test
    public void testLoadLists() throws Exception {
        List<Address> aLists = adao.loadLists();
        for (Address a : aLists) {
            System.out.println(a + " User: " + a.getUser());
        }
    }
}