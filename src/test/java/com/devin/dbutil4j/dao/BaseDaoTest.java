package com.devin.dbutil4j.dao;

import com.devin.dbutil4j.dao.impl.BaseDaoImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by devin on 2016/12/15.
 */
public class BaseDaoTest {
    private static Logger logger = LoggerFactory.getLogger(BaseDaoTest.class);

    @Test
    public void testBaseDaoInsert() {
        BaseDao baseDao = new BaseDaoImpl();

        // insert 测试
        String insertSql = "insert into mybatis_users (name, age) values (?, ?)";
        Object[] insertParams = new Object[]{"Mike", 14};
        int insertResult = baseDao.executeUpdate(insertSql, insertParams);
        logger.info("插入了 " + insertResult + " 行");
    }

    @Test
    public void testBaseDaoUpdate() {
        BaseDao baseDao = new BaseDaoImpl();

        // update 测试
        String sql = "update mybatis_users set age = ? where name = ?";
        Object[] params = new Object[]{11, "Mike"};
        int result = baseDao.executeUpdate(sql, params);
        logger.info("修改了 " + result + " 行");
    }

    @Test
    public void testBaseDaoDelete() {
        BaseDao baseDao = new BaseDaoImpl();

        // delete 测试
        String sql = "delete from mybatis_users where id = ?";
        Object[] params = new Object[]{3};
        int result = baseDao.executeUpdate(sql, params);
        logger.info("删除了 " + result + " 行");
    }

    @Test
    public void testBaseDaoSelect() {
        BaseDao baseDao = new BaseDaoImpl();

        // select  测试
        String sql = "SELECT * FROM drools_rule_cond_tpl;";

        List<Map<String, String>> result = baseDao.executeQuery(sql, null);

        for (Map<String, String> item : result) {
            for (Map.Entry<String, String> entry : item.entrySet()) {
                logger.info(entry.getKey() + "--->" + entry.getValue());
            }
            logger.info("==================================");
        }
    }
}
