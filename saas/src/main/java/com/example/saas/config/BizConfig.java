package com.example.saas.config;

import com.example.saas.bean.DsConfigBean;
import com.example.saas.component.DataSourceFactory;
import com.example.saas.component.DsComposer;
import com.example.saas.component.TenantAwareRoutingSource;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 马成军
 **/
@Configuration
@ComponentScan
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
@ConditionalOnProperty(
        value = {"javaPractice.bizconfig.enable"},
        matchIfMissing = true
)
public class BizConfig {
    private static final Logger log = LoggerFactory.getLogger(BizConfig.class);
    @Value("${javaPractice.saas.ds.maximumPoolSize:10}")
    private int maximumPoolSize;
    @Value("${javaPractice.saas.ds.minimumIdle:2}")
    private int minimumIdle;
    @Value("${javaPractice.saas.ds.maxLifetime:130000}")
    private long maxLifetime;
    @Value("${javaPractice.saas.ds.idleTimeout:10000}")
    private long idleTimeout;
    @Value("${javaPractice.saas.ds.connectionTimeout:3000}")
    private long connectionTimeout;

    public BizConfig() {
        log.debug("BizConfig ...");
    }

    @Bean
    DsConfigBean dsConfigBean() {
        return new DsConfigBean();
    }

    @Bean
    DsComposer dsComposer() {
        return new DsComposer();
    }

    @Bean
    public DataSource dataSource(DsComposer dsComposer, DsConfigBean dsConfig) {
        TenantAwareRoutingSource dataSource = new TenantAwareRoutingSource();
        Map<Object, Object> targetDataSources = new HashMap();
        DataSource masterDs = DataSourceFactory.build(dsConfig.getDriver(), dsConfig.getUrl(), dsConfig.getUsername(), dsConfig.getPassword(), "masterHikari", this.maximumPoolSize, this.minimumIdle, this.maxLifetime, this.idleTimeout, this.connectionTimeout);
        dsComposer.getMap().put("masterDs", masterDs);
        targetDataSources.put("masterDs", masterDs);
        dataSource.setDefaultTargetDataSource(masterDs);
        dataSource.setLenientFallback(true);
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.afterPropertiesSet();
        dataSource.setMode("saasMode");
        return dataSource;
    }

    @Bean
    SchemaExport schemaExport() {
        SchemaExport export = new SchemaExport();
        export.setHaltOnError(true);
        export.setFormat(true);
        export.setDelimiter(";");
        return export;
    }
}