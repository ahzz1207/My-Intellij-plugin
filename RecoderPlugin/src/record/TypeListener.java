package record;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import org.jetbrains.annotations.NotNull;


import java.util.Date;

public class TypeListener implements TypedActionHandler {
    StringBuilder stringBuilder;
    TypedActionHandler myhandler;
    long lasttime;
//    long time;
//    String fileDir;
//    int count;
    int flag;
//    int caretOffset;
//    Date start;
//    Date end;
//    String fileTemp;
    getArrayList getArrayList=new getArrayList();

    public TypeListener(TypedActionHandler myhandler) {
        stringBuilder = new StringBuilder();
        this.myhandler=myhandler;
        lasttime=0;
        this.flag=0;
    }


    @Override
    public void execute(@NotNull Editor editor, char charTyped, @NotNull DataContext dataContext) {
        Document document=editor.getDocument();
        ChangeInfo changeInfo = getArrayList.getChangeInfoMap().get(document.toString());
        getArrayList.addActionOperate();
        if(changeInfo != null) {
            changeInfo.typeChar = "Type";
            if (editor.getSelectionModel().hasSelection()) {
                changeInfo.isSelected = true;
                changeInfo.selectedText = editor.getSelectionModel().getSelectedText();
                changeInfo.selectedIds = new GetIDbySelected(document).getId(editor.getSelectionModel().getSelectionStart(), editor.getSelectionModel().getSelectionEnd());
            }
        }
        myhandler.execute(editor, charTyped, dataContext);
    }

//    寻找PSI元素
//    public int getCaretOffset(Editor editor){
//        CaretModel caretModel=editor.getCaretModel();
//        int offset=caretModel.getOffset();
//        return offset;
//    }
//
//    public int getOffset(PsiElement psiElement, int offset){
//
//        offset=offset+psiElement.getStartOffsetInParent();
//        if(psiElement.getParent().getNode().getElementType().toString()=="METHOD"
//                ||psiElement.getParent().getNode().getElementType().toString()=="CLASS"){
//            return offset;
//        }
//        else{
//            return getOffset(psiElement.getParent(),offset);
//        }
//
////        if (psiElementParent.getParent() == psiElementParent.getContainingFile()){
////
////            return offset;
////        }
////        else {
////            offset=offset+psiElement.getStartOffsetInParent();
////            return getOffset(psiElementParent,offset);
////        }
//    }
//
//    public TextRange getRange(PsiElement psiElement){
//        if (psiElement.getParent().getNode().getElementType().toString()=="METHOD"
//                ||psiElement.getParent().getNode().getElementType().toString()=="CLASS"){
//
//            return psiElement.getParent().getTextRange();}
//         else   {
//            return  getRange(psiElement.getParent());
//        }
////        if (psiElement.getParent() == psiElement.getContainingFile()){
////
////            return psiElement.getTextRange();
////        }
////        else {
////            return getRange(psiElement.getParent());
////        }
//    }
//
//    public PsiElement findPsiElement(int offset,PsiFile psiFile){
//
//        PsiElement psiElement=psiFile.findElementAt(offset);
//        return psiElement;
//    }
//
//    public String getSignature(PsiElement psiElement){
//        if (psiElement.getParent().getNode().getElementType().toString()=="METHOD"
//                ||psiElement.getParent().getNode().getElementType().toString()=="CLASS"){
//            return psiElement.getParent().getFirstChild().getText();}
//         else{
//             return getSignature(psiElement.getParent());
//            }
//    }
//
//    public String getParameters(PsiElement psiElement){
//        if (psiElement.getParent().getNode().getElementType().toString()=="METHOD"
//                ||psiElement.getParent().getNode().getElementType().toString()=="CLASS"){
//            PsiElement[] children= psiElement.getParent().getChildren();
//            int i=0;
//            while(i<=children.length){
//                if (children[i].getNode().getElementType().toString()=="PARAMETER_LIST"){
//                break;}
//                else{
//                    i++;
//                }
//            }
//            return children[i].getText();
//        }
//        else{
//            return getParameters(psiElement.getParent());
//        }
//    }
//


}