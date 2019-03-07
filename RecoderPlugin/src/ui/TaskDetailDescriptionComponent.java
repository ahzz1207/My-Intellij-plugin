package ui;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import constants.Constants;
//import db.UserTaskDBOperation;
import record.Closed;
import record.Init;
import service.ConfigureCheck;
import service.TaskFileHandler;
import service.TaskManagerSetting;
import service.TaskProjectHandler;
import taskload.LoadTask;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Date;
import java.util.List;

/**
 * Created by chenchi on 18/6/1.
 */
public class TaskDetailDescriptionComponent {
    private int totalTaskCount;
    private String id;
    private String title;
    private String description;
    private String state;
    private JPanel panel;
    private JButton startButton;
    private JButton submitButton;
    private JButton stopButton;
    private JLabel previousLabel;
    private JLabel nextLabel;
    private JFrame frame;

    public void setId(String id) {
        this.id = id;
    }

    public TaskDetailDescriptionComponent(String id) {
        this.id = id;
        panel = new JPanel();
        startButton = new JButton();
        submitButton = new JButton();
        stopButton = new JButton();
        submitButton.setEnabled(false);
        stopButton.setEnabled(false);
        nextLabel = new JLabel("<HTML><U>Next Task</U></HTML>");
        previousLabel = new JLabel("<HTML><U>Previous Task</U></HTML>");
        totalTaskCount = Constants.count;
        // UserTaskDBOperation userTaskDbOperation = new UserTaskDBOperation();
        // totalTaskCount = userTaskDbOperation.getAllTaskCountFromDB(TaskManagerWindowFactory.username);
    }

