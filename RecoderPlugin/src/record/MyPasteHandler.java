//package record;
//import com.intellij.openapi.actionSystem.DataContext;
//import com.intellij.openapi.editor.Caret;
//import com.intellij.openapi.editor.Editor;
//import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
//import com.intellij.openapi.ui.Messages;
//import com.intellij.util.Producer;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//import com.intellij.openapi.editor.actionSystem.EditorTextInsertHandler;
//
//import java.awt.datatransfer.Transferable;
//
//public class MyPasteHandler extends EditorActionHandler implements EditorTextInsertHandler{
//    EditorActionHandler lastHandler;
//    public MyPasteHandler(EditorActionHandler lastHandler){
//        this.lastHandler=lastHandler;
//    }
//
//    @Override
//    protected void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext dataContext) {
//        super.doExecute(editor, caret, dataContext);
//        lastHandler.execute(editor,caret,dataContext);
//    }
//
//    @Override
//    public void execute(Editor editor, DataContext dataContext, Producer<Transferable> producer) {
//    }
//}
