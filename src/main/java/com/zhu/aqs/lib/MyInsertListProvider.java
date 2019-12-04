package com.zhu.aqs.lib;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

/**
 * @Author: tangtao
 * @Date: 2018/12/14 17:38
 * @Description: 自定义批量插入list，自增与非自增主键都适用，不要求主键字段名必须为id
 */
public class MyInsertListProvider extends MapperTemplate {

    public MyInsertListProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     INSERT INTO t_activity_detail(`activity_detail_id`, `activity_id`, `detail_type`, `detail_content`,`detail_sort`)
        VALUES
        <foreach collection="list" item="record" separator=",">
            (
            #{record.activityDetailId,jdbcType=BIGINT},
            #{record.activityId,jdbcType=BIGINT},
            #{record.detailType,jdbcType=VARCHAR},
            #{record.detailContent,jdbcType=LONGVARCHAR},
            #{record.detailSort,jdbcType=INTEGER}
            )
        </foreach>
     */
    public String myInsertList(MappedStatement statement){

        //1、创建StringBuilder用于拼接SQL语句的各个组成部分
        StringBuilder sql=new StringBuilder();

        //2、获取实体类对应的Class对象
        Class<?> entityClass = super.getEntityClass(statement);
        //3、获取实体类在数据库中对应的表名
        String tableName = super.tableName(entityClass);
        //4、生成insert子句：INSERT INTO t_activity_detail
        String insertClause = SqlHelper.insertIntoTable(entityClass, tableName);
        sql.append(insertClause);
        sql.append("(");

        //5、获取表中所有字段的信息
        Set<EntityColumn> columns = EntityHelper.getColumns(entityClass);
        for (EntityColumn entityColumn : columns) {
            String column = entityColumn.getColumn();
            //6、拼接要插入的字段
            sql.append("`").append(column).append("`,");
        }
        //7、删除SQL语句中最后一个多余的逗号
        sql.deleteCharAt(sql.length()-1);

        sql.append(")").append(" VALUES ");

        //8、拼接foreach标签
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\">");
        sql.append("(");
        for (EntityColumn entityColumn : columns) {
            //返回格式如:#{record.age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
            String columnHolder = entityColumn.getColumnHolder("record");
            sql.append(columnHolder).append(",");
        }
        //9、删除SQL语句中最后一个多余的逗号
        sql.deleteCharAt(sql.length()-1);
        sql.append(")").append("</foreach>");
        return sql.toString();
    }


















}
