package ra.business.entity;

import java.io.Serializable;

public class Table implements Serializable {
    private String tableID;
    private String tableName;
    private int tableSits;
    private String tableStatus;

    public Table() {
    }

    public Table(String tableID, String tableName, int tableSits, String tableStatus) {
        this.tableID = tableID;
        this.tableName = tableName;
        this.tableSits = tableSits;
        this.tableStatus = tableStatus;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getTableSits() {
        return tableSits;
    }

    public void setTableSits(int tableSits) {
        this.tableSits = tableSits;
    }

    public String getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus;
    }
}
