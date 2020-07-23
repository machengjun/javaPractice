package com.example.saas.component;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

/**
 * @author 马成军
 **/
public class DataSourceFactory {
    public DataSourceFactory() {
    }

    public static DataSource build(String driver, String url, String username, String password, String poolName, int maximumPoolSize, int minimumIdle, long maxLifetime, long idleTimeout, long connectionTimeout) {
        DataSourceBuilder<HikariDataSource> hikariDataSourceBuilder = DataSourceBuilder.create().type(HikariDataSource.class);
        HikariDataSource hikariDataSource = (HikariDataSource)hikariDataSourceBuilder.driverClassName(driver).url(url).username(username).password(password).build();
        hikariDataSource.setAutoCommit(true);
        hikariDataSource.setConnectionTestQuery("select 1");
        hikariDataSource.setConnectionTimeout(connectionTimeout);
        hikariDataSource.setIdleTimeout(idleTimeout);
        hikariDataSource.setIsolateInternalQueries(false);
        hikariDataSource.setMaximumPoolSize(maximumPoolSize);
        hikariDataSource.setMinimumIdle(minimumIdle);
        hikariDataSource.setMaxLifetime(maxLifetime);
        hikariDataSource.setPoolName(poolName);
        return hikariDataSource;
    }
}