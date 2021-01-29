package com.bugtracker.main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
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
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String date = sdf.format(new Date());

                int res = JOptionPane.showConfirmDialog(null, "Confirm addition of " +
                                "this ticket", "Confirm", JOptionPane.YES_NO_OPTION);
                if(res == 0) {
                    String added = btc.addTicket(title, lang, prio, desc, file, date);
                    JOptionPane.showMessageDialog(null, added);
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










    public static void main(String args[]) {
        JFrame frame = new JFrame("Bug Tracker");
        frame.setContentPane(new BugTrackerView().bugTrackingWelcome);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
