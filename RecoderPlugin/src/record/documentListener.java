package record;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.RangeMarker;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.sun.tools.corba.se.idl.InterfaceGen;
import org.codehaus.jackson.map.ObjectMapper;

import javax.sound.sampled.Line;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.util.*;
import java.util.List;

public class documentListener implements DocumentListener {

    public Document document;
    public Editor editor;
    public ArrayList<LineInfo> lineInfoArrayList=new ArrayList<>();
    public ArrayList<Integer> arrayList=new ArrayList<>();
    public int lineCount;
    private int newLineNumber;
    private int oldLineNumber=-1;
    private int changeLines=0;
    private int eventStartLine;
    private int eventEndLine;
    private int addStartLine;
    private int delStartLine;
    public int modifyStartLine;
    private int modifyLines =0;
    private int eventStartOffset;
    private int caretOffset;
    public int modifyID =0;
    public int operate;
    private Date date;
    private String fileTemp;
    private getArrayList getArrayList=new getArrayList();
    private TypeRecoder typeRecoder=new TypeRecoder();
    private int isInit = 0;
    private String documentName;
    private ChangeInfo changeInfo = new ChangeInfo();
    public String fileName;
    private StringBuilder delSb;
    private ArrayList updateLines=new ArrayList();

    //
    private String[] change={"",""};
    private int completionStartLine;
    private int completionEndLine;
    private String eventType=new String();
    private int completionLineCount;
    private Map<Integer,LineInfo> deleteLineInfo=new HashMap<>();


    public  documentListener(Document document,Editor editor,Boolean flag) throws IOException {
        this.document=document;
        this.documentName=document.toString();
        this.fileName=getArrayList.getFileName(documentName);
        this.editor=editor;
        lineCount=document.getLineCount();
        fileTemp =getArrayList.getFileTemp();
        this.changeInfo=getArrayList.getChangeInfoMap().get(documentName);

        if (flag) {
            init();
        }
        else {
            readFile();
            //
            operate=getArrayList.getLastOperate(documentName);
        }
    }

