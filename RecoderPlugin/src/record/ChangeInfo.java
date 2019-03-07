package record;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.psi.PsiTreeChangeEvent;

import java.util.ArrayList;

public class ChangeInfo {
    public DocumentEvent event;
//    public String documentName;
    public String typeChar="";
//    public PsiTreeChangeEvent psiTreeChangeEvent;
//    public int eventLine;
    public Boolean isSelected=false;
    public String selectedText="";
    public String selectedIds ;

    public ChangeInfo() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        this.selectedIds = list.toString();
    }

    //    public void setTypeChar(String typeChar){ChangeInfo.typeChar=typeChar;}
//    public String getTypeChar(){return typeChar;}
//    public void setEvent(DocumentEvent event){ChangeInfo.event=event;}
//    public DocumentEvent getEvent(){return event;}
//
//    public void setPsiTreeChangeEvent(PsiTreeChangeEvent psiTreeChangeEvent) {
//        ChangeInfo.psiTreeChangeEvent = psiTreeChangeEvent;
//    }
//
//    public PsiTreeChangeEvent getPsiTreeChangeEvent() {
//        return psiTreeChangeEvent;
//    }
}
