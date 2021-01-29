package com.bugtracker.main;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

public class BugTrackerMain {
    private static PreparedStatement preparedStatement;
    private static ResultSet data;
    private static Connection connect = null;
    private static Statement statement = null;


    public static DefaultTableModel getTableData(ResultSet data){
        //create vector for column names
        Vector<String> columnName = new Vector<>();
        //data for the table is vector(implements list) of vectors with a row of SQL table data in each nested.
        Vector<Vector<Object>> info = new Vector<Vector<Object>>();
        try {
            //get data about the result set data.
            ResultSetMetaData metadata = data.getMetaData();

            //populate columnName with names of SQL columns from metadata
            int columnCount = metadata.getColumnCount();
            for (int column = 1; column <= columnCount; column++){
               columnName.add(metadata.getColumnName(column));
            }

            while(data.next()){
                //create a vector of objects to be nested in vector and add row of data from result set
                Vector<Object> vec = new Vector<>();
                for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++){
                    vec.add(data.getObject(columnIndex));
                }
                info.add(vec);
            }}
            catch (SQLException throwables){
                throwables.printStackTrace();
            }
            return new DefaultTableModel(info, columnName);
    }

    public static DefaultTableModel getCurrentBugs(){
           try{
               MySQLAccess();
               preparedStatement = connect.prepareStatement("SELECT * FROM current_bugs");
               data = preparedStatement.executeQuery();
           }
           catch (SQLException throwables) {
               throwables.printStackTrace();
           }
           close();
           return getTableData(data);
    }

    public static DefaultTableModel getInProgress() {
        try{
            MySQLAccess();
            preparedStatement = connect.prepareStatement("SELECT * FROM in_progress");
            data = preparedStatement.executeQuery();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        close();
        return getTableData(data);
    }

    public static TableModel getPastBugs() {
        try{
            MySQLAccess();
            preparedStatement = connect.prepareStatement("SELECT * FROM past_bugs");
            data = preparedStatement.executeQuery();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        close();
        return getTableData(data);
    }

    public static String addTicket(String title, String lang, String prio, String desc, String file, String date) {
        String success = "Not able to add this ticket, please check the provided information and retry";
        try{
            MySQLAccess();
            if(alreadyPresent(title)){
                success.replace("false", "There is already a ticket with this title, " +
                        "please choose another");
                return success;
            }
            preparedStatement = connect.prepareStatement("INSERT INTO current_bugs VALUES(?,?,?,?,?,?");
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, lang);
            preparedStatement.setString(3, prio);
            preparedStatement.setString(4, desc);
            preparedStatement.setString(5, file);
            preparedStatement.setString(6, date);
            int count = preparedStatement.executeUpdate();
            if(count>0) {
                success.replace("Not able to add this ticket, please check the provided information and retry",
                        "Ticket added successfully");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    private static boolean alreadyPresent(String title) {
        try{
            MySQLAccess();
            preparedStatement = connect.prepareStatement("SELECT 1 FROM current_bugs WHERE" +
                    "Title = ?");
            preparedStatement.setString(1, title);
            ResultSet exist = preparedStatement.executeQuery();
            if(exist.next()){
                return false;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;

    }

    //Helper Methods
    public static void close(){
           try{
               data.close();
               statement.close();
               connect.close();
           }
           catch (SQLException throwables) {
               throwables.printStackTrace();
           }
    }


    //TO BE ADJUSTED BY ADMIN BEFORE DEPLOYMENT WITH CORRECT INFORMATION for getConnection and username/password
    public static final void MySQLAccess() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        //connectionProps.put("password", password);

        connect = DriverManager.getConnection(
                    "jdbc:" + "mysql" + "://" +
                            "127.0.0.1" +
                            ":" + "3306" + "/" + "bugtracker",
                    connectionProps);

        System.out.println("Connected to database");
    }

    public static boolean logIn() throws SQLException {
        MySQLAccess();
        return true;
    }
}


