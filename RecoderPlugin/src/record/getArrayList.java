package record;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class getArrayList {
    private static int actionOperate;

    public int getActionOperate() {
        return actionOperate;
    }

    public void setActionOperate(int actionOperate) {
        getArrayList.actionOperate = actionOperate;
    }

    public void addActionOperate(){
        actionOperate=actionOperate+1;
    }

    private static Map<String, documentListener> documentListeners = new HashMap<>();

    private static ArrayList<Document> documents = new ArrayList();

    private static String fileTemp;
    private static String fileTemp2;
    private static String fileTemp3;

    private static String fileTemp4cd;
    private static String fileTemp4map;
    private static Map<String, Integer> operates = new HashMap<>();
    private static Map<String, String> fileTemp4s = new HashMap<>();

    private static Map<String, ChangeInfo> changeInfoMap = new HashMap<>();

    private static Map<String, Editor> editorMap =new HashMap<>();


    public Map<String, Editor> getEditorMap() {
        return editorMap;
    }

//    public void setEditorMap(Map<String, Editor> editorMap) {
//        getArrayList.editorMap = editorMap;
//    }

    //关于Document与监听器
    public void setDocumentListener(documentListener documentListener, String documentName) {
        documentListeners.put(documentName, documentListener);
    }

    public documentListener getDocumentListener(String documentName) {
        return getArrayList.documentListeners.get(documentName);
    }

    public Map<String, documentListener> getDocumentListeners() {
        return documentListeners;
    }

//    public void setDocuments(Document document) {
//        documents.add(document);
//    }

    public ArrayList getDocuments() {
        return documents;
    }

    public void setOperates(String documentName, int lastOperate) {
        operates.put(documentName, lastOperate);
    }

    public int getLastOperate(String documentNmae) {
        return operates.get(documentNmae);
    }

    public Map<String, Integer> getOperatesMap() {
        return operates;
    }

    //关于存储路径
    public void setFileTemp(String fileTemp) {
        getArrayList.fileTemp = fileTemp;
    }

    public String getFileTemp() {
        return getArrayList.fileTemp;
    }

    public void setFileTemp2(String fileTemp2) {
        getArrayList.fileTemp2 = fileTemp2;
    }

    public String getFileTemp2() {
        return fileTemp2;
    }

    public void setFileTemp3(String fileTemp3) {
        getArrayList.fileTemp3 = fileTemp3;
    }

    public String getFileTemp3() {
        return fileTemp3;
    }

    //关于Result文件
//    public void setFileTemp4(String documentName, String fileTemp4) {
//        fileTemp4s.put(documentName, fileTemp4);
//    }

    public String getFileTemp4(String documentName) {
        return fileTemp4s.get(documentName);
    }

    public void setFileTemp4cd(String fileTemp4cd) {
        getArrayList.fileTemp4cd = fileTemp4cd;
    }

    public String getFileTemp4cd() {
        return fileTemp4cd;
    }

    public void setFileTemp4map(String fileTemp4map) {
        getArrayList.fileTemp4map = fileTemp4map;
    }

    public String getFileTemp4map() {
        return fileTemp4map;
    }

    public Map<String, String> getFileTemp4s() {
        return fileTemp4s;
    }

    public Map<String, ChangeInfo> getChangeInfoMap() {
        return changeInfoMap;
    }

    public void setChangeInfo(String documentName, ChangeInfo changeInfo) {
        changeInfoMap.put(documentName, changeInfo);
    }

    //用当前行得到LineInfo
    public LineInfo currentLineGetLineInfo(String documentName, int currentLine) {
        int id = 0;
        try {
            id = documentListeners.get(documentName).arrayList.indexOf(currentLine);
        }
        catch (Exception e){
        }
        catch (Error e){
        }
        LineInfo lineInfo = documentListeners.get(documentName).lineInfoArrayList.get(id);
        return lineInfo;
    }

    //用ID得到LineInfo
    public LineInfo idGetLineInfo(String documentName, int id) {
        LineInfo lineInfo = documentListeners.get(documentName).lineInfoArrayList.get(id);
        return lineInfo;
    }

    //得到FileName
    public String getFileName(String documentName) {
        String[] temp;
        try {
            temp = documentName.split(File.separator);
        }catch(Exception e){
            temp = documentName.split("\\\\");
        }catch(Error e){
            temp = documentName.split("\\\\");
        }
        String fileName = documentName;
        for (String ss : temp) {
            if (ss.contains(".java")) {
                fileName = ss.substring(0, ss.length() - 1);
            }
        }
        return fileName;
    }

}
