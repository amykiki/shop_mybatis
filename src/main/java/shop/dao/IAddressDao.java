package shop.dao;

import shop.model.Address;

import java.util.List;

/**
 * Created by Amysue on 2016/3/20.
 */
public interface IAddressDao {
    public Address load(int id);

    public int add(Address address, int userId);

    public int delete(int id);

    public int update(Address address);

    public List<Address> loadLists();
}
