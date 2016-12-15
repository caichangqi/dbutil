package com.devin.dbutil4j.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by devin on 2016/12/15.
 * 包含了基本的数据库增删改查
 */
public interface BaseDao {

    /**
     * 执行增, 删, 改语句
     * @param sql sql 语句
     * @param params sql 语句中的参数(注意顺序)
     * @return 受影响的行数
     */
    int executeUpdate(String sql, Object[] params);

    /**
     * 执行查询语句
     * @param sql 查询 sql 语句
     * @param params sql 语句中的参数(注意顺序)
     * @return 返回查询的结果.
     * 查询结果的每一行对应 List 中的一个 Map 对象,
     * Map 对象的 key 对应改行记录的列(建议给列设置别名),
     * Map 对象的 value 对应改行记录的值.
     */
    List<Map<String, String>> executeQuery(String sql, Object[] params);
}
