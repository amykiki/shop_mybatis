package shop.dao;

import shop.model.Address;
import shop.mybatis.map.AddressMapper;

import java.util.List;

/**
 * Created by Amysue on 2016/3/17.
 */
public class AddressDao extends BaseDao<Address>{
    public AddressDao() {
        super(AddressMapper.class);
    }

    @Override
    public Address load(int id) {
        return super.load(id);
    }

    @Override
    public int add(Address obj) {
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
