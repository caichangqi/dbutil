package com.devin.dbutil4j.util;

import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by devin on 2016/12/15.
 */
public class ConnectionFactoryTest {
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactoryTest.class);

    @Test
    public void testConnectionFactory() throws SQLException {
        Connection conn = ConnectionFactory.getInstance().openConnection();

        logger.info("{}", conn.getAutoCommit());
        assertTrue(conn.getAutoCommit());
    }
}
