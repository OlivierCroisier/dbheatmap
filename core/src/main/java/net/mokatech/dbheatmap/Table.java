package net.mokatech.dbheatmap;

import java.util.Objects;

public class Table {

    public static final String DEFAULT_SCHEMA = "default";

    public static Table of(String tableName) {
        String schema = DEFAULT_SCHEMA;
        String table = tableName;
        if (tableName.contains(".")) {
            String[] schemaAndTable = tableName.split(".");
            schema = schemaAndTable[0];
            schema = schema.length() == 0 ? DEFAULT_SCHEMA : schema;
            table = schemaAndTable[1];
        }
        return new Table(schema, table);
    }

    private final String schema;
    private final String table;

    private Table(String schema, String table) {
        this.schema = Objects.requireNonNull(schema);
        this.table = Objects.requireNonNull(table);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table1 = (Table) o;
        return Objects.equals(schema, table1.schema) &&
                Objects.equals(table, table1.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schema, table);
    }

    @Override
    public String toString() {
        return (DEFAULT_SCHEMA.equals(schema) ? "" : schema + ".") + table;
    }

    public String getSchema() {
        return schema;
    }

    public String getTable() {
        return table;
    }
}
