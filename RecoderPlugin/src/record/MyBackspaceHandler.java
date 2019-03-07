//package record;
//import com.google.gson.JsonObject;
//import com.intellij.openapi.actionSystem.DataContext;
//import com.intellij.openapi.editor.Caret;
//import com.intellij.openapi.editor.CaretModel;
//import com.intellij.openapi.editor.Editor;
//import com.intellij.openapi.editor.LogicalPosition;
//import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
//import com.intellij.openapi.editor.event.DocumentEvent;
//import com.intellij.openapi.ui.Messages;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.io.IOException;
//
//public class MyBackspaceHandler extends EditorActionHandler {
//    EditorActionHandler lastHandler;
//    public MyBackspaceHandler(EditorActionHandler lastHandler){ this.lastHandler=lastHandler; }
//
//    @Override
//    protected void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
//        super.doExecute(editor, caret, dataContext);
//        lastHandler.execute(editor, caret, dataContext);
//        //记录事件
//        new TypeRecoder().recode(editor,"","Backspace");
//    }
//}
