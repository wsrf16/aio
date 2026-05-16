package com.aio.portable.swiss.suite.storage.db.mybatis;

import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MapTypeHandler extends BaseTypeHandler<Map<?, ?>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<?, ?> parameter, JdbcType jdbcType) throws SQLException {
        String json = JacksonSugar.obj2Json(parameter);
        ps.setString(i, json);
    }

    @Override
    public Map<?, ?> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return toMap(value);
    }

    @Override
    public Map<?, ?> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return toMap(value);
    }

    @Override
    public Map<?, ?> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return toMap(value);
    }

    private static Map<?, ?> toMap(String value) {
        return StringUtils.isEmpty(value) ? null : JacksonSugar.json2ObjectMap(value);
    }

}
