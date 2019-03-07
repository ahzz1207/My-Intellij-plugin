package record;
import com.google.gson.JsonObject;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LanguageIndentStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class TypeImportRecoder {

    public TypeImportRecoder(String c, int line,int id,int bornLine ,String name) {
        JsonObject json = makeAImportjson(line,c,id,bornLine,name);
        String fileTemp=new getArrayList().getFileTemp2();
        SaveFile save = new SaveFile(fileTemp, json.toString());
        try {
            save.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        } catch(Error e){
            e.printStackTrace();
        }
    }

    public JsonObject makeAImportjson(int line,String c,int id,int bornLine,String name){
        getArrayList getArrayList=new getArrayList();
        ArrayList list=new ArrayList();
        list.add("");
        list.add(c);
        c=list.toString();
        Date date=new Date();
        JsonObject json=new JsonObject();
        json.addProperty("Name",name);
        json.addProperty("Event","AutoImport");
        json.addProperty("Change",c);
        json.addProperty("Text",c);
        json.addProperty("ID",id);
        json.addProperty("BornLine",bornLine);
        json.addProperty("CurrentLine",line);
        json.addProperty("Column",0);
        json.addProperty("ActionOperate",getArrayList.getActionOperate());
        json.addProperty("Time",date.toString());
        return json;
    }
}
