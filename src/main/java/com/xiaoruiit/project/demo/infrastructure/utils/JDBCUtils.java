package com.xiaoruiit.project.demo.infrastructure.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


/**
 * JdbcTemplate工具类
 *
 * @author hxr
 */

@Slf4j
public class JDBCUtils {
    //  type: com.alibaba.druid.pool.DruidDataSource
    //    driverClassName: com.mysql.cj.jdbc.Driver
    static String url = "jdbc:mysql://127.0.0.1:3306?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true";
    static String username = "root";
    static String password = "123456";

    public static JdbcTemplate getJdbcTemplate(){
        // Spring 内置连接池
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // 需要 jdbc 四个参数
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

}
