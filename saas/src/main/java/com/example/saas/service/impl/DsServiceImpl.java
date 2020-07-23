package com.example.saas.service.impl;

import com.example.saas.DsService.DsService;
import com.example.saas.component.DataSourceFactory;
import com.example.saas.component.DsComposer;
import com.example.saas.component.TenantAwareRoutingSource;
import com.example.saas.rest.JsonResult;
import com.example.saas.vo.DataSourceVo;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 马成军
 **/
@Service
public class DsServiceImpl implements DsService {
    private static final Logger log = LoggerFactory.getLogger(DsServiceImpl.class);
    @Autowired
    private DsComposer dsComposer;
    @Autowired
    private DataSource routingDs;

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

    public DsServiceImpl() {
    }

    public JsonResult refreshDsInfo() {
        log.info("init SAAS mode...");
        Assert.isTrue(this.routingDs instanceof TenantAwareRoutingSource, "TenantAwareRoutingSource not found");
        DataSource masterDs = (DataSource)this.dsComposer.getMap().get("masterDs");
        Assert.notNull(masterDs, "masterDs not null");
        TenantAwareRoutingSource routingSource = (TenantAwareRoutingSource)this.routingDs;
        routingSource.setMode("saasMode");
        Map<Object, Object> dsMap = new HashMap(16);

        List<DataSourceVo> dataSourceVoList = getAllDataSource();
        dataSourceVoList.forEach((vo) -> {
            String driver = vo.getDriver();
            String url = vo.getUrl();
            String username = vo.getUsername();
            String password = vo.getPassword();
            String schema = vo.getSchema();
            DataSource dataSource = DataSourceFactory.build(driver, url, username, password, schema, this.maximumPoolSize, this.minimumIdle, this.maxLifetime, this.idleTimeout, this.connectionTimeout);
            log.info("添加数据源dataSource, driver:" + driver + ",url:" + url + ",schema:" + schema);
            HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
            log.info("saas dataSource init(lrd-ms-saas)...MaxLifetime={}, maximumPoolSize={}, minimumIdle={}, idleTimeout={}", new Object[]{hikariDataSource.getMaxLifetime(), hikariDataSource.getMaximumPoolSize(), hikariDataSource.getMinimumIdle(), hikariDataSource.getIdleTimeout()});
            dsMap.put(schema, dataSource);
        });
        dsMap.put("masterDs", masterDs);
        routingSource.setTargetDataSources(dsMap);
        routingSource.afterPropertiesSet();
        log.info("getTargetDataSources :" + routingSource.getTargetDataSources());
        log.info("init SAAS mode finished...");
        return JsonResult.ok("用户数据源信息刷新成功", (Object) null);
    }

    /**
     * 此处根据实际架构，替换数据 saas配置 来源
     *
     * @return
     */
    private List<DataSourceVo> getAllDataSource() {
        List<DataSourceVo> dataSourceVoList = Arrays.asList(
                new DataSourceVo()
                        .setDriver("com.mysql.cj.jdbc.Driver")
                        .setUsername("sddt")
                        .setPassword("sddt8888")
                        .setUrl("jdbc:mysql://localhost:3306/java_practice?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false")
                        .setSchema("saas_one"),
                new DataSourceVo()
                        .setDriver("com.mysql.cj.jdbc.Driver")
                        .setUsername("sddt")
                        .setPassword("sddt8888")
                        .setUrl("jdbc:mysql://localhost:3306/java_practice?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false")
                        .setSchema("saas_two")
        );
        return dataSourceVoList;
    }


}
