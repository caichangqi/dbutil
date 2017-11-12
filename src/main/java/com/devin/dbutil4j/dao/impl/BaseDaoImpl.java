package com.devin.dbutil4j.dao.impl;

import com.devin.dbutil4j.dao.BaseDao;
import com.devin.dbutil4j.util.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by devin on 2016/12/15.
 */
public class BaseDaoImpl implements BaseDao {
    private static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    private Connection conn;

    public BaseDaoImpl() {
    }

    @Override
    public int executeUpdate(String sql, Object[] params) {
        int result = 0;

        // 获取数据库连接
        conn = ConnectionFactory.getInstance().openConnection();

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);

            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i+1, params[i]);
                }
            }

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("execute update error", e);
            e.printStackTrace();
        } finally {
            closeAll(conn, preparedStatement, null);
        }

        return result;
    }

    @Override
    public List<Map<String, String>> executeQuery(String sql, Object[] params) {
        List<Map<String, String>> list = new ArrayList<>();

        // 获取数据库连接
        conn = ConnectionFactory.getInstance().openConnection();

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            preparedStatement = conn.prepareStatement(sql);

            if (null != params) {
                for (int i = 0; i < params.length; i ++) {
                    preparedStatement.setObject(i+1, params[0]);
                }
            }

            rs = preparedStatement.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            // 获取结果集的列数
            int columnCount = rsmd.getColumnCount();

            // 遍历结果集
            while (rs.next()) {
                Map<String, String> item = new HashMap<>();
                for (int i = 1; i <= columnCount; i ++) {
                    // 以结果集中的列名为 key, 对应的值为 value.
                    // getColumnName(int i) 获取的是真实的字段名
                    // getColumnLabel(int i) 可以获取别名
                    item.put(rsmd.getColumnLabel(i), rs.getString(i));
                }
                list.add(item);
            }
        } catch (SQLException e) {
            logger.error("execute query error", e);
            e.printStackTrace();
        } finally {
            closeAll(conn, preparedStatement, rs);
        }

        return list;
    }

    private void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (null != rs) {
                rs.close();
            }
        } catch (SQLException e) {
            logger.error("关闭结果集失败", e);
            e.printStackTrace();
        }

        try {
            if (null != ps) {
                ps.close();
            }
        } catch (SQLException e) {
            logger.error("关闭预处理语句失败", e);
            e.printStackTrace();
        }

        try {
            if (null != conn) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("关闭连接失败", e);
            e.printStackTrace();
        }
    }
}
