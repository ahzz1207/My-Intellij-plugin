//package record;
//import com.intellij.notification.Notification;
//import com.intellij.notification.NotificationType;
//import com.intellij.notification.Notifications;
//import com.intellij.openapi.actionSystem.DataContext;
//import com.intellij.openapi.command.CommandProcessor;
//import com.intellij.openapi.editor.Caret;
//import com.intellij.openapi.editor.Editor;
//import com.intellij.openapi.editor.EditorBundle;
//import com.intellij.openapi.editor.actionSystem.EditorAction;
//import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
//import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
//import com.intellij.openapi.ui.Messages;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//public class MyEnterHandler extends EditorActionHandler {
//    private getArrayList getArrayList=new getArrayList();
//    EditorActionHandler lastHandler;
//    public MyEnterHandler(EditorActionHandler lastHandler){
//        this.lastHandler=lastHandler;
//    }
//
//    @Override
//    protected void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
//        super.doExecute(editor, caret, dataContext);
//        lastHandler.execute(editor,caret,dataContext);
//        new TypeRecoder().recode(editor,"","Enter");
//
//        //
//
//    }
//
//}
