package com.example.saas.debug;

import com.example.saas.DsService.DsService;
import com.example.saas.component.TenantAwareRoutingSource;
import com.example.saas.rest.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping({"/debug/ds"})
public class DsInfoApi {
    @Autowired
    private DsService dsService;
    private TenantAwareRoutingSource tenantAwareRoutingSource;

    @Autowired
    public DsInfoApi(DataSource routingDs) {
        Assert.isTrue(routingDs instanceof AbstractRoutingDataSource, "AbstractRoutingDataSource not found");
        Assert.isTrue(routingDs instanceof TenantAwareRoutingSource, "TenantAwareRoutingSource not found");
        this.tenantAwareRoutingSource = (TenantAwareRoutingSource)routingDs;
    }

    @GetMapping({"/list"})
//    @ApiOperationeration("查询当前所有持有的DataSource信息")
    public JsonResult getTargetDataSources() {
        Map<Object, Object> targetDataSources = this.tenantAwareRoutingSource.getTargetDataSources();
        Set<Object> keySet = new HashSet();
        if (targetDataSources != null) {
            keySet.addAll(targetDataSources.keySet());
        }

        return JsonResult.ok("targetDataSources", keySet);
    }

    @PostMapping({"/refresh"})
//    @ApiOperation("刷新所有用户数据源")
    public JsonResult createSchema() {
        this.dsService.refreshDsInfo();
        return JsonResult.ok("刷新所有用户数据源成功", (Object)null);
    }
}
