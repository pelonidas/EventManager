package com.project.dal.connectorDAO;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:sqlserver://10.176.111.31;database=Event_Manager;");
        ds.setUsername("CSe21B_26");
        ds.setPassword("CSe21B_26");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public  Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public DBCPDataSource(){ }
}