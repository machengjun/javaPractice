package com.example.concurrent.component;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @author 马成军
 **/

public final class DsSchemaHolder {
    private static final TransmittableThreadLocal<String> SCHEMA_HOLDER = new TransmittableThreadLocal();

    private DsSchemaHolder() {
    }

    public static void setSchema(String schema) {
        SCHEMA_HOLDER.set(schema);
    }

    public static String getSchema() {
        return (String) SCHEMA_HOLDER.get();
    }

    public static boolean isEmpty() {
        return SCHEMA_HOLDER.get() == null;
    }

    public static void clear() {
        SCHEMA_HOLDER.remove();
    }

    public static ThreadLocal<String> getSchemaHolder() {
        return SCHEMA_HOLDER;
    }
}
