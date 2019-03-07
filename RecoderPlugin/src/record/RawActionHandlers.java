package record;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;

import java.awt.event.AWTEventListener;
import java.lang.reflect.Type;

public class RawActionHandlers {
//    private static EditorActionHandler enterHandler;
    private static TypedActionHandler typeHandler;
//    private static EditorActionHandler backSpaceHandler;
//    private static EditorActionHandler delHandler;
//    private static EditorActionHandler pasteHandler;
    private static AnActionListener AnActionListener;
    private static AWTEventListener awtEventListener;

//    public void setEnterHandler(EditorActionHandler enterHandler) {
//        RawActionHandlers.enterHandler = enterHandler;
//    }
//
//    public void setBackSpaceHandler(EditorActionHandler backSpaceHandler) {
//        RawActionHandlers.backSpaceHandler = backSpaceHandler; }
//
//    public void setDelHandler(EditorActionHandler delHandler) {
//        RawActionHandlers.delHandler = delHandler;
//    }

    public void setTypeHandler(TypedActionHandler typeHandler) {
        RawActionHandlers.typeHandler = typeHandler;
    }

//    public void setPasteHandler(EditorActionHandler pasteHandler) {
//        RawActionHandlers.pasteHandler = pasteHandler; }

    public void setAnActionListener(AnActionListener anActionListener) {
        RawActionHandlers.AnActionListener = anActionListener;
    }

    public void setAwtEventListener(AWTEventListener awtEventListener) {
        RawActionHandlers.awtEventListener = awtEventListener;
    }

    public AnActionListener getAnActionListener() {
        return AnActionListener;
    }

//    public EditorActionHandler getBackSpaceHandler() {
//        return backSpaceHandler;
//    }
//
//    public EditorActionHandler getDelHandler() {
//        return delHandler;
//    }
//
//    public EditorActionHandler getEnterHandler() {
//        return enterHandler;
//    }

    public TypedActionHandler getTypeHandler() {
        return typeHandler;
    }

    public AWTEventListener getAwtEventListener() {
        return awtEventListener;
    }
}
