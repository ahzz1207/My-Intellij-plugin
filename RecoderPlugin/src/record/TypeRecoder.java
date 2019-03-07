package record;
import com.google.gson.JsonObject;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;

import java.io.IOException;
import java.util.ArrayList;

public class TypeRecoder {

//    public void recode(Editor editor,String c,String key) {
//
//        String documentName=editor.getDocument().toString();
//
//        //与Change事件同步
//        ChangeInfo changeInfo = new getArrayList().getChangeInfoMap().get(documentName);
//
//        DocumentEvent event = changeInfo.event;
//        Document document=editor.getDocument();
//
//        //得到光标位置
//        CaretModel caretModel = editor.getCaretModel();
//        LogicalPosition logicalPosition = caretModel.getLogicalPosition();
//        //得到此行的LineInfo
//        getArrayList getArrayList = new getArrayList();
//        LineInfo lineInfo = getArrayList.currentLineGetLineInfo(documentName,logicalPosition.line);
//        TextRange textRange=new TextRange(document.getLineStartOffset(logicalPosition.line),document.getLineEndOffset(logicalPosition.line));
//        String text=editor.getDocument().getText(textRange);
//
//        //记录事件
//        MakeTypeJson makeTypeJson = new MakeTypeJson(editor, c, event, lineInfo, key,text);
//        JsonObject json = makeTypeJson.makeAjson();
//        String fileTemp=new getArrayList().getFileTemp2();
//        SaveFile save = new SaveFile(fileTemp, json.toString());
//        try {
//            save.saveFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void recode(Editor editor,ArrayList<String> c,String key,String IDs,int actionOperate){
        String documentName=editor.getDocument().toString();

        //与Change事件同步
        ChangeInfo changeInfo = new getArrayList().getChangeInfoMap().get(documentName);
        DocumentEvent event = changeInfo.event;
        Document document=editor.getDocument();

        //得到光标位置
        CaretModel caretModel = editor.getCaretModel();
        LogicalPosition logicalPosition = caretModel.getLogicalPosition();
        //得到此行的LineInfo
        getArrayList getArrayList = new getArrayList();
        LineInfo lineInfo = getArrayList.currentLineGetLineInfo(documentName,logicalPosition.line);
        TextRange textRange=new TextRange(document.getLineStartOffset(logicalPosition.line),document.getLineEndOffset(logicalPosition.line));
        String text=editor.getDocument().getText(textRange);


        //记录事件


        MakeTypeJson makeTypeJson = new MakeTypeJson(editor, c, event, lineInfo, key,text,actionOperate);
        //JsonObject json = makeTypeJson.makeAjson2(IDs);
        String fileTemp=new getArrayList().getFileTemp2();
//        SaveFile save = new SaveFile(fileTemp, json.toString());
        SaveFile save = new SaveFile(fileTemp, makeTypeJson.makeAjson2(IDs));
        try {
            save.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e){
            e.printStackTrace();
        }
    }

}
