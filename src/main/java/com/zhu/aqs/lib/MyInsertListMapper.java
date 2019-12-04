package com.zhu.aqs.lib;

import org.apache.ibatis.annotations.InsertProvider;

import java.util.List;

/**
 * @Author: tangtao
 * @Date: 2018/12/14 17:27
 * @Description: 自定义批量插入list，自增与非自增主键都适用，不要求主键字段名必须为id
 */
public interface MyInsertListMapper<T> {

    /**
     * 自定义批量插入list，自增与非自增主键都适用，不要求主键字段名必须为id
     * @param list 需要保存的数据集合
     */
    @InsertProvider(type = MyInsertListProvider.class,method = "dynamicSQL")
    void myInsertList(List<T> list);
}
