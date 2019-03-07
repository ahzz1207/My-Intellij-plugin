package record;
import com.google.gson.JsonObject;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.util.TextRange;
import org.codehaus.jackson.map.ObjectMapper;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class Closed {
    private Map<String,documentListener> documentListeners;
    private ArrayList<Document> documents;
    private getArrayList getArrayList;
    private Map<String,String> filePaths;
    private Editor editor;
    private RawActionHandlers rawActionHandlers;
    private Boolean flag;

    public Closed(Editor editor,Boolean flag) {
        this.getArrayList = new getArrayList();
        documents=getArrayList.getDocuments();
        documentListeners = getArrayList.getDocumentListeners();

        filePaths = getArrayList.getFileTemp4s();
        this.editor=editor;
        this.rawActionHandlers=new RawActionHandlers();
        this.flag=flag;

        //进入关闭程序
        shutDown();
    }

    public void shutDown(){

        //关闭所有监听器并保存所有document的映射关系
        for ( Document document:documents) {
            documentListener documentListener = documentListeners.get(document.toString());
            if (documentListener != null) {
                //缓存操作数
                getArrayList.setOperates(document.toString(), documentListener.operate);
                //保存标记
                if (flag) {
                    saveHighLighters(document, documentListener);
                }
                //保存行信息
                saveResult(document.toString(), documentListener);
                //卸载监听器
                document.removeDocumentListener(documentListener);
            }
        }
        //保存documents与结果文件的映射表及操作数
        saveResultMap();

        //关闭处理器
        shutDownHandler();

        //清楚ArrayList中的映射关系
        clearArrayList();
    }

    public void saveHighLighters(Document document,documentListener documentListener){
        Editor editor=getArrayList.getEditorMap().get(document.toString());
        RangeHighlighter[] highlighters = editor.getMarkupModel().getAllHighlighters();
        for (int i = 0; i < highlighters.length; i++) {
            int layer = highlighters[i].getLayer();
            if (layer == 1 || layer == 2 || layer == 3) {
                recodeLine(document, documentListener,documentListener.fileName, getArrayList.getFileTemp3(), layer, highlighters[i].getStartOffset(), highlighters[i].getEndOffset());
            }
        }
        // 清除所有高亮标记
        // editor.getMarkupModel().removeAllHighlighters();
    }

//    public void saveLastModify(){
//        if (editor!=null) {
//            String documentName = editor.getDocument().toString();
//            documentListener documentListener = documentListeners.get(documentName);
//            documentListener.modifyStartLine = documentListener.arrayList.get(documentListener.modifyID);
//            documentListener.recodeModifyEvent(1);
//        }
//    }

    public void saveResult(String documentName, documentListener documentListener){
        String filePath=filePaths.get(documentName);
        //将行信息写入新文件
        ArrayList<LineInfo> infoArrayList = documentListener.lineInfoArrayList;
        //用迭代器把LineInfo要的信息都写进来
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<LineInfo> list = infoArrayList.iterator(); list.hasNext(); ) {
            JsonObject jsonObject = new JsonObject();
            LineInfo lineInfo = list.next();
            //将LineFlow的Map转为String
            ObjectMapper mapperObj = new ObjectMapper();
            String lineFlowString="";
            try {
                lineFlowString = mapperObj.writeValueAsString(lineInfo.lineFlow);
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error e){
                e.printStackTrace();
            }
            jsonObject.addProperty("ID", lineInfo.lineId);
            jsonObject.addProperty("BornLine", lineInfo.bornLine);
            jsonObject.addProperty("BornTime",lineInfo.bornTime);
            jsonObject.addProperty("CurrentLine", lineInfo.currentLine);
            jsonObject.addProperty("Text", lineInfo.LineText);
            jsonObject.addProperty("LineFlow",lineFlowString);
            stringBuilder.append(jsonObject.toString());
            stringBuilder.append('\n');
        }
        SaveFile saveFile = new SaveFile(filePath, stringBuilder.toString());
        try {
            saveFile.overSave();
        } catch (Exception e){
            e.printStackTrace();
        } catch (Error e){
            e.printStackTrace();
        }
    }

    public void saveResultMap(){
        StringBuilder sb=new StringBuilder();
        Map<String,String> fileTemps=getArrayList.getFileTemp4s();
        Map<String,Integer> operates=getArrayList.getOperatesMap();
        Iterator it=fileTemps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry=(Map.Entry)it.next();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Name", entry.getKey().toString());
            jsonObject.addProperty("Path", entry.getValue().toString());
            jsonObject.addProperty("LastOperate",operates.get(entry.getKey()));
            sb.append(jsonObject.toString());
            sb.append('\n');
        }
        sb.append(getArrayList.getActionOperate());

        SaveFile saveFile = new SaveFile(getArrayList.getFileTemp4map(), sb.toString());
        try {
            saveFile.overSave();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e){
            e.printStackTrace();
        }
    }

    public void recodeLine(Document document,documentListener documentListener,String fileName,String fileTemp,int flag,int startOffset,int endOffset){
        TextRange textRange=new TextRange(startOffset,endOffset);
        String text=document.getText(textRange);
        int startLineNumber=document.getLineNumber(startOffset);
        int endLineNumber=document.getLineNumber(endOffset);
        JsonObject jsonObject=new JsonObject();
        Date date=new Date();

        if (startLineNumber==endLineNumber){
            int lineId= documentListener.arrayList.indexOf(startLineNumber) < 0? 0:documentListener.arrayList.indexOf(startLineNumber);
            LineInfo lineInfo=getArrayList.idGetLineInfo(document.toString(),lineId);
            jsonObject.addProperty("Name",fileName);
            jsonObject.addProperty("Level",flag);
            jsonObject.addProperty("Text",text);
            jsonObject.addProperty("ID",String.valueOf(lineId));
            jsonObject.addProperty("BornLine",lineInfo.bornLine);
            jsonObject.addProperty("CurrentLine",startLineNumber);
            jsonObject.addProperty("Time",date.toString());
        }
        else {
            ArrayList idArrayList=new ArrayList();
            ArrayList lineArray=new ArrayList();
            ArrayList lineInfoArray = new ArrayList();
            for (int i=startLineNumber;i<=endLineNumber;i++){
                int lineId= documentListener.arrayList.indexOf(i) < 0? 0: documentListener.arrayList.indexOf(i);
                LineInfo lineInfo=getArrayList.idGetLineInfo(document.toString(),lineId);
                idArrayList.add(lineId);
                lineArray.add(i);
                lineInfoArray.add(lineInfo.bornLine);
            }
            jsonObject.addProperty("Name",fileName);
            jsonObject.addProperty("Level",flag);
            jsonObject.addProperty("Text",text);
            jsonObject.addProperty("ID",idArrayList.toString());
            jsonObject.addProperty("BornLine",lineInfoArray.toString());
            jsonObject.addProperty("CurrentLine",lineArray.toString());
            jsonObject.addProperty("Time",date.toString());
        }

        try {
            SaveFile saveFile=new SaveFile(fileTemp,jsonObject.toString());
            saveFile.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        }catch (Error e){
            e.printStackTrace();
        }
    }

    public void shutDownHandler(){
        final EditorActionManager actionManager = EditorActionManager.getInstance();
        actionManager.getTypedAction().setupHandler(rawActionHandlers.getTypeHandler());
        ActionManager actionManager2=ActionManager.getInstance();
        actionManager2.removeAnActionListener(rawActionHandlers.getAnActionListener());
        Toolkit.getDefaultToolkit().removeAWTEventListener(rawActionHandlers.getAwtEventListener());
    }

    public void clearArrayList(){
        //销毁全局变量
        getArrayList.getDocuments().clear();
        getArrayList.getDocumentListeners().clear();
        getArrayList.getChangeInfoMap().clear();
        getArrayList.getFileTemp4s().clear();
        getArrayList.getEditorMap().clear();
    }
}
