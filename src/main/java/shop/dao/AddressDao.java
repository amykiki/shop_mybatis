package shop.dao;

import shop.model.Address;
import shop.model.User;
import shop.mybatis.map.AddressMapper;
import shop.util.ShopDi;
import shop.util.ShopException;

import java.util.List;

/**
 * Created by Amysue on 2016/3/17.
 */
public class AddressDao extends BaseDao<Address> implements IAddressDao{
    private IUserDao userDao;

    public AddressDao() {
        super(AddressMapper.class);
    }

    @ShopDi
    public void setUserDao(IUserDao userDao) {
        System.out.println("************Set User Dao Begin**************");
        this.userDao = userDao;
    }

    @Override
    public Address load(int id) {
        return super.load(id);
    }

    @Override
    public int add(Address obj, int userId) throws ShopException{
        User u = userDao.load(userId);
        if (u == null) {
            throw new ShopException("Not User has the Address " + obj.getAddressInfo() + ",cann't be added");
        }
        obj.setUser(u);
        return super.add(obj);
    }

    @Override
    public int delete(int id) {
        return super.delete(id);
    }

    @Override
    public int update(Address obj) {
        return super.update(obj);
    }

    @Override
    public List<Address> loadLists() {
        return super.loadLists();
    }


}
