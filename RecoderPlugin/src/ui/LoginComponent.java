//package ui;
//
//import constants.Constants;
//import db.LoginDBOperation;
//import db.UserTaskDBOperation;
//
//import javax.swing.*;
//import javax.swing.border.TitledBorder;
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.util.List;
//
///**
// * Created by chenchi on 18/6/7.
// */
//public class LoginComponent {
//    private LoginDBOperation loginDBOperation = new LoginDBOperation();
//
//    public JPanel initLoginUI() {
//        /*declare all components*/
//        JPanel outermostPanel = new JPanel();
//        JPanel panel = new JPanel();
//        panel.setBorder(BorderFactory.createTitledBorder(null, "Login Task Manager", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Times New Roman", 1, 15), Color.BLACK));
//        JPanel userNamePanel = new JPanel();
//        JPanel passwordPanel = new JPanel();
//        JPanel loginPanel = new JPanel();
//        JPanel signPanel = new JPanel();
//        JLabel welcomeLabel = new JLabel();
//        JLabel userNameLabel = new JLabel();
//        JLabel passwordLabel = new JLabel();
//        JTextField userNameTextField = new JTextField();
//        JPasswordField passwordField = new JPasswordField();
//        JButton loginButton = new JButton();
//        JLabel forgetLabel = new JLabel();
//        JLabel createLabel = new JLabel();
//        JLabel signLabel = new JLabel();
//        /*set the font*/
//        userNameLabel.setFont(Constants.font);
//        passwordLabel.setFont(Constants.font);
//        userNameTextField.setFont(Constants.font);
//        passwordField.setFont(Constants.font);
//        loginButton.setFont(Constants.font);
//        forgetLabel.setFont(Constants.font);
//        createLabel.setFont(Constants.font);
//        signLabel.setFont(Constants.font);
//        /*set the text of label*/
//        welcomeLabel.setText("Login Task Manager");
//        userNameLabel.setText("Username");
//        passwordLabel.setText("Password");
//        loginButton.setText("Login");
//        forgetLabel.setText("<HTML><U>Forgot your password?</U></HTML>");
//        createLabel.setText("Need an account?");
//        signLabel.setText("<HTML><U>Sign up</U></HTML>");
//        /*set alignment*/
//        userNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//        /*set color*/
//        userNameTextField.setBackground(new Color(253, 251, 220));
//        passwordField.setBackground(new Color(253, 251, 220));
//        welcomeLabel.setForeground(Color.blue);
//        forgetLabel.setForeground(Color.blue);
//        signLabel.setForeground(Color.blue);
//        /*set size**/
//        userNameLabel.setPreferredSize(new Dimension(120, userNameLabel.getPreferredSize().height));
//        passwordLabel.setPreferredSize(new Dimension(120, userNameLabel.getPreferredSize().height));
//        userNameTextField.setPreferredSize(new Dimension(200, userNameTextField.getPreferredSize().height));
//        passwordField.setPreferredSize(new Dimension(200, passwordField.getPreferredSize().height));
//        /*add in corresponding panel*/
//        userNamePanel.add(userNameLabel);
//        userNamePanel.add(userNameTextField);
//        passwordPanel.add(passwordLabel);
//        passwordPanel.add(passwordField);
//        loginPanel.add(loginButton);
//        loginPanel.add(forgetLabel);
//        signPanel.add(createLabel);
//        signPanel.add(signLabel);
//        panel.setLayout(new GridLayout(4, 1));
//        panel.add(userNamePanel);
//        panel.add(passwordPanel);
//        panel.add(loginPanel);
//        panel.add(signPanel);
//        JPanel tempPanel = new JPanel();
//        outermostPanel.add(tempPanel);
//        outermostPanel.add(panel);
//        /*set location*/
//        GridBagLayout layout = new GridBagLayout();
//        outermostPanel.setLayout(layout);
//        GridBagConstraints s = new GridBagConstraints();
//        s.fill = GridBagConstraints.BOTH;
//        s.gridwidth = 0;
//        s.weightx = 1;
//        s.weighty = 1;
//        layout.setConstraints(tempPanel, s);
//        s.gridwidth = 0;
//        s.weightx = 1;
//        s.weighty = 0;
//        layout.setConstraints(panel, s);
//        s.gridwidth = 0;
//        s.weightx = 1;
//        s.weighty = 0;
//        layout.setConstraints(tempPanel, s);
//        /*add listener*/
//        /*
//        forgetLabel.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                forgetLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                forgetLabel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
//            }
//        });
//        signLabel.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                TaskManagerWindowFactory.toolWindowPanel.setVisible(false);
//                TaskManagerWindowFactory.toolWindowPanel.removeAll();
//                TaskManagerWindowFactory.toolWindowPanel.repaint();
//                TaskManagerWindowFactory.toolWindowPanel.add(TaskManagerWindowFactory.toolWindowPanel.add(TaskManagerWindowFactory.signPanel));
//                TaskManagerWindowFactory.toolWindowPanel.setVisible(true);
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                signLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                signLabel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
//            }
//        });
//        */
//        loginButton.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                String username = userNameTextField.getText();
//                String password = passwordField.getText();
//                loginClickEvent(username, password);
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
//            }
//        });
//        return outermostPanel;
//    }
//
//    /**
//     * judge whether the username is exit
//     *
//     * @param username
//     * @return
//     */
//    private boolean isUsernameCorrect(String username) {
//        if (username != null) {
//            List<String> usernames = loginDBOperation.getALLUserNamesFromDB();
//            if (usernames.contains(username)) {
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * judge whether the password is correct
//     *
//     * @param username
//     * @param password
//     * @return
//     */
//    private boolean isPasswordCorrect(String username, String password) {
//        String correctPassword = loginDBOperation.getPasswordFromDB(username);
//        if (correctPassword != null) {
//            if (correctPassword.equals(password)) {
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * login button click
//     *
//     * @param username
//     * @param password
//     */
//    private void loginClickEvent(String username, String password) {
//        if (isPasswordCorrect(username, password)) {
//            TaskManagerWindowFactory.toolWindowPanel.setVisible(false);
//            TaskManagerWindowFactory.toolWindowPanel.removeAll();
//            TaskManagerWindowFactory.toolWindowPanel.repaint();
//            TaskManagerWindowFactory.username = username;
//            UserTaskDBOperation userTaskDBOperation = new UserTaskDBOperation();
//            List<String> ids = userTaskDBOperation.getAllTaskIDFromDB(username);
//            for(int i = 0; i < ids.size(); i ++ ){
//                TaskManagerWindowFactory.taskIDMap.put(ids.get(i), Integer.toString(i+1));
//                TaskManagerWindowFactory.reverseTaskIDMap.put(Integer.toString(i+1),ids.get(i));
//            }
//            TaskManagerWindowFactory.initTaskInfoUI();
//            TaskManagerWindowFactory.toolWindowPanel.add(TaskManagerWindowFactory.toolWindowPanel.add(TaskManagerWindowFactory.taskInfoPanel));
//            TaskManagerWindowFactory.toolWindowPanel.setVisible(true);
//        } else {
//            JOptionPane.getRootFrame().setAlwaysOnTop(true);
//            JOptionPane.showMessageDialog(null, "Your username or password is incorrect!", "", JOptionPane.WARNING_MESSAGE);
//            JOptionPane.getRootFrame().setAlwaysOnTop(false);
//
//        }
//
//    }
//}