    @Override
    public void documentChanged(DocumentEvent event) {
        deleteLineInfo.clear();
        //获取事件发生位置
        eventStartOffset=event.getOffset();
        eventStartLine =getLineNumber(eventStartOffset);

        //获取光标位置
        caretOffset= editor.getCaretModel().getOffset();
        newLineNumber=getLineNumber(caretOffset);
        String flag="False";
        updateLines=new ArrayList();

        int actionLineCount=lineCount;

        //初始化第一次事件
        if (oldLineNumber==-1){
            modifyStartLine =newLineNumber;
            modifyLines =1;
            oldLineNumber=newLineNumber;
            modifyID =arrayList.indexOf(modifyStartLine);
        }
        //对进入行准备操作
        String text = event.getNewFragment().toString();
        int newFragmentLines = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n') {
                newFragmentLines++;
            }
        }
        if (modifyID ==-1){
            modifyID =arrayList.indexOf(eventStartLine);
        }

        //判断行号变化
        boolean isLineNumberChanged=(newLineNumber != oldLineNumber&&event.getNewLength()!=0) || lineCount!=document.getLineCount()||(newLineNumber!=eventStartLine&&newFragmentLines!=0);
        if (isLineNumberChanged) {
            //得到改变行数
            changeLines=document.getLineCount()-lineCount;

            //若总行数增加，准备记录Add事件
            if (lineCount < document.getLineCount()) {
                //若可能同时存在Modify事件
                if (event.getNewLength()!=0) {
                    //修正光标位置
                    if (event.getNewLength() > 0 && eventStartLine == newLineNumber) {
                        newLineNumber++;
                    }
                    TextRange textRange2 = new TextRange(document.getLineStartOffset(0), document.getLineEndOffset(0));
                    String newText2 = document.getText(textRange2);
                    //检测是否是首行import事件
                    Boolean isImportAtFirstLine=(eventStartLine == 0&&newText2.startsWith("import")&&!lineInfoArrayList.get(arrayList.indexOf(0)).LineText.startsWith("import"))||
                            (eventStartLine==0&&newText2.isEmpty());
                    if (isImportAtFirstLine) {
                        addStartLine = -1;
                        //更新数组
                        updateAddArray(changeLines);
                        recodeAddEvent(changeLines);
                    } else {
                        //检测并记录Modify事件
                        eventEndLine = eventStartLine + newFragmentLines;
                        modifyStartLine = eventStartLine;

                        for (int i = eventStartLine; i <= eventEndLine - changeLines; i++) {
                            TextRange textRange = new TextRange(document.getLineStartOffset(i), document.getLineEndOffset(i));
                            String newText = document.getText(textRange);
                            String oldText = lineInfoArrayList.get(arrayList.indexOf(i)).LineText;
                            if (document.isLineModified(i) && !newText.equals(oldText)) {
                                modifyStartLine = i;
                                recodeModifyEvent(1);
                            }
                        }

                        //记录Add事件
                        addStartLine = modifyStartLine;
                        //更新数组
                        updateAddArray(changeLines);
                        recodeAddEvent(changeLines);
                    }
                }
                //若是特殊Add事件
                else{
                    //检测当前行未被添加过
                    if(arrayList.indexOf(eventStartLine)==-1) {
                        //再进行Add记录
                        addStartLine = eventStartLine - 1;
                    }
                    //更新数组
                    updateAddArray(changeLines);
                    recodeAddEvent(changeLines);
                }
                //对待记录Modify行进行操作
                modifyStartLine =arrayList.get(modifyID);
                //判断没有被删除
                if (modifyStartLine <document.getLineCount()) {
                    //Modify行是否确实有修改
                    if (document.isLineModified(modifyStartLine)&& modifyStartLine !=eventStartLine) {
                        //若有修改，对离开行进行Modify事件处理
                        recodeModifyEvent(1);
                    }
                }
                //将插入符行登记为Modify行
                modifyID =arrayList.indexOf(newLineNumber);
                oldLineNumber = newLineNumber;

            }
            //若总行数减少，准备记录Delete事件
            else if (lineCount>document.getLineCount()){
                //如果是单纯删除事件
                if (event.getNewLength()==0){
                    if (eventStartLine==0&&newLineNumber!=0){
                        delStartLine =-1;
                    }
                    else{
                        delStartLine =eventStartLine;}
                }
                //如果是Modify+删除事件
                else {
                    //检测并记录Modify事件
                    for (int i = eventStartLine; i <= newLineNumber; i++) {
                        TextRange textRange = new TextRange(document.getLineStartOffset(i), document.getLineEndOffset(i));
                        String newText = document.getText(textRange);
                        String oldText = lineInfoArrayList.get(arrayList.indexOf(i)).LineText;
                        if (document.isLineModified(i) && !newText.equals(oldText)) {
                            modifyStartLine = i;
                            recodeModifyEvent(1);
                        }
                        delStartLine = newLineNumber;
                    }
                }
                //记录delete事件
                updateDelArray(changeLines);
                recodeDelEvent(changeLines);

                //对待记录Modify行进行操作
                modifyStartLine =arrayList.get(modifyID);
                //判断没有被删除
                if (modifyStartLine !=-1) {
                    //Modify行是否确实有修改
                    if (document.isLineModified(modifyStartLine)) {
                        //若有修改，对离开行进行Modify事件处理
                        recodeModifyEvent(1);
                    }
                }
                //将插入符行登记为Modify行
                modifyID =arrayList.indexOf(newLineNumber);
                oldLineNumber=newLineNumber;
            }

            //总行数不变，准备记录单纯Modify事件
            else if(lineCount== document.getLineCount()){
                //若一次修改多行
                if (newFragmentLines>0||newLineNumber!=eventStartLine) {
                    //先记录修改好的多行代码
                    modifyStartLine = eventStartLine;
                    modifyLines = newLineNumber-eventStartLine;
                    recodeModifyEvent(modifyLines);
                }
                //对待记录Modify行进行操作
                modifyStartLine =arrayList.get(modifyID);
                //判断没有被删除
                if (modifyStartLine !=-1) {
                    //Modify行是否确实有修改
                    if (document.isLineModified(modifyStartLine)) {
                        //若有修改，对离开行进行Modify事件处理
                        recodeModifyEvent(1);
                    }
                }
                //将插入符行登记为Modify行
                modifyID =arrayList.indexOf(newLineNumber);
                oldLineNumber = newLineNumber;
            }
            flag="True";
            lineCount = document.getLineCount();

        }
        recodeActions(event,actionLineCount);
        StringBuilder sb = new StringBuilder();
