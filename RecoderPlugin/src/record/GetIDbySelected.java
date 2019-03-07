package record;

import com.intellij.openapi.editor.Document;

import java.util.ArrayList;

public class GetIDbySelected {
    Document document;
    documentListener documentListener;

    public GetIDbySelected(Document document) {
        this.document=document;
        this.documentListener=new getArrayList().getDocumentListener(document.toString());
    }

    public String getId(int startOffset, int endOffset){
        int startLine=document.getLineNumber(startOffset);
        int endLine=document.getLineNumber(endOffset);
        ArrayList lines=new ArrayList();
        if (startLine==endLine){
            int id = documentListener.arrayList.indexOf(startLine) < 0? 0: documentListener.arrayList.indexOf(startLine);
            lines.add(id);
        }else {
            for (int i = startLine; i <=endLine ; i++) {
                int id = documentListener.arrayList.indexOf(i) < 0? 0: documentListener.arrayList.indexOf(i);
                lines.add(id);
            }
        }
        return lines.toString();
    }
}
