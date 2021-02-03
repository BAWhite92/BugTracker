package com.bugtracker.main;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

public class BugTrackerMain {
    private static PreparedStatement preparedStatement;
    private static ResultSet data;
    private static Connection connect = null;
    private static Statement statement = null;

    public static boolean logIn(String name, char[] password) throws SQLException {
        String pass = "";

        try {
            preparedStatement = connect.prepareStatement("SELECT password FROM accounts WHERE username = ?");
            preparedStatement.setString(1, name);
            ResultSet user = preparedStatement.executeQuery();
            if(!user.isBeforeFirst()){
                return false;
            }
            else{
                while(user.next()){
                    pass = user.getString("password");
                }
            }
            if(validatePassword(password, pass)){
                return true;
            }
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean registerUser(String name, String user, char[] pass) {
        String passHash = null;
        try {
            passHash = generateStrongPasswordHash(pass);
            preparedStatement = connect.prepareStatement("INSERT INTO accounts(username, password, auth, name)" +
                    " VALUES(?,?,?,?)");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, passHash);
            preparedStatement.setInt(3, 2);
            preparedStatement.setString(4, name);
            int success = preparedStatement.executeUpdate();
            if (success > 0) {
                return true;
            }
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean registerAdmin(String name, String user, char[] pass) throws SQLException {
        String passHash = null;
        try {
            passHash = generateStrongPasswordHash(pass);
            preparedStatement = connect.prepareStatement("INSERT INTO accounts (username, password, auth, name)" +
                    " VALUES (?,?,?,?)");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, passHash);
            preparedStatement.setInt(3, 1);
            preparedStatement.setString(4, name);
            int success = preparedStatement.executeUpdate();
            if (success > 0) {
                return true;
            }
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean adminExistsCheck() throws SQLException {
        preparedStatement = connect.prepareStatement("SELECT * FROM accounts WHERE auth = 1");
        ResultSet exist = preparedStatement.executeQuery();
        if(exist.next()){
            return true;
        }
        return false;
    }

    public static void adminPassInit(char[] password) {
        try {
            String pass = generateStrongPasswordHash(password);
            preparedStatement = connect.prepareStatement("INSERT INTO adminpassword VALUES(?)");
            preparedStatement.setString(1, pass);
            preparedStatement.executeUpdate();
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean adminPassCheck(char[] password) {
        String savedPass = "";
        try {
            preparedStatement = connect.prepareStatement("SELECT * FROM adminpassword");
            ResultSet res = preparedStatement.executeQuery();
            if(res.next()){
                savedPass = res.getString(1);
                return validatePassword(password, savedPass);
            }
        }
        catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    //Password hashing used from HowToDoInJava on Java Secure Hashing page.
    private static String generateStrongPasswordHash(char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password;
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    //Validate password methods also from HowToDoInJava
    private static boolean validatePassword(char[] originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword, salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    public static ArrayList<String> getInfo(String uname) {
        ArrayList<String> info = new ArrayList<>();
        String lognName = "";
        String auth = "";
        try {
            preparedStatement = connect.prepareStatement("SELECT name, auth FROM accounts WHERE username=?");
            preparedStatement.setString(1, uname);
            ResultSet res = preparedStatement.executeQuery();
            while(res.next()){
                lognName = res.getString("name");
                int a = res.getInt("auth");
                auth = Integer.toString(a);
                System.out.println(auth);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        info.add(lognName);
        info.add(auth);
        return info;
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

    public static boolean setTicketFixing(String title, String user) {
        String name = "", lang = "", prio = "", desc = "", file = "", date = "";

        try{
            ResultSet data = getCurrentData(title);
            while (data.next()){
                name = data.getString("Title");
                lang = data.getString("Language");
                prio = data.getString("Priority");
                desc = data.getString("Description");
                file = data.getString("File");
                date = data.getString("Date");
            }

            preparedStatement = connect.prepareStatement("DELETE FROM current_bugs WHERE Title = ?");
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();

            preparedStatement = connect.prepareStatement("INSERT INTO in_progress VALUES (?,?,?,?,?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lang);
            preparedStatement.setString(3, prio);
            preparedStatement.setString(4, desc);
            preparedStatement.setString(5, file);
            preparedStatement.setString(6, date);
            preparedStatement.setString(7, user);
            preparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean updateTicket(String prio, String desc, String lang, String loc) throws SQLException {
        preparedStatement = connect.prepareStatement("UPDATE current_bugs SET Language=?, Priority=?, " +
                "Description=?, File=?");
        preparedStatement.setString(1, lang);
        preparedStatement.setString(2, prio);
        preparedStatement.setString(3, desc);
        preparedStatement.setString(4, loc);
        int success = preparedStatement.executeUpdate();
        if(success > 0){
            return true;
        }
        return false;
    }

    public static boolean updateInProgressTicket(String prio, String desc, String lang, String loc,
                                       String fix) throws SQLException {
        preparedStatement = connect.prepareStatement("UPDATE in_progress SET Language=?, Priority=?, " +
                "Description=?, File=?, FixedBy=?");
        preparedStatement.setString(1, lang);
        preparedStatement.setString(2, prio);
        preparedStatement.setString(3, desc);
        preparedStatement.setString(4, loc);
        preparedStatement.setString(5, fix);
        int success = preparedStatement.executeUpdate();
        if(success > 0){
            return true;
        }
        return false;
    }

    public static boolean removeTicket(String title, String table) throws SQLException {
        preparedStatement = connect.prepareStatement("DELETE FROM " + table + " WHERE Title=?");
        preparedStatement.setString(1, title);
        int success = preparedStatement.executeUpdate();
        if(success > 0){
            return true;
        }
        return false;
    }

    public static boolean markCompleted(String title) throws SQLException {
        String name = "", lang = "", prio = "", desc = "", file = "", date = "", fixer = "";

        ResultSet data = getInProgressData(title);
        while (data.next()) {
            name = data.getString("Title");
            lang = data.getString("Language");
            prio = data.getString("Priority");
            desc = data.getString("Description");
            file = data.getString("File");
            date = data.getString("Date");
            fixer = data.getString("FixedBy");
        }

        preparedStatement = connect.prepareStatement("DELETE FROM in_progress WHERE Title=?");
        preparedStatement.setString(1, title);
        int success = preparedStatement.executeUpdate();
        if(success > 0){
            preparedStatement = connect.prepareStatement("INSERT INTO past_bugs VALUES (?,?,?,?,?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lang);
            preparedStatement.setString(3, prio);
            preparedStatement.setString(4, desc);
            preparedStatement.setString(5, file);
            preparedStatement.setString(6, date);
            preparedStatement.setString(7, fixer);
            preparedStatement.executeUpdate();
            return true;
        }
        return false;
    }

    public static String getFixer(String title, String table) throws SQLException {
        preparedStatement = connect.prepareStatement("SELECT FixedBy FROM "+ table + " WHERE Title=?");
        preparedStatement.setString(1, title);
        ResultSet res = preparedStatement.executeQuery();
        if(res.next()){
            String user = res.getString("FixedBy");
            return user;
        }
        return "No User";
    }

    //Helper Methods
    //Get data from a given row of the current_bugs database table
    public static ResultSet getCurrentData(String title){
        try{
            preparedStatement = connect.prepareStatement("SELECT * FROM current_bugs WHERE Title = ?");
            preparedStatement.setString(1, title);
            return preparedStatement.executeQuery();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static ResultSet getInProgressData(String title){
        try{
            preparedStatement = connect.prepareStatement("SELECT * FROM in_progress WHERE Title = ?");
            preparedStatement.setString(1, title);
            return preparedStatement.executeQuery();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    //TO BE ADJUSTED BY ADMIN BEFORE DEPLOYMENT WITH CORRECT INFORMATION for getConnection and username/password
    public static final void MySQLAccess() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "baw");
        connectionProps.put("password", "Eairnon14");

        connect = DriverManager.getConnection(
                    "jdbc:" + "mysql" + "://" +
                            "127.0.0.1" +
                            ":" + "3306" + "/" + "bugtracker",
                    connectionProps);

        System.out.println("Connected to database");
    }


    public static void closeConn() throws SQLException {
        try{
            connect.close();
        }
        catch(Exception e){
            throw e;
        }
    }
}