//        sb.append(flag);
//        sb.append("NewLength:");
//        sb.append(eventStartLine);
//        sb.append("----");
//        sb.append(event.getOldFragment().toString());
//        sb.append("====");
//        sb.append(event.getNewFragment().toString());
//        sb.append("eventLine");
//        sb.append(eventType);
//        sb.append(changeInfo.typeChar);

//        //更新事件发生行的LineText
//        lineInfoArrayList.get(arrayList.indexOf(eventStartLine)).LineText=getLineText(eventStartLine);
//        lineInfoArrayList.get(arrayList.indexOf(newLineNumber)).LineText=getLineText(newLineNumber);
    }

    @Override
    public void beforeDocumentChange(DocumentEvent event) {
        //先将补全事件记录
        if (!changeInfo.typeChar.equals("Com")&&!eventType.isEmpty()) {
            if (completionLineCount<document.getLineCount()) {
                completionEndLine=completionStartLine+document.getLineCount()-completionLineCount;
                TextRange textRange=new TextRange(document.getLineStartOffset(completionStartLine),document.getLineEndOffset(completionEndLine));
                change[1]=document.getText(textRange);
                //
                ArrayList list=new ArrayList();
                list.add(change[0]);
                list.add(change[1]);
                typeRecoder.recode(editor, list, eventType, getIDs(completionStartLine, completionEndLine),getArrayList.getActionOperate());
            }
            //清空
            eventType=new String();
            change= new String[]{"", ""};
        }

    }

    //获取光标位置
    public int getCaretOffset(Editor editor){
        CaretModel caretModel=editor.getCaretModel();
        int offset=caretModel.getOffset();
        return offset;
    }

    //获取当前行号
    public int getLineNumber(int offset){
        return  document.getLineNumber(offset);
    }

    //Add事件维护数组
    public void updateAddArray(int lines) {
        operate++;
        //更新整个数组
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) >= addStartLine +1) {
                arrayList.set(i, arrayList.get(i) + lines);
                lineInfoArrayList.get(i).updateLine(arrayList.get(i),operate);
            }
        }
        int i=1;
        while (i<=lines) {
            int addLine= addStartLine +i;
            TextRange textRange=new TextRange(document.getLineStartOffset(addLine),document.getLineEndOffset(addLine));
            LineInfo lineInfo=new LineInfo(arrayList.size(),addLine,document.getText(textRange),operate);
            lineInfoArrayList.add(lineInfo);
            arrayList.add(addLine);
            //
            updateLines.add(lineInfo.lineId);
            i++;
        }
//        new MyNotification(arrayList.toString());

    }

    //Delete事件维护数组
    public void updateDelArray(int lines){
        operate++;
        lines=-lines;
        //将删除行的当前行号置为-1
        for (int i=1;i<=lines;i++){
            int id=arrayList.indexOf(delStartLine +i);
            if (id!=-1) {
                arrayList.set(id, -1);
                lineInfoArrayList.get(id).DeleteLine();
                //
                updateLines.add(id);
                //
                deleteLineInfo.put(delStartLine+i,lineInfoArrayList.get(id));
            }
        }
        //更新整个数组
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) > delStartLine +lines) {
                arrayList.set(i, arrayList.get(i) - lines);
                lineInfoArrayList.get(i).updateLine(arrayList.get(i),operate);
            }
        }

