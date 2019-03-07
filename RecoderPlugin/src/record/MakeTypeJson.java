package record;
import com.google.gson.JsonObject;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiTreeChangeEvent;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


public class MakeTypeJson {
    public LineInfo lineInfo;
    public Editor editor;
    //    public String c;
    public ArrayList<String> change;
    public DocumentEvent event;
    public LogicalPosition logicalPosition;
    public String text;
    private String key;
    //    public PsiTreeChangeEvent psiTreeChangeEvent;
//    public String psiEvent=null;
    public String documentName;
    public String fileName;
    public int actionOperate;
    private getArrayList getArrayList=new getArrayList();

    //    public MakeTypeJson(Editor editor, String c, DocumentEvent event, LineInfo lineInfo, String key,String text){
//        this.editor=editor;
//        this.documentName=editor.getDocument().toString();
//        this.fileName=getFileName(documentName);
//        this.c=c;
//        this.key=key;
//        this.lineInfo=lineInfo;
//        CaretModel caretModel = editor.getCaretModel();
//        this.logicalPosition = caretModel.getLogicalPosition();
//        this.lineInfo.TypeLineText=text;
//        this.text=text;
//    }
    public MakeTypeJson(Editor editor, ArrayList<String> change, DocumentEvent event, LineInfo lineInfo, String key,String text,int actionOperate){
        this.editor=editor;
        this.documentName=editor.getDocument().toString();
        this.fileName=getFileName(documentName);
        this.change=change;
        this.key=key;
        this.lineInfo=lineInfo;
        CaretModel caretModel = editor.getCaretModel();
        this.logicalPosition = caretModel.getLogicalPosition();
        this.lineInfo.TypeLineText=text;
        this.text=text;
        this.actionOperate=actionOperate;
    }

//    //单行事件
//    public JsonObject makeAjson(){
//        ArrayList list=new ArrayList();
//        list.add(lineInfo.lineId);
//        Date date=new Date();
//        JsonObject json=new JsonObject();
//        json.addProperty("Name",fileName);
//        json.addProperty("Event",key);
//        json.addProperty("Change",c);
//        json.addProperty("Text",text);
//        json.addProperty("ID",list.toString());
//        json.addProperty("BornLine",lineInfo.bornLine);
//        json.addProperty("CurrentLine",logicalPosition.line);
//        json.addProperty("Column",logicalPosition.column);
//        json.addProperty("Time",date.toString());
//        return json;
//    }

//    //多行事件
//    public JsonObject makeAjson2(String IDs){
//        Date date=new Date();
//        JsonObject json=new JsonObject();
//        json.addProperty("Name",fileName);
//        json.addProperty("Event",key);
//        json.addProperty("Change",c);
//        json.addProperty("Text",text);
//        json.addProperty("ID",IDs);
//        json.addProperty("BornLine",lineInfo.bornLine);
//        json.addProperty("CurrentLine",logicalPosition.line);
//        json.addProperty("Column",logicalPosition.column);
//        json.addProperty("Time",date.toString());
//        return json;
//    }

    //多行事件
    public String makeAjson2(String IDs){
        Date date=new Date();
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        JsonObject json=new JsonObject();
        json.addProperty("Name",fileName);
        json.addProperty("Event",key);
        ObjectMapper mapper = new ObjectMapper();
        try {
            json.addProperty("Change",mapper.writeValueAsString(change));
        } catch (IOException e) {
//            e.printStackTrace();
        }
        json.addProperty("Text",text);
        json.addProperty("ID",IDs);
        json.addProperty("BornLine",lineInfo.bornLine);
        json.addProperty("CurrentLine",logicalPosition.line);
        json.addProperty("Column",logicalPosition.column);

        actionOperate = getArrayList.getActionOperate();
        json.addProperty("ActionOperate",actionOperate);
        json.addProperty("Time",timestamp.toString());
        return json.toString();
    }

    //得到FileName
    public String getFileName(String documentName){
        String[] temp;
        try {
            temp = documentName.split(File.separator);
        }catch(Exception e){
            temp = documentName.split("\\\\");
        }catch(Error e){
            temp = documentName.split("\\\\");
        }        String fileName=documentName;
        for (String ss:temp) {
            if (ss.contains(".java")){
                fileName=ss.substring(0,ss.length()-1);
            }
        }
        return fileName;
    }


}
