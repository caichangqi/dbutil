package com.devin.dbutil4j.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by devin on 2016/12/15.
 *
 * 加载
 */
public class ConnectionFactory {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    private static Logger logger = LogManager.getLogger(ConnectionFactory.class.getName());

    private static final ConnectionFactory CONNECTION_FACTORY = new ConnectionFactory();

    private Connection connection;

    static {
        Properties properties = new Properties();

        try {

            // 读取配置文件
            InputStream is = ConnectionFactory.class.getClassLoader()
                    .getResourceAsStream("db.properties");

            // 加载配置属性
            properties.load(is);
        } catch (IOException e) {
            logger.catching(Level.ERROR, e);
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    private ConnectionFactory() {
    }

    /**
     * 获取 ConnectionFactory 的单例对象.
     * @return ConnectionFactory 的实例
     */
    public static ConnectionFactory getInstance() {
        return CONNECTION_FACTORY;
    }

    /**
     * 开启数据库连接
     * @return 数据库连接对象
     */
    public Connection openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            logger.catching(Level.ERROR, e);
        } catch (SQLException e) {
            logger.catching(Level.ERROR, e);
        }

        return connection;
    }
}