//        new MyNotification(arrayList.toString());


    }

    //记录Add事件功能
    public void recodeAddEvent(int lines) {
        date=new Date();
        if (lines>=1){
            for (int i=1;i<=lines;i++){
                int addLine= addStartLine +i;
                String newText=getLineText(addLine);
                String event="Add";
                if (isInit ==1){
                    new LineRecode().readRecode(fileName,"Read",arrayList.indexOf(addLine),addLine,addLine,newText,date,fileTemp,arrayList,getArrayList.getActionOperate());
                }
                else {
                    ArrayList influLines=new ArrayList();
                    for (int j=addLine;j<document.getLineCount();j++){
                        influLines.add(arrayList.indexOf(j));
                    }
                    new LineRecode().Recode(fileName,event,arrayList.indexOf(addLine),addLine,addLine,"",newText,date,fileTemp,influLines,arrayList,getArrayList.getActionOperate());
                    if (newText.startsWith("import")){
                        new TypeImportRecoder(newText,addLine,arrayList.indexOf(addLine),addLine,fileName);
                    }
                }

            }
        }
        isInit =0;
    }

    //记录Modify事件功能
    public void recodeModifyEvent(int lines) {
        date=new Date();
        for (int i=0;i<=lines-1;i++){
            int modifyLine= modifyStartLine +i;
            LineInfo lineInfo=lineInfoArrayList.get(arrayList.indexOf(modifyLine));
            String oldText=lineInfo.LineText;
            String newText=getLineText(modifyLine);
            //若确实发生了文本变化
            if (!oldText.equals(newText)) {
                ArrayList influLines=new ArrayList();
                influLines.add(arrayList.indexOf(modifyLine));
                new LineRecode().Recode(fileName,"Modify",arrayList.indexOf(modifyLine),lineInfo.bornLine,modifyLine,oldText,newText,date,fileTemp,influLines,arrayList,getArrayList.getActionOperate());
                lineInfo.ModifyLine(newText);
            }
            //对AutoImport记录
            if (newText.startsWith("import")){
                new TypeImportRecoder(newText,modifyLine,arrayList.indexOf(modifyLine),lineInfo.bornLine,fileName);
            }
        }
    }

    //记录Delete事件功能
    public void recodeDelEvent(int lines) {
        date=new Date();
        lines=-lines;
        if (lines>=1){
            for (int i=1;i<=lines;i++){
                int deleteLine= delStartLine +i;
                LineInfo lineInfo=lineInfoArrayList.get(arrayList.indexOf(deleteLine));
                ArrayList influLines=new ArrayList();
                for (int j=deleteLine-1;j<document.getLineCount();j++){
                    if (j!=0) {
                        influLines.add(arrayList.indexOf(j));
                    }
                }
                new LineRecode().Recode(fileName,"Del",deleteLineInfo.get(deleteLine).lineId,lineInfo.bornLine,-1,lineInfo.LineText,"",date,fileTemp,influLines,arrayList,getArrayList.getActionOperate());
            }
        }
    }
