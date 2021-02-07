package bugtrack;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


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
                if (BugTrackerContr.logIn(name, password)) {
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
                                } else {
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
                if (auth.contains("1")) {
                    removeThisTicketButton.setVisible(true);
                } else {
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
                if (auth.contains("1")) {
                    removeThisTicketButton.setVisible(true);
                } else {
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
                if (auth.contains("1")) {
                    removeThisTicketButton.setVisible(true);
                } else {
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
                            completeEditButton.setVisible(false);
                            if (auth.contains("1")) {
                                removeThisTicketButton.setVisible(true);
                            } else {
                                removeThisTicketButton.setVisible(false);
                            }

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
                            workOnBugButton.setVisible(false);
                            markAsCompleteButton.setVisible(true);
                            completeEditButton.setVisible(false);
                            if (auth.contains("1")) {
                                removeThisTicketButton.setVisible(true);
                            } else {
                                removeThisTicketButton.setVisible(false);
                            }

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

    public void backToResults() {
        if (current) {
            CurrentBugTable.setModel(BugTrackerContr.getCurrentBugs());
            CurrentBugSearchCard.setVisible(true);
        }
        if (inProgress) {
            InProgressTable.setModel(BugTrackerContr.getInProgressBugs());
            InProgressSearchCard.setVisible(true);
        }
        if (fixed) {
            PastBugsTable.setModel(BugTrackerContr.getPastBugs());
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

    public void resetRegistration() {
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        bugTrackingWelcome = new JPanel();
        bugTrackingWelcome.setLayout(new CardLayout(0, 0));
        bugTrackingWelcome.setAutoscrolls(true);
        bugTrackingWelcome.setBackground(new Color(-8148840));
        bugTrackingWelcome.setForeground(new Color(-16777216));
        bugTrackingWelcome.setMaximumSize(new Dimension(600, 800));
        bugTrackingWelcome.setMinimumSize(new Dimension(600, 800));
        bugTrackingWelcome.setOpaque(true);
        bugTrackingWelcome.setPreferredSize(new Dimension(600, 800));
        bugTrackingWelcome.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Bug Tracking", TitledBorder.CENTER, TitledBorder.BELOW_TOP, this.$$$getFont$$$("Fira Code", Font.BOLD, 48, bugTrackingWelcome.getFont()), new Color(-16777216)));
        WelcomeCard = new JScrollPane();
        WelcomeCard.setAutoscrolls(false);
        WelcomeCard.setFocusable(false);
        WelcomeCard.setMinimumSize(new Dimension(514, 123));
        bugTrackingWelcome.add(WelcomeCard, "WelcomePage");
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setAutoscrolls(true);
        panel1.setBackground(new Color(-8148840));
        panel1.setDoubleBuffered(true);
        panel1.setFocusable(false);
        panel1.setForeground(new Color(-8148840));
        WelcomeCard.setViewportView(panel1);
        PasswordLabel = new JLabel();
        PasswordLabel.setAlignmentY(0.1f);
        Font PasswordLabelFont = this.$$$getFont$$$("Arial", Font.BOLD, 18, PasswordLabel.getFont());
        if (PasswordLabelFont != null) PasswordLabel.setFont(PasswordLabelFont);
        PasswordLabel.setForeground(new Color(-16777216));
        PasswordLabel.setText("Password");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        panel1.add(PasswordLabel, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 5;
        gbc.weighty = 2.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer1, gbc);
        Usernamelabel = new JLabel();
        Usernamelabel.setAlignmentY(0.1f);
        Font UsernamelabelFont = this.$$$getFont$$$("Arial", Font.BOLD, 18, Usernamelabel.getFont());
        if (UsernamelabelFont != null) Usernamelabel.setFont(UsernamelabelFont);
        Usernamelabel.setForeground(new Color(-16777216));
        Usernamelabel.setText("Username");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        panel1.add(Usernamelabel, gbc);
        UsernameText = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(UsernameText, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 2.0;
        gbc.weighty = 2.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer3, gbc);
        PasswordText = new JPasswordField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(PasswordText, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer4, gbc);
        registerButton = new JButton();
        registerButton.setText("Register");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 5;
        panel1.add(registerButton, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.weighty = 1.8;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer5, gbc);
        LogInButton = new JButton();
        LogInButton.setAlignmentY(1.0f);
        LogInButton.setMargin(new Insets(0, 0, 0, 0));
        LogInButton.setText("Log In");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 4.0;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 5;
        panel1.add(LogInButton, gbc);
        SearchCard = new JScrollPane();
        SearchCard.setBackground(new Color(-8148840));
        bugTrackingWelcome.add(SearchCard, "Search");
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-8148840));
        SearchCard.setViewportView(panel2);
        SolvedBugSearch = new JButton();
        SolvedBugSearch.setName("SolvedBugSearch");
        SolvedBugSearch.setText("Search for resolved Bug Tickets");
        panel2.add(SolvedBugSearch, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createANewBugButton = new JButton();
        createANewBugButton.setText("Create a new Bug Ticket");
        panel2.add(createANewBugButton, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        BeingFixedSearchButton = new JButton();
        BeingFixedSearchButton.setName("BeingFixedSearchButton");
        BeingFixedSearchButton.setText("Search for Bug Tickets currently under development");
        panel2.add(BeingFixedSearchButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        BugToFixSearchButton = new JButton();
        BugToFixSearchButton.setName("BugToFixSearchButton");
        BugToFixSearchButton.setText("Search for current outstanding Bug Tickets");
        panel2.add(BugToFixSearchButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SettingsButton = new JButton();
        SettingsButton.setName("SettingsButton");
        SettingsButton.setText("Settings");
        panel2.add(SettingsButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        WelcomeTextArea = new JTextArea();
        WelcomeTextArea.setEditable(false);
        WelcomeTextArea.setFocusable(false);
        WelcomeTextArea.setLineWrap(true);
        WelcomeTextArea.setMargin(new Insets(5, 5, 5, 5));
        WelcomeTextArea.setText("Welcome to Bug Tracking v1.0! Please select from the options below to search for the required \"Bug Tickets\". Use the settings to assign priority weights and customise your ticket ordering. ");
        WelcomeTextArea.setWrapStyleWord(true);
        panel2.add(WelcomeTextArea, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        LogOutButton = new JButton();
        LogOutButton.setName("LogOutButton");
        LogOutButton.setText("Log Out");
        panel2.add(LogOutButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CurrentBugSearchCard = new JScrollPane();
        CurrentBugSearchCard.setName("CurrentBugSearchCard");
        bugTrackingWelcome.add(CurrentBugSearchCard, "CurrentBugSearch");
        CurrentBugSearchCard.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, Font.BOLD, -1, CurrentBugSearchCard.getFont()), null));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        CurrentBugSearchCard.setViewportView(panel3);
        HomeButtonCurrent = new JButton();
        HomeButtonCurrent.setName("HomeButton");
        HomeButtonCurrent.setText("Home");
        panel3.add(HomeButtonCurrent, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        CurrentBugTable = new JTable();
        CurrentBugTable.setCellSelectionEnabled(true);
        CurrentBugTable.setFillsViewportHeight(true);
        CurrentBugTable.setFocusable(false);
        CurrentBugTable.setName("CurrentBugTable");
        scrollPane1.setViewportView(CurrentBugTable);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.BOLD, 18, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Current Bug Tickets Awaiting Resolution");
        panel3.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CurrentBugRefresh = new JButton();
        CurrentBugRefresh.setText("Refresh Page");
        panel3.add(CurrentBugRefresh, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selectedABugToTextField = new JTextField();
        Font selectedABugToTextFieldFont = this.$$$getFont$$$(null, -1, 14, selectedABugToTextField.getFont());
        if (selectedABugToTextFieldFont != null) selectedABugToTextField.setFont(selectedABugToTextFieldFont);
        selectedABugToTextField.setText("Selected a bug to view the full ticket details");
        panel3.add(selectedABugToTextField, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        PastBugSearchCard = new JScrollPane();
        PastBugSearchCard.setName("PastBugSearchCard");
        bugTrackingWelcome.add(PastBugSearchCard, "PastBugSearch");
        PastBugSearchCard.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, Font.BOLD, -1, PastBugSearchCard.getFont()), null));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        PastBugSearchCard.setViewportView(panel4);
        HomeButtonPast = new JButton();
        HomeButtonPast.setText("Home");
        panel4.add(HomeButtonPast, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, Font.BOLD, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Resolved Bug Tickets History");
        panel4.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel4.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        PastBugsTable = new JTable();
        PastBugsTable.setInheritsPopupMenu(true);
        PastBugsTable.setName("PastBugsTable");
        scrollPane2.setViewportView(PastBugsTable);
        PastBugRefresh = new JButton();
        PastBugRefresh.setText("Refresh Page");
        panel4.add(PastBugRefresh, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JTextField textField1 = new JTextField();
        Font textField1Font = this.$$$getFont$$$(null, -1, 14, textField1.getFont());
        if (textField1Font != null) textField1.setFont(textField1Font);
        textField1.setText("Selected a bug to view the full ticket details");
        panel4.add(textField1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        InProgressSearchCard = new JScrollPane();
        InProgressSearchCard.setName("InProgressSearchCard");
        bugTrackingWelcome.add(InProgressSearchCard, "InProgressSearch");
        InProgressSearchCard.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, Font.BOLD, -1, InProgressSearchCard.getFont()), null));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        InProgressSearchCard.setViewportView(panel5);
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, Font.BOLD, 18, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Bug Tickets Currently Being Resolved");
        panel5.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel5.add(scrollPane3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        InProgressTable = new JTable();
        InProgressTable.setCellSelectionEnabled(true);
        InProgressTable.setFillsViewportHeight(true);
        InProgressTable.setFocusable(false);
        InProgressTable.setName("InProgressTable");
        scrollPane3.setViewportView(InProgressTable);
        CurrentHomeButton = new JButton();
        CurrentHomeButton.setText("Home");
        panel5.add(CurrentHomeButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        InProgressRefresh = new JButton();
        InProgressRefresh.setText("Refresh Page");
        panel5.add(InProgressRefresh, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JTextField textField2 = new JTextField();
        Font textField2Font = this.$$$getFont$$$(null, -1, 14, textField2.getFont());
        if (textField2Font != null) textField2.setFont(textField2Font);
        textField2.setText("Selected a bug to view the full ticket details");
        panel5.add(textField2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        SettingsCard = new JScrollPane();
        SettingsCard.setName("SettingsCard");
        bugTrackingWelcome.add(SettingsCard, "Settings");
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setBackground(new Color(-8148840));
        SettingsCard.setViewportView(panel6);
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        panel6.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        selectATimeScaleTextArea = new JTextArea();
        selectATimeScaleTextArea.setLineWrap(true);
        selectATimeScaleTextArea.setMargin(new Insets(5, 5, 5, 5));
        selectATimeScaleTextArea.setName("SelectsTimeScaleTextArea");
        selectATimeScaleTextArea.setRequestFocusEnabled(false);
        selectATimeScaleTextArea.setText("");
        selectATimeScaleTextArea.setWrapStyleWord(true);
        panel6.add(selectATimeScaleTextArea, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
        settingsTextField = new JTextField();
        settingsTextField.setBackground(new Color(-8148840));
        Font settingsTextFieldFont = this.$$$getFont$$$(null, Font.BOLD, 20, settingsTextField.getFont());
        if (settingsTextFieldFont != null) settingsTextField.setFont(settingsTextFieldFont);
        settingsTextField.setForeground(new Color(-16777216));
        settingsTextField.setHorizontalAlignment(0);
        settingsTextField.setText("Settings");
        panel6.add(settingsTextField, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        SettingsHomeButton = new JButton();
        SettingsHomeButton.setText("Home");
        panel6.add(SettingsHomeButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CreateATicketCard = new JScrollPane();
        CreateATicketCard.setName("CreateATicketCard");
        bugTrackingWelcome.add(CreateATicketCard, "Card1");
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(8, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel7.setBackground(new Color(-8148840));
        Font panel7Font = this.$$$getFont$$$(null, Font.PLAIN, 14, panel7.getFont());
        if (panel7Font != null) panel7.setFont(panel7Font);
        CreateATicketCard.setViewportView(panel7);
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$(null, Font.BOLD, 20, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-12828863));
        label4.setHorizontalAlignment(0);
        label4.setText("Create a Bug Ticket:");
        panel7.add(label4, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CreateTitleLabel = new JLabel();
        Font CreateTitleLabelFont = this.$$$getFont$$$(null, Font.PLAIN, 14, CreateTitleLabel.getFont());
        if (CreateTitleLabelFont != null) CreateTitleLabel.setFont(CreateTitleLabelFont);
        CreateTitleLabel.setForeground(new Color(-12828863));
        CreateTitleLabel.setInheritsPopupMenu(true);
        CreateTitleLabel.setName("CreateTitleLabel");
        CreateTitleLabel.setText("Title of Issue:");
        panel7.add(CreateTitleLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CreateTitleText = new JTextField();
        CreateTitleText.setName("CreateTitleText");
        panel7.add(CreateTitleText, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        CreateLangText = new JTextField();
        CreateLangText.setName("CreateLangText");
        panel7.add(CreateLangText, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        CreateLangLabel = new JLabel();
        Font CreateLangLabelFont = this.$$$getFont$$$(null, Font.PLAIN, 14, CreateLangLabel.getFont());
        if (CreateLangLabelFont != null) CreateLangLabel.setFont(CreateLangLabelFont);
        CreateLangLabel.setForeground(new Color(-12828863));
        CreateLangLabel.setName("CreateLangLabel");
        CreateLangLabel.setText("Coding Language:");
        panel7.add(CreateLangLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CreatePrioText = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Aesthetic (Minimal)");
        defaultComboBoxModel1.addElement("Low");
        defaultComboBoxModel1.addElement("Medium");
        defaultComboBoxModel1.addElement("High");
        defaultComboBoxModel1.addElement("Very High");
        defaultComboBoxModel1.addElement("Critical");
        CreatePrioText.setModel(defaultComboBoxModel1);
        CreatePrioText.setName("CreatePrioText");
        panel7.add(CreatePrioText, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CreatePrioLabel = new JLabel();
        Font CreatePrioLabelFont = this.$$$getFont$$$(null, Font.PLAIN, 14, CreatePrioLabel.getFont());
        if (CreatePrioLabelFont != null) CreatePrioLabel.setFont(CreatePrioLabelFont);
        CreatePrioLabel.setForeground(new Color(-12828863));
        CreatePrioLabel.setName("CreatePrioLabel");
        CreatePrioLabel.setText("Priority:");
        panel7.add(CreatePrioLabel, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CreateDescrLabel = new JLabel();
        Font CreateDescrLabelFont = this.$$$getFont$$$(null, Font.PLAIN, 14, CreateDescrLabel.getFont());
        if (CreateDescrLabelFont != null) CreateDescrLabel.setFont(CreateDescrLabelFont);
        CreateDescrLabel.setForeground(new Color(-12828863));
        CreateDescrLabel.setName("CreateDescrLabel");
        CreateDescrLabel.setText("Description of Issue:");
        panel7.add(CreateDescrLabel, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CreateFileLocText = new JTextField();
        CreateFileLocText.setName("CreateFileLocText");
        panel7.add(CreateFileLocText, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        CreateFileLocLabel = new JLabel();
        Font CreateFileLocLabelFont = this.$$$getFont$$$(null, Font.PLAIN, 14, CreateFileLocLabel.getFont());
        if (CreateFileLocLabelFont != null) CreateFileLocLabel.setFont(CreateFileLocLabelFont);
        CreateFileLocLabel.setForeground(new Color(-12828863));
        CreateFileLocLabel.setName("CreateFileLocLabel");
        CreateFileLocLabel.setText("File error occured in:");
        panel7.add(CreateFileLocLabel, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        createTicketButton = new JButton();
        createTicketButton.setText("Create Ticket ");
        panel7.add(createTicketButton, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        TicketHomeButton = new JButton();
        TicketHomeButton.setText("Home");
        panel7.add(TicketHomeButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane4 = new JScrollPane();
        scrollPane4.setAutoscrolls(true);
        panel7.add(scrollPane4, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        CreateDescrText = new JTextArea();
        CreateDescrText.setAutoscrolls(true);
        CreateDescrText.setEditable(true);
        CreateDescrText.setLineWrap(true);
        CreateDescrText.setMargin(new Insets(5, 5, 5, 5));
        CreateDescrText.setName("CreateDescrText");
        CreateDescrText.setRequestFocusEnabled(true);
        CreateDescrText.setText("");
        CreateDescrText.setToolTipText("Describe the step you took that led to the issue as well as what the issue was and did/caused.");
        CreateDescrText.setVerifyInputWhenFocusTarget(false);
        CreateDescrText.setWrapStyleWord(true);
        scrollPane4.setViewportView(CreateDescrText);
        RegisterCard = new JScrollPane();
        RegisterCard.setBackground(new Color(-8148840));
        bugTrackingWelcome.add(RegisterCard, "Card2");
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel8.setBackground(new Color(-8148840));
        RegisterCard.setViewportView(panel8);
        RegisterNameLabel = new JLabel();
        RegisterNameLabel.setText("Name");
        panel8.add(RegisterNameLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Username");
        panel8.add(label5, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        RegUsernameText = new JTextField();
        panel8.add(RegUsernameText, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        RegNameText = new JTextField();
        panel8.add(RegNameText, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        RegPasswordText = new JPasswordField();
        panel8.add(RegPasswordText, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        RegAuthText = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Developer (Default)");
        defaultComboBoxModel2.addElement("Administrator");
        RegAuthText.setModel(defaultComboBoxModel2);
        panel8.add(RegAuthText, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Password");
        panel8.add(label6, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        RegAddUserButton = new JButton();
        RegAddUserButton.setText("Submit");
        panel8.add(RegAddUserButton, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Auth Level");
        panel8.add(label7, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        RegisterHeading = new JTextField();
        RegisterHeading.setBackground(new Color(-8148840));
        Font RegisterHeadingFont = this.$$$getFont$$$(null, Font.BOLD, 20, RegisterHeading.getFont());
        if (RegisterHeadingFont != null) RegisterHeading.setFont(RegisterHeadingFont);
        RegisterHeading.setForeground(new Color(-16777216));
        RegisterHeading.setHorizontalAlignment(0);
        RegisterHeading.setText("Register Your Account");
        panel8.add(RegisterHeading, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        RegHomeButton = new JButton();
        RegHomeButton.setText("Home");
        panel8.add(RegHomeButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SelectedBugCard = new JScrollPane();
        bugTrackingWelcome.add(SelectedBugCard, "Card3");
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(22, 21, new Insets(0, 0, 0, 0), -1, -1));
        panel9.setAlignmentX(1.0f);
        panel9.setAlignmentY(1.0f);
        panel9.setBackground(new Color(-8148840));
        SelectedBugCard.setViewportView(panel9);
        SelectedPrioText = new JTextField();
        SelectedPrioText.setEditable(false);
        panel9.add(SelectedPrioText, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 20, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 1, false));
        final JLabel label8 = new JLabel();
        label8.setAlignmentX(1.0f);
        Font label8Font = this.$$$getFont$$$(null, Font.BOLD, 14, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setForeground(new Color(-16777216));
        label8.setHorizontalAlignment(10);
        label8.setText("Priority Of Issue:");
        panel9.add(label8, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 20, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        SelectedDescText = new JTextArea();
        SelectedDescText.setEditable(false);
        SelectedDescText.setLineWrap(true);
        SelectedDescText.setWrapStyleWord(true);
        panel9.add(SelectedDescText, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 4, 20, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 1, false));
        final JLabel label9 = new JLabel();
        label9.setAlignmentX(1.0f);
        Font label9Font = this.$$$getFont$$$(null, Font.BOLD, 14, label9.getFont());
        if (label9Font != null) label9.setFont(label9Font);
        label9.setForeground(new Color(-16777216));
        label9.setHorizontalAlignment(10);
        label9.setText("Description Of Issue");
        panel9.add(label9, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 20, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JLabel label10 = new JLabel();
        label10.setAlignmentX(1.0f);
        Font label10Font = this.$$$getFont$$$(null, Font.BOLD, 14, label10.getFont());
        if (label10Font != null) label10.setFont(label10Font);
        label10.setForeground(new Color(-16777216));
        label10.setHorizontalAlignment(10);
        label10.setText("Language Used:");
        panel9.add(label10, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 20, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        SelectedLangText = new JTextField();
        SelectedLangText.setEditable(false);
        panel9.add(SelectedLangText, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 20, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 1, false));
        final JLabel label11 = new JLabel();
        label11.setAlignmentX(1.0f);
        Font label11Font = this.$$$getFont$$$(null, Font.BOLD, 14, label11.getFont());
        if (label11Font != null) label11.setFont(label11Font);
        label11.setForeground(new Color(-16777216));
        label11.setHorizontalAlignment(10);
        label11.setText("File Location of Issue:");
        panel9.add(label11, new com.intellij.uiDesigner.core.GridConstraints(10, 0, 1, 20, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        SelectedFileLocText = new JTextField();
        SelectedFileLocText.setEditable(false);
        panel9.add(SelectedFileLocText, new com.intellij.uiDesigner.core.GridConstraints(11, 0, 1, 20, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 1, false));
        SelectedBackButton = new JButton();
        SelectedBackButton.setText("Back to Results");
        panel9.add(SelectedBackButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setAlignmentX(1.0f);
        Font label12Font = this.$$$getFont$$$(null, Font.BOLD, 14, label12.getFont());
        if (label12Font != null) label12.setFont(label12Font);
        label12.setForeground(new Color(-16777216));
        label12.setHorizontalAlignment(10);
        label12.setText("Date of Discovery");
        panel9.add(label12, new com.intellij.uiDesigner.core.GridConstraints(12, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        SelectedBugDate = new JTextField();
        SelectedBugDate.setEditable(false);
        panel9.add(SelectedBugDate, new com.intellij.uiDesigner.core.GridConstraints(13, 0, 9, 20, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 1, false));
        removeThisTicketButton = new JButton();
        removeThisTicketButton.setText("Remove This Ticket");
        panel9.add(removeThisTicketButton, new com.intellij.uiDesigner.core.GridConstraints(15, 20, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        editBugButton = new JButton();
        editBugButton.setText("Edit Bug");
        panel9.add(editBugButton, new com.intellij.uiDesigner.core.GridConstraints(14, 20, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        workOnBugButton = new JButton();
        workOnBugButton.setText("Work on Bug");
        panel9.add(workOnBugButton, new com.intellij.uiDesigner.core.GridConstraints(13, 20, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JLabel label13 = new JLabel();
        Font label13Font = this.$$$getFont$$$(null, Font.BOLD, 14, label13.getFont());
        if (label13Font != null) label13.setFont(label13Font);
        label13.setForeground(new Color(-16777216));
        label13.setText("Being Fixed By:");
        panel9.add(label13, new com.intellij.uiDesigner.core.GridConstraints(1, 20, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SelectedFixedBy = new JTextField();
        SelectedFixedBy.setEditable(false);
        panel9.add(SelectedFixedBy, new com.intellij.uiDesigner.core.GridConstraints(2, 20, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setEnabled(true);
        label14.setText("");
        panel9.add(label14, new com.intellij.uiDesigner.core.GridConstraints(5, 20, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SelectedBugLabel = new JLabel();
        SelectedBugLabel.setBackground(new Color(-8148840));
        Font SelectedBugLabelFont = this.$$$getFont$$$(null, Font.BOLD, 22, SelectedBugLabel.getFont());
        if (SelectedBugLabelFont != null) SelectedBugLabel.setFont(SelectedBugLabelFont);
        SelectedBugLabel.setForeground(new Color(-12828863));
        SelectedBugLabel.setHorizontalAlignment(0);
        SelectedBugLabel.setText("Selected Bug");
        panel9.add(SelectedBugLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 8, 1, 9, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        markAsCompleteButton = new JButton();
        markAsCompleteButton.setText("Mark As Complete");
        panel9.add(markAsCompleteButton, new com.intellij.uiDesigner.core.GridConstraints(16, 20, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        SelectedBugHome = new JButton();
        SelectedBugHome.setText("Home");
        panel9.add(SelectedBugHome, new com.intellij.uiDesigner.core.GridConstraints(0, 20, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        completeEditButton = new JButton();
        completeEditButton.setText("Complete Edit");
        panel9.add(completeEditButton, new com.intellij.uiDesigner.core.GridConstraints(9, 20, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CreateTitleLabel.setLabelFor(CreateTitleText);
        CreateLangLabel.setLabelFor(CreateLangText);
        CreatePrioLabel.setLabelFor(CreatePrioText);
        CreateDescrLabel.setLabelFor(CreateDescrText);
        CreateFileLocLabel.setLabelFor(CreateFileLocText);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return bugTrackingWelcome;
    }

}
