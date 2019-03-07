package service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.impl.ProjectManagerImpl;

import javax.swing.*;
import java.io.IOException;

/**
 * TaskProjectHandler
 *
 * 对任务项目的处理器
 *
 * @author wangxin
 * */
public class TaskProjectHandler {

    public TaskProjectHandler() {
    }

    /**
     * 根据给出的路径打开存在的Project
     * @param targetPath: project的BasePath
     * @return result: is IDEA open the target project successfully? yes: true; no: false.
     * */
    public boolean openProject(String targetPath) throws IOException {
        ProjectManagerImpl pm = (ProjectManagerImpl) ProjectManagerImpl.getInstanceEx();
        Project pjBefore = pm.getOpenProjects()[0];

        if(!targetPath.equals(pjBefore.getBasePath())) {
           // int isConfirm = JOptionPane.showConfirmDialog(null,"Opening the project of this task?");
           // if(pm.canClose(pjBefore) && isConfirm == JOptionPane.YES_OPTION){
            if(pm.canClose(pjBefore)){
                pm.closeProject(pjBefore);
                Project pjTarget = pm.loadProject(targetPath);
                if(pjTarget != null) {
                    pm.openProject(pjTarget);
                    return true;
                }
            }
        }else{
            return true;
        }
//        else {
//            JOptionPane.showMessageDialog(null,"The project now is the project of this task.");
//            return true;
//        }
        return false;
    }

}
