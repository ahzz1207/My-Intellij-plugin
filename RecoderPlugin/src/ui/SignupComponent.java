package ui;

import constants.Constants;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by chenchi on 18/6/7.
 */
public class SignupComponent {
    public JPanel initSignUI(){
         /*declare all components*/
        JPanel outermostPanel = new JPanel();
        JPanel panel = new JPanel();
        panel.setBorder (BorderFactory.createTitledBorder (null,"Create Your Account", TitledBorder.LEADING,TitledBorder.DEFAULT_POSITION,new Font("Times New Roman",1,15),Color.BLACK));
        JPanel userNamePanel = new JPanel();
        JPanel passwordPanel = new JPanel();
        JPanel confirmPasswordPanel = new JPanel();
        JPanel emailPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JLabel userNameLabel = new JLabel();
        JLabel passwordLabel = new JLabel();
        JLabel confirmPasswordLabel = new JLabel();
        JLabel emailLabel = new JLabel();
        JLabel returnLabel = new JLabel();
        JTextField userNameTextField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        JTextField emailTextField = new JTextField();
        JButton registerButton = new JButton();
        /*set the font*/
        userNameLabel.setFont(Constants.font);
        passwordLabel.setFont(Constants.font);
        confirmPasswordLabel.setFont(Constants.font);
        emailLabel.setFont(Constants.font);
        userNameTextField.setFont(Constants.font);
        passwordField.setFont(Constants.font);
        confirmPasswordField.setFont(Constants.font);
        emailTextField.setFont(Constants.font);
        registerButton.setFont(Constants.font);
        returnLabel.setFont(Constants.font);
        /*set the text of label*/
        userNameLabel.setText("Username");
        passwordLabel.setText("Password");
        confirmPasswordLabel.setText("Confirm Password");
        emailLabel.setText("Email Address");
        registerButton.setText("Register");
        returnLabel.setText("<HTML><U>Return to login</U></HTML>");
        /*set color*/
        userNameTextField.setBackground(new Color(253,251,220));
        passwordField.setBackground(new Color(253,251,220));
        confirmPasswordField.setBackground(new Color(253,251,220));
        emailTextField.setBackground(new Color(253,251,220));
        returnLabel.setForeground(Color.blue);
        /*set alignment*/
        userNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        confirmPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        /*set size**/
        userNameLabel.setPreferredSize(new Dimension(120,userNameLabel.getPreferredSize().height));
        passwordLabel.setPreferredSize(new Dimension(120,userNameLabel.getPreferredSize().height));
        confirmPasswordLabel.setPreferredSize(new Dimension(120,userNameLabel.getPreferredSize().height));
        emailLabel.setPreferredSize(new Dimension(120,userNameLabel.getPreferredSize().height));
        userNameTextField.setPreferredSize(new Dimension(200,userNameTextField.getPreferredSize().height));
        passwordField.setPreferredSize(new Dimension(200,passwordField.getPreferredSize().height));
        confirmPasswordField.setPreferredSize(new Dimension(200,userNameTextField.getPreferredSize().height));
        emailTextField.setPreferredSize(new Dimension(200,userNameTextField.getPreferredSize().height));
        /*add in corresponding panel*/
        userNamePanel.add(userNameLabel);
        userNamePanel.add(userNameTextField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        confirmPasswordPanel.add(confirmPasswordLabel);
        confirmPasswordPanel.add(confirmPasswordField);
        emailPanel.add(emailLabel);
        emailPanel.add(emailTextField);
        buttonPanel.add(registerButton);
        buttonPanel.add(returnLabel);
        panel.setLayout(new GridLayout(5,1));
        panel.add(userNamePanel);
        panel.add(passwordPanel);
        panel.add(confirmPasswordPanel);
        panel.add(emailPanel);
        panel.add(buttonPanel);
        JPanel tempPanel = new JPanel();
        outermostPanel.add(tempPanel);
        outermostPanel.add(panel);
        /*set location*/
        GridBagLayout layout = new GridBagLayout();
        outermostPanel.setLayout(layout);
        GridBagConstraints s= new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        s.gridwidth=0;
        s.weightx = 1;
        s.weighty=1;
        layout.setConstraints(tempPanel, s);
        s.gridwidth=0;
        s.weightx = 1;
        s.weighty=0;
        layout.setConstraints(panel, s);
        s.gridwidth=0;
        s.weightx = 1;
        s.weighty=0;
        layout.setConstraints(tempPanel, s);
        /*add listener*/
        registerButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TaskManagerWindowFactory.toolWindowPanel.setVisible(false);
                TaskManagerWindowFactory.toolWindowPanel.removeAll();
                TaskManagerWindowFactory.toolWindowPanel.repaint();
                TaskManagerWindowFactory.toolWindowPanel.add(TaskManagerWindowFactory.toolWindowPanel.add(TaskManagerWindowFactory.loginPanel));
                TaskManagerWindowFactory.toolWindowPanel.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
        });
        returnLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TaskManagerWindowFactory.toolWindowPanel.setVisible(false);
                TaskManagerWindowFactory.toolWindowPanel.removeAll();
                TaskManagerWindowFactory.toolWindowPanel.repaint();
                TaskManagerWindowFactory.toolWindowPanel.add(TaskManagerWindowFactory.toolWindowPanel.add(TaskManagerWindowFactory.loginPanel));
                TaskManagerWindowFactory.toolWindowPanel.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                returnLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                returnLabel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
        });
        return outermostPanel;
    }
}
