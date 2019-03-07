package ui;

import constants.Constants;

import javax.swing.*;
import javax.swing.plaf.metal.MetalProgressBarUI;
import java.awt.*;

/**
 * Created by chenchi on 18/5/24.
 */
public class TaskProgressComponent {
    private JProgressBar progressBar;

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JPanel initProcess(String type) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(type,JLabel.CENTER);
        label.setFont(new Font("Times New Roman",0,15));
        createProgressBar();
        panel.add(label);
        panel.add(progressBar);
        return panel;
    }

    public void createProgressBar(){
        progressBar = new JProgressBar();
        progressBar.setOrientation(JProgressBar.HORIZONTAL);
        //progressBar.setSize(200, 100);
        progressBar.setMinimum(0);
        progressBar.setBorderPainted(true);
        progressBar.setUI(new MetalProgressBarUI());
        //progressBar.setString("0/50");
        //progressBar.setBackground(Color.green);
        //progressBar.setForeground(Color.BLUE);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Times New Roman",0,15));
    }
}
