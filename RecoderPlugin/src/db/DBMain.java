package db;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * Created by chenchi on 18/5/24.
 */
//public class DBMain {
//    public static void main(String[] args){
//        try {
//            Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
//            add.setAccessible(true);
//            URLClassLoader classloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
//            URL url = new File("/Users/lingxiaoxia/Desktop/TaskManagerPlugin/libs/mysql-connector-java-5.1.6-bin.jar").toURI().toURL();
//            add.invoke(classloader, new Object[] { url });
//            DBOperation dbOperation = new DBOperation();
//            List<List<String>> initData = dbOperation.getAllTaskInfoFromDB();
//            int amount = initData.get(0).size();
//            for (int i = 0; i < 3; i++) {
//                System.out.println(initData.get(i).size());
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//}
