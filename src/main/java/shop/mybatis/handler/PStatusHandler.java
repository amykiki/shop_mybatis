package shop.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import shop.enums.PStatus;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amysue on 2016/4/7.
 */
public class PStatusHandler extends BaseTypeHandler<PStatus> {
    private Class<PStatus> type;
    private final Map<Integer, PStatus> pstatus;

    public PStatusHandler(Class<PStatus> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument can't be NULL");
        }
        this.type = type;
        pstatus = new HashMap<>();
        PStatus[] values = type.getEnumConstants();
        if (values == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent a ENUM type");
        }
        for (PStatus value : values) {
            int key = value.getCode();
            pstatus.put(key, value);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public PStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return getPStatus(i);
        }
    }

    @Override
    public PStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return getPStatus(i);
        }
    }

    @Override
    public PStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return getPStatus(i);
        }
    }

    private PStatus getPStatus(int i) {
        if (pstatus.containsKey(i)) {
            return pstatus.get(i);
        } else {
            throw new IllegalArgumentException("Cannot convert " + i + " to " + type.getSimpleName() + " by value.");
        }
    }
}
