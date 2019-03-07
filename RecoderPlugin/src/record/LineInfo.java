package record;

import com.intellij.openapi.util.Pair;

import java.util.*;

public class LineInfo {
    public int lineId;
    public int bornLine;
    public String bornTime;
    public String LineText;
    public String TypeLineText;
    public int currentLine;
    public List<Pair<Integer,Integer>> lineFlow=new LinkedList<>();

    public LineInfo(int lineId, int bornLine, String lineText,int operate){
        this.lineId=lineId;
        this.bornLine=bornLine;
        this.LineText = lineText;
        this.TypeLineText = lineText;
        this.currentLine=bornLine;
        this.bornTime=new Date().toString();
        this.lineFlow.add(new Pair<>(bornLine,operate));
    }

    public LineInfo(int lineId, int bornLine, String lineText,String bornTime){
        this.lineId=lineId;
        this.bornLine=bornLine;
        this.LineText = lineText;
        this.TypeLineText=lineText;
        this.currentLine=bornLine;
        this.bornTime=bornTime;
    }


    //行修改
    public void ModifyLine(String text){
        this.LineText =text;
        this.TypeLineText=text;
    }

    //行删除
    public void DeleteLine(){
        this.currentLine=-1;
    }

    //更新行号
    public void updateLine(int currentLine,int operate){
        this.currentLine=currentLine;
        this.lineFlow.add(new Pair<>(currentLine,operate));
    }


}
