package com.zhu.aqs.lib;

import tk.mybatis.mapper.common.Mapper;

public interface MyBaseMapper<T> extends Mapper<T>, /*MySqlMapper<T>,*/MyInsertListMapper<T> {

}
