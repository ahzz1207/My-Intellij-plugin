package service;

import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

import javax.swing.*;

/**
 * Created by chenchi on 18/6/20.
 */
public class ConfigureCheck {
    // 持久化组件
    TaskManagerSetting setting = TaskManagerSetting.getInstance();
    /**
     * 可调用该函数检查配置是否正确
     * */
    public boolean checkConfigurations(){
        if(setting.getLD().equals("/") || setting.getPD().equals("/") || !isInConfigureProjectDir()){
            //JOptionPane.showMessageDialog(null,"Please Configure Correctly.","Warning from TaskManager",JOptionPane.WARNING_MESSAGE);
            //showConfigure();
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 跳转到配置页面
     * */
    public void showConfigure(){
        Project p = ProjectManager.getInstance().getOpenProjects()[0];
        ShowSettingsUtil.getInstance().showSettingsDialog(p, "TaskManager Plugin");
    }

    public boolean isInConfigureProjectDir(){
        Project p = ProjectManager.getInstance().getOpenProjects()[0];
        String basedir = p.getBasePath();
        return basedir.equals(setting.getPD());
    }
}
