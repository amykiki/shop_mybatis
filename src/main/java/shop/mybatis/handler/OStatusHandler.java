package shop.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import shop.enums.OStatus;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amysue on 2016/4/7.
 */
public class OStatusHandler extends BaseTypeHandler<OStatus> {
    private Class<OStatus> type;
    private final Map<Integer, OStatus> ostatus;

    public OStatusHandler(Class<OStatus> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument can't be NULL");
        }
        this.type = type;
        ostatus = new HashMap<>();
        OStatus[] values = type.getEnumConstants();
        if (values == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent a ENUM type");
        }
        for (OStatus value : values) {
            int key = value.getCode();
            ostatus.put(key, value);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public OStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return getOStatus(i);
        }
    }

    @Override
    public OStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return getOStatus(i);
        }
    }

    @Override
    public OStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return getOStatus(i);
        }
    }

    private OStatus getOStatus(int i) {
        if (ostatus.containsKey(i)) {
            return ostatus.get(i);
        } else {
            throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by value.");
        }
    }
}
