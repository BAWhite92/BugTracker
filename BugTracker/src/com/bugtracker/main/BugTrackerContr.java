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

    public boolean logIn(String name, char[] password) {
        try {
            return BugTrackerMain.logIn();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public String addTicket(String title, String lang, String prio, String desc, String file, String date) {
        return BugTrackerMain.addTicket(title, lang, prio, desc, file, date);
    }
}
