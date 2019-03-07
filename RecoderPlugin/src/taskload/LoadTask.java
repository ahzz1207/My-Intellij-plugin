package taskload;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import constants.Constants;
import org.json.JSONObject;
import service.TaskManagerSetting;

public class LoadTask {

    private String globalPath;
    private TaskManagerSetting setting = TaskManagerSetting.getInstance();

    public LoadTask() {
        this.globalPath = setting.getPD() + File.separator + "taskload" + File.separator;
    }

    public List<String> getTaskInfo(String id) {
        List<String> result = new ArrayList<>();
        List<String> data = getData(globalPath + "task"+File.separator+"task" + id + ".txt");
        result.add(data.get(1));
        result.add(data.get(2));
        result.add(data.get(3));
        return result;
    }

    public List<List<String>> getAllTaskInfo() {
        List<String> id = new ArrayList<>();
        List<String> title = new ArrayList<>();
        List<String> state = new ArrayList<>();
        for (int i = 0; i < Constants.count; i++) {
            List<String> data = getData(globalPath + "task"+File.separator+"task" + (i + 1) + ".txt");
            id.add(data.get(0));
            title.add(data.get(1));
            state.add(data.get(3));
        }
        List<List<String>> result = new ArrayList<>();
        result.add(id);
        result.add(title);
        result.add(state);
        return result;
    }

    public List<String> getAllTaskID() {
        List<String> id = new ArrayList<>();
        for (int i = 0; i < Constants.count; i++) {
            List<String> data = getData(globalPath + "task"+File.separator+"task" + (i + 1) + ".txt");
            id.add(data.get(0));
        }
        return id;
    }

    public List<List<String>> getFinishedOrUnfinishedTaskInfo(String type) {
        List<String> finishedID = getFinishedID(globalPath + "task"+File.separator+"finish.txt");
        List<String> id = new ArrayList<>();
        List<String> title = new ArrayList<>();
        List<String> state = new ArrayList<>();
        for (int i = 0; i < Constants.count; i++) {
            List<String> data = getData(globalPath + "task"+File.separator+"task" + (i + 1) + ".txt");
            if (type.equals(Constants.finishedState) && finishedID.contains(data.get(0))) {
                id.add(data.get(0));
                title.add(data.get(1));
                state.add(data.get(3));
            } else if (type.equals(Constants.unfinishedState) && !finishedID.contains(data.get(0))) {
                id.add(data.get(0));
                title.add(data.get(1));
                state.add(data.get(3));
            }

        }
        List<List<String>> result = new ArrayList<>();
        result.add(id);
        result.add(title);
        result.add(state);
        return result;
    }

    public int getAllTaskCount() {
        return Constants.count;
    }

    public int getFinishedOrUnfinishedTaskCount(String type) {
        List<String> finishedID = getFinishedID(globalPath + "task"+File.separator+"finish.txt");
        if (type.equals(Constants.finishedState)) {
            return finishedID.size();
        } else {
            return (Constants.count - finishedID.size());
        }
    }

    public List<String> getData(String path) {
        List<String> rs = new ArrayList<>();
        List<String> finishedID = getFinishedID(globalPath + "task"+File.separator+"finish.txt");
        try {
            FileInputStream inputStream = new FileInputStream(new File(path));
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                try {
                    JSONObject jsonObj = new JSONObject(scanner.nextLine());
                    rs.add(jsonObj.getString("id"));
                    rs.add(jsonObj.getString("title"));
                    rs.add(jsonObj.getString("description"));
                    if (finishedID.contains(jsonObj.getString("id"))) {
                        rs.add(Constants.finishedState);
                    } else {
                        rs.add(Constants.unfinishedState);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (Error e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return rs;
    }

    public List<String> getFinishedID(String path) {
        List<String> rs = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream(new File(path));
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                try {
                    rs.add(scanner.nextLine());
                } catch (Exception e) {
                } catch (Error e) {
                }
            }
        } catch (Exception e) {
        } catch (Error e) {
        }
        return rs;
    }

    public void updateData(String id) {
        String path = globalPath + "task"+File.separator+"finish.txt";
        try {
            FileWriter fileWriter = new FileWriter(path,true);
            fileWriter.write(id + "\r\n");
            fileWriter.close();
        }catch(Exception e){

        }catch(Error e){

        }
    }
}
