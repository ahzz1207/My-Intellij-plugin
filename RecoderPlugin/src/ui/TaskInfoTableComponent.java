package ui;

import constants.Constants;
import taskload.LoadTask;
//import db.UserTaskDBOperation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenchi on 18/5/22.
 */
public class TaskInfoTableComponent {
    private JTable table;

    public JScrollPane initTable(String type) {
        String[] columnNames = {Constants.taskIDLabel, Constants.taskTitleLabel, Constants.taskStateLabel, Constants.taskDescriptionLabel};
        Object[][] data = new Object[TaskManagerWindowFactory.count][4];
        DefaultTableModel dm = new DefaultTableModel();
        dm.setDataVector(data, columnNames);
        table = new JTable(dm) {
            public boolean isCellEditable(int row, int column) {
               return false;
            }//表格不允许被编辑
        };
        table .getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Times New Roman",0,15));
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (table.getSelectedColumn() == 3) {
                    if(TaskManagerWindowFactory.flag) {
                        String id = (String) table.getValueAt(table.getSelectedRow(), 0);
                        if(!TaskManagerWindowFactory.frameExistFlag) {
                            TaskManagerWindowFactory.taskDetailDescriptionComponent = new TaskDetailDescriptionComponent(id);
                            TaskManagerWindowFactory.taskDetailDescriptionComponent.showTaskDetailDescription();
                            TaskManagerWindowFactory.frameExistFlag = true;
                            TaskManagerWindowFactory.logoutFlag = false;
//                        TaskDetailDescriptionComponent taskDetailDescriptionComponent = new TaskDetailDescriptionComponent(id);
//                        taskDetailDescriptionComponent.showTaskDetailDescription();
                        }else{
                            TaskManagerWindowFactory.taskDetailDescriptionComponent.setId(id);
                            TaskManagerWindowFactory.taskDetailDescriptionComponent.refresh();
                        }
                    }else{
                        JOptionPane.getRootFrame().setAlwaysOnTop(true);
                        JOptionPane.showMessageDialog(null, "You are working on a task, thus you are not\nallowed to change to other tasks!\n" +
                                "Please submit or stop current task and then\nchange to other tasks!","Task Warning", JOptionPane.WARNING_MESSAGE);
                        JOptionPane.getRootFrame().setAlwaysOnTop(false);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                table.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
        });
        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = table.getMousePosition();
                if (p != null) {
                    int column = table.columnAtPoint(p);
                    if (column == 3) {
                        table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    } else {
                        table.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                    }
                }
            }
        });
        //UserTaskDBOperation userTaskDbOperation = new UserTaskDBOperation();
        LoadTask loadTask = new LoadTask();
        List<List<String>> initData = new ArrayList<>();
        if(Constants.allTask.equals(type)) {
            //initData = userTaskDbOperation.getAllTaskInfoFromDB(TaskManagerWindowFactory.username);
            initData = loadTask.getAllTaskInfo();
        }else if(Constants.finishedTask.equals(type)){
            //initData = userTaskDbOperation.getFinishedOrUnfinishedTaskInfoFromDB(TaskManagerWindowFactory.username, Constants.finishedState);
            initData = loadTask.getFinishedOrUnfinishedTaskInfo(Constants.finishedState);
        }else if(Constants.unfinishedTask.equals(type)){
            //initData = userTaskDbOperation.getFinishedOrUnfinishedTaskInfoFromDB(TaskManagerWindowFactory.username, Constants.unfinishedState);
            initData = loadTask.getFinishedOrUnfinishedTaskInfo(Constants.unfinishedState);
        }
        int amount = initData.get(0).size();
        int tableWidth = table.getPreferredSize().width;
        TableCellTextAreaRenderer tableCellTextAreaRenderer = new TableCellTextAreaRenderer();
        table.getColumnModel().getColumn(0).setCellRenderer(new TableCellLabelRenderer());
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        //table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(1).setCellRenderer(tableCellTextAreaRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        //table.getColumnModel().getColumn(1).setMinWidth(200);
        table.getColumnModel().getColumn(2).setCellRenderer(new TableCellLabelRenderer());
        table.getColumnModel().getColumn(2).setPreferredWidth(95);
        //table.getColumnModel().getColumn(2).setMinWidth(80);
        table.getColumnModel().getColumn(3).setCellRenderer(new TableCellLabelRenderer(Color.blue));
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        //table.getColumnModel().getColumn(3).setMinWidth(80);
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < 3; j++) {
                table.setValueAt(initData.get(j).get(i), i, j);
            }
            table.setValueAt("<HTML><U>view detail</U></HTML>", i, 3);
        }
        JScrollPane jScrollPane = new JScrollPane(table);
        return jScrollPane;

    }

    public void repaintData(String type) {
        //UserTaskDBOperation userTaskDbOperation = new UserTaskDBOperation();
        LoadTask loadTask = new LoadTask();
        List<List<String>> initData = new ArrayList<>();
        if(Constants.allTask.equals(type)) {
            //initData = userTaskDbOperation.getAllTaskInfoFromDB(TaskManagerWindowFactory.username);
            initData = loadTask.getAllTaskInfo();
        }else if(Constants.finishedTask.equals(type)){
            //initData = userTaskDbOperation.getFinishedOrUnfinishedTaskInfoFromDB(TaskManagerWindowFactory.username, Constants.finishedState);
            initData = loadTask.getFinishedOrUnfinishedTaskInfo(Constants.finishedState);
        }else if(Constants.unfinishedTask.equals(type)){
            //initData = userTaskDbOperation.getFinishedOrUnfinishedTaskInfoFromDB(TaskManagerWindowFactory.username, Constants.unfinishedState);
            initData = loadTask.getFinishedOrUnfinishedTaskInfo(Constants.unfinishedState);
        }
        int amount = initData.get(0).size();
        DefaultTableModel dm = (DefaultTableModel)table.getModel();
        dm.setRowCount(amount);
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < 3; j++) {
                table.setValueAt(initData.get(j).get(i), i, j);
            }
            table.setValueAt("<HTML><U>view detail</U></HTML>", i, 3);
        }
    }
}
