 package record;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAWTEventListener implements AWTEventListener {
    private FileEditorManager fileEditorManager;
    private Editor editor=null;
    private ArrayList<Document> documents;
    private getArrayList getArrayList=new getArrayList();
    private Map<String,String> fileTemps = new HashMap<>();
//    private Map<String,ChangeInfo> changeInfoMap;
    private Map<String,Integer> operates = new HashMap<>();

    public MyAWTEventListener(Project project,boolean flag){
        this.fileEditorManager=FileEditorManager.getInstance(project);
        documents = getArrayList.getDocuments();
        fileTemps= getArrayList.getFileTemp4s();
//        changeInfoMap=getArrayList.getChangeInfoMap();
        operates=getArrayList.getOperatesMap();
        //恢复结果文件映射
        if (!flag){
            try {
                recoverResult(getArrayList.getFileTemp4map());
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error e){
                e.printStackTrace();
            }
        }

        //在加载监听器时做第一次加载操作
        Editor selectedEditor=fileEditorManager.getSelectedTextEditor();
        if(selectedEditor!=null&&selectedEditor.getDocument().toString().contains(".java")){
            this.editor=fileEditorManager.getSelectedTextEditor();
            Document document = editor.getDocument();
            if (document!=null&&documents.indexOf(document)==-1) {
                documentInit(document);
            }
        }
    }

    @Override
    //监听Editor切换
    public void eventDispatched(AWTEvent event) {
        Editor selectedEditor=fileEditorManager.getSelectedTextEditor();
        if (selectedEditor!=null&&selectedEditor.getDocument().toString().contains(".java")) {
            //若当前Editor改变
            if (editor !=selectedEditor) {
                //保存上一Editor的modifyLine记录
                if (editor!=null) {
                    saveModifyLine(editor);
                }
                //切换当前editor与document
                this.editor = selectedEditor;
                Document document = editor.getDocument();
                //若是全新document，启动初始化流程
                if (documents.indexOf(document) == -1) {
                    documentInit(document);
                }
            }
        }
    }

    //新的document加载初始化
    public void documentInit(Document document){
        //判断是否需要读取结果
        Boolean flag=createResultFile(document);
        documents.add(document);

        //生成document与editor对应
        getArrayList.getEditorMap().put(document.toString(),editor);

        //生成他的ChangeInfo
        ChangeInfo changeInfo=new ChangeInfo();
        getArrayList.setChangeInfo(document.toString(),changeInfo);

        //为他加载监听器
        documentListener listener=addDocumentListener(document,flag);
        getArrayList.setDocumentListener(listener, document.toString());

    }

    //初始化并加载监听器
    public documentListener addDocumentListener(Document document,Boolean flag){
        documentListener listener = null;
        try {
            listener = new documentListener(document, editor, flag);
            //为此新document加载document监听器
            document.addDocumentListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e){
            e.printStackTrace();
        }
        return listener;
    }

    //判断是否存在结果文件，若不存在结果文件，创建新结果文件
    public Boolean createResultFile(Document document){
        boolean flag=true;
        String documentName=document.toString();
        String[] temp;
        try {
            temp = documentName.split(File.separator);
        }catch(Exception e){
            temp = documentName.split("\\\\");
        }catch(Error e){
            temp = documentName.split("\\\\");
        }
        for (String ss:temp) {
            if (ss.contains(".java")){
                documentName=ss;
            }
        }
        String cd=getArrayList.getFileTemp4cd();
        String fileTemp=cd.concat(File.separator).concat(documentName).concat(".txt");
        File file=new File(fileTemp);
        if (!file.exists()){
            try {
                file.createNewFile();
                flag=true;
                fileTemps.put(document.toString(),fileTemp);
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error e){
                e.printStackTrace();
            }
        }
        else {
            flag=false;
        }
        return flag;
    }

    //恢复结果文件映射
    public void recoverResult(String filePath) throws IOException {
        File file=new File(filePath);
        BufferedReader reader=new BufferedReader(new FileReader(file));
        String info;
        while ((info=reader.readLine())!=null) {
            if (info.length() == 1) {
                int b=0;
                try {
                    b = Integer.valueOf(info).intValue();
                } catch (NumberFormatException e){
                    e.printStackTrace();
                }
                getArrayList.setActionOperate(b);
            } else {
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(info);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                this.fileTemps.put(jsonObject.get("Name").getAsString(), jsonObject.get("Path").getAsString());
                this.operates.put(jsonObject.get("Name").getAsString(), jsonObject.get("LastOperate").getAsInt());
            }
        }
    }

    //切换时保存上一editor的ModifyLine
    public void saveModifyLine(Editor editor){
        String documentName=editor.getDocument().toString();
        documentListener documentListener= getArrayList.getDocumentListener(documentName);
        if (documentListener!=null) {
            documentListener.modifyStartLine = documentListener.arrayList.get(documentListener.modifyID);
            documentListener.recodeModifyEvent(1);
        }
    }

}
