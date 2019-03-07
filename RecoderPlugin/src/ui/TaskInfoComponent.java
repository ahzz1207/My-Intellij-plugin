package ui;

import constants.Constants;
import taskload.LoadTask;
//import db.UserTaskDBOperation;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by chenchi on 18/6/7.
 */
public class TaskInfoComponent {

    public JPanel initTaskInfoUI(){
        JPanel panel = new JPanel();
        JPanel welcomePanel = initWelcomePanel();
        JTabbedPane tabbedPane = initJTabbedPane();
        panel.add(welcomePanel);
        panel.add(tabbedPane);
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints s= new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        s.gridwidth=0;
        s.weightx = 1;
        s.weighty=0;
        layout.setConstraints(welcomePanel, s);
        s.gridwidth=0;
        s.weightx = 1;
        s.weighty=1;
        layout.setConstraints(tabbedPane, s);
        return panel;
    }

    public JPanel initWelcomePanel(){
        JPanel panel = new JPanel();
        JLabel welcomeLabel = new JLabel();
        JLabel logoutLabel = new JLabel();
        welcomeLabel.setFont(Constants.font);
        logoutLabel.setFont(Constants.font);
        welcomeLabel.setText("Welcome! Your tasks are shown below.");
        logoutLabel.setText("<HTML><U>Logout</U><HTML>");
        logoutLabel.setForeground(Color.blue);
        panel.add(welcomeLabel);
        panel.add(logoutLabel);
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints s= new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        s.gridwidth=1;
        s.weightx = 1;
        s.weighty=0;
        layout.setConstraints(welcomeLabel, s);
        s.gridwidth=0;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(logoutLabel, s);
//        logoutLabel.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if(TaskManagerWindowFactory.logoutFlag){
//                    TaskManagerWindowFactory.toolWindowPanel.setVisible(false);
//                    TaskManagerWindowFactory.toolWindowPanel.removeAll();
//                    TaskManagerWindowFactory.toolWindowPanel.repaint();
//                    TaskManagerWindowFactory.toolWindowPanel.add(TaskManagerWindowFactory.toolWindowPanel.add(TaskManagerWindowFactory.loginPanel));
//                    TaskManagerWindowFactory.toolWindowPanel.setVisible(true);
//                }else{
//                    JOptionPane.getRootFrame().setAlwaysOnTop(true);
//                    JOptionPane.showMessageDialog(null, "You should first close the window of task\n" +
//                            "detail description and then logout!","Logout Warning", JOptionPane.WARNING_MESSAGE);
//                    JOptionPane.getRootFrame().setAlwaysOnTop(false);
//                }
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
//                logoutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                logoutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
//            }
//        });
        return panel;
    }