//
//    //判断Text是否为空
//    public boolean textIsEmpty(String text){
//        int count=0;
//        for (int i=0;i<text.length();i++){
//            if (text.charAt(i)!=' '){
//                count++;
//            }
//        }
//        if (count>0){
//            return true;
//        }
//        else {
//            return false;
//        }
//    }

    public String getLineText(int lineNumber){
        TextRange textRange=new TextRange(document.getLineStartOffset(lineNumber),document.getLineEndOffset(lineNumber));
        String text=document.getText(textRange);
        return text;
    }

    //对EditorActions进行记录
    public void recodeActions(DocumentEvent event,int lineCount){
        changeInfo.event=event;
        String insert=event.getNewFragment().toString();
        String delete=event.getOldFragment().toString();
        String typeChar=changeInfo.typeChar;
        Boolean isSelected=changeInfo.isSelected;
        String selectedText=changeInfo.selectedText;

        //记录小括号补全
        if (typeChar.equals("Com")&&!eventType.isEmpty()){
            if (insert.equals(String.valueOf('('))){
                ArrayList<String> list = new ArrayList<>();
                list.add("");
                list.add("(");
                typeRecoder.recode(editor,list,"Brace",getID(eventStartLine),getArrayList.getActionOperate());
            }else if (insert.equals(String.valueOf(')'))){
                ArrayList<String> list = new ArrayList<>();
                list.add("");
                list.add(")");
                typeRecoder.recode(editor,list,"Brace",getID(eventStartLine),getArrayList.getActionOperate());
            }
        }
        //记录Type事件
        if (typeChar.equals("Type")&&!insert.isEmpty()){
//           insert=getChangeText(isSelected,selectedText,change,insert);
            ArrayList<String> list = getChangeText(isSelected,selectedText,change,insert);
            typeRecoder.recode(editor,list,typeChar,getID(eventStartLine),getArrayList.getActionOperate());
            clear();
        }

        //撤销事件记录
        if (typeChar.equals("Undo")){
            ArrayList<String> list = new ArrayList<>();
            list.add("");
            list.add("");
            typeRecoder.recode(editor,list,"Undo","",getArrayList.getActionOperate());
//            if (lineCount!=document.getLineCount()){
//                String IDs=getIDs(eventStartLine);
//                typeRecoder.recode(editor,event.getNewFragment().toString(),typeChar,IDs);
//            }
//            else{
//                typeRecoder.recode(editor,event.getNewFragment().toString(),typeChar);
//            }
            clear();
        }
        //粘贴事件记录
        else if(typeChar.equals("Paste")){
            //获取剪贴板内容
            insert=getCliboardText();
            String id;
            if (lineCount==document.getLineCount()){
                id=getID(eventStartLine);
            }else if (lineCount<document.getLineCount()){
                id=getIDs(eventStartLine,eventStartLine+document.getLineCount()-lineCount);
            }else {
                id=changeInfo.selectedIds;
            }
//            insert=getChangeText(isSelected,selectedText,change,insert);
            ArrayList<String> list = getChangeText(isSelected,selectedText,change,insert);
            typeRecoder.recode(editor,list,typeChar,id,getArrayList.getActionOperate());
            clear();
        }

        //补全事件记录
        else if (typeChar.equals("Complete")||typeChar.equals("Show")||typeChar.equals("Generate")){

            if (typeChar.equals("Complete")||typeChar.equals("Show")) {
//                insert=getChangeText(isSelected,selectedText,change,insert);
                ArrayList<String> list = getChangeText(isSelected,selectedText,change,insert);
                typeRecoder.recode(editor, list,typeChar,getID(eventStartLine),getArrayList.getActionOperate());
            }
            //
            completionStartLine=eventStartLine;
            completionLineCount=lineCount;
            eventType=typeChar;
            changeInfo.typeChar="Com";

        }

        //Enter记录
        else if (typeChar.equals("Enter")){
            String id;
            if (event.getNewFragment().toString().contains("}")){
                insert="\n \n }";
                id=getIDs(eventStartLine,eventStartLine+2);

            }else {
                insert="\n";
                id=getIDs(eventStartLine,eventStartLine+1);

            }
//            insert=getChangeText(isSelected,selectedText,change,insert);
            ArrayList<String> list = getChangeText(isSelected,selectedText,change,insert);
            typeRecoder.recode(editor,list,typeChar,id,getArrayList.getActionOperate());
            clear();
        }
        //删除事件记录
        else if (typeChar.equals("Del")||typeChar.equals("Backspace")||typeChar.equals("Cut")){

            change[0]=delete;
            String id;
            if (isSelected){
                id=changeInfo.selectedIds;
            }else {
                id=getID(eventStartLine);
            }
//            insert=getChangeText(isSelected,selectedText,change,"");
            ArrayList<String> list = getChangeText(isSelected,selectedText,change,"");
            typeRecoder.recode(editor,list,typeChar,id,getArrayList.getActionOperate());
            clear();
//            //若是删除行
//            if (lineCount>document.getLineCount()){
//                StringBuilder delSb2=new StringBuilder();
//                String newText=getLineText(eventStartLine);
//                String oleText=getArrayList.currentLineGetLineInfo(documentName,eventStartLine).LineText;
//                String change=oleText.replace(newText,"");
//                delSb2.append(change);
//                delSb2.append("\n");
//                delSb.deleteCharAt(delSb.lastIndexOf("\n"));
//                delSb2.append(delSb.toString());
//                String IDs=getIDs(eventStartLine);
//                typeRecoder.recode(editor,delSb2.toString(),typeChar,IDs);
//            }
//            //
//            else {
//                String newText=getLineText(eventStartLine);
//                String oleText=getArrayList.currentLineGetLineInfo(documentName,eventStartLine).LineText;
//                String change=oleText.replace(newText,"");
//                typeRecoder.recode(editor,change,typeChar);
//            }
//            changeInfo.typeChar="";
        }
        //注释事件记录
        else if (typeChar.equals("Comment")) {
            int id=eventStartLine;
            LineInfo lineInfo=lineInfoArrayList.get(arrayList.indexOf(eventStartLine));

            if (event.getNewLength()==0){
                change[0]="//";
//                insert=getChangeText(false,"",change,"");
                ArrayList<String> list = getChangeText(false,"",change,"");
                typeRecoder.recode(editor,list,typeChar,String.valueOf(id),getArrayList.getActionOperate());
                //
                new LineRecode().Recode(fileName,"Modify",id,lineInfo.bornLine,lineInfo.currentLine,lineInfo.LineText,lineInfo.LineText.replaceFirst("//", ""),new Date(),fileTemp,new ArrayList<Integer>(){{add(id);}},arrayList,getArrayList.getActionOperate());
                lineInfo.ModifyLine(lineInfo.LineText.replaceFirst("//", ""));
            }else if (event.getOldLength()==0){
//                insert=getChangeText(false,"",change,"//");
                ArrayList<String> list = getChangeText(false,"",change,"//");
                typeRecoder.recode(editor,list,typeChar,String.valueOf(id),getArrayList.getActionOperate());
                //
                new LineRecode().Recode(fileName,"Modify",id,lineInfo.bornLine,lineInfo.currentLine,lineInfo.LineText,"//"+lineInfo.LineText,new Date(),fileTemp,new ArrayList<Integer>(){{add(id);}},arrayList,getArrayList.getActionOperate());
                lineInfo.ModifyLine("//"+lineInfo.LineText);
            }
            change= new String[]{"",""};
            changeInfo.isSelected=false;
        }
        //其他事件
        else if (typeChar.equals("Others")){
            ArrayList<String> list = new ArrayList<>();
            list.add("");
            list.add("");
            typeRecoder.recode(editor,list,"Others","",getArrayList.getActionOperate());
            clear();
        }
        //其他事件记录
//        else if (typeChar.equals("Others")) {
//            completionStartLine = eventStartLine;
//            completionLineCount = lineCount;
//            delSb.append(event.getOldFragment().toString());
//
//            if (lineCount != document.getLineCount()) {
//                if (lineCount > document.getLineCount()) {
//                    StringBuilder delSb2 = new StringBuilder();
//                    String newText = getLineText(eventStartLine);
//                    String oleText = getArrayList.currentLineGetLineInfo(documentName, eventStartLine).LineText;
//                    String change = oleText.replace(newText, "");
//                    delSb2.append(change);
//                    delSb2.append("\n");
//                    delSb2.append(delSb.toString());
//                    String IDs = getIDs(eventStartLine);
//                    typeRecoder.recode(editor, delSb2.toString(), typeChar, IDs);
//                } else {
//                    String IDs = getIDs(eventStartLine);
//                    typeRecoder.recode(editor, event.getNewFragment().toString(), typeChar, IDs);
//                }
//            } else {
//                typeRecoder.recode(editor, event.getNewFragment().toString(), typeChar);
//
//            }
//        }
    }

    private void clear(){
        change= new String[]{"",""};
        changeInfo.isSelected=false;
        changeInfo.typeChar="";
    }

    private ArrayList<String> getChangeText(Boolean isSelected,String selectedText,String[] change,String insert){
        if (isSelected){
            change[0]=selectedText;
        }
        change[1]=insert;
//        insert=StringUtil.join(change,"--");
        ArrayList<String> list=new ArrayList<>();
        list.add(change[0]);
        list.add(change[1]);
        return list;
    }

    private String getIDs(int completionStartLine,int completionEndLine){
        ArrayList list=new ArrayList();
        for (int i = completionStartLine; i <= completionEndLine ; i++) {
            list.add(arrayList.indexOf(i));
        }
        return list.toString();
    }

    private String getID(int eventLine){
        int id=arrayList.indexOf(eventLine);
        return String.valueOf(id);
    }


    private String getCliboardText(){
        Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipT = sysc.getContents(null);
        String text="";
        if (clipT != null) {
            // 检查内容是否是文本类型
            if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    text=  (String) clipT.getTransferData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return text;
    }
    //默认初始化
    private void init() {
        isInit = 1;
        if (lineCount>0) {
            addStartLine = -1;
            updateAddArray(lineCount);
            recodeAddEvent(lineCount);
        }
        new MyNotification("已启动记录");
    }

    //恢复数组
    private void readFile() throws IOException {
        addStartLine = -1;
        String resultPath=getArrayList.getFileTemp4(documentName);
        File file=new File(resultPath);
        BufferedReader reader=null;
        String info;
        Map<Integer,Integer> id2Line=new HashMap<>();
        Map<Integer,Integer> id2BornLine=new HashMap<>();
        Map<Integer,String> id2Text=new HashMap<>();
        Map<Integer,String> id2LineFlow=new HashMap<>();
        Map<Integer,String> id2BornTime=new HashMap<>();
        int count=0;
        try{
            reader=new BufferedReader(new FileReader(file));
            //读取json
            while ((info=reader.readLine())!=null){
                JsonParser jsonParser=new JsonParser();
                JsonElement jsonElement=jsonParser.parse(info);
                JsonObject jsonObject=jsonElement.getAsJsonObject();
                id2Line.put(jsonObject.get("ID").getAsInt(),jsonObject.get("CurrentLine").getAsInt());
                id2BornLine.put(jsonObject.get("ID").getAsInt(),jsonObject.get("BornLine").getAsInt());
                id2Text.put(jsonObject.get("ID").getAsInt(),jsonObject.get("Text").getAsString());
                id2BornTime.put(jsonObject.get("ID").getAsInt(),jsonObject.get("BornTime").getAsString());
                id2LineFlow.put(jsonObject.get("ID").getAsInt(),jsonObject.get("LineFlow").getAsString());
                count++;
            }
            reader.close();
        }finally {
            if (reader!=null){
                reader.close();
            }
        }
        int i=0;
        while (count>0){
            int line=id2Line.get(i);
            arrayList.add(i,line);
            //恢复LineFLow
            ObjectMapper mapper=new ObjectMapper();
            List<Pair<Integer,Integer>> lineFlow=mapper.readValue(id2LineFlow.get(i),List.class);
            LineInfo lineInfo=new LineInfo(i,id2BornLine.get(i),id2Text.get(i),id2BornTime.get(i));
            lineInfo.lineFlow=lineFlow;
            lineInfoArrayList.add(i,lineInfo);
            i++;
            count--;
        }
        new MyNotification("读取成功");

    }

}