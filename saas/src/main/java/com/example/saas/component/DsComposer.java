package com.example.saas.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 马成军
 **/
public class DsComposer {
    private static final Logger log = LoggerFactory.getLogger(DsComposer.class);
    private Map<Object, Object> map = new HashMap();
    private Map<String, String> t2sMap = new HashMap();

    public DsComposer() {
    }

    public Map<Object, Object> getDsMap() {
        return this.map;
    }

    public void putDsMap(String key, DataSource dataSource) {
        this.map.put(key, dataSource);
    }

    public Map<Object, Object> getMap() {
        return this.map;
    }

    public Map<String, String> getT2sMap() {
        return this.t2sMap;
    }

    public void setMap(final Map<Object, Object> map) {
        this.map = map;
    }

    public void setT2sMap(final Map<String, String> t2sMap) {
        this.t2sMap = t2sMap;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DsComposer)) {
            return false;
        } else {
            DsComposer other = (DsComposer)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$map = this.getMap();
                Object other$map = other.getMap();
                if (this$map == null) {
                    if (other$map != null) {
                        return false;
                    }
                } else if (!this$map.equals(other$map)) {
                    return false;
                }

                Object this$t2sMap = this.getT2sMap();
                Object other$t2sMap = other.getT2sMap();
                if (this$t2sMap == null) {
                    if (other$t2sMap != null) {
                        return false;
                    }
                } else if (!this$t2sMap.equals(other$t2sMap)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DsComposer;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $map = this.getMap();
         result = result * 59 + ($map == null ? 43 : $map.hashCode());
        Object $t2sMap = this.getT2sMap();
        result = result * 59 + ($t2sMap == null ? 43 : $t2sMap.hashCode());
        return result;
    }

    public String toString() {
        return "DsComposer(map=" + this.getMap() + ", t2sMap=" + this.getT2sMap() + ")";
    }
}
