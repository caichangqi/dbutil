一个简单的 jdbc 的封装

将 jdbc 的 API 进行了封装, 简单易用, 开发者只需要关注业务的实现即可. 

##### 安装
1. 将 jar 包导入工程中

2. 依赖
```xml
<dependencies>
    <!-- 数据库驱动 -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
    </dependency>
</dependencies>
```
3. 创建 db.properties
```
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/database_name
username=username
password=password
```

#### API
```java
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
```