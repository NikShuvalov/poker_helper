package sample;

import java.sql.*;

public class SQLiteHelper {
    private static Connection mConnection;

    private static final String DATABASE_NAME = "PokerHelper.db";

    private static final String USERNAME = "username";
    private static final String USER_LIST_TABLE_NAME = "user_list";
//    private static final String CREATE_DATABASE_EXE = ""


    public ResultSet displayUser(){
        try {
            if(mConnection == null){
                getConnection();
            }
            Statement statement = mConnection.createStatement();
            return statement.executeQuery("SELECT " + USERNAME + " FROM " + USER_LIST_TABLE_NAME);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }


    private void getConnection() throws SQLException {
        String url = "jdbc:sqlite:"+System.getProperty("user.dir") + DATABASE_NAME;
        try {
            Class.forName("org.sqlite.jdbc4");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        mConnection = DriverManager.getConnection(url);
        initialise();
    }

    private void initialise() throws SQLException {
        ResultSet dataBaseCatalogue = mConnection.getMetaData().getCatalogs();
        if(!dataBaseCatalogue.first()) {
            Statement statement = mConnection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT")
        } 
    }
}
