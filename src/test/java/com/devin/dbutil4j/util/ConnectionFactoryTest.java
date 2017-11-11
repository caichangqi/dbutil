package com.devin.dbutil4j.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by devin on 2016/12/15.
 */
public class ConnectionFactoryTest {
    private static Logger logger =
            LogManager.getLogger(ConnectionFactoryTest.class.getName());

    @Test
    public void testConnectionFactory() throws SQLException {
        Connection conn = ConnectionFactory.getInstance().openConnection();

        logger.info(conn.getAutoCommit());
    }
}
