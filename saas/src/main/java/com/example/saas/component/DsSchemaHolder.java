package com.example.saas.component;

/**
 * @author 马成军
 **/
public final class DsSchemaHolder {
    private static final ThreadLocal<String> SCHEMA_HOLDER = new ThreadLocal();

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
