//package db;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by chenchi on 18/6/7.
// */
//public class LoginDBOperation {
//    private static String driver;
//    private static String url;
//    private static String user;
//    private static String password;
//    private static String table;
//    private Connection conn;
//    private PreparedStatement preparedStatement;
//
//    private void initializeDBConnectionParams() {
//        driver = "com.mysql.jdbc.Driver";
//        url = "jdbc:mysql://localhost:3306/user";
//        user = "root";
//        password = "fdse";
//        table = "user";
//    }
//
//    public LoginDBOperation() {
//        if (driver == null) {
//            initializeDBConnectionParams();
//        }
//    }
//
//    public void initializeConnection() {
//        while (true) {
//            try {
//                Class.forName(driver);
//                conn = DriverManager.getConnection(url,
//                        user, password);
//                break;
//            }
//            catch (SQLException e) {
//               // e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//               // e.printStackTrace();
//            }
//        }
//    }
//
//    private void finalizeResources(Connection connection,
//                                   PreparedStatement stat, ResultSet rs) {
//        if (rs != null) {
//            try {
//                rs.close();
//            } catch (SQLException e) {
//               // e.printStackTrace();
//            }
//        }
//        if (stat != null) {
//            try {
//                stat.close();
//            } catch (SQLException e) {
//                //e.printStackTrace();
//            }
//        }
//        if (connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                //e.printStackTrace();
//            }
//        }
//    }
//
//    public List<String> getALLUserNamesFromDB(){
//        initializeConnection();
//        List<String> usernames = new ArrayList<>();
//        String sql = "select username from " + table;
//        ResultSet rs = null;
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                usernames.add(rs.getString("username"));
//            }
//            rs.close();
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            //e.printStackTrace();
//            usernames = null;
//        } finally {
//            finalizeResources(conn, preparedStatement, rs);
//        }
//        return usernames;
//    }
//
//    public String getPasswordFromDB(String username) {
//        initializeConnection();
//        String password = null;
//        String sql = "select password from " + table + " where username = ?";
//        ResultSet rs = null;
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1,username);
//            rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                password = rs.getString("password");
//                break;
//            }
//            rs.close();
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            //e.printStackTrace();
//        } finally {
//            finalizeResources(conn, preparedStatement, rs);
//        }
//        return password;
//    }
//
//    public String getEmailFromDB(String username) {
//        initializeConnection();
//        String email = null;
//        String sql = "select email from " + table + " where username = ?";
//        ResultSet rs = null;
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1,username);
//            rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                email = rs.getString("email");
//                break;
//            }
//            rs.close();
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            //e.printStackTrace();
//        } finally {
//            finalizeResources(conn, preparedStatement, rs);
//        }
//        return email;
//    }
//
//    public boolean insertUserInDB(String username, String password, String email){
//        initializeConnection();
//        boolean flag = false;
//        String sql = "insert into " + table + " values (?,?,?)";
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1,username);
//            preparedStatement.setString(2,password);
//            preparedStatement.setString(3,email);
//            preparedStatement.executeUpdate();
//            preparedStatement.close();
//            conn.close();
//            flag = true;
//        } catch (SQLException e) {
//            //e.printStackTrace();
//        } finally {
//            finalizeResources(conn, preparedStatement, null);
//        }
//        return flag;
//    }
//}
