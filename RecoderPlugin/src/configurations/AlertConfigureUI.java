package configurations;

import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

import javax.swing.*;

/**
 * @author wangxin
 */
public class AlertConfigureUI {
    private JPanel panel1;
    private JButton config;

    public AlertConfigureUI() {
        this.config.addActionListener(
                e -> {
                    Project p = ProjectManager.getInstance().getOpenProjects()[0];
                    ShowSettingsUtil.getInstance().showSettingsDialog(p, "TaskManager Plugin");
                }
        );
    }

    public JPanel getRoot(){
        return panel1;
    }
}
