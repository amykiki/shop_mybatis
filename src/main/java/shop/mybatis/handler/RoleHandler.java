package shop.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import shop.model.Role;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Amysue on 2016/3/17.
 */
public class RoleHandler extends BaseTypeHandler<Role> {

    private Class<Role> type;
    public RoleHandler(Class<Role> type) {

    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Role role, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Role getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return null;
    }

    @Override
    public Role getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public Role getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }

}
