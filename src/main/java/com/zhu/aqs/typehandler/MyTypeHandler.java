package com.zhu.aqs.typehandler;

import com.zhu.aqs.common.SexEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyTypeHandler extends BaseTypeHandler<SexEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, SexEnum sexEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,sexEnum.ordinal());
    }

    @Override
    public SexEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        System.out.println(resultSet.getInt(s));
        return SexEnum.getInstance(resultSet.getInt(s));
    }

    @Override
    public SexEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        System.out.println(resultSet.getInt(i));
        return SexEnum.getInstance(resultSet.getInt(i));
    }

    @Override
    public SexEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        System.out.println(callableStatement.getInt(i));
        return SexEnum.getInstance(callableStatement.getInt(i));
    }
}
