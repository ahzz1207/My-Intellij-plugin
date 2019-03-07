package record;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import java.util.HashMap;
import java.util.Map;

public class MyActionListener implements AnActionListener {
    public String actionName = "";
    public int actionId = 1;
//    public TypeRecoder typeRecoder=new TypeRecoder();
    public getArrayList getArrayList=new getArrayList();
    public ChangeInfo changeInfo = new ChangeInfo();
    public Map<String,Integer> actionIds=new HashMap<String, Integer>(){{
        put("Undo ()",1);
        put("Choose Lookup Item ()",2);
        put("Copy ()",3);
        put("Enter ()",4);
        put("Backspace ()",5);
        put("Delete ()",6);
        put("Paste ()",7);
        put("Cut ()",8);
        put("Generate... (Generate constructor, getter or setter method, etc.)",9);
        put("Show Intention Actions ()",10);
        put("Comment with Line Comment (Comment/uncomment current line or selected block with line comments)",11);
    }} ;

    public MyActionListener() {
    }

    @Override
    public void beforeActionPerformed(AnAction action, DataContext dataContext, AnActionEvent event) {
        actionName=action.toString();
//        new MyNotification(actionName);
        //获得对应的ChangeInfo对象
        Editor editor=event.getData(DataKey.create("editor"));
        if (editor!=null) {
            Document document = editor.getDocument();
            changeInfo = getArrayList.getChangeInfoMap().get(document.toString());
            if (changeInfo!=null) {
                if (editor.getSelectionModel().hasSelection()){
                    changeInfo.isSelected=true;
                    changeInfo.selectedText=editor.getSelectionModel().getSelectedText();
                    changeInfo.selectedIds=new GetIDbySelected(document).getId(editor.getSelectionModel().getSelectionStart(),editor.getSelectionModel().getSelectionEnd());
                }
                if (actionIds.containsKey(actionName)) {
                    getArrayList.addActionOperate();
                    actionId=actionIds.get(actionName);
                    //修改对应的ChangeInfo
                    if (actionId == 1) {
                        changeInfo.typeChar = "Undo";
                    } else if (actionId == 2) {
                        changeInfo.typeChar = "Complete";
                    } else if (actionId == 4) {
                        changeInfo.typeChar = "Enter";
                    } else if (actionId == 5) {
                        changeInfo.typeChar = "Backspace";
                    } else if (actionId == 6) {
                        changeInfo.typeChar = "Del";
                    } else if (actionId == 7) {
                        changeInfo.typeChar = "Paste";
                    } else if (actionId == 8) {
                        changeInfo.typeChar = "Cut";
                    } else if (actionId == 9) {
                        changeInfo.typeChar = "Generate";
                    } else if (actionId == 10) {
                        changeInfo.typeChar = "Show";
                    } else if (actionId == 11){
                        changeInfo.typeChar = "Comment";
                    }
                }
                else {
                    changeInfo.typeChar="Others";
                }
        }
    }

    }
}
