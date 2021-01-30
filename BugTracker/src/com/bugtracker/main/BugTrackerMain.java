package com.bugtracker.main;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;

public class BugTrackerMain {
    private static PreparedStatement preparedStatement;
    private static ResultSet data;
    private static Connection connect = null;
    private static Statement statement = null;

    public static boolean logIn() throws SQLException {
        return true;
    }

    public static boolean registerUser(String name, String user, char[] pass) throws SQLException {
        return true;
    }

    public static boolean registerAdmin(String name, String user, char[] pass) throws SQLException {
        preparedStatement = connect.prepareStatement("INSERT INTO accounts (username, password, auth, name)" +
                " VALUES (?, ?, ?, ?");
        preparedStatement.setString(1, user);
        //preparedStatement.setString(2, pass);
        preparedStatement.setInt(3, 1);
        preparedStatement.setString(4, name);
        preparedStatement.executeUpdate();

        return false;
    }

    public static boolean adminCheck() throws SQLException {
        preparedStatement = connect.prepareStatement("SELECT * FROM accounts");
        ResultSet exist = preparedStatement.executeQuery();
        if(exist.next()){
            return true;
        }
        return false;
    }

    public static void adminPassInit(char[] password) {
        //securely create password for admin registration and save it to database table specifically for it.
    }

    public static boolean adminPassCheck(char[] password) {
        //check the admin password provided against saved password and if correct allow access.
        return false;
    }

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
               preparedStatement = connect.prepareStatement("SELECT * FROM current_bugs");
               data = preparedStatement.executeQuery();
           }
           catch (SQLException throwables) {
               throwables.printStackTrace();
           }
           return getTableData(data);
    }

    public static DefaultTableModel getInProgress() {
        try{
            preparedStatement = connect.prepareStatement("SELECT * FROM in_progress");
            data = preparedStatement.executeQuery();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return getTableData(data);
    }

    public static TableModel getPastBugs() {
        try{
            preparedStatement = connect.prepareStatement("SELECT * FROM past_bugs");
            data = preparedStatement.executeQuery();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return getTableData(data);
    }

    public static String addTicket(String title, String lang, String prio, String desc, String file, String date) {
        String success = "Not able to add this ticket, please check the provided information and retry";
        try{
            if(alreadyPresent(title)){
                success = success.replace("Not able to add this ticket, please check the provided information and retry",
                        "There is already a ticket with this title, please choose another");
                return success;
            }
            preparedStatement = connect.prepareStatement("INSERT INTO current_bugs VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, lang);
            preparedStatement.setString(3, prio);
            preparedStatement.setString(4, desc);
            preparedStatement.setString(5, file);
            preparedStatement.setString(6, date);
            int count = preparedStatement.executeUpdate();
            if(count>0) {
                success = success.replace("Not able to add this ticket, please check the provided information and retry",
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
            preparedStatement = connect.prepareStatement("SELECT * FROM current_bugs WHERE " +
                    "title = ?");
            preparedStatement.setString(1, title);
            ResultSet exist = preparedStatement.executeQuery();
            if(exist.next()){
                return true;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;

    }

    //Helper Methods

    //TO BE ADJUSTED BY ADMIN BEFORE DEPLOYMENT WITH CORRECT INFORMATION for getConnection and username/password
    public static final void MySQLAccess() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "");
        connectionProps.put("password", "");

        connect = DriverManager.getConnection(
                    "jdbc:" + "mysql" + "://" +
                            "127.0.0.1" +
                            ":" + "3306" + "/" + "bugtracker",
                    connectionProps);

        System.out.println("Connected to database");
    }


    public static void closeConn() throws SQLException {
        connect.close();
    }
}


