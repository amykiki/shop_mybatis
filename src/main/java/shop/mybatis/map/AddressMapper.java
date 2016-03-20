package shop.mybatis.map;

import shop.model.Address;

import java.util.List;

/**
 * Created by Amysue on 2016/3/18.
 */
public interface AddressMapper {
    public Address load(int id);

    public int add(Address address);

    public int delete(int id);

    public int update(Address address);

    public List<Address> loadLists();
}