    public JTabbedPane initJTabbedPane() {
//        UserTaskDBOperation userTaskDbOperation = new UserTaskDBOperation();
//        TaskManagerWindowFactory.count = userTaskDbOperation.getAllTaskCountFromDB(TaskManagerWindowFactory.username);
        LoadTask loadTask = new LoadTask();
        TaskManagerWindowFactory.count = Constants.count;
        TaskManagerWindowFactory.frameExistFlag = false;
        JScrollPane overallTaskInfoTableScrollPane;
        JScrollPane finishedTaskInfoTableScrollPane;
        JScrollPane unfinishedTaskInfoTableScrollPane;
        JTabbedPane tabbedPane;
        JPanel finishedTaskPanel;
        JPanel finishedTaskProgressPanel;
        JPanel unfinishedTaskPanel;
        JPanel unfinishedTaskProgressPanel;
        /*创建tab pane*/
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Times New Roman", 0, 15));
        /*初始化所有任务信息tab*/
        TaskInfoTableComponent taskOverallInfoTableComponent = new TaskInfoTableComponent();
        overallTaskInfoTableScrollPane = taskOverallInfoTableComponent.initTable(Constants.allTask);
        tabbedPane.addTab(Constants.allTask, overallTaskInfoTableScrollPane);
        /*初始化已完成任务信息tab*/
        finishedTaskPanel = new JPanel();
        GridBagLayout finishedLayout = new GridBagLayout();
        //初始化进度条
        TaskProgressComponent taskFinishedProgressComponent = new TaskProgressComponent();
        finishedTaskProgressPanel = taskFinishedProgressComponent.initProcess(Constants.finishedProgress);
        finishedTaskPanel.add(finishedTaskProgressPanel);
        //初始化已完成任务信息表
        TaskInfoTableComponent taskFinishedInfoTableComponent = new TaskInfoTableComponent();
        finishedTaskInfoTableScrollPane = taskFinishedInfoTableComponent.initTable(Constants.finishedTask);
        finishedTaskPanel.add(finishedTaskInfoTableScrollPane);
        //布局已完成信息任务面板
        finishedTaskPanel.setLayout(finishedLayout);
        GridBagConstraints s = new GridBagConstraints();//定义一个GridBagConstraints，
        s.fill = GridBagConstraints.BOTH;
        //该方法是为了设置如果组件所在的区域比组件本身要大时的显示情况
        //NONE：不调整组件大小。
        //HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
        //VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
        //BOTH：使组件完全填满其显示区域。
        s.gridwidth = 0;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        s.weightx = 1;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        s.weighty = 0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        finishedLayout.setConstraints(finishedTaskProgressPanel, s);//设置组件
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 1;
        finishedLayout.setConstraints(finishedTaskInfoTableScrollPane, s);//设置组件
        tabbedPane.addTab(Constants.finishedTask, finishedTaskPanel);
        /*初始化未完成任务信息tab*/
        unfinishedTaskPanel = new JPanel();
        GridBagLayout unfinishedLayout = new GridBagLayout();
        //初始化进度条
        TaskProgressComponent taskUnfinishedProgressComponent = new TaskProgressComponent();
        unfinishedTaskProgressPanel = taskUnfinishedProgressComponent.initProcess(Constants.unfinishedProgress);
        unfinishedTaskPanel.add(unfinishedTaskProgressPanel);
        //初始化未完成任务信息表
        TaskInfoTableComponent taskUnfinishedInfoTableComponent = new TaskInfoTableComponent();
        unfinishedTaskInfoTableScrollPane = taskUnfinishedInfoTableComponent.initTable(Constants.unfinishedTask);
        unfinishedTaskPanel.add(unfinishedTaskInfoTableScrollPane);
        //布局未完成信息任务面板
        unfinishedTaskPanel.setLayout(unfinishedLayout);
        GridBagConstraints s2 = new GridBagConstraints();//定义一个GridBagConstraints，
        s2.fill = GridBagConstraints.BOTH;
        s2.gridwidth = 0;
        s2.weightx = 1;
        s2.weighty = 0;
        unfinishedLayout.setConstraints(unfinishedTaskProgressPanel, s2);//设置组件
        s2.gridwidth = 0;
        s2.weightx = 1;
        s2.weighty = 1;
        unfinishedLayout.setConstraints(unfinishedTaskInfoTableScrollPane, s2);//设置组件
        tabbedPane.addTab(Constants.unfinishedTask, unfinishedTaskPanel);
        /*添加tab pane面板切换监听器*/
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = tabbedPane.getSelectedIndex();
                if (index == 0) {
                    taskOverallInfoTableComponent.repaintData(Constants.allTask);
                } else if (index == 1) {
                    finishedTaskProgressPanel.repaint();
                    //int finishedCount = userTaskDbOperation.getFinishedOrUnfinishedTaskCountFromDB(TaskManagerWindowFactory.username, Constants.finishedState);
                    int finishedCount = loadTask.getFinishedOrUnfinishedTaskCount(Constants.finishedState);
                    taskFinishedProgressComponent.getProgressBar().setMaximum(TaskManagerWindowFactory.count);
                    taskFinishedProgressComponent.getProgressBar().setValue(finishedCount);
                    taskFinishedProgressComponent.getProgressBar().setString(finishedCount + "/" + TaskManagerWindowFactory.count);
                    taskFinishedInfoTableComponent.repaintData(Constants.finishedTask);
                } else {
                    unfinishedTaskProgressPanel.repaint();
                    //int unfinishedCount = userTaskDbOperation.getFinishedOrUnfinishedTaskCountFromDB(TaskManagerWindowFactory.username, Constants.unfinishedState);
                    int unfinishedCount = loadTask.getFinishedOrUnfinishedTaskCount(Constants.unfinishedState);
                    taskUnfinishedProgressComponent.getProgressBar().setMaximum(TaskManagerWindowFactory.count);
                    taskUnfinishedProgressComponent.getProgressBar().setValue(unfinishedCount);
                    taskUnfinishedProgressComponent.getProgressBar().setString(unfinishedCount + "/" + TaskManagerWindowFactory.count);
                    taskUnfinishedInfoTableComponent.repaintData(Constants.unfinishedTask);
                }
            }
        });
        return tabbedPane;
    }
}
