package com.zetcode;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbHandler {

    private static String CON_STR = null;
    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance(String connectionString) throws SQLException {
        //CON_STR = connectionString;
        if (instance == null)
            instance = new DbHandler(connectionString);
        return instance;
    }

    private Connection connection;

    private DbHandler(String connectionString) throws SQLException {
        CON_STR = connectionString;
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public ArrayList<String> getDatabaseMetaData() {

        ArrayList<String> kok = new ArrayList<>();

        try {
            DatabaseMetaData dbmd = this.connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables(null, null, "%", types);

            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
                kok.add(rs.getString("TABLE_NAME"));
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kok;
    }

    public void setStateDeleted(String fileName, String tableName){
        this.inPowersetState(fileName, tableName, "DELETED");
    }

    public void setStateApproved(String fileName, String tableName){
        this.inPowersetState(fileName, tableName, "APPROVED");
    }

    private void inPowersetState(String fileName, String tableName, String state){

        try (PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE " + tableName + " SET state = '" + state + "' WHERE fileName LIKE '%"+ fileName +"%';")) {


            //ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " WHERE fileName LIKE '%"+ fileName +"%';");
            //statement.setObject(1, fileWithDateWhenItPosted.fileId);
            //statement.setObject(2, fileWithDateWhenItPosted.filePostDate);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


