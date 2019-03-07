package record;

import com.google.gson.JsonObject;
import com.intellij.openapi.util.text.StringUtil;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class LineRecode {
    public Timestamp timestamp;
    //标记行记录
    public void Recode(String fileName, String event, int id, int bornLine, int currentLine, String text, String newText,Date date, String fileTemp, ArrayList influLines,ArrayList arrayList,int actionOperate){
        timestamp=new Timestamp(System.currentTimeMillis());
        String[] change={"",""};
        change[0]=text;
        change[1]=newText;
        ArrayList<String> list=new ArrayList<>();
        list.add(change[0]);
        list.add(change[1]);
        ObjectMapper mapper = new ObjectMapper();
        text = list.toString();
        try {
            text=mapper.writeValueAsString(list);
        } catch (Exception e) {
//            e.printStackTrace();
        } catch (Error e){
//            e.printStackTrace();
        }

        String influence = influLines.toString();
        try {
            influence = mapper.writeValueAsString(influLines);
        } catch (Exception e) {
//            e.printStackTrace();
        } catch (Error e){
//            e.printStackTrace();
        }

        JsonObject json=new JsonObject();
        json.addProperty("Name",fileName);
        json.addProperty("Event",event);
        json.addProperty("ID",id);
        json.addProperty("BornLine",bornLine);
        json.addProperty("CurrentLine",currentLine);
        json.addProperty("Influence",influence);
        json.addProperty("Change",text);
        json.addProperty("Text",newText);
        json.addProperty("LineList",arrayList.toString());
        json.addProperty("actionOperate",actionOperate);
        json.addProperty("Time",timestamp.toString());
        try {
            new SaveFile(fileTemp,json.toString()).saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e){
            e.printStackTrace();
        }
    }
    public void readRecode(String fileName, String event, int id, int bornLine, int currentLine, String text, Date date, String fileTemp,ArrayList arrayList,int actionOperate){
        timestamp=new Timestamp(System.currentTimeMillis());
        JsonObject json=new JsonObject();
        json.addProperty("Name",fileName);
        json.addProperty("Event",event);
        json.addProperty("ID",id);
        json.addProperty("BornLine",bornLine);
        json.addProperty("CurrentLine",currentLine);
        json.addProperty("Influence","[]");
        json.addProperty("Change",text);
        json.addProperty("Text",text);
        json.addProperty("LineList",arrayList.toString());
        json.addProperty("actionOperate",actionOperate);
        json.addProperty("Time",timestamp.toString());
        try {
            new SaveFile(fileTemp,json.toString()).saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e){
            e.printStackTrace();
        }
    }
}
