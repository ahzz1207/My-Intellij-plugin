package record;
import java.io.*;

public class SaveFile {
    public String fileTemp;
    public String input;

    public SaveFile(String fileTemp,String input){
        this.fileTemp=fileTemp;
        this.input=input;
    }

    //尾部添加写入
    public boolean saveFile() throws IOException {
        Boolean bool=false;
        String fileIn=input;
        String temp="";

        FileInputStream fileInputStream=null;
        InputStreamReader inputStreamReader=null;
        BufferedReader bufferedReader=null;
        FileOutputStream fileOutputStream=null;
        PrintWriter printWriter=null;
        try{

            File file =new File(fileTemp);
            fileInputStream=new FileInputStream(file);
            inputStreamReader=new InputStreamReader(fileInputStream);
            bufferedReader=new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();

            for (int i=0;(temp=bufferedReader.readLine())!=null;i++){
                buffer.append(temp);
                buffer=buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(fileIn);

            fileOutputStream = new FileOutputStream(file);
            printWriter = new PrintWriter(fileOutputStream);
            printWriter.write(buffer.toString().toCharArray());
            printWriter.flush();
            bool=true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (printWriter!=null){
                printWriter.close();
            }
            if (fileOutputStream!=null){
                fileOutputStream.close();
            }
            if (inputStreamReader!=null){
                inputStreamReader.close();
            }
            if (bufferedReader!=null){
                bufferedReader.close();
            }
            if (fileInputStream!=null){
                fileInputStream.close();
            }
        }
        return bool;
    }

    //覆盖式写入
    public void overSave() throws IOException {
        FileWriter fileWriter=new FileWriter(fileTemp,false);
        fileWriter.write(input);
        fileWriter.flush();
        fileWriter.close();
    }
}
