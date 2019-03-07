package record;

import com.intellij.ide.scratch.ScratchFileServiceImpl;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.RangeHighlighter;

public class RemoveFlag extends AnAction {
    private RangeHighlighter rangeHighlighter;
    private Editor editor;
    @Override
    public void actionPerformed(AnActionEvent e) {
        editor.getMarkupModel().removeHighlighter(rangeHighlighter);
    }

    @Override
    public void update(AnActionEvent e) {
        this.editor=e.getData(CommonDataKeys.EDITOR);
        super.update(e);
        int offset=editor.getCaretModel().getOffset();
        Boolean flag=false;
        RangeHighlighter[] highlighters = editor.getMarkupModel().getAllHighlighters();
        for (int i = 0; i <highlighters.length ; i++) {
            int layer=highlighters[i].getLayer();
            if (layer==1||layer==2||layer==3) {
                if (highlighters[i].getStartOffset() <= offset && offset <= highlighters[i].getEndOffset()) {
                    flag = true;
                    rangeHighlighter = highlighters[i];
                }
            }
        }
        e.getPresentation().setVisible( editor != null&&flag);
    }
}
