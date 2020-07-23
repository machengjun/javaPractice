package com.example.saas.component;

import java.util.Map;

import com.example.saas.exceptions.SaasFailedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;


/**
 * @author 马成军
 **/
public class TenantAwareRoutingSource extends AbstractRoutingDataSource {
    private static final Logger log = LoggerFactory.getLogger(TenantAwareRoutingSource.class);
    @Nullable
    private Map<Object, Object> targetDataSources;
    private String mode = "singleMode";
    @Nullable
    private Object defaultTargetDataSource;

    public void setTargetDataSources(@Nullable Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        this.targetDataSources = targetDataSources;
    }

    protected Object determineCurrentLookupKey() {
        if (this.mode.equals("singleMode")) {
            return "masterDs";
        } else if (this.mode.equals("saasMode")) {
            String scheme = DsSchemaHolder.getSchema();
            if (StringUtils.isBlank(scheme)) {
                return "masterDs";
            } else {
                return scheme;
            }
        } else {
            throw new SaasFailedException("Mode not found!");
        }
    }

    public TenantAwareRoutingSource() {
    }

    @Nullable
    public Map<Object, Object> getTargetDataSources() {
        return this.targetDataSources;
    }

    public String getMode() {
        return this.mode;
    }

    @Nullable
    public Object getDefaultTargetDataSource() {
        return this.defaultTargetDataSource;
    }

    public void setMode(final String mode) {
        this.mode = mode;
    }

    public void setDefaultTargetDataSource(@Nullable final Object defaultTargetDataSource) {
        this.defaultTargetDataSource = defaultTargetDataSource;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof TenantAwareRoutingSource)) {
            return false;
        } else {
            TenantAwareRoutingSource other = (TenantAwareRoutingSource)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$targetDataSources = this.getTargetDataSources();
                    Object other$targetDataSources = other.getTargetDataSources();
                    if (this$targetDataSources == null) {
                        if (other$targetDataSources == null) {
                            break label47;
                        }
                    } else if (this$targetDataSources.equals(other$targetDataSources)) {
                        break label47;
                    }

                    return false;
                }

                Object this$mode = this.getMode();
                Object other$mode = other.getMode();
                if (this$mode == null) {
                    if (other$mode != null) {
                        return false;
                    }
                } else if (!this$mode.equals(other$mode)) {
                    return false;
                }

                Object this$defaultTargetDataSource = this.getDefaultTargetDataSource();
                Object other$defaultTargetDataSource = other.getDefaultTargetDataSource();
                if (this$defaultTargetDataSource == null) {
                    if (other$defaultTargetDataSource != null) {
                        return false;
                    }
                } else if (!this$defaultTargetDataSource.equals(other$defaultTargetDataSource)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TenantAwareRoutingSource;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $targetDataSources = this.getTargetDataSources();
        result = result * 59 + ($targetDataSources == null ? 43 : $targetDataSources.hashCode());
        Object $mode = this.getMode();
        result = result * 59 + ($mode == null ? 43 : $mode.hashCode());
        Object $defaultTargetDataSource = this.getDefaultTargetDataSource();
        result = result * 59 + ($defaultTargetDataSource == null ? 43 : $defaultTargetDataSource.hashCode());
        return result;
    }

    public String toString() {
        return "TenantAwareRoutingSource(targetDataSources=" + this.getTargetDataSources() + ", mode=" + this.getMode() + ", defaultTargetDataSource=" + this.getDefaultTargetDataSource() + ")";
    }
}
