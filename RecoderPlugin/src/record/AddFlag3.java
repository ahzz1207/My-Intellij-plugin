package record;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;


public class AddFlag3 extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        new RecodeFlag(e,1);
    }
    @Override
    public void update(AnActionEvent e) {
        //Get required data keys
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        //Set visibility only in case of existing project and editor
        e.getPresentation().setVisible(project != null && editor != null && editor.getSelectionModel().hasSelection());
    }
}
