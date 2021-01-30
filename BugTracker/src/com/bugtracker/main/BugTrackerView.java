package com.bugtracker.main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class BugTrackerView {
    private JPanel bugTrackingWelcome;
    private JScrollPane WelcomeCard;
    private JScrollPane SearchCard;
    private JScrollPane CurrentBugSearchCard;
    private JScrollPane PastBugSearchCard;
    private JScrollPane InProgressSearchCard;
    private JScrollPane SettingsCard;
    private JLabel Usernamelabel;
    private JLabel PasswordLabel;
    private JButton LogInButton;
    private JTextField UsernameText;
    private JPasswordField PasswordText;
    private JButton createANewBugButton;
    private JButton BeingFixedSearchButton;
    private JButton BugToFixSearchButton;
    private JButton SettingsButton;
    private JTextArea WelcomeTextArea;
    private JButton SolvedBugSearch;
    private JScrollPane CreateATicketCard;
    private JButton LogOutButton;
    private JComboBox PrioritySelectBox;
    private JTextArea selectATimeScaleTextArea;
    private JTextField settingsTextField;
    private JTextField CreateTitleText;
    private JTextField CreateLangText;
    private JComboBox CreatePrioText;
    private JLabel CreateTitleLabel;
    private JLabel CreateLangLabel;
    private JLabel CreatePrioLabel;
    private JTextArea CreateDescrText;
    private JLabel CreateDescrLabel;
    private JTextField CreateFileLocText;
    private JLabel CreateFileLocLabel;
    private JButton createTicketButton;
    private JTable CurrentBugTable;
    private JButton HomeButtonCurrent;
    private JTable PastBugsTable;
    private JButton HomeButtonPast;
    private JTable InProgressTable;
    private JButton CurrentHomeButton;
    private JButton SettingsHomeButton;
    private JButton TicketHomeButton;
    private JButton CurrentBugRefresh;
    private JButton PastBugRefresh;
    private JButton InProgressRefresh;
    private JButton registerButton;
    private JScrollPane RegisterCard;
    private JTextField RegisterHeading;
    private JTextField RegNameText;
    private JLabel RegisterNameLabel;
    private JTextField RegUsernameText;
    private JPasswordField RegPasswordText;
    private JComboBox RegAuthText;
    private JButton RegAddUserButton;
    private JButton RegHomeButton;
    private JButton SavePriorityButton;

    BugTrackerContr btc = new BugTrackerContr();


    public BugTrackerView() {
        LogInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = UsernameText.getText();
                char[] password = PasswordText.getPassword();

                if(btc.logIn(name, password))
                {
                    WelcomeCard.setVisible(false);
                    SearchCard.setVisible(true);
                    CurrentBugSearchCard.setVisible(false);
                    PastBugSearchCard.setVisible(false);
                    InProgressSearchCard.setVisible(false);
                    SettingsCard.setVisible(false);
                    CreateATicketCard.setVisible(false);
                }
            }
        });

        RegAddUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = RegNameText.getText();
                char[] pass = RegPasswordText.getPassword();
                String user = RegUsernameText.getText();
                String auth = RegAuthText.getSelectedItem().toString();

                try {
                    if(auth == "Administrator"){
                        String checkNew = "Create";
                        boolean firstAdmin = true;
                        //Is there already an admin for the system
                        if (BugTrackerContr.adminCheck()){
                            checkNew.replace("Create", "Enter");
                            firstAdmin = false;
                        }

                        //create the admin password entry box
                        JPanel panel = new JPanel();
                        JLabel label = new JLabel(checkNew + " an admin password:");
                        JPasswordField passw = new JPasswordField();
                        panel.add(label);
                        panel.add(passw);
                        String[] options = new String[]{"OK", "Cancel"};
                        int option = JOptionPane.showOptionDialog(null, panel, "Admin Password",
                                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

                        //act based on if password is correct and addition successful
                        if(option == 0) {
                            char[] password = passw.getPassword();
                            if (firstAdmin == true) {
                                BugTrackerContr.addFirstAdmin(password);
                                if (btc.registerAdmin(name, user, pass)) {
                                    JOptionPane.showMessageDialog(null, "First admin added " +
                                            "successfully");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Issue adding admin " +
                                            "please try again");
                                }
                            } else {
                                if (BugTrackerContr.adminPasswordCheck(password)) {
                                    if (btc.registerAdmin(name, user, pass)) {
                                        JOptionPane.showMessageDialog(null, "Admin added " +
                                                "successfully");
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Issue adding " +
                                                "admin please try again");
                                    }
                                }
                            }
                        }
                    }

                    else{
                        if(btc.registerUser(name, user, pass)){
                            JOptionPane.showConfirmDialog(null,"User added successfully");
                        }
                        else{
                            JOptionPane.showConfirmDialog(null, "Issue adding account please " +
                                    "try again");
                        }
                    }
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        LogOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomeCard.setVisible(true);
                SearchCard.setVisible(false);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
            }
        });

        SettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomeCard.setVisible(false);
                SearchCard.setVisible(false);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(true);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
            }
        });

        BugToFixSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentBugTable.setModel(BugTrackerContr.getCurrentBugs());

                WelcomeCard.setVisible(false);
                SearchCard.setVisible(false);
                CurrentBugSearchCard.setVisible(true);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
            }
        });

        BeingFixedSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InProgressTable.setModel(BugTrackerContr.getInProgressBugs());

                WelcomeCard.setVisible(false);
                SearchCard.setVisible(false);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(true);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
            }
        });

        SolvedBugSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PastBugsTable.setModel(BugTrackerContr.getPastBugs());

                WelcomeCard.setVisible(false);
                SearchCard.setVisible(false);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(true);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
            }
        });

        createANewBugButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomeCard.setVisible(false);
                SearchCard.setVisible(false);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(true);
                RegisterCard.setVisible(false);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomeCard.setVisible(false);
                SearchCard.setVisible(false);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(true);
            }
        });

        HomeButtonCurrent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomeCard.setVisible(false);
                SearchCard.setVisible(true);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
            }
        });

        HomeButtonPast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomeCard.setVisible(false);
                SearchCard.setVisible(true);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
            }
        });

        CurrentHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomeCard.setVisible(false);
                SearchCard.setVisible(true);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
            }
        });

        SettingsHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomeCard.setVisible(false);
                SearchCard.setVisible(true);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
            }
        });

        TicketHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomeCard.setVisible(false);
                SearchCard.setVisible(true);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
            }
        });

        RegHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomeCard.setVisible(true);
                SearchCard.setVisible(false);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
            }
        });

        createTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = CreateTitleText.getText();
                String lang = CreateLangText.getText();
                String prio = CreatePrioText.getSelectedItem().toString();
                String desc = CreateDescrText.getText();
                String file;
                if(CreateFileLocText.getText().isEmpty()) {
                    file = "N/A";
                }
                else {
                    file = CreateFileLocText.getText();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new Date());

                int res = JOptionPane.showConfirmDialog(null, "Confirm addition of " +
                                "this ticket", "Confirm", JOptionPane.YES_NO_OPTION);
                if(res == 0) {
                    String added = btc.addTicket(title, lang, prio, desc, file, date);
                    JOptionPane.showMessageDialog(null, added);
                    if(added.contains("Ticket added successfully")){
                        CreateTitleText.setText("");
                        CreateLangText.setText("");
                        CreatePrioText.setSelectedIndex(0);
                        CreateDescrText.setText("");
                        CreateFileLocText.setText("");
                    }
                }
            }
        });

        InProgressRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InProgressTable.setModel(BugTrackerContr.getInProgressBugs());
            }
        });

        PastBugRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PastBugsTable.setModel(BugTrackerContr.getPastBugs());
            }
        });

        CurrentBugRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentBugTable.setModel(BugTrackerContr.getCurrentBugs());
            }
        });

    }










    public static void main(String args[]) throws SQLException {
        JFrame frame = new JFrame("Bug Tracker");
        frame.setContentPane(new BugTrackerView().bugTrackingWelcome);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    BugTrackerContr.closeConn();
                    System.out.println("Connect closed");
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        BugTrackerContr.MySQLAccess();
    }

}