    public void showTaskDetailDescription() {
        /*declare frame*/
        frame = new JFrame();
        frame.setSize(500, 450);
        frame.setTitle("Detail Description of Task_" + id);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                TaskManagerWindowFactory.frameExistFlag = false;
                TaskManagerWindowFactory.flag = true;
                TaskManagerWindowFactory.logoutFlag = true;
                frame.dispose();
            }
        });
        /*set frame to show in center*/
        int windowWidth = frame.getWidth();
        int windowHeight = frame.getHeight();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
        /*init all components*/
        init();
        /*add panel into frame*/
        frame.add(panel);
        frame.setVisible(true);
        /*add button listener*/
        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!Constants.finishedState.equals(state)) {
                    frame.setAlwaysOnTop(false);
                    //ConfigureCheck configureCheck = new ConfigureCheck();
                    //if(configureCheck.checkConfigurations()) {
                    frame.setAlwaysOnTop(true);
                    previousLabel.setEnabled(false);
                    nextLabel.setEnabled(false);
                    startButton.setEnabled(false);
                    submitButton.setEnabled(true);
                    stopButton.setEnabled(true);
                    TaskManagerWindowFactory.flag = false;
                    startButtonClickedEvent();
                }
                //}else{
                //    frame.setAlwaysOnTop(true);
                //}
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
        });
        submitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!Constants.finishedState.equals(state)) {
                    TaskManagerSetting setting = TaskManagerSetting.getInstance();
                    String path = setting.getLD() + File.separator + "Task" + id + "Log" + File.separator + "time.txt";
                    Timestamp date = new Timestamp(System.currentTimeMillis());
                    try {
                        FileWriter writer = new FileWriter(path, true);
                        writer.write("submit " + date + "\r\n");
                        writer.close();
                    } catch (Exception ee) {

                    } catch (Error ee) {

                    }
                    previousLabel.setEnabled(true);
                    nextLabel.setEnabled(true);
                    startButton.setEnabled(false);
                    submitButton.setEnabled(false);
                    stopButton.setEnabled(false);
                    TaskManagerWindowFactory.flag = true;
                    submitButtonClickedEvent();
                    state = Constants.finishedState;
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
                submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
        });
        stopButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!Constants.finishedState.equals(state)) {
                    TaskManagerSetting setting = TaskManagerSetting.getInstance();
                    String path = setting.getLD() + File.separator + "Task" + id + "Log" + File.separator + "time.txt";
                    Timestamp date = new Timestamp(System.currentTimeMillis());
                    try {
                        FileWriter writer = new FileWriter(path, true);
                        writer.write("stop " + date + "\r\n");
                        writer.close();
                    } catch (Exception ee) {

                    } catch (Error ee) {

                    }
                    previousLabel.setEnabled(true);
                    nextLabel.setEnabled(true);
                    startButton.setEnabled(true);
                    submitButton.setEnabled(false);
                    stopButton.setEnabled(false);
                    TaskManagerWindowFactory.flag = true;
                    stopButtonClickedEvent(false);
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
                stopButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                stopButton.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
        });
        /*add label listener*/
        previousLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (previousLabel.isEnabled()) {
                    int pageID = Integer.parseInt(TaskManagerWindowFactory.taskIDMap.get(id)) - 1;
                    if (pageID > 0) {
                        id = TaskManagerWindowFactory.reverseTaskIDMap.get(Integer.toString(pageID));
                        refresh();
//                        getValue();
//                        init();
//                        frame.setTitle("Detail Description of Task_" + id);
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
                previousLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                previousLabel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
        });
        nextLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nextLabel.isEnabled()) {
                    int pageID = Integer.parseInt(TaskManagerWindowFactory.taskIDMap.get(id)) + 1;
                    if (pageID <= totalTaskCount) {
                        id = TaskManagerWindowFactory.reverseTaskIDMap.get(Integer.toString(pageID));
                        refresh();
//                        getValue();
//                        init();
//                        frame.setTitle("Detail Description of Task_" + id);
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
                nextLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                nextLabel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
        });
    }

    private void getValue() {
        LoadTask loadTask = new LoadTask();
        List<String> value = loadTask.getTaskInfo(id);
//        UserTaskDBOperation userTaskDbOperation = new UserTaskDBOperation();
//        List<String> value = userTaskDbOperation.getTaskInfoFromDB(TaskManagerWindowFactory.username, id);
        title = value.get(0);
        state = value.get(2);
        description = value.get(1);
    }

    private int getLineNumbers(JTextArea textArea, int width) {
        AttributedString text = new AttributedString(textArea.getText());
        text.addAttribute(TextAttribute.FONT, textArea.getFont());
        FontRenderContext frc = textArea.getFontMetrics(textArea.getFont())
                .getFontRenderContext();
        AttributedCharacterIterator charIt = text.getIterator();
        LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(charIt, frc);
        //float formatWidth = (float) textArea.getPreferredSize().width;
        float formatWidth = (float) width;
        lineMeasurer.setPosition(charIt.getBeginIndex());

        int lineCount = 0;
        while (lineMeasurer.getPosition() < charIt.getEndIndex()) {
            lineMeasurer.nextLayout(formatWidth);
            lineCount++;
        }
        return lineCount;
    }

    private void startButtonClickedEvent() {
        //TaskFileHandler taskFileHandler = new TaskFileHandler();
        //taskFileHandler.openTaskFile("Task"+id);
        String[] filePath = new String[5];
        TaskManagerSetting setting = TaskManagerSetting.getInstance();
        try {
            TaskProjectHandler taskProjectHandler = new TaskProjectHandler();
            taskProjectHandler.openProject(setting.getPD() + "/Task" + id);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        createDic(setting.getLD() + File.separator + "Task" + id + "Log");
        filePath[0] = setting.getLD() + File.separator + "Task" + id + "Log" + File.separator + "statement.txt";
        filePath[1] = setting.getLD() + File.separator + "Task" + id + "Log" + File.separator + "char.txt";
        filePath[2] = setting.getLD() + File.separator + "Task" + id + "Log" + File.separator + "flag.txt";
        filePath[3] = setting.getLD() + File.separator + "Task" + id + "Log" + File.separator + "result";
        filePath[4] = setting.getLD() + File.separator + "Task" + id + "Log" + File.separator + "result" + File.separator + "resultMap.txt";
        boolean flag = createFile(filePath);
        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        FileEditorManager manager = FileEditorManager.getInstance(project);
        //Editor editor =  manager.getSelectedTextEditor();
        //Init init = new Init(filePath,project, editor,flag);
        Init init = new Init(filePath, project, flag);
        String path = setting.getLD() + File.separator + "Task" + id + "Log" + File.separator + "time.txt";
        Timestamp date = new Timestamp(System.currentTimeMillis());
        try {
            FileWriter writer = new FileWriter(path, true);
            writer.write("start " + date + "\r\n");
            writer.close();
        } catch (Exception ee) {

        } catch (Error ee) {

        }
    }

    private void submitButtonClickedEvent() {
        LoadTask loadTask = new LoadTask();
        loadTask.updateData(id);
        stopButtonClickedEvent(true);
    }

    private void stopButtonClickedEvent(boolean flag) {
        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        FileEditorManager manager = FileEditorManager.getInstance(project);
        Editor editor = manager.getSelectedTextEditor();
        Closed closed = new Closed(editor, flag);
    }

    private void init() {
        panel.setVisible(false);
        panel.removeAll();
        panel.repaint();
        panel.setVisible(true);
        startButton.setText("Start Task_" + id);
        submitButton.setText("Submit Task_" + id);
        stopButton.setText("Stop Task_" + id);
        //declare the components
        panel.setBackground(Color.lightGray);
        JPanel taskInfoPanel = new JPanel();
        taskInfoPanel.setBackground(Color.white);
        taskInfoPanel.setSize(500, 350);
        JPanel taskIDPanel = new JPanel();
        taskIDPanel.setBorder(BorderFactory.createTitledBorder(null, Constants.taskIDLabel + ":", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Times New Roman", 1, 15), Color.BLACK));
        taskIDPanel.setBackground(Color.white);
        JPanel taskTitlePanel = new JPanel();
        taskTitlePanel.setBorder(BorderFactory.createTitledBorder(null, Constants.taskTitleLabel + ":", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Times New Roman", 1, 15), Color.BLACK));
        taskTitlePanel.setBackground(Color.white);
        JPanel taskStatePanel = new JPanel();
        taskStatePanel.setBorder(BorderFactory.createTitledBorder(null, Constants.taskStateLabel + ":", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Times New Roman", 1, 15), Color.BLACK));
        taskStatePanel.setBackground(Color.white);
        JPanel taskDescriptionPanel = new JPanel();
        taskDescriptionPanel.setBorder(BorderFactory.createTitledBorder(null, Constants.taskDescriptionLabel + ":", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Times New Roman", 1, 15), Color.BLACK));
        taskDescriptionPanel.setBackground(Color.white);
        JLabel taskID = new JLabel();
        JLabel taskState = new JLabel();
        JTextArea taskTitleArea = new JTextArea();
        JTextArea taskDescriptionArea = new JTextArea();
        taskTitleArea.setLineWrap(true);
        taskDescriptionArea.setLineWrap(true);
        taskTitleArea.setRows(2);
        taskDescriptionArea.setRows(8);
        JScrollPane taskTitleScroll = new JScrollPane(taskTitleArea);
        JScrollPane taskDescriptionScroll = new JScrollPane(taskDescriptionArea);
        taskTitleScroll.setBorder(null);
        taskDescriptionScroll.setBorder(null);
        JPanel pagePanel = new JPanel();
        pagePanel.setBackground(Color.white);
        pagePanel.setSize(500, 50);
        JLabel pageShowLabel = new JLabel();
        nextLabel.setForeground(Color.blue);
        previousLabel.setForeground(Color.blue);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);
        buttonPanel.setSize(500, 50);
        startButton.setBackground(Color.white);
        submitButton.setBackground(Color.white);
        stopButton.setBackground(Color.white);
        /*set the font*/
        taskID.setFont(new Font("Times New Roman", 0, 15));
        taskState.setFont(new Font("Times New Roman", 0, 15));
        taskTitleArea.setFont(new Font("Times New Roman", 0, 15));
        taskDescriptionArea.setFont(new Font("Times New Roman", 0, 15));
        pageShowLabel.setFont(new Font("Times New Roman", 0, 15));
        previousLabel.setFont(new Font("Times New Roman", 0, 15));
        nextLabel.setFont(new Font("Times New Roman", 0, 15));
        startButton.setFont(new Font("Times New Roman", 0, 15));
        submitButton.setFont(new Font("Times New Roman", 0, 15));
        stopButton.setFont(new Font("Times New Roman", 0, 15));
        /*set the value(text) of each component*/
        getValue();
        if (Constants.finishedState.equals(state)) {
            startButton.setEnabled(false);
        } else {
            startButton.setEnabled(true);
        }
        taskID.setText("task_" + id);
        taskTitleArea.setText(title);
        taskTitleArea.setEditable(false);
        taskState.setText(state);
        taskDescriptionArea.setText(description);
        taskDescriptionArea.setEditable(false);
        taskDescriptionScroll.doLayout();
        taskTitleArea.setCaretPosition(0);
        taskDescriptionArea.setCaretPosition(0);
        pageShowLabel.setText(TaskManagerWindowFactory.taskIDMap.get(id) + "/" + totalTaskCount);
        /*add all components into panels*/
        taskIDPanel.add(taskID);
        taskTitlePanel.add(taskTitleScroll);
        taskStatePanel.add(taskState);
        taskDescriptionPanel.add(taskDescriptionScroll);
        buttonPanel.add(startButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(stopButton);
        taskInfoPanel.add(taskIDPanel);
        taskInfoPanel.add(taskTitlePanel);
        taskInfoPanel.add(taskStatePanel);
        taskInfoPanel.add(taskDescriptionPanel);
        pagePanel.add(previousLabel);
        pagePanel.add(pageShowLabel);
        pagePanel.add(nextLabel);
        panel.add(taskInfoPanel);
        panel.add(pagePanel);
        panel.add(buttonPanel);
        /*set the location of each component in panel*/
        taskIDPanel.setLayout(new GridLayout(1, 1));
        taskTitlePanel.setLayout(new GridLayout(1, 1));
        taskStatePanel.setLayout(new GridLayout(1, 1));
        taskDescriptionPanel.setLayout(new GridLayout(1, 1));
        GridBagLayout layout = new GridBagLayout();
        taskInfoPanel.setLayout(layout);
        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 0;
        layout.setConstraints(taskIDPanel, s);
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 0;
        layout.setConstraints(taskTitlePanel, s);
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 0;
        layout.setConstraints(taskStatePanel, s);
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 0;
        layout.setConstraints(taskDescriptionPanel, s);
        GridBagLayout layout2 = new GridBagLayout();
        panel.setLayout(layout2);
        GridBagConstraints s2 = new GridBagConstraints();
        s2.fill = GridBagConstraints.BOTH;
        s2.gridwidth = 0;
        s2.weightx = 1;
        s2.weighty = 0;
        layout2.setConstraints(taskInfoPanel, s2);
        s2.gridwidth = 0;
        s2.weightx = 1;
        s2.weighty = 0;
        layout2.setConstraints(pagePanel, s2);
        s2.gridwidth = 0;
        s2.weightx = 1;
        s2.weighty = 0;
        layout2.setConstraints(buttonPanel, s2);
    }

    public void refresh() {
        getValue();
        init();
        frame.setTitle("Detail Description of Task_" + id);
    }

    public void createDic(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public boolean createFile(String[] paths) {
        boolean flag = false;
        for (int i = 0; i < 5; i++) {
            File file = new File(paths[i]);
            if (!file.exists()) {
                flag = true;
                try {
                    if (i != 3) {
                        file.createNewFile();
                    } else {
                        file.mkdir();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return flag;
    }
}
