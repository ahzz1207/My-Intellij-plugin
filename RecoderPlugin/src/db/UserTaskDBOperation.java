package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by chenchi on 18/5/24.
 */
public class UserTaskDBOperation {
    private static String driver;
    private static String url;
    private static String user;
    private static String password;
    private static String table;
    private Connection conn;
    private PreparedStatement preparedStatement;

    /*
    * the method of initializing parameters connected to DB
    */
    private void initializeDBConnectionParams() {
            driver = "com.mysql.jdbc.Driver";
            url = "jdbc:mysql://localhost:3306/task";
            user = "root";
            password = "fdse";
            table = "user_task";
    }

    /*
     * the constructed method
    */
    public UserTaskDBOperation() {
        if (driver == null) {
            initializeDBConnectionParams();
        }
    }

    /*
     *  connecting to DB
    */
    public void initializeConnection() {
//        while (true) {
//            try {
//                Class.forName(driver);
//                conn = DriverManager.getConnection(url,
//                        user, password);
//                break;
//            }
//            catch (SQLException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void finalizeConnection(){
        finalizeResources(conn,preparedStatement,null);
    }
    /*
     * the method of finalize resources such as connection,preparedStatement and result
     * object
     */
    private void finalizeResources(Connection connection,
                                   PreparedStatement stat, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /*
 * the method of reading task info from DB according to username and id
 */
    public List<String> getTaskInfoFromDB(String username, String id) {
        List<String> result = new ArrayList<>();
        initializeConnection();
        String sql = "select * from " + table + " where username = ? and id = ?";
        ResultSet rs = null;
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1,username);
//            preparedStatement.setString(2,id);
//            rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                result.add(rs.getString("title"));
//                result.add(rs.getString("state"));
//                result.add(rs.getString("description"));
//            }
//            rs.close();
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            finalizeResources(conn, preparedStatement, rs);
//        }
        return result;
    }


    /**
     * load user's tasks list
     * @param username
     * @return
     */
    public List<List<String>> getAllTaskInfoFromDB(String username) {
        initializeConnection();
        String sql = "select id, title, state from " + table + " where username = ?";
        ResultSet rs = null;
        List<String> id = new ArrayList<>();
        List<String> title = new ArrayList<>();
        List<String> state = new ArrayList<>();
        id.add("1");
        title.add("1");
        state.add("1");
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1,username);
//            rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                id.add(rs.getString("id"));
//                title.add(rs.getString("title"));
//                state.add(rs.getString("state"));
//            }
//            rs.close();
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            finalizeResources(conn, preparedStatement, rs);
//        }
        List<List<String>> result = new ArrayList<>();
        result.add(id);
        result.add(title);
        result.add(state);
        return result;
    }

    /**
     * load user's task ids list
     * @param username
     * @return
     */
    public List<String> getAllTaskIDFromDB(String username) {
        initializeConnection();
        String sql = "select id from " + table + " where username = ?";
        ResultSet rs = null;
        List<String> id = new ArrayList<>();
        id.add("1");
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1,username);
//            rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                id.add(rs.getString("id"));
//            }
//            rs.close();
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            finalizeResources(conn, preparedStatement, rs);
//        }
        return id;
    }

    /**
     * read user's finished or unfinished tasks info from DB
     * @param type
     * @return
     */
    public List<List<String>> getFinishedOrUnfinishedTaskInfoFromDB(String username, String type) {
        initializeConnection();
        String sql = "select id, title, state from " + table + " where username = ? and state = ?";
        ResultSet rs = null;
        List<String> id = new ArrayList<>();
        List<String> title = new ArrayList<>();
        List<String> state = new ArrayList<>();
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1,username);
//            preparedStatement.setString(2,type);
//            rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                id.add(rs.getString("id"));
//                title.add(rs.getString("title"));
//                state.add(rs.getString("state"));
//            }
//            rs.close();
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            finalizeResources(conn, preparedStatement, rs);
//        }
        List<List<String>> result = new ArrayList<>();
        result.add(id);
        result.add(title);
        result.add(state);
        return result;
    }

    /**
     * get the count of tasks of user from DB
     * @param username
     * @return
     */
    public int getAllTaskCountFromDB(String username) {
        initializeConnection();
        int count = 0;
//        String sql = "select count(*) from " + table + " where username = ?";
//        ResultSet rs = null;
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1,username);
//            rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                count = Integer.parseInt(rs.getString("count(*)"));
//                break;
//            }
//            rs.close();
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            finalizeResources(conn, preparedStatement, rs);
//        }
        return 1;
       // return count;
    }

    /**
     * get the count of finished or unfinished tasks of user from DB
     * @param username
     * @param type
     * @return
     */
    public int getFinishedOrUnfinishedTaskCountFromDB(String username, String type) {
        initializeConnection();
        int count = 0;
//        String sql = "select count(*) from " + table + " where username = ? and state = ?";
//        ResultSet rs = null;
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1,username);
//            preparedStatement.setString(2,type);
//            rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                count = Integer.parseInt(rs.getString("count(*)"));
//                break;
//            }
//            rs.close();
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            finalizeResources(conn, preparedStatement, rs);
//        }
        return count;
    }
}
