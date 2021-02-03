package com.bugtracker.main;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    private JLabel SelectedBugLabel;
    private JButton SelectedBugHome;
    private JScrollPane SelectedBugCard;
    private JTextField SelectedPrioText;
    private JTextField SelectedLangText;
    private JTextArea SelectedDescText;
    private JTextField SelectedFileLocText;
    private JButton workOnBugButton;
    private JButton removeThisTicketButton;
    private JButton editBugButton;
    private JButton SelectedBackButton;
    private JTextField SelectedBugDate;
    private JTextField SelectedFixedBy;
    private JTextField selectedABugToTextField;
    private JButton markAsCompleteButton;
    private JButton completeEditButton;
    private JButton SavePriorityButton;

    BugTrackerContr btc = new BugTrackerContr();
    boolean current = false;
    boolean inProgress = false;
    boolean fixed = false;
    String user;
    String auth;


    public BugTrackerView() {
        LogInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = UsernameText.getText();
                char[] password = PasswordText.getPassword();
                if(BugTrackerContr.logIn(name, password)){
                    ArrayList<String> info = BugTrackerContr.getInfo(name);
                    user = info.get(0);
                    auth = info.get(1);

                    WelcomeCard.setVisible(false);
                    SearchCard.setVisible(true);
                    CurrentBugSearchCard.setVisible(false);
                    PastBugSearchCard.setVisible(false);
                    InProgressSearchCard.setVisible(false);
                    SettingsCard.setVisible(false);
                    CreateATicketCard.setVisible(false);
                    UsernameText.setText("");
                    PasswordText.setText("");
                }
            }
        });

        RegAddUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = RegNameText.getText();
                char[] pass = RegPasswordText.getPassword();
                String user = RegUsernameText.getText();
                String autho = RegAuthText.getSelectedItem().toString();

                try {
                    if (autho == "Administrator") {
                        String checkNew = "Create";
                        boolean firstAdmin = true;
                        //Is there already an admin for the system
                        if (BugTrackerContr.adminExistsCheck()) {
                            checkNew = checkNew.replace("Create", "Enter");
                            firstAdmin = false;
                        }

                        //create the admin password entry box
                        JPanel panel = new JPanel();
                        JLabel label = new JLabel(checkNew + " an admin password:");
                        JPasswordField passw = new JPasswordField(20);
                        panel.add(label);
                        panel.add(passw);
                        String[] options = new String[]{"OK", "Cancel"};
                        int option = JOptionPane.showOptionDialog(null, panel, "Admin Password",
                                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

                        //act based on if password is correct and addition successful
                        if (option == 0) {
                            char[] password = passw.getPassword();
                            if (firstAdmin == true) {
                                BugTrackerContr.addFirstAdmin(password);
                                if (btc.registerAdmin(name, user, pass)) {
                                    JOptionPane.showMessageDialog(null, "First admin added " +
                                            "successfully");
                                    resetRegistration();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Issue adding admin " +
                                            "please try again");
                                }
                            } else {
                                if (BugTrackerContr.adminPasswordCheck(password)) {
                                    if (btc.registerAdmin(name, user, pass)) {
                                        JOptionPane.showMessageDialog(null, "Admin added " +
                                                "successfully");
                                        resetRegistration();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Issue adding " +
                                                "admin please try again");
                                    }
                                }
                                else{
                                    JOptionPane.showMessageDialog(null, "Incorrect Password");
                                }
                            }
                        }
                    } else {
                        if (btc.registerUser(name, user, pass)) {
                            JOptionPane.showMessageDialog(null, "User added successfully");
                            resetRegistration();
                        } else {
                            JOptionPane.showMessageDialog(null, "Issue adding account please " +
                                    "try again");
                        }
                    }
                } catch (SQLException throwables) {
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
                SelectedBugCard.setVisible(false);
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
                SelectedBugCard.setVisible(false);
            }
        });

        BugToFixSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentBugTable.setModel(BugTrackerContr.getCurrentBugs());

                current = true;
                inProgress = false;
                fixed = false;

                WelcomeCard.setVisible(false);
                SearchCard.setVisible(false);
                CurrentBugSearchCard.setVisible(true);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
                SelectedBugCard.setVisible(false);
            }
        });

        BeingFixedSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InProgressTable.setModel(BugTrackerContr.getInProgressBugs());
                inProgress = true;
                current = false;
                fixed = false;

                WelcomeCard.setVisible(false);
                SearchCard.setVisible(false);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(true);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
                SelectedBugCard.setVisible(false);
            }
        });

        SolvedBugSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PastBugsTable.setModel(BugTrackerContr.getPastBugs());
                fixed = true;
                current = false;
                inProgress = false;

                WelcomeCard.setVisible(false);
                SearchCard.setVisible(false);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(true);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
                SelectedBugCard.setVisible(false);
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
                SelectedBugCard.setVisible(false);
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
                SelectedBugCard.setVisible(false);
            }
        });

        HomeButtonCurrent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current = false;
                inProgress = false;
                fixed = false;

                WelcomeCard.setVisible(false);
                SearchCard.setVisible(true);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
                SelectedBugCard.setVisible(false);
            }
        });

        HomeButtonPast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current = false;
                inProgress = false;
                fixed = false;

                WelcomeCard.setVisible(false);
                SearchCard.setVisible(true);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
                SelectedBugCard.setVisible(false);
            }
        });

        CurrentHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current = false;
                inProgress = false;
                fixed = false;

                WelcomeCard.setVisible(false);
                SearchCard.setVisible(true);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
                SelectedBugCard.setVisible(false);
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
                SelectedBugCard.setVisible(false);
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
                SelectedBugCard.setVisible(false);
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
                SelectedBugCard.setVisible(false);
            }
        });

        SelectedBugHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current = false;
                inProgress = false;
                fixed = false;

                WelcomeCard.setVisible(false);
                SearchCard.setVisible(true);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
                SelectedBugCard.setVisible(false);
            }
        });

        SelectedBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToResults();
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
                if (CreateFileLocText.getText().isEmpty()) {
                    file = "N/A";
                } else {
                    file = CreateFileLocText.getText();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new Date());

                int res = JOptionPane.showConfirmDialog(null, "Confirm addition of " +
                        "this ticket", "Confirm", JOptionPane.YES_NO_OPTION);
                if (res == 0) {
                    String added = btc.addTicket(title, lang, prio, desc, file, date);
                    JOptionPane.showMessageDialog(null, added);
                    if (added.contains("Ticket added successfully")) {
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

        CurrentBugTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                current = true;
                int row = CurrentBugTable.getSelectedRow();
                List<String> data = new ArrayList();
                for (int i = 0; i < CurrentBugTable.getColumnCount(); i++) {
                    data.add(CurrentBugTable.getValueAt(row, i).toString());
                }

                WelcomeCard.setVisible(false);
                SearchCard.setVisible(false);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
                SelectedBugCard.setVisible(true);

                workOnBugButton.setVisible(true);
                editBugButton.setVisible(true);
                markAsCompleteButton.setVisible(false);
                completeEditButton.setVisible(false);
                if(auth.contains("1")){
                    removeThisTicketButton.setVisible(true);
                } else{
                    removeThisTicketButton.setVisible(false);
                }

                SelectedBugLabel.setText(data.get(0));
                SelectedLangText.setText(data.get(1));
                SelectedPrioText.setText(data.get(2));
                SelectedDescText.setText(data.get(3));
                SelectedFileLocText.setText(data.get(4));
                SelectedBugDate.setText(data.get(5));
                SelectedFixedBy.setText("");
            }
        });

        PastBugsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                fixed = true;
                int row = PastBugsTable.getSelectedRow();
                List<String> data = new ArrayList();
                for (int i = 0; i < PastBugsTable.getColumnCount(); i++) {
                    data.add(PastBugsTable.getValueAt(row, i).toString());
                }

                WelcomeCard.setVisible(false);
                SearchCard.setVisible(false);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
                SelectedBugCard.setVisible(true);

                workOnBugButton.setVisible(false);
                editBugButton.setVisible(false);
                markAsCompleteButton.setVisible(false);
                completeEditButton.setVisible(false);
                if(auth.contains("1")){
                    removeThisTicketButton.setVisible(true);
                } else{
                    removeThisTicketButton.setVisible(false);
                }

                SelectedBugLabel.setText(data.get(0));
                SelectedLangText.setText(data.get(1));
                SelectedPrioText.setText(data.get(2));
                SelectedDescText.setText(data.get(3));
                SelectedFileLocText.setText(data.get(4));
                SelectedBugDate.setText(data.get(5));
                String fixer = null;
                try {
                    fixer = BugTrackerContr.getFixer(data.get(0), "past_bugs");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                SelectedFixedBy.setText(fixer);
            }
        });

        InProgressTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                inProgress = true;
                int row = InProgressTable.getSelectedRow();
                List<String> data = new ArrayList();
                for (int i = 0; i < InProgressTable.getColumnCount(); i++) {
                    data.add(InProgressTable.getValueAt(row, i).toString());
                }

                WelcomeCard.setVisible(false);
                SearchCard.setVisible(false);
                CurrentBugSearchCard.setVisible(false);
                PastBugSearchCard.setVisible(false);
                InProgressSearchCard.setVisible(false);
                SettingsCard.setVisible(false);
                CreateATicketCard.setVisible(false);
                RegisterCard.setVisible(false);
                SelectedBugCard.setVisible(true);

                workOnBugButton.setVisible(false);
                editBugButton.setVisible(true);
                markAsCompleteButton.setVisible(true);
                completeEditButton.setVisible(false);
                if(auth.contains("1")){
                    removeThisTicketButton.setVisible(true);
                } else{
                    removeThisTicketButton.setVisible(false);
                }

                SelectedBugLabel.setText(data.get(0));
                SelectedLangText.setText(data.get(1));
                SelectedPrioText.setText(data.get(2));
                SelectedDescText.setText(data.get(3));
                SelectedFileLocText.setText(data.get(4));
                SelectedBugDate.setText(data.get(5));
                String fixer = null;
                try {
                    fixer = BugTrackerContr.getFixer(data.get(0), "in_progress");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                SelectedFixedBy.setText(fixer);

            }
        });

        workOnBugButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Are you sure you want to fix this" +
                        "bug?", "Confirm Fix", JOptionPane.YES_NO_OPTION);
                if (res == 0) {
                    String title = SelectedBugLabel.getText();
                    if (BugTrackerContr.updateTicketFixing(title, user)) {
                        JOptionPane.showMessageDialog(null, "You are now fixing this issue");
                        backToResults();
                        CurrentBugTable.setModel(BugTrackerContr.getCurrentBugs());
                    } else {
                        JOptionPane.showMessageDialog(null, "There was an problem, please try " +
                                "again");
                    }
                }
            }
        });

        editBugButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectedPrioText.setEditable(true);
                SelectedDescText.setEditable(true);
                SelectedLangText.setEditable(true);
                SelectedFileLocText.setEditable(true);
                SelectedFixedBy.setEditable(true);

                JOptionPane.showMessageDialog(null, "Please complete your edits and press the " +
                        "edit button again to confirm.");

                editBugButton.setVisible(false);
                workOnBugButton.setVisible(false);
                markAsCompleteButton.setVisible(false);
                removeThisTicketButton.setVisible(false);
                completeEditButton.setVisible(true);

            }
        });

        completeEditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prio = SelectedPrioText.getText();
                String desc = SelectedDescText.getText();
                String lang = SelectedLangText.getText();
                String loc = SelectedFileLocText.getText();
                String fix;
                if (SelectedFixedBy.getText().isEmpty()) {
                    fix = "";
                } else {
                    fix = SelectedFixedBy.getText();
                }
                if (current) {
                    try {
                        if (BugTrackerContr.updateTicket(prio, desc, lang, loc)) {
                            JOptionPane.showMessageDialog(null, "Edit Completed");
                            editBugButton.setVisible(true);
                            workOnBugButton.setVisible(true);
                            markAsCompleteButton.setVisible(true);
                            removeThisTicketButton.setVisible(true);
                            completeEditButton.setVisible(false);

                            SelectedPrioText.setEditable(false);
                            SelectedDescText.setEditable(false);
                            SelectedLangText.setEditable(false);
                            SelectedFileLocText.setEditable(false);
                            SelectedFixedBy.setEditable(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Edit unsuccessful, please" +
                                    " check input and retry");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                if (inProgress) {
                    try {
                        if (BugTrackerContr.updateInProgressTicket(prio, desc, lang, loc, fix)) {
                            JOptionPane.showMessageDialog(null, "Edit Completed");
                            editBugButton.setVisible(true);
                            workOnBugButton.setVisible(true);
                            markAsCompleteButton.setVisible(true);
                            removeThisTicketButton.setVisible(true);
                            completeEditButton.setVisible(false);

                            SelectedPrioText.setEditable(false);
                            SelectedDescText.setEditable(false);
                            SelectedLangText.setEditable(false);
                            SelectedFileLocText.setEditable(false);
                            SelectedFixedBy.setEditable(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Edit unsuccessful, please" +
                                    " check input and retry");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        removeThisTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = SelectedBugLabel.getText();
                int conf = JOptionPane.showConfirmDialog(null, "Please confirm removal of this " +
                        "ticket", "Confirm Removal", JOptionPane.YES_NO_OPTION);
                if (conf == 0) {
                    String table = "";
                    if (current) {
                        table = "current_bugs";
                    }
                    if (inProgress) {
                        table = "in_progress";
                    }
                    if (fixed) {
                        table = "past_bugs";
                    }

                    try {
                        if (BugTrackerContr.removeTicket(title, table)) {
                            JOptionPane.showMessageDialog(null, "Ticket Deleted");
                            backToResults();
                            CurrentBugTable.setModel(BugTrackerContr.getCurrentBugs());
                            InProgressTable.setModel(BugTrackerContr.getInProgressBugs());
                            PastBugsTable.setModel(BugTrackerContr.getPastBugs());
                        } else {
                            JOptionPane.showMessageDialog(null, "Problem removing ticket, " +
                                    "please retry");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        markAsCompleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = SelectedBugLabel.getText();
                int conf = JOptionPane.showConfirmDialog(null, "Please confirm completion of this " +
                        "ticket", "Confirm Completion", JOptionPane.YES_NO_OPTION);
                if (conf == 0) {
                    try {
                        if (BugTrackerContr.markAsComplete(title)) {
                            JOptionPane.showMessageDialog(null, "Completion Successful");
                            backToResults();
                            InProgressTable.setModel(BugTrackerContr.getInProgressBugs());
                        } else {
                            JOptionPane.showMessageDialog(null, "Problem completing ticket, " +
                                    "please retry");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
    }

    public void backToResults(){
        if (current) {
            CurrentBugSearchCard.setVisible(true);
        }
        if (inProgress) {
            InProgressSearchCard.setVisible(true);
        }
        if (fixed) {
            PastBugSearchCard.setVisible(true);
        }
        WelcomeCard.setVisible(false);
        SearchCard.setVisible(false);
        SettingsCard.setVisible(false);
        CreateATicketCard.setVisible(false);
        RegisterCard.setVisible(false);
        SelectedBugCard.setVisible(false);

        current = false;
        inProgress = false;
        fixed = false;
    }

    public void resetRegistration(){
        WelcomeCard.setVisible(true);
        SearchCard.setVisible(false);
        CurrentBugSearchCard.setVisible(false);
        PastBugSearchCard.setVisible(false);
        InProgressSearchCard.setVisible(false);
        SettingsCard.setVisible(false);
        CreateATicketCard.setVisible(false);
        RegNameText.setText("");
        RegPasswordText.setText("");
        RegUsernameText.setText("");
        RegAuthText.setSelectedIndex(0);

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
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        BugTrackerContr.MySQLAccess();
    }
}
