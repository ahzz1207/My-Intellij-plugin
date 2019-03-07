package record;
import com.google.gson.JsonObject;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class RecodeFlag  {
    public Editor editor;
    public Document document;
    public Project project;
    public getArrayList getArrayList=new getArrayList();
    public String fileTemp;
    public Date date;
    public String fileName;
    public documentListener documentListener;

    public RecodeFlag(AnActionEvent e,int layer) {
        //准备需要的材料
        editor=e.getRequiredData(CommonDataKeys.EDITOR);
        document=editor.getDocument();
        documentListener=getArrayList.getDocumentListener(document.toString());
        this.fileName=getArrayList.getFileName(document.toString());
        project=editor.getProject();
        fileTemp= getArrayList.getFileTemp3();
        this.date=new Date();
        Recode(layer);
//        final PsiFile psiFile=PsiDocumentManager.getInstance(project).getPsiFile(document);
//        EditorActionManager actionManager=EditorActionManager.getInstance();
//        EditorActionHandler actionHandler=actionManager.getActionHandler(IdeActions.ACTION_EDITOR_SELECT_WORD_AT_CARET);
    }

    //初始化记录
    public void Recode(int layer){
        final SelectionModel selectionModel = editor.getSelectionModel();
//        int startLine=document.getLineNumber(selectionModel.getSelectionStart());
//        int endLine=document.getLineNumber(selectionModel.getSelectionEnd());
        //判断是单行记录还是多行记录
//        if (startLine==endLine){
//            recodeLine(selectionModel);
//        }
//        else{
//            recodeLines(selectionModel);
//        }
        editor.getMarkupModel().addRangeHighlighter(selectionModel.getSelectionStart(),selectionModel.getSelectionEnd(),layer,selectionModel.getTextAttributes(),HighlighterTargetArea.EXACT_RANGE);

    }

//    //记录一行
//    public void recodeLine(SelectionModel selectionModel){
//        final int start = selectionModel.getSelectionStart();
//        final int end = selectionModel.getSelectionEnd();
//        String text=selectionModel.getSelectedText();
//        int lineNumber=document.getLineNumber(start);
//        documentListener documentListener=getArrayList.getDocumentListener(document.toString());
//        int lineId= documentListener.arrayList.indexOf(lineNumber);
//        LineInfo lineInfo=getArrayList.idGetLineInfo(document.toString(),lineId);
//
//        JsonObject jsonObject=new JsonObject();
//        jsonObject.addProperty("Name",fileName);
//        jsonObject.addProperty("Level",flag);
//        jsonObject.addProperty("Text",text);
//        jsonObject.addProperty("ID",String.valueOf(lineId));
//        jsonObject.addProperty("BornLine",lineInfo.bornLine);
//        jsonObject.addProperty("CurrentLine",lineNumber);
//        jsonObject.addProperty("Time",date.toString());
//
//        try {
//            SaveFile saveFile=new SaveFile(fileTemp,jsonObject.toString());
//            saveFile.saveFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //记录多行
//    public void recodeLines(SelectionModel selectionModel){
//        final int start = selectionModel.getSelectionStart();
//        final int end = selectionModel.getSelectionEnd();
//        String text=selectionModel.getSelectedText();
//        int startLineNumber=document.getLineNumber(start);
//        int endLineNumber=document.getLineNumber(end);
//        int maxSize=endLineNumber-startLineNumber+1;
//        ArrayList idArrayList=new ArrayList();
//        ArrayList lineArray=new ArrayList();
//        ArrayList lineInfoArray = new ArrayList();
//        for (int i=startLineNumber;i<=endLineNumber;i++){
//            documentListener documentListener=getArrayList.getDocumentListener(document.toString());
//            int lineId= documentListener.arrayList.indexOf(i);
//            LineInfo lineInfo=getArrayList.idGetLineInfo(document.toString(),lineId);
//            idArrayList.add(lineId);
//            lineArray.add(i);
//            lineInfoArray.add(lineInfo.bornLine);
//        }
//
//
//        try {
//            SaveFile saveFile=new SaveFile(fileTemp,jsonObject.toString());
//            saveFile.saveFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    //获取行号
//    public int getLineNumber(int offset){
//        return  document.getLineNumber(offset);
//    }
//    //获得光标位置
//    public int getCaretOffset(Editor editor){
//        CaretModel caretModel=editor.getCaretModel();
//        int offset=caretModel.getOffset();
//        return offset;
//    }
//    //获得PSI元素位置
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
//    //获得PSI元素的text范围
//    public TextRange getRange(PsiElement psiElement){
//        if (psiElement.getParent().getNode().getElementType().toString()=="METHOD"
//                ||psiElement.getParent().getNode().getElementType().toString()=="CLASS"){
//
//            return psiElement.getParent().getTextRange();}
//        else   {
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
//    //获得PSI元素到指定父节点的偏移量
//    public PsiElement findPsiElement(int offset,PsiFile psiFile){
//
//        PsiElement psiElement=psiFile.findElementAt(offset);
//        return psiElement;
//    }
//    //获得PSI元素的标签
//    public String getSignature(PsiElement psiElement){
//        if (psiElement.getParent().getNode().getElementType().toString()=="METHOD"
//                ||psiElement.getParent().getNode().getElementType().toString()=="CLASS"){
//            return psiElement.getParent().getFirstChild().getText();}
//        else{
//            return getSignature(psiElement.getParent());
//        }
//    }
//    //获得PSI元素的参数
//    public String getParameters(PsiElement psiElement){
//        if (psiElement.getParent().getNode().getElementType().toString()=="METHOD"
//                ||psiElement.getParent().getNode().getElementType().toString()=="CLASS"){
//            PsiElement[] children= psiElement.getParent().getChildren();
//            int i=0;
//            while(i<=children.length){
//                if (children[i].getNode().getElementType().toString()=="PARAMETER_LIST"){
//                    break;}
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

}
