package com.bugtracker.main;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.SQLException;

public class BugTrackerContr {

    public static DefaultTableModel getCurrentBugs() {
        return BugTrackerMain.getCurrentBugs();
    }

    public static DefaultTableModel getInProgressBugs() {
        return BugTrackerMain.getInProgress();
    }

    public static TableModel getPastBugs() {
        return BugTrackerMain.getPastBugs();
    }

    public static void MySQLAccess() {
        try {
            BugTrackerMain.MySQLAccess();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeConn() throws SQLException {
        BugTrackerMain.closeConn();
    }

    public static boolean adminCheck() throws SQLException {
        return BugTrackerMain.adminCheck();
    }

    public static void addFirstAdmin(char[] password) {
        BugTrackerMain.adminPassInit(password);
    }

    public static boolean adminPasswordCheck(char[] password) {
        return BugTrackerMain.adminPassCheck(password);
    }

    public boolean logIn(String name, char[] password) {
        try {
            return BugTrackerMain.logIn();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean registerAdmin(String name, String user, char[] pass) throws SQLException {
        return BugTrackerMain.registerAdmin(name, user, pass);
    }

    public boolean registerUser(String name, String user, char[] pass) throws SQLException {
        return BugTrackerMain.registerUser(name, user, pass);
    }

    public String addTicket(String title, String lang, String prio, String desc, String file, String date) {
        return BugTrackerMain.addTicket(title, lang, prio, desc, file, date);
    }

}
