package bugtrack;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.ArrayList;

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
        catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeConn() throws SQLException {
        BugTrackerMain.closeConn();
    }

    public static boolean adminExistsCheck() throws SQLException {
        return BugTrackerMain.adminExistsCheck();
    }

    public static void addFirstAdmin(char[] password) {
        BugTrackerMain.adminPassInit(password);
    }

    public static boolean adminPasswordCheck(char[] password) {
        return BugTrackerMain.adminPassCheck(password);
    }

    public static boolean logIn(String name, char[] password) {
        try {
            return BugTrackerMain.logIn(name, password);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static ArrayList<String> getInfo(String name) {
        return BugTrackerMain.getInfo(name);
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

    public static boolean updateTicketFixing(String title, String user) {
        return BugTrackerMain.setTicketFixing(title, user);
    }

    public static boolean updateTicket(String prio, String desc, String lang, String loc) throws SQLException {
        return BugTrackerMain.updateTicket(prio, desc, lang, loc);
    }

    public static boolean updateInProgressTicket(String prio, String desc, String lang,
                                                 String loc, String fix) throws SQLException {
        return BugTrackerMain.updateInProgressTicket(prio, desc, lang, loc, fix);
    }

    public static boolean removeTicket(String title, String table) throws SQLException {
        return BugTrackerMain.removeTicket(title, table);
    }

    public static String getFixer(String title, String table) throws SQLException {
        return BugTrackerMain.getFixer(title, table);

    }

    public static boolean markAsComplete(String title) throws SQLException {
        return BugTrackerMain.markCompleted(title);
    }


}
