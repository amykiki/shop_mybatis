package shop.dao;

import shop.model.Address;
import shop.mybatis.map.AddressMapper;

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
}
