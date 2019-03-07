package service;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.NavigatableFileEditor;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import com.intellij.pom.Navigatable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TaskFileHandler
 *
 * 对任务文件的处理器
 *
 * @author wangxin
 * */
public class TaskFileHandler {


    /**
     * 调用IntelliJ接口打开相应任务编辑文件
     * */
    public void openTaskFile(String task) {
        TaskManagerSetting setting = TaskManagerSetting.getInstance();
        String path = setting.getPD() + "/src" + "/" + task + ".java";

        // create file if not exist
        createFile(path,task);
        // open file in editor
        openFile(path);
    }

    /**
     * 打开一个已经存在的文件
     * @param path: 该文件的绝对路径
     * */
    public void openFile(String path) {

        Project project = ProjectManager.getInstance().getOpenProjects()[0];

        // get specific file virtualFile
        VirtualFile virtualFile = project.getWorkspaceFile();

        if(virtualFile!=null) {
            VirtualFileSystem system = virtualFile.getFileSystem();
            // must refresh, then find file, or here will report a error because the ignore of the new file.
            virtualFile = system.refreshAndFindFileByPath(path);

            // to spec file
            FileEditorManager manager = FileEditorManager.getInstance(project);
            FileEditor[] fileEditors = manager.openFile(virtualFile, true);
            NavigatableFileEditor navigatableFileEditor = (NavigatableFileEditor) fileEditors[0];
            Navigatable descriptor = new OpenFileDescriptor(project, virtualFile);
            navigatableFileEditor.navigateTo(descriptor);
            new OpenFileDescriptor(project, virtualFile).navigate(true);
        }
        else {
            System.out.println("[INFO] no workspace file.");
        }
    }

    private void createFile(String path,String task) {
        File file = new File(path);
        if(!file.exists()){
            try {
                boolean isCreate = file.createNewFile();
                // content of task file
                String content = "public class "+task+" {\n" +
                        "}";
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(content);
                writer.flush();
                writer.close();
                System.out.println(isCreate);
                System.out.println("create file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
