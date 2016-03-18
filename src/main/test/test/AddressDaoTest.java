package test;

import org.junit.Test;
import shop.dao.AddressDao;
import shop.model.Address;
import shop.model.User;

import static org.junit.Assert.*;

/**
 * Created by Amysue on 2016/3/18.
 */
public class AddressDaoTest {
    AddressDao adao = new AddressDao();

    @Test
    public void testLoad() throws Exception {
        Address address = adao.load(5);
        System.out.println(address + " " + address.getUser().toString());
//        System.out.println(address);
    }

    @Test
    public void testAdd() throws Exception {
        Address address = new Address();
        User u = new User();
        u.setId(2);
        address.setRecipient("管理员");
        address.setAddressInfo("上海市北江燕路338弄17号");
        address.setPhone("13585603589");
        address.setZip("dd4526");
        address.setUser(u);

        address.setRecipient("莉莉");
        address.setAddressInfo("北京市北江燕路338弄17号");
        address.setPhone("18930161861");
        address.setZip("4526");

        int id = adao.add(address);
        System.out.println("Generated ID = " + id);
    }
}