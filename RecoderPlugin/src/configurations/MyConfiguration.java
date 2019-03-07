package configurations;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import service.TaskManagerSetting;
import ui.TaskManagerWindowFactory;

import javax.swing.*;

/**
 * MyConfiguration
 *
 * 将配置页面连接到IDE的接口
 *
 * */
public class MyConfiguration implements Configurable {

    // 配置页面
    ConfigurationGUI root;
    // 持久化组件
    TaskManagerSetting setting = TaskManagerSetting.getInstance();
    // 应用管理器
    Application application = ApplicationManager.getApplication();
    // 插件主界面
    TaskManagerWindowFactory windowFactory;

    @Nls
    @Override
    public String getDisplayName() {
        return "TaskManager Plugin";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        root = new ConfigurationGUI();
        return root.getRootJPanel();
    }

    @Override
    public boolean isModified() {
        return (!this.setting.getPD().equals(root.pd) || !this.setting.getLD().equals(root.ld));
    }

    @Override
    public void reset(){
        root.reset(setting);
    }

    @Override
    public void apply() throws ConfigurationException {
        root.apply(setting);
        windowFactory = application.getComponent(TaskManagerWindowFactory.class);
        if(TaskManagerWindowFactory.taskInfoPanel != null) {
            TaskManagerWindowFactory.taskInfoPanel.setVisible(false);
            TaskManagerWindowFactory.toolWindowPanel.setVisible(false);
            TaskManagerWindowFactory.taskInfoPanel.removeAll();
            TaskManagerWindowFactory.taskInfoPanel.repaint();
            TaskManagerWindowFactory.toolWindowPanel.remove(TaskManagerWindowFactory.taskInfoPanel);
            TaskManagerWindowFactory.toolWindowPanel.repaint();
            TaskManagerWindowFactory.initTaskInfoUI();
            TaskManagerWindowFactory.toolWindowPanel.add(TaskManagerWindowFactory.taskInfoPanel);
            TaskManagerWindowFactory.taskInfoPanel.setVisible(true);
            TaskManagerWindowFactory.toolWindowPanel.setVisible(true);
        }
    }

}
