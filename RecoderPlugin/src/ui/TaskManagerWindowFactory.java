package ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import configurations.AlertConfigureUI;
import service.ConfigureCheck;
import service.TaskManagerSetting;
import taskload.LoadTask;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by chenchi on 18/5/21.
 */
public class TaskManagerWindowFactory implements ToolWindowFactory {

    public static String username;
    public static JPanel toolWindowPanel;
    private ToolWindow toolWindow;
    public static JPanel loginPanel;
    public static JPanel signPanel;
    public static JPanel taskInfoPanel;
    public static boolean flag = true;
    public static boolean logoutFlag = true;
    public static int count;
    public static boolean frameExistFlag;
    public static TaskDetailDescriptionComponent taskDetailDescriptionComponent;
    public static Map<String, String> taskIDMap = new HashMap<>();
    public static Map<String, String> reverseTaskIDMap = new HashMap<>();

    static TaskManagerSetting setting = TaskManagerSetting.getInstance();

    public TaskManagerWindowFactory() {
//        hideToolWindowButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                myToolWindow.hide(null);
//            }
//        });
//        refreshToolWindowButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                TaskManagerWindowFactory.this.currentDateTime();
//            }
//        });
    }

    // Create the tool window content.
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        initToolWindow();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(toolWindowPanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    public void initToolWindow() {
        toolWindowPanel = new JPanel();
        toolWindowPanel.setLayout(new GridLayout());
        //initLoginUI();
        // initSignUI();
//        ConfigureCheck check = new ConfigureCheck();
//        check.checkConfigurations();
        initTaskInfoUI();
        //toolWindowPanel.add(loginPanel);
        toolWindowPanel.add(taskInfoPanel);
    }

    public static void initLoginUI() {
//        LoginComponent loginComponent = new LoginComponent();
//        loginPanel = loginComponent.initLoginUI();
    }

    public static void initSignUI() {
        SignupComponent signupComponent = new SignupComponent();
        signPanel = signupComponent.initSignUI();
    }

    public static void initTaskInfoUI() {
        String path = setting.getPD() + File.separator + "taskload" + File.separator + "task" + File.separator + "task1.txt";
        boolean flag = true;
        try{
            File file = new File(path);
            if(!file.exists()){
                flag = false;
            }
        }catch(Exception e){
            flag = false;
        }catch(Error e){
            flag = false;
        }
        if (flag) {
            LoadTask loadTask = new LoadTask();
            java.util.List<String> ids = loadTask.getAllTaskID();
            for (int i = 0; i < ids.size(); i++) {
                TaskManagerWindowFactory.taskIDMap.put(ids.get(i), Integer.toString(i + 1));
                TaskManagerWindowFactory.reverseTaskIDMap.put(Integer.toString(i + 1), ids.get(i));
            }
            TaskInfoComponent taskInfoComponent = new TaskInfoComponent();
            taskInfoPanel = taskInfoComponent.initTaskInfoUI();
        }
        else {
            taskInfoPanel = new AlertConfigureUI().getRoot();
        }
    }

}