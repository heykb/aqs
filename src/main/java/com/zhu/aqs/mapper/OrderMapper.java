package com.zhu.aqs.mapper;

import com.zhu.aqs.lib.MyBaseMapper;
import com.zhu.aqs.model.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Created by Mybatis Generator on 2019/10/15
*/
@Mapper
public interface OrderMapper extends MyBaseMapper<Order> {
    List<Order> select0();
}