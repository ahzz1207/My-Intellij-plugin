//package record;
//import com.intellij.openapi.actionSystem.DataContext;
//import com.intellij.openapi.editor.Caret;
//import com.intellij.openapi.editor.Editor;
//import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
//import com.intellij.openapi.ui.Messages;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//public class MyDeleteHandler extends EditorActionHandler {
//    EditorActionHandler lastHandler;
//    public MyDeleteHandler(EditorActionHandler lastHandler){
//        this.lastHandler=lastHandler;
//    }
//
//    @Override
//    protected void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
//        super.doExecute(editor, caret, dataContext);
//        lastHandler.execute(editor, caret, dataContext);
//        new TypeRecoder().recode(editor,"","Delete");
//    }
//}
