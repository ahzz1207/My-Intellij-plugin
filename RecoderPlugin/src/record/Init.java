package record;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.io.IOException;

public class Init {
    private getArrayList getArrayList;
    private Project project;
   // private Editor editor;
    private static String filePath1;
    private static String filePath2;
    private static String filePath3;
    private static String filePath4;
    private static String filePath4Map;
    private RawActionHandlers rawActionHandlers = new RawActionHandlers();

    //插件初始化
    public Init(String filePaths[], Project project, boolean flag){
        this.project=project;
        filePath1=filePaths[0];//statement
        filePath2=filePaths[1];//char
        filePath3=filePaths[2];//flag
        filePath4=filePaths[3];//result dir
        filePath4Map=filePaths[4];//resultMap
        setFilepath();
        startListen(flag);
    }
//    public Init(String filePaths[], Project project,Editor editor, boolean flag){
//        this.project=project;
//        this.editor=editor;
//        filePath1=filePaths[0];//statement
//        filePath2=filePaths[1];//char
//        filePath3=filePaths[2];//flag
//        filePath4=filePaths[3];//result dir
//        filePath4Map=filePaths[4];//resultMap
//        setFilepath();
//        startListen(flag);
//    }

    //设置文件存储路径
    public void setFilepath(){
        getArrayList=new getArrayList();
        getArrayList.setFileTemp(filePath1);
        getArrayList.setFileTemp2(filePath2);
        getArrayList.setFileTemp3(filePath3);
        getArrayList.setFileTemp4cd(filePath4);
        getArrayList.setFileTemp4map(filePath4Map);
    }

    //加载Editor事件监听, flag: 判断是否读文件
    private void startListen(boolean flag) {
        final EditorActionManager actionManager = EditorActionManager.getInstance();
        addTypeHandler(actionManager);
        addActionListener();
        addAwtEventListener(flag);
    }

    //加载TypeActions事件监听
    private void addTypeHandler(EditorActionManager actionManager){
        final TypedAction typedAction = actionManager.getTypedAction();
        final TypedActionHandler myhandler=typedAction.getHandler();
        typedAction.setupHandler(new TypeListener(myhandler));
        rawActionHandlers.setTypeHandler(myhandler);
    }

//    //加载Enter事件监听
//    private  void addEnterHandler(EditorActionManager actionManager){
//        final EditorActionHandler enterHandler=actionManager.getActionHandler("EditorEnter");
//        final EditorActionHandler myEnterHandler=new MyEnterHandler(enterHandler);
//        actionManager.setActionHandler("EditorEnter",myEnterHandler);
//        rawActionHandlers.setEnterHandler(enterHandler);
//    }

//    //加载Backspace事件监听
//    private void addBackspaceHandler(EditorActionManager actionManager){
//        final EditorActionHandler backspaceHandler=actionManager.getActionHandler("EditorBackSpace");
//        final EditorActionHandler myBackspaceHandler=new MyBackspaceHandler(backspaceHandler);
//        actionManager.setActionHandler("EditorBackSpace",myBackspaceHandler);
//        rawActionHandlers.setBackSpaceHandler(backspaceHandler);
//    }

//    //加载Delete事件监听
//    private void addDeleteHandler(EditorActionManager actionManager){
//        final EditorActionHandler deleteHandler=actionManager.getActionHandler("EditorDelete");
//        final EditorActionHandler myDeleteHandler=new MyDeleteHandler(deleteHandler);
//        actionManager.setActionHandler("EditorDelete",myDeleteHandler);
//        rawActionHandlers.setDelHandler(deleteHandler);
//    }

    //加载Paste事件监听
//    private void addPasteHandler(EditorActionManager actionManager){
//        final EditorActionHandler pasteHandler=actionManager.getActionHandler("EditorPaste");
//        final EditorActionHandler myPasteHandler=new MyPasteHandler(pasteHandler);
//        actionManager.setActionHandler("EditorPaste",myPasteHandler);
//        rawActionHandlers.setPasteHandler(pasteHandler);
//    }

    //加载Actions事件监听
    private void addActionListener(){
        ActionManager actionManager=ActionManager.getInstance();
        AnActionListener anActionListener=new MyActionListener();
        actionManager.addAnActionListener(anActionListener);
        rawActionHandlers.setAnActionListener(anActionListener);
    }

    private void addAwtEventListener(boolean flag){
        long eventMask = AWTEvent.MOUSE_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK | AWTEvent.WINDOW_STATE_EVENT_MASK;
        AWTEventListener awtEventListener=new MyAWTEventListener(project,flag);
        Toolkit.getDefaultToolkit().addAWTEventListener(awtEventListener, eventMask);
        rawActionHandlers.setAwtEventListener(awtEventListener);
    }

    //加载Psi事件监听
//    private void addPsiEventListener(){
//        PsiFile psiFile=
//        PsiManager psiManager=psiFile.getManager();
//        psiManager.addPsiTreeChangeListener(new MyPsiChangeListener());
//    }


}
