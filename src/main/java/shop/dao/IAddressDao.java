package shop.dao;

import shop.model.Address;
import shop.util.ShopException;

import java.util.List;

/**
 * Created by Amysue on 2016/3/20.
 */
public interface IAddressDao {
    public Address load(int id) throws ShopException;

    public int add(Address address, int userId) throws ShopException;

    public int delete(int id) throws ShopException;
    public int deleteLists(List<Integer> list) throws ShopException;
    public int update(Address address);

    public List<Address> loadLists();
}
