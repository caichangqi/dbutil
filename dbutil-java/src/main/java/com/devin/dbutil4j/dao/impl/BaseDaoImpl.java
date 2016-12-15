package com.devin.dbutil4j.dao.impl;

import com.devin.dbutil4j.dao.BaseDao;
import com.devin.dbutil4j.util.ConnectionFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by devin on 2016/12/15.
 */
public class BaseDaoImpl implements BaseDao {
    private static Logger logger = LogManager.getLogger(BaseDaoImpl.class.getName());

    private Connection conn;

    public BaseDaoImpl() {
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public int executeUpdate(String sql, Object[] params) {
        int result = 0;

        // 获取数据库连接
        if (null == conn) {
            conn = ConnectionFactory.getInstance().openConnection();
        }

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
            logger.catching(Level.ERROR, e);
        } finally {
            closeAll(conn, preparedStatement, null);
        }

        return result;
    }

    public List<Map<String, String>> executeQuery(String sql, Object[] params) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        // 获取数据库连接
        if (null == conn) {
            conn = ConnectionFactory.getInstance().openConnection();
        }

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
                Map<String, String> item = new HashMap<String, String>();
                for (int i = 1; i <= columnCount; i ++) {
                    // 以结果集中的列名为 key, 对应的值为 value.
                    item.put(rsmd.getColumnName(i), rs.getString(i));
                }
                list.add(item);
            }
        } catch (SQLException e) {
            logger.catching(Level.ERROR, e);
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
            logger.catching(Level.ERROR, e);
        }

        try {
            if (null != ps) {
                ps.close();
            }
        } catch (SQLException e) {
            logger.catching(Level.ERROR, e);
        }

        try {
            if (null != conn) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.catching(Level.ERROR, e);
        }
    }
}