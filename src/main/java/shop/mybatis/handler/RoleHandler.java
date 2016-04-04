package shop.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import shop.enums.Role;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amysue on 2016/3/17.
 */
public class RoleHandler extends BaseTypeHandler<Role> {

    private       Class<Role>        type;
    private final Map<Integer, Role> roles;

    public RoleHandler(Class<Role> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument can't be NULL");
        }
        this.type = type;
        roles = new HashMap<>();
        Role[] values = type.getEnumConstants();
        if (values == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does no represent a ENUM TYPE");
        }

        for (Role value : values) {
            int key = value.getCode();
            roles.put(key, value);
        }


    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Role role, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, role.getCode());
    }

    @Override
    public Role getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        int i = resultSet.getInt(columnName);
        if (resultSet.wasNull()) {
            return null;
        } else {
            return getRole(i);
        }
    }

    @Override
    public Role getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int i = resultSet.getInt(columnIndex);
        if (resultSet.wasNull()) {
            return null;
        } else {
            return getRole(i);
        }
    }

    @Override
    public Role getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        int i = callableStatement.getInt(columnIndex);
        if (callableStatement.wasNull()) {
            return null;
        } else {
            return getRole(i);
        }
    }

    private Role getRole(int i) {
        if (roles.containsKey(i)) {
            return roles.get(i);
        } else {
            throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by value.");
        }
    }


}
